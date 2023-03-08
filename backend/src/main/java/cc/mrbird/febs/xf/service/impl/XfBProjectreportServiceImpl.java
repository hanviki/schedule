package cc.mrbird.febs.xf.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xf.entity.XfBProjectreport;
import cc.mrbird.febs.xf.dao.XfBProjectreportMapper;
import cc.mrbird.febs.xf.service.IXfBProjectreportService;
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
@Service("IXfBProjectreportService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XfBProjectreportServiceImpl extends ServiceImpl<XfBProjectreportMapper, XfBProjectreport> implements IXfBProjectreportService {


@Override
public IPage<XfBProjectreport> findXfBProjectreports(QueryRequest request, XfBProjectreport xfBProjectreport){
        try{
        LambdaQueryWrapper<XfBProjectreport> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XfBProjectreport::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(xfBProjectreport.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(XfBProjectreport::getUserAccount, xfBProjectreport.getUserAccount()).or()
        .like(XfBProjectreport::getUserAccountName, xfBProjectreport.getUserAccount()));

        }
        if (xfBProjectreport.getState()!=null) {
        queryWrapper.eq(XfBProjectreport::getState, xfBProjectreport.getState());
        }


        Page<XfBProjectreport> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XfBProjectreport> findXfBProjectreportList (QueryRequest request, XfBProjectreport xfBProjectreport){
        try{
        Page<XfBProjectreport> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXfBProjectreport(page,xfBProjectreport);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXfBProjectreport(XfBProjectreport xfBProjectreport){
                xfBProjectreport.setCreateTime(new Date());
        xfBProjectreport.setIsDeletemark(1);
        this.save(xfBProjectreport);
        }

@Override
@Transactional
public void updateXfBProjectreport(XfBProjectreport xfBProjectreport){
        xfBProjectreport.setModifyTime(new Date());
        this.baseMapper.updateXfBProjectreport(xfBProjectreport);
        }

@Override
@Transactional
public void deleteXfBProjectreports(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }