package cc.mrbird.febs.xf.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xf.entity.XfBErrordept;
import cc.mrbird.febs.xf.dao.XfBErrordeptMapper;
import cc.mrbird.febs.xf.service.IXfBErrordeptService;
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
@Service("IXfBErrordeptService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XfBErrordeptServiceImpl extends ServiceImpl<XfBErrordeptMapper, XfBErrordept> implements IXfBErrordeptService {


@Override
public IPage<XfBErrordept> findXfBErrordepts(QueryRequest request, XfBErrordept xfBErrordept){
        try{
        LambdaQueryWrapper<XfBErrordept> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XfBErrordept::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(xfBErrordept.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(XfBErrordept::getUserAccount, xfBErrordept.getUserAccount()).or()
        .like(XfBErrordept::getUserAccountName, xfBErrordept.getUserAccount()));

        }
        if (xfBErrordept.getState()!=null) {
        queryWrapper.eq(XfBErrordept::getState, xfBErrordept.getState());
        }

                                if (StringUtils.isNotBlank(xfBErrordept.getDeptNew())) {
                                queryWrapper.like(XfBErrordept::getDeptNew, xfBErrordept.getDeptNew());
                                }

        Page<XfBErrordept> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XfBErrordept> findXfBErrordeptList (QueryRequest request, XfBErrordept xfBErrordept){
        try{
        Page<XfBErrordept> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXfBErrordept(page,xfBErrordept);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXfBErrordept(XfBErrordept xfBErrordept){
                xfBErrordept.setCreateTime(new Date());
        xfBErrordept.setIsDeletemark(1);
        this.save(xfBErrordept);
        }

@Override
@Transactional
public void updateXfBErrordept(XfBErrordept xfBErrordept){
        xfBErrordept.setModifyTime(new Date());
        this.baseMapper.updateXfBErrordept(xfBErrordept);
        }

@Override
@Transactional
public void deleteXfBErrordepts(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }