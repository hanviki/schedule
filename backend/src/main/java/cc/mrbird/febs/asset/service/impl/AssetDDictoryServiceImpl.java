package cc.mrbird.febs.asset.service.impl;

import cc.mrbird.febs.common.domain.FebsConstant;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.asset.entity.AssetDDictory;
import cc.mrbird.febs.asset.dao.AssetDDictoryMapper;
import cc.mrbird.febs.asset.service.IAssetDDictoryService;
import cc.mrbird.febs.common.utils.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.LocalDate;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2023-02-06
 */
@Slf4j
@Service("IAssetDDictoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AssetDDictoryServiceImpl extends ServiceImpl<AssetDDictoryMapper, AssetDDictory> implements IAssetDDictoryService {

@Override
public IPage<AssetDDictory> findAssetDDictoryList (QueryRequest request, AssetDDictory assetDDictory){
        try{
        Page<AssetDDictory> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findAssetDDictory(page,assetDDictory);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createAssetDDictory(AssetDDictory assetDDictory){
    if(StringUtils.isEmpty(assetDDictory.getId())) {
        assetDDictory.setId(UUID.randomUUID().toString());
    }


        assetDDictory.setCreateTime(new Date());

        this.save(assetDDictory);
        }

@Override
@Transactional
public void updateAssetDDictory(AssetDDictory assetDDictory){
        assetDDictory.setModifyTime(new Date());
        this.baseMapper.updateAssetDDictory(assetDDictory);
        }

@Override
@Transactional
public void deleteAssetDDictorys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }


    @Override
    public Map<String, Object> findAssetDDictorys(QueryRequest request, AssetDDictory assetDDictory) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<AssetDDictory> depts = findAssetDDictorys(assetDDictory, request);
            List<Tree<AssetDDictory>> trees = new ArrayList<>();
            buildTrees(trees, depts);
            Tree<AssetDDictory> deptTree = TreeUtil.build(trees);

            result.put("rows", deptTree);
            result.put("total", depts.size());
        } catch (Exception e) {
            log.error("获取部门列表失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }
    @Override
    public List<AssetDDictory> findAssetDDictorys(AssetDDictory dept, QueryRequest request) {
        QueryWrapper<AssetDDictory> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(dept.getParentId())) {
            // queryWrapper.lambda().eq(Dept::getDeptId,dept.getDeptId());
            queryWrapper.lambda().eq(AssetDDictory::getParentId,dept.getParentId());
        }
        if (dept.getId()!=null) {
            // queryWrapper.lambda().eq(Dept::getDeptId,dept.getDeptId());
            queryWrapper.lambda().and(p->p.eq(AssetDDictory::getId,dept.getId()).or().eq(AssetDDictory::getParentId,dept.getId()));
        }
        if (StringUtils.isNotBlank(dept.getName()))
            queryWrapper.lambda().eq(AssetDDictory::getName, dept.getName());

        if (dept.getTypeIndex()!=null)
            queryWrapper.lambda().eq(AssetDDictory::getTypeIndex, dept.getTypeIndex());

        SortUtil.handleWrapperSort(request, queryWrapper, "orderNum", FebsConstant.ORDER_ASC, true);
        return this.baseMapper.selectList(queryWrapper);
    }
    private void buildTrees(List<Tree<AssetDDictory>> trees, List<AssetDDictory> depts) {
        depts.forEach(dept -> {
            Tree<AssetDDictory> tree = new Tree<>();
            tree.setId(dept.getId().toString());
            tree.setKey(tree.getId());
            tree.setParentId(dept.getParentId().toString());
            tree.setText(dept.getName());
            tree.setCreateTime(dept.getCreateTime());
            tree.setType(dept.getTypeIndex().toString());
            tree.setModifyTime(dept.getModifyTime());
            tree.setOrder(dept.getOrderNum());
            tree.setTitle(tree.getText());
            tree.setValue(tree.getId());
            trees.add(tree);
        });
    }
        }