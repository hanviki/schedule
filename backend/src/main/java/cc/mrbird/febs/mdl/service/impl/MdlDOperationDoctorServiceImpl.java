package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlDOperationDoctor;
import cc.mrbird.febs.mdl.dao.MdlDOperationDoctorMapper;
import cc.mrbird.febs.mdl.service.IMdlDOperationDoctorService;
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
 * 操作分级目录及资质授权 服务实现类
 * </p>
 *
 * @author viki
 * @since 2023-03-16
 */
@Slf4j
@Service("IMdlDOperationDoctorService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlDOperationDoctorServiceImpl extends ServiceImpl<MdlDOperationDoctorMapper, MdlDOperationDoctor> implements IMdlDOperationDoctorService {


@Override
public IPage<MdlDOperationDoctor> findMdlDOperationDoctors(QueryRequest request, MdlDOperationDoctor mdlDOperationDoctor){
        try{
        LambdaQueryWrapper<MdlDOperationDoctor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlDOperationDoctor::getIsDeletemark, 1);//1是未删 0是已删

        if (mdlDOperationDoctor.getState()!=null) {
        queryWrapper.eq(MdlDOperationDoctor::getState, mdlDOperationDoctor.getState());
        }

                                if (StringUtils.isNotBlank(mdlDOperationDoctor.getDeptNew())) {
                                queryWrapper.like(MdlDOperationDoctor::getDeptNew, mdlDOperationDoctor.getDeptNew());
                                }
                                if (StringUtils.isNotBlank(mdlDOperationDoctor.getCode())) {
                                queryWrapper.like(MdlDOperationDoctor::getCode, mdlDOperationDoctor.getCode());
                                }
                                if (StringUtils.isNotBlank(mdlDOperationDoctor.getName())) {
                                queryWrapper.like(MdlDOperationDoctor::getName, mdlDOperationDoctor.getName());
                                }
                                if (StringUtils.isNotBlank(mdlDOperationDoctor.getDocto())) {
                                queryWrapper.like(MdlDOperationDoctor::getDocto, mdlDOperationDoctor.getDocto());
                                }

        Page<MdlDOperationDoctor> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlDOperationDoctor> findMdlDOperationDoctorList (QueryRequest request, MdlDOperationDoctor mdlDOperationDoctor){
        try{
        Page<MdlDOperationDoctor> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlDOperationDoctor(page,mdlDOperationDoctor);
        }catch(Exception e){
        log.error("获取操作分级目录及资质授权失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlDOperationDoctor(MdlDOperationDoctor mdlDOperationDoctor){
                mdlDOperationDoctor.setCreateTime(new Date());
        mdlDOperationDoctor.setIsDeletemark(1);
        this.save(mdlDOperationDoctor);
        }

@Override
@Transactional
public void updateMdlDOperationDoctor(MdlDOperationDoctor mdlDOperationDoctor){
        mdlDOperationDoctor.setModifyTime(new Date());
        this.baseMapper.updateMdlDOperationDoctor(mdlDOperationDoctor);
        }

@Override
@Transactional
public void deleteMdlDOperationDoctors(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }