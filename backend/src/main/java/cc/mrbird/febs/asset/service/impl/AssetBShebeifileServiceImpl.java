package cc.mrbird.febs.asset.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.asset.entity.AssetBShebeifile;
import cc.mrbird.febs.asset.dao.AssetBShebeifileMapper;
import cc.mrbird.febs.asset.service.IAssetBShebeifileService;
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
@Service("IAssetBShebeifileService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AssetBShebeifileServiceImpl extends ServiceImpl<AssetBShebeifileMapper, AssetBShebeifile> implements IAssetBShebeifileService {


@Override
public IPage<AssetBShebeifile> findAssetBShebeifiles(QueryRequest request, AssetBShebeifile assetBShebeifile){
        try{
        LambdaQueryWrapper<AssetBShebeifile> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(AssetBShebeifile::getIsDeletemark, 1);//1是未删 0是已删

        if (assetBShebeifile.getState()!=null) {
        queryWrapper.eq(AssetBShebeifile::getState, assetBShebeifile.getState());
        }
                if(assetBShebeifile.getBaseId()!=null){
                        queryWrapper.eq(AssetBShebeifile::getBaseId, assetBShebeifile.getBaseId());
                }

        Page<AssetBShebeifile> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<AssetBShebeifile> findAssetBShebeifileList (QueryRequest request, AssetBShebeifile assetBShebeifile){
        try{
        Page<AssetBShebeifile> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findAssetBShebeifile(page,assetBShebeifile);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createAssetBShebeifile(AssetBShebeifile assetBShebeifile){
                assetBShebeifile.setId(UUID.randomUUID().toString());
        assetBShebeifile.setCreateTime(new Date());
        assetBShebeifile.setIsDeletemark(1);
        this.save(assetBShebeifile);
        }

@Override
@Transactional
public void updateAssetBShebeifile(AssetBShebeifile assetBShebeifile){
        assetBShebeifile.setModifyTime(new Date());
        this.baseMapper.updateAssetBShebeifile(assetBShebeifile);
        }

@Override
@Transactional
public void deleteAssetBShebeifiles(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }