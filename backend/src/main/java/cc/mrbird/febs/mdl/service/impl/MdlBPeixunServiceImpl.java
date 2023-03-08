package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBPeixun;
import cc.mrbird.febs.mdl.dao.MdlBPeixunMapper;
import cc.mrbird.febs.mdl.service.IMdlBPeixunService;
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
 * @since 2023-01-05
 */
@Slf4j
@Service("IMdlBPeixunService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBPeixunServiceImpl extends ServiceImpl<MdlBPeixunMapper, MdlBPeixun> implements IMdlBPeixunService {


@Override
public IPage<MdlBPeixun> findMdlBPeixuns(QueryRequest request, MdlBPeixun mdlBPeixun){
        try{
        LambdaQueryWrapper<MdlBPeixun> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlBPeixun::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(mdlBPeixun.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(MdlBPeixun::getUserAccount, mdlBPeixun.getUserAccount()).or()
        .like(MdlBPeixun::getUserAccountName, mdlBPeixun.getUserAccount()));

        }
        if (mdlBPeixun.getState()!=null) {
        queryWrapper.eq(MdlBPeixun::getState, mdlBPeixun.getState());
        }


        Page<MdlBPeixun> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlBPeixun> findMdlBPeixunList (QueryRequest request, MdlBPeixun mdlBPeixun){
        try{
        Page<MdlBPeixun> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlBPeixun(page,mdlBPeixun);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlBPeixun(MdlBPeixun mdlBPeixun){
                mdlBPeixun.setCreateTime(new Date());
        mdlBPeixun.setIsDeletemark(1);
        this.save(mdlBPeixun);
        }

@Override
@Transactional
public void updateMdlBPeixun(MdlBPeixun mdlBPeixun){
        mdlBPeixun.setModifyTime(new Date());
        this.baseMapper.updateMdlBPeixun(mdlBPeixun);
        }

@Override
@Transactional
public void deleteMdlBPeixuns(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }