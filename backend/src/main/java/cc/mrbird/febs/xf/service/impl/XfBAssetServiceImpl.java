package cc.mrbird.febs.xf.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xf.entity.XfBAsset;
import cc.mrbird.febs.xf.dao.XfBAssetMapper;
import cc.mrbird.febs.xf.service.IXfBAssetService;
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
 * @since 2022-11-01
 */
@Slf4j
@Service("IXfBAssetService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XfBAssetServiceImpl extends ServiceImpl<XfBAssetMapper, XfBAsset> implements IXfBAssetService {


@Override
public IPage<XfBAsset> findXfBAssets(QueryRequest request, XfBAsset xfBAsset){
        try{
        LambdaQueryWrapper<XfBAsset> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XfBAsset::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(xfBAsset.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(XfBAsset::getUserAccount, xfBAsset.getUserAccount()).or()
        .like(XfBAsset::getUserAccountName, xfBAsset.getUserAccount()));

        }
        if (xfBAsset.getState()!=null) {
        queryWrapper.eq(XfBAsset::getState, xfBAsset.getState());
        }

                                if (StringUtils.isNotBlank(xfBAsset.getDeptNew())) {
                                queryWrapper.like(XfBAsset::getDeptNew, xfBAsset.getDeptNew());
                                }

        Page<XfBAsset> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XfBAsset> findXfBAssetList (QueryRequest request, XfBAsset xfBAsset){
        try{
        Page<XfBAsset> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXfBAsset(page,xfBAsset);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXfBAsset(XfBAsset xfBAsset){
                xfBAsset.setCreateTime(new Date());
        xfBAsset.setIsDeletemark(1);
        this.save(xfBAsset);
        }

@Override
@Transactional
public void updateXfBAsset(XfBAsset xfBAsset){
        xfBAsset.setModifyTime(new Date());
        this.baseMapper.updateXfBAsset(xfBAsset);
        }

@Override
@Transactional
public void deleteXfBAssets(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }