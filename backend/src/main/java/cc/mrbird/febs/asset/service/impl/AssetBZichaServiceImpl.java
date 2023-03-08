package cc.mrbird.febs.asset.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.asset.entity.AssetBZicha;
import cc.mrbird.febs.asset.dao.AssetBZichaMapper;
import cc.mrbird.febs.asset.service.IAssetBZichaService;
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
@Service("IAssetBZichaService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AssetBZichaServiceImpl extends ServiceImpl<AssetBZichaMapper, AssetBZicha> implements IAssetBZichaService {


@Override
public IPage<AssetBZicha> findAssetBZichas(QueryRequest request, AssetBZicha assetBZicha){
        try{
        LambdaQueryWrapper<AssetBZicha> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(AssetBZicha::getIsDeletemark, 1);//1是未删 0是已删

        if (assetBZicha.getState()!=null) {
        queryWrapper.eq(AssetBZicha::getState, assetBZicha.getState());
        }
                if(assetBZicha.getBaseId()!=null){
                        queryWrapper.eq(AssetBZicha::getBaseId, assetBZicha.getBaseId());
                }


        Page<AssetBZicha> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<AssetBZicha> findAssetBZichaList (QueryRequest request, AssetBZicha assetBZicha){
        try{
        Page<AssetBZicha> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findAssetBZicha(page,assetBZicha);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createAssetBZicha(AssetBZicha assetBZicha){
                assetBZicha.setId(UUID.randomUUID().toString());
        assetBZicha.setCreateTime(new Date());
        assetBZicha.setIsDeletemark(1);
        this.save(assetBZicha);
        }

@Override
@Transactional
public void updateAssetBZicha(AssetBZicha assetBZicha){
        assetBZicha.setModifyTime(new Date());
        this.baseMapper.updateAssetBZicha(assetBZicha);
        }

@Override
@Transactional
public void deleteAssetBZichas(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }