package cc.mrbird.febs.asset.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.asset.entity.AssetBBaopi;
import cc.mrbird.febs.asset.dao.AssetBBaopiMapper;
import cc.mrbird.febs.asset.service.IAssetBBaopiService;
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
@Service("IAssetBBaopiService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AssetBBaopiServiceImpl extends ServiceImpl<AssetBBaopiMapper, AssetBBaopi> implements IAssetBBaopiService {


@Override
public IPage<AssetBBaopi> findAssetBBaopis(QueryRequest request, AssetBBaopi assetBBaopi){
        try{
        LambdaQueryWrapper<AssetBBaopi> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(AssetBBaopi::getIsDeletemark, 1);//1是未删 0是已删

        if (assetBBaopi.getState()!=null) {
        queryWrapper.eq(AssetBBaopi::getState, assetBBaopi.getState());
        }
                if(assetBBaopi.getBaseId()!=null){
                        queryWrapper.eq(AssetBBaopi::getBaseId, assetBBaopi.getBaseId());
                }

        Page<AssetBBaopi> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<AssetBBaopi> findAssetBBaopiList (QueryRequest request, AssetBBaopi assetBBaopi){
        try{
        Page<AssetBBaopi> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findAssetBBaopi(page,assetBBaopi);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createAssetBBaopi(AssetBBaopi assetBBaopi){
        if(StringUtils.isEmpty(assetBBaopi.getId())) {
                assetBBaopi.setId(UUID.randomUUID().toString());
        }
        assetBBaopi.setCreateTime(new Date());
        assetBBaopi.setIsDeletemark(1);
        this.save(assetBBaopi);
        }

@Override
@Transactional
public void updateAssetBBaopi(AssetBBaopi assetBBaopi){
        assetBBaopi.setModifyTime(new Date());
        this.baseMapper.updateAssetBBaopi(assetBBaopi);
        }

@Override
@Transactional
public void deleteAssetBBaopis(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }