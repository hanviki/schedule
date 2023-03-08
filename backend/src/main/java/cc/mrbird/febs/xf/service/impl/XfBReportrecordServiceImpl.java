package cc.mrbird.febs.xf.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xf.entity.XfBReportrecord;
import cc.mrbird.febs.xf.dao.XfBReportrecordMapper;
import cc.mrbird.febs.xf.service.IXfBReportrecordService;
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
@Service("IXfBReportrecordService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XfBReportrecordServiceImpl extends ServiceImpl<XfBReportrecordMapper, XfBReportrecord> implements IXfBReportrecordService {


@Override
public IPage<XfBReportrecord> findXfBReportrecords(QueryRequest request, XfBReportrecord xfBReportrecord){
        try{
        LambdaQueryWrapper<XfBReportrecord> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XfBReportrecord::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(xfBReportrecord.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(XfBReportrecord::getUserAccount, xfBReportrecord.getUserAccount()).or()
        .like(XfBReportrecord::getUserAccountName, xfBReportrecord.getUserAccount()));

        }
        if (xfBReportrecord.getState()!=null) {
        queryWrapper.eq(XfBReportrecord::getState, xfBReportrecord.getState());
        }

                                if (StringUtils.isNotBlank(xfBReportrecord.getDeptNew())) {
                                queryWrapper.like(XfBReportrecord::getDeptNew, xfBReportrecord.getDeptNew());
                                }

        Page<XfBReportrecord> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XfBReportrecord> findXfBReportrecordList (QueryRequest request, XfBReportrecord xfBReportrecord){
        try{
        Page<XfBReportrecord> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXfBReportrecord(page,xfBReportrecord);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXfBReportrecord(XfBReportrecord xfBReportrecord){
                xfBReportrecord.setCreateTime(new Date());
        xfBReportrecord.setIsDeletemark(1);
        this.save(xfBReportrecord);
        }

@Override
@Transactional
public void updateXfBReportrecord(XfBReportrecord xfBReportrecord){
        xfBReportrecord.setModifyTime(new Date());
        this.baseMapper.updateXfBReportrecord(xfBReportrecord);
        }

@Override
@Transactional
public void deleteXfBReportrecords(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }