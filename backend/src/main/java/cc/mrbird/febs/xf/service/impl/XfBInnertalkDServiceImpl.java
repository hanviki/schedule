package cc.mrbird.febs.xf.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xf.entity.XfBInnertalkD;
import cc.mrbird.febs.xf.dao.XfBInnertalkDMapper;
import cc.mrbird.febs.xf.service.IXfBInnertalkDService;
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
 * @since 2022-11-22
 */
@Slf4j
@Service("IXfBInnertalkDService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XfBInnertalkDServiceImpl extends ServiceImpl<XfBInnertalkDMapper, XfBInnertalkD> implements IXfBInnertalkDService {


@Override
public IPage<XfBInnertalkD> findXfBInnertalkDs(QueryRequest request, XfBInnertalkD xfBInnertalkD){
        try{
        LambdaQueryWrapper<XfBInnertalkD> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XfBInnertalkD::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(xfBInnertalkD.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(XfBInnertalkD::getUserAccount, xfBInnertalkD.getUserAccount()).or()
        .like(XfBInnertalkD::getUserAccountName, xfBInnertalkD.getUserAccount()));
        }
        if (xfBInnertalkD.getState()!=null) {
        queryWrapper.eq(XfBInnertalkD::getState, xfBInnertalkD.getState());
        }
                if (xfBInnertalkD.getBaseId()!=null ) {
                        queryWrapper.eq(XfBInnertalkD::getBaseId, xfBInnertalkD.getBaseId());
                }
                else{
                        queryWrapper.eq(XfBInnertalkD::getBaseId, "-1");
                }
                                if (StringUtils.isNotBlank(xfBInnertalkD.getCreateTimeFrom()) && StringUtils.isNotBlank(xfBInnertalkD.getCreateTimeTo())) {
                                queryWrapper
                                .ge(XfBInnertalkD::getCreateTime, xfBInnertalkD.getCreateTimeFrom())
                                .le(XfBInnertalkD::getCreateTime, xfBInnertalkD.getCreateTimeTo());
                                }

        Page<XfBInnertalkD> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XfBInnertalkD> findXfBInnertalkDList (QueryRequest request, XfBInnertalkD xfBInnertalkD){
        try{
        Page<XfBInnertalkD> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXfBInnertalkD(page,xfBInnertalkD);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXfBInnertalkD(XfBInnertalkD xfBInnertalkD){
                xfBInnertalkD.setId(UUID.randomUUID().toString());
        xfBInnertalkD.setCreateTime(new Date());
        xfBInnertalkD.setIsDeletemark(1);
        this.save(xfBInnertalkD);
        }

@Override
@Transactional
public void updateXfBInnertalkD(XfBInnertalkD xfBInnertalkD){
        xfBInnertalkD.setModifyTime(new Date());
        this.baseMapper.updateXfBInnertalkD(xfBInnertalkD);
        }

@Override
@Transactional
public void deleteXfBInnertalkDs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }