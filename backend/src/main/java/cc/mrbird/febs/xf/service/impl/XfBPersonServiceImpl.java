package cc.mrbird.febs.xf.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xf.entity.XfBPerson;
import cc.mrbird.febs.xf.dao.XfBPersonMapper;
import cc.mrbird.febs.xf.service.IXfBPersonService;
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
@Service("IXfBPersonService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XfBPersonServiceImpl extends ServiceImpl<XfBPersonMapper, XfBPerson> implements IXfBPersonService {


@Override
public IPage<XfBPerson> findXfBPersons(QueryRequest request, XfBPerson xfBPerson){
        try{
        LambdaQueryWrapper<XfBPerson> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XfBPerson::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(xfBPerson.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(XfBPerson::getUserAccount, xfBPerson.getUserAccount()).or()
        .like(XfBPerson::getUserAccountName, xfBPerson.getUserAccount()));

        }
        if (xfBPerson.getState()!=null) {
        queryWrapper.eq(XfBPerson::getState, xfBPerson.getState());
        }

                                if (StringUtils.isNotBlank(xfBPerson.getDeptNew())) {
                                queryWrapper.like(XfBPerson::getDeptNew, xfBPerson.getDeptNew());
                                }

        Page<XfBPerson> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XfBPerson> findXfBPersonList (QueryRequest request, XfBPerson xfBPerson){
        try{
        Page<XfBPerson> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXfBPerson(page,xfBPerson);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXfBPerson(XfBPerson xfBPerson){
                xfBPerson.setCreateTime(new Date());
        xfBPerson.setIsDeletemark(1);
        this.save(xfBPerson);
        }

@Override
@Transactional
public void updateXfBPerson(XfBPerson xfBPerson){
        xfBPerson.setModifyTime(new Date());
        this.baseMapper.updateXfBPerson(xfBPerson);
        }

@Override
@Transactional
public void deleteXfBPersons(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }