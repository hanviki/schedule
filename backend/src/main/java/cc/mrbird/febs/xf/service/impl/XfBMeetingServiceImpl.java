package cc.mrbird.febs.xf.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xf.entity.XfBMeeting;
import cc.mrbird.febs.xf.dao.XfBMeetingMapper;
import cc.mrbird.febs.xf.service.IXfBMeetingService;
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
@Service("IXfBMeetingService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XfBMeetingServiceImpl extends ServiceImpl<XfBMeetingMapper, XfBMeeting> implements IXfBMeetingService {


@Override
public IPage<XfBMeeting> findXfBMeetings(QueryRequest request, XfBMeeting xfBMeeting){
        try{
        LambdaQueryWrapper<XfBMeeting> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XfBMeeting::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(xfBMeeting.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(XfBMeeting::getUserAccount, xfBMeeting.getUserAccount()).or()
        .like(XfBMeeting::getUserAccountName, xfBMeeting.getUserAccount()));

        }
        if (xfBMeeting.getState()!=null) {
        queryWrapper.eq(XfBMeeting::getState, xfBMeeting.getState());
        }

                                if (StringUtils.isNotBlank(xfBMeeting.getDeptNew())) {
                                queryWrapper.like(XfBMeeting::getDeptNew, xfBMeeting.getDeptNew());
                                }

        Page<XfBMeeting> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XfBMeeting> findXfBMeetingList (QueryRequest request, XfBMeeting xfBMeeting){
        try{
        Page<XfBMeeting> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXfBMeeting(page,xfBMeeting);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXfBMeeting(XfBMeeting xfBMeeting){
                xfBMeeting.setCreateTime(new Date());
        xfBMeeting.setIsDeletemark(1);
        this.save(xfBMeeting);
        }

@Override
@Transactional
public void updateXfBMeeting(XfBMeeting xfBMeeting){
        xfBMeeting.setModifyTime(new Date());
        this.baseMapper.updateXfBMeeting(xfBMeeting);
        }

@Override
@Transactional
public void deleteXfBMeetings(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }