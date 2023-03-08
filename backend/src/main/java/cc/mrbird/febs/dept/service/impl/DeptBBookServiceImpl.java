package cc.mrbird.febs.dept.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dept.entity.DeptBBook;
import cc.mrbird.febs.dept.dao.DeptBBookMapper;
import cc.mrbird.febs.dept.entity.ViewDicType;
import cc.mrbird.febs.dept.service.IDeptBBookService;
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
@Service("IDeptBBookService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptBBookServiceImpl extends ServiceImpl<DeptBBookMapper, DeptBBook> implements IDeptBBookService {


@Override
public IPage<DeptBBook> findDeptBBooks(QueryRequest request, DeptBBook deptBBook){
        try{
        LambdaQueryWrapper<DeptBBook> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DeptBBook::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(deptBBook.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DeptBBook::getUserAccount, deptBBook.getUserAccount()).or()
        .like(DeptBBook::getUserAccountName, deptBBook.getUserAccount()));

        }
        if (deptBBook.getState()!=null) {
        queryWrapper.eq(DeptBBook::getState, deptBBook.getState());
        }


        Page<DeptBBook> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DeptBBook> findDeptBBookList (QueryRequest request, DeptBBook deptBBook){
        try{
        Page<DeptBBook> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDeptBBook(page,deptBBook);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDeptBBook(DeptBBook deptBBook){
                deptBBook.setId(UUID.randomUUID().toString());
        deptBBook.setCreateTime(new Date());
        deptBBook.setIsDeletemark(1);
        this.save(deptBBook);
        }

@Override
@Transactional
public void updateDeptBBook(DeptBBook deptBBook){
        deptBBook.setModifyTime(new Date());
        this.baseMapper.updateDeptBBook(deptBBook);
        }

@Override
@Transactional
public void deleteDeptBBooks(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
        @Override
        @Transactional
        public      List<ViewDicType> findBook(String account, List<String> accountList){
           return  this.baseMapper.findBook(account,accountList);
        }
        }