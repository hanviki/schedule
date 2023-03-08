package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlLBaseinfo;
import cc.mrbird.febs.mdl.dao.MdlLBaseinfoMapper;
import cc.mrbird.febs.mdl.service.IMdlLBaseinfoService;
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
 * @since 2022-11-29
 */
@Slf4j
@Service("IMdlLBaseinfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlLBaseinfoServiceImpl extends ServiceImpl<MdlLBaseinfoMapper, MdlLBaseinfo> implements IMdlLBaseinfoService {


@Override
public IPage<MdlLBaseinfo> findMdlLBaseinfos(QueryRequest request, MdlLBaseinfo mdlLBaseinfo){
        try{
        LambdaQueryWrapper<MdlLBaseinfo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlLBaseinfo::getIsDeletemark, 1);//1是未删 0是已删

        if (mdlLBaseinfo.getState()!=null) {
        queryWrapper.eq(MdlLBaseinfo::getState, mdlLBaseinfo.getState());
        }
                if (StringUtils.isNotBlank(mdlLBaseinfo.getLabName())) {
                        queryWrapper.like(MdlLBaseinfo::getLabName, mdlLBaseinfo.getLabName());
                }
                                if (StringUtils.isNotBlank(mdlLBaseinfo.getDeptNew())) {
                                queryWrapper.like(MdlLBaseinfo::getDeptNew, mdlLBaseinfo.getDeptNew());
                                }

        Page<MdlLBaseinfo> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlLBaseinfo> findMdlLBaseinfoList (QueryRequest request, MdlLBaseinfo mdlLBaseinfo){
        try{
        Page<MdlLBaseinfo> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlLBaseinfo(page,mdlLBaseinfo);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlLBaseinfo(MdlLBaseinfo mdlLBaseinfo){
                mdlLBaseinfo.setId(UUID.randomUUID().toString());
        mdlLBaseinfo.setCreateTime(new Date());
        mdlLBaseinfo.setIsDeletemark(1);
        this.save(mdlLBaseinfo);
        }

@Override
@Transactional
public void updateMdlLBaseinfo(MdlLBaseinfo mdlLBaseinfo){
        mdlLBaseinfo.setModifyTime(new Date());
        this.baseMapper.updateMdlLBaseinfo(mdlLBaseinfo);
        }

@Override
@Transactional
public void deleteMdlLBaseinfos(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }