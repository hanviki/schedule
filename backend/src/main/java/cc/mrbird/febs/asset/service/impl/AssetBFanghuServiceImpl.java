package cc.mrbird.febs.asset.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.asset.entity.AssetBFanghu;
import cc.mrbird.febs.asset.dao.AssetBFanghuMapper;
import cc.mrbird.febs.asset.service.IAssetBFanghuService;
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
 * @since 2023-02-06
 */
@Slf4j
@Service("IAssetBFanghuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AssetBFanghuServiceImpl extends ServiceImpl<AssetBFanghuMapper, AssetBFanghu> implements IAssetBFanghuService {


@Override
public IPage<AssetBFanghu> findAssetBFanghus(QueryRequest request, AssetBFanghu assetBFanghu){
        try{
        LambdaQueryWrapper<AssetBFanghu> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(AssetBFanghu::getIsDeletemark, 1);//1是未删 0是已删

        if (assetBFanghu.getState()!=null) {
        queryWrapper.eq(AssetBFanghu::getState, assetBFanghu.getState());
        }
                if(assetBFanghu.getBaseId()!=null){
                        queryWrapper.eq(AssetBFanghu::getBaseId, assetBFanghu.getBaseId());
                }

        Page<AssetBFanghu> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<AssetBFanghu> findAssetBFanghuList (QueryRequest request, AssetBFanghu assetBFanghu){
        try{
        Page<AssetBFanghu> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findAssetBFanghu(page,assetBFanghu);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createAssetBFanghu(AssetBFanghu assetBFanghu){
                assetBFanghu.setId(UUID.randomUUID().toString());
        assetBFanghu.setCreateTime(new Date());
        assetBFanghu.setIsDeletemark(1);
        this.save(assetBFanghu);
        }

@Override
@Transactional
public void updateAssetBFanghu(AssetBFanghu assetBFanghu){
        assetBFanghu.setModifyTime(new Date());
        this.baseMapper.updateAssetBFanghu(assetBFanghu);
        }

@Override
@Transactional
public void deleteAssetBFanghus(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }