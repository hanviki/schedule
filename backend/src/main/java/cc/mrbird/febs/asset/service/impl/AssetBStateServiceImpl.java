package cc.mrbird.febs.asset.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.asset.entity.AssetBState;
import cc.mrbird.febs.asset.dao.AssetBStateMapper;
import cc.mrbird.febs.asset.service.IAssetBStateService;
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
@Service("IAssetBStateService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AssetBStateServiceImpl extends ServiceImpl<AssetBStateMapper, AssetBState> implements IAssetBStateService {


@Override
public IPage<AssetBState> findAssetBStates(QueryRequest request, AssetBState assetBState){
        try{
        LambdaQueryWrapper<AssetBState> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(AssetBState::getIsDeletemark, 1);//1是未删 0是已删

        if (assetBState.getState()!=null) {
        queryWrapper.eq(AssetBState::getState, assetBState.getState());
        }
                if(assetBState.getBaseId()!=null){
                        queryWrapper.eq(AssetBState::getBaseId, assetBState.getBaseId());
                }


        Page<AssetBState> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<AssetBState> findAssetBStateList (QueryRequest request, AssetBState assetBState){
        try{
        Page<AssetBState> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findAssetBState(page,assetBState);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createAssetBState(AssetBState assetBState){
                assetBState.setId(UUID.randomUUID().toString());
        assetBState.setCreateTime(new Date());
        assetBState.setIsDeletemark(1);
        this.save(assetBState);
        }

@Override
@Transactional
public void updateAssetBState(AssetBState assetBState){
        assetBState.setModifyTime(new Date());
        this.baseMapper.updateAssetBState(assetBState);
        }

@Override
@Transactional
public void deleteAssetBStates(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        @Override
        @Transactional
        public    List<String> getAllBaseIdByState(String assetState){
         return  this.baseMapper.getAllBaseIdByState(assetState);
        }
        }