package cc.mrbird.febs.asset.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.asset.entity.AssetBFsjbxx;
import cc.mrbird.febs.asset.dao.AssetBFsjbxxMapper;
import cc.mrbird.febs.asset.service.IAssetBFsjbxxService;
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

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2023-02-08
 */
@Slf4j
@Service("IAssetBFsjbxxService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AssetBFsjbxxServiceImpl extends ServiceImpl<AssetBFsjbxxMapper, AssetBFsjbxx> implements IAssetBFsjbxxService {


@Override
public IPage<AssetBFsjbxx> findAssetBFsjbxxs(QueryRequest request, AssetBFsjbxx assetBFsjbxx){
        try{
        LambdaQueryWrapper<AssetBFsjbxx> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(AssetBFsjbxx::getIsDeletemark, 1);//1是未删 0是已删

        if (assetBFsjbxx.getState()!=null) {
        queryWrapper.eq(AssetBFsjbxx::getState, assetBFsjbxx.getState());
        }
        if(assetBFsjbxx.getBaseId()!=null){
                queryWrapper.eq(AssetBFsjbxx::getBaseId, assetBFsjbxx.getBaseId());
        }


        Page<AssetBFsjbxx> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<AssetBFsjbxx> findAssetBFsjbxxList (QueryRequest request, AssetBFsjbxx assetBFsjbxx){
        try{
        Page<AssetBFsjbxx> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findAssetBFsjbxx(page,assetBFsjbxx);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createAssetBFsjbxx(AssetBFsjbxx assetBFsjbxx){
                assetBFsjbxx.setId(UUID.randomUUID().toString());
        assetBFsjbxx.setCreateTime(new Date());
        assetBFsjbxx.setIsDeletemark(1);
        this.save(assetBFsjbxx);
        }

@Override
@Transactional
public void updateAssetBFsjbxx(AssetBFsjbxx assetBFsjbxx){
        assetBFsjbxx.setModifyTime(new Date());
        this.baseMapper.updateAssetBFsjbxx(assetBFsjbxx);
        }

@Override
@Transactional
public void deleteAssetBFsjbxxs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }