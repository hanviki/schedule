package cc.mrbird.febs.xf.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xf.entity.XfBTeachrecord;
import cc.mrbird.febs.xf.dao.XfBTeachrecordMapper;
import cc.mrbird.febs.xf.service.IXfBTeachrecordService;
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
@Service("IXfBTeachrecordService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XfBTeachrecordServiceImpl extends ServiceImpl<XfBTeachrecordMapper, XfBTeachrecord> implements IXfBTeachrecordService {


@Override
public IPage<XfBTeachrecord> findXfBTeachrecords(QueryRequest request, XfBTeachrecord xfBTeachrecord){
        try{
        LambdaQueryWrapper<XfBTeachrecord> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XfBTeachrecord::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(xfBTeachrecord.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(XfBTeachrecord::getUserAccount, xfBTeachrecord.getUserAccount()).or()
        .like(XfBTeachrecord::getUserAccountName, xfBTeachrecord.getUserAccount()));

        }
        if (xfBTeachrecord.getState()!=null) {
        queryWrapper.eq(XfBTeachrecord::getState, xfBTeachrecord.getState());
        }

                                if (StringUtils.isNotBlank(xfBTeachrecord.getDeptNew())) {
                                queryWrapper.like(XfBTeachrecord::getDeptNew, xfBTeachrecord.getDeptNew());
                                }

        Page<XfBTeachrecord> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XfBTeachrecord> findXfBTeachrecordList (QueryRequest request, XfBTeachrecord xfBTeachrecord){
        try{
        Page<XfBTeachrecord> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXfBTeachrecord(page,xfBTeachrecord);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXfBTeachrecord(XfBTeachrecord xfBTeachrecord){
                xfBTeachrecord.setCreateTime(new Date());
        xfBTeachrecord.setIsDeletemark(1);
        this.save(xfBTeachrecord);
        }

@Override
@Transactional
public void updateXfBTeachrecord(XfBTeachrecord xfBTeachrecord){
        xfBTeachrecord.setModifyTime(new Date());
        this.baseMapper.updateXfBTeachrecord(xfBTeachrecord);
        }

@Override
@Transactional
public void deleteXfBTeachrecords(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }