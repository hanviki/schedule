package cc.mrbird.febs.dept.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dept.entity.DeptBUser;
import cc.mrbird.febs.dept.dao.DeptBUserMapper;
import cc.mrbird.febs.dept.entity.DicType;
import cc.mrbird.febs.dept.entity.ViewDept;
import cc.mrbird.febs.dept.service.IDeptBUserService;
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
@Service("IDeptBUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptBUserServiceImpl extends ServiceImpl<DeptBUserMapper, DeptBUser> implements IDeptBUserService {


@Override
public IPage<DeptBUser> findDeptBUsers(QueryRequest request, DeptBUser deptBUser){
        try{
        LambdaQueryWrapper<DeptBUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DeptBUser::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(deptBUser.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DeptBUser::getUserAccount, deptBUser.getUserAccount()).or()
        .like(DeptBUser::getUserAccountName, deptBUser.getUserAccount()));

        }
        if (deptBUser.getState()!=null) {
        queryWrapper.eq(DeptBUser::getState, deptBUser.getState());
        }
                if (StringUtils.isNotEmpty(deptBUser.getDeptId())) {
                        queryWrapper.eq(DeptBUser::getDeptId, deptBUser.getDeptId());
                }


        Page<DeptBUser> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<ViewDept> findDeptBUserList (QueryRequest request, ViewDept deptBUser){
        try{
        Page<DeptBUser> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
            return  this.baseMapper.getUser2(page,deptBUser);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDeptBUser(DeptBUser deptBUser){
                deptBUser.setId(UUID.randomUUID().toString());
        deptBUser.setCreateTime(new Date());
        deptBUser.setIsDeletemark(1);
        this.save(deptBUser);
        }

@Override
@Transactional
public void updateDeptBUser(DeptBUser deptBUser){
        deptBUser.setModifyTime(new Date());
        this.baseMapper.updateDeptBUser(deptBUser);
        }

@Override
@Transactional
public void deleteDeptBUsers(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

    @Override
    @Transactional
    public  List<DicType>  getDicType(String account){
    return  this.baseMapper.getDicType(account);
    }
        }