package cc.mrbird.febs.xf.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xf.entity.XfBTousu;
import cc.mrbird.febs.xf.dao.XfBTousuMapper;
import cc.mrbird.febs.xf.service.IXfBTousuService;
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
@Service("IXfBTousuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XfBTousuServiceImpl extends ServiceImpl<XfBTousuMapper, XfBTousu> implements IXfBTousuService {


@Override
public IPage<XfBTousu> findXfBTousus(QueryRequest request, XfBTousu xfBTousu){
        try{
        LambdaQueryWrapper<XfBTousu> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XfBTousu::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(xfBTousu.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(XfBTousu::getUserAccount, xfBTousu.getUserAccount()).or()
        .like(XfBTousu::getUserAccountName, xfBTousu.getUserAccount()));

        }
        if (xfBTousu.getState()!=null) {
        queryWrapper.eq(XfBTousu::getState, xfBTousu.getState());
        }

                                if (StringUtils.isNotBlank(xfBTousu.getDeptNew())) {
                                queryWrapper.like(XfBTousu::getDeptNew, xfBTousu.getDeptNew());
                                }

        Page<XfBTousu> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XfBTousu> findXfBTousuList (QueryRequest request, XfBTousu xfBTousu){
        try{
        Page<XfBTousu> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXfBTousu(page,xfBTousu);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXfBTousu(XfBTousu xfBTousu){
                xfBTousu.setCreateTime(new Date());
        xfBTousu.setIsDeletemark(1);
        this.save(xfBTousu);
        }

@Override
@Transactional
public void updateXfBTousu(XfBTousu xfBTousu){
        xfBTousu.setModifyTime(new Date());
        this.baseMapper.updateXfBTousu(xfBTousu);
        }

@Override
@Transactional
public void deleteXfBTousus(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }