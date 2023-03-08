package cc.mrbird.febs.xf.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xf.entity.XfBArchive;
import cc.mrbird.febs.xf.dao.XfBArchiveMapper;
import cc.mrbird.febs.xf.service.IXfBArchiveService;
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
@Service("IXfBArchiveService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XfBArchiveServiceImpl extends ServiceImpl<XfBArchiveMapper, XfBArchive> implements IXfBArchiveService {


@Override
public IPage<XfBArchive> findXfBArchives(QueryRequest request, XfBArchive xfBArchive){
        try{
        LambdaQueryWrapper<XfBArchive> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XfBArchive::getIsDeletemark, 1);//1是未删 0是已删

        if (xfBArchive.getState()!=null) {
        queryWrapper.eq(XfBArchive::getState, xfBArchive.getState());
        }


        Page<XfBArchive> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XfBArchive> findXfBArchiveList (QueryRequest request, XfBArchive xfBArchive){
        try{
        Page<XfBArchive> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXfBArchive(page,xfBArchive);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXfBArchive(XfBArchive xfBArchive){
                xfBArchive.setCreateTime(new Date());
        xfBArchive.setIsDeletemark(1);
        this.save(xfBArchive);
        }

@Override
@Transactional
public void updateXfBArchive(XfBArchive xfBArchive){
        xfBArchive.setModifyTime(new Date());
        this.baseMapper.updateXfBArchive(xfBArchive);
        }

@Override
@Transactional
public void deleteXfBArchives(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }