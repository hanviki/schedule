package cc.mrbird.febs.dept.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dept.entity.DeptBBookD;
import cc.mrbird.febs.dept.dao.DeptBBookDMapper;
import cc.mrbird.febs.dept.service.IDeptBBookDService;
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
 * @since 2023-02-28
 */
@Slf4j
@Service("IDeptBBookDService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptBBookDServiceImpl extends ServiceImpl<DeptBBookDMapper, DeptBBookD> implements IDeptBBookDService {


@Override
public IPage<DeptBBookD> findDeptBBookDs(QueryRequest request, DeptBBookD deptBBookD){
        try{
        LambdaQueryWrapper<DeptBBookD> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DeptBBookD::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(deptBBookD.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DeptBBookD::getUserAccount, deptBBookD.getUserAccount()).or()
        .like(DeptBBookD::getUserAccountName, deptBBookD.getUserAccount()));

        }
        if (deptBBookD.getState()!=null) {
        queryWrapper.eq(DeptBBookD::getState, deptBBookD.getState());
        }


        Page<DeptBBookD> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DeptBBookD> findDeptBBookDList (QueryRequest request, DeptBBookD deptBBookD){
        try{
        Page<DeptBBookD> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDeptBBookD(page,deptBBookD);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDeptBBookD(DeptBBookD deptBBookD){
                deptBBookD.setId(UUID.randomUUID().toString());
        deptBBookD.setCreateTime(new Date());
        deptBBookD.setIsDeletemark(1);
        this.save(deptBBookD);
        }

@Override
@Transactional
public void updateDeptBBookD(DeptBBookD deptBBookD){
        deptBBookD.setModifyTime(new Date());
        this.baseMapper.updateDeptBBookD(deptBBookD);
        }

@Override
@Transactional
public void deleteDeptBBookDs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }