package cc.mrbird.febs.xf.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xf.entity.XfBProject;
import cc.mrbird.febs.xf.dao.XfBProjectMapper;
import cc.mrbird.febs.xf.service.IXfBProjectService;
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
@Service("IXfBProjectService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XfBProjectServiceImpl extends ServiceImpl<XfBProjectMapper, XfBProject> implements IXfBProjectService {


@Override
public IPage<XfBProject> findXfBProjects(QueryRequest request, XfBProject xfBProject){
        try{
        LambdaQueryWrapper<XfBProject> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XfBProject::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(xfBProject.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(XfBProject::getUserAccount, xfBProject.getUserAccount()).or()
        .like(XfBProject::getUserAccountName, xfBProject.getUserAccount()));

        }
        if (xfBProject.getState()!=null) {
        queryWrapper.eq(XfBProject::getState, xfBProject.getState());
        }

                                if (StringUtils.isNotBlank(xfBProject.getName())) {
                                queryWrapper.like(XfBProject::getName, xfBProject.getName());
                                }

        Page<XfBProject> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XfBProject> findXfBProjectList (QueryRequest request, XfBProject xfBProject){
        try{
        Page<XfBProject> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXfBProject(page,xfBProject);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXfBProject(XfBProject xfBProject){
                xfBProject.setCreateTime(new Date());
        xfBProject.setIsDeletemark(1);
        this.save(xfBProject);
        }

@Override
@Transactional
public void updateXfBProject(XfBProject xfBProject){
        xfBProject.setModifyTime(new Date());
        this.baseMapper.updateXfBProject(xfBProject);
        }

@Override
@Transactional
public void deleteXfBProjects(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }