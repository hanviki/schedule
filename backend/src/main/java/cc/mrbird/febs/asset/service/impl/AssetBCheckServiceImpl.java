package cc.mrbird.febs.asset.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.asset.entity.AssetBCheck;
import cc.mrbird.febs.asset.dao.AssetBCheckMapper;
import cc.mrbird.febs.asset.service.IAssetBCheckService;
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
@Service("IAssetBCheckService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AssetBCheckServiceImpl extends ServiceImpl<AssetBCheckMapper, AssetBCheck> implements IAssetBCheckService {


@Override
public IPage<AssetBCheck> findAssetBChecks(QueryRequest request, AssetBCheck assetBCheck){
        try{
        LambdaQueryWrapper<AssetBCheck> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(AssetBCheck::getIsDeletemark, 1);//1是未删 0是已删

        if (assetBCheck.getState()!=null) {
        queryWrapper.eq(AssetBCheck::getState, assetBCheck.getState());
        }
                if(assetBCheck.getBaseId()!=null){
                        queryWrapper.eq(AssetBCheck::getBaseId, assetBCheck.getBaseId());
                }

        Page<AssetBCheck> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<AssetBCheck> findAssetBCheckList (QueryRequest request, AssetBCheck assetBCheck){
        try{
        Page<AssetBCheck> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findAssetBCheck(page,assetBCheck);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createAssetBCheck(AssetBCheck assetBCheck){
                assetBCheck.setId(UUID.randomUUID().toString());
        assetBCheck.setCreateTime(new Date());
        assetBCheck.setIsDeletemark(1);
        this.save(assetBCheck);
        }

@Override
@Transactional
public void updateAssetBCheck(AssetBCheck assetBCheck){
        assetBCheck.setModifyTime(new Date());
        this.baseMapper.updateAssetBCheck(assetBCheck);
        }

@Override
@Transactional
public void deleteAssetBChecks(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }