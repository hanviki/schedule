package cc.mrbird.febs.xf.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xf.entity.XfBErrorperson;
import cc.mrbird.febs.xf.dao.XfBErrorpersonMapper;
import cc.mrbird.febs.xf.service.IXfBErrorpersonService;
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
@Service("IXfBErrorpersonService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XfBErrorpersonServiceImpl extends ServiceImpl<XfBErrorpersonMapper, XfBErrorperson> implements IXfBErrorpersonService {


@Override
public IPage<XfBErrorperson> findXfBErrorpersons(QueryRequest request, XfBErrorperson xfBErrorperson){
        try{
        LambdaQueryWrapper<XfBErrorperson> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XfBErrorperson::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(xfBErrorperson.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(XfBErrorperson::getUserAccount, xfBErrorperson.getUserAccount()).or()
        .like(XfBErrorperson::getUserAccountName, xfBErrorperson.getUserAccount()));

        }
        if (xfBErrorperson.getState()!=null) {
        queryWrapper.eq(XfBErrorperson::getState, xfBErrorperson.getState());
        }

                                if (StringUtils.isNotBlank(xfBErrorperson.getDeptNew())) {
                                queryWrapper.like(XfBErrorperson::getDeptNew, xfBErrorperson.getDeptNew());
                                }

        Page<XfBErrorperson> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XfBErrorperson> findXfBErrorpersonList (QueryRequest request, XfBErrorperson xfBErrorperson){
        try{
        Page<XfBErrorperson> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXfBErrorperson(page,xfBErrorperson);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXfBErrorperson(XfBErrorperson xfBErrorperson){
                xfBErrorperson.setCreateTime(new Date());
        xfBErrorperson.setIsDeletemark(1);
        this.save(xfBErrorperson);
        }

@Override
@Transactional
public void updateXfBErrorperson(XfBErrorperson xfBErrorperson){
        xfBErrorperson.setModifyTime(new Date());
        this.baseMapper.updateXfBErrorperson(xfBErrorperson);
        }

@Override
@Transactional
public void deleteXfBErrorpersons(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }