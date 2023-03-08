package cc.mrbird.febs.asset.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.asset.entity.AssetBPeibei;
import cc.mrbird.febs.asset.dao.AssetBPeibeiMapper;
import cc.mrbird.febs.asset.service.IAssetBPeibeiService;
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
@Service("IAssetBPeibeiService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AssetBPeibeiServiceImpl extends ServiceImpl<AssetBPeibeiMapper, AssetBPeibei> implements IAssetBPeibeiService {


@Override
public IPage<AssetBPeibei> findAssetBPeibeis(QueryRequest request, AssetBPeibei assetBPeibei){
        try{
        LambdaQueryWrapper<AssetBPeibei> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(AssetBPeibei::getIsDeletemark, 1);//1是未删 0是已删

        if (assetBPeibei.getState()!=null) {
        queryWrapper.eq(AssetBPeibei::getState, assetBPeibei.getState());
        }

        if(StringUtils.isNotEmpty(assetBPeibei.getDeptId())){
                queryWrapper.eq(AssetBPeibei::getDeptId, assetBPeibei.getDeptId());
        }
        Page<AssetBPeibei> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<AssetBPeibei> findAssetBPeibeiList (QueryRequest request, AssetBPeibei assetBPeibei){
        try{
        Page<AssetBPeibei> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findAssetBPeibei(page,assetBPeibei);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createAssetBPeibei(AssetBPeibei assetBPeibei){
                assetBPeibei.setId(UUID.randomUUID().toString());
        assetBPeibei.setCreateTime(new Date());
        assetBPeibei.setIsDeletemark(1);
        this.save(assetBPeibei);
        }

@Override
@Transactional
public void updateAssetBPeibei(AssetBPeibei assetBPeibei){
        assetBPeibei.setModifyTime(new Date());
        this.baseMapper.updateAssetBPeibei(assetBPeibei);
        }

@Override
@Transactional
public void deleteAssetBPeibeis(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }