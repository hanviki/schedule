package cc.mrbird.febs.xf.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xf.entity.XfBRules;
import cc.mrbird.febs.xf.dao.XfBRulesMapper;
import cc.mrbird.febs.xf.service.IXfBRulesService;
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
@Service("IXfBRulesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XfBRulesServiceImpl extends ServiceImpl<XfBRulesMapper, XfBRules> implements IXfBRulesService {


@Override
public IPage<XfBRules> findXfBRuless(QueryRequest request, XfBRules xfBRules){
        try{
        LambdaQueryWrapper<XfBRules> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XfBRules::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(xfBRules.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(XfBRules::getUserAccount, xfBRules.getUserAccount()).or()
        .like(XfBRules::getUserAccountName, xfBRules.getUserAccount()));

        }
        if (xfBRules.getState()!=null) {
        queryWrapper.eq(XfBRules::getState, xfBRules.getState());
        }

                                if (StringUtils.isNotBlank(xfBRules.getDeptNew())) {
                                queryWrapper.like(XfBRules::getDeptNew, xfBRules.getDeptNew());
                                }

        Page<XfBRules> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XfBRules> findXfBRulesList (QueryRequest request, XfBRules xfBRules){
        try{
        Page<XfBRules> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXfBRules(page,xfBRules);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXfBRules(XfBRules xfBRules){
                xfBRules.setCreateTime(new Date());
        xfBRules.setIsDeletemark(1);
        this.save(xfBRules);
        }

@Override
@Transactional
public void updateXfBRules(XfBRules xfBRules){
        xfBRules.setModifyTime(new Date());
        this.baseMapper.updateXfBRules(xfBRules);
        }

@Override
@Transactional
public void deleteXfBRuless(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }