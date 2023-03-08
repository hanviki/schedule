package cc.mrbird.febs.xf.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xf.entity.XfBUser;
import cc.mrbird.febs.xf.dao.XfBUserMapper;
import cc.mrbird.febs.xf.service.IXfBUserService;
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
@Service("IXfBUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XfBUserServiceImpl extends ServiceImpl<XfBUserMapper, XfBUser> implements IXfBUserService {


@Override
public IPage<XfBUser> findXfBUsers(QueryRequest request, XfBUser xfBUser){
        try{
        LambdaQueryWrapper<XfBUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XfBUser::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(xfBUser.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(XfBUser::getUserAccount, xfBUser.getUserAccount()).or()
        .like(XfBUser::getUserAccountName, xfBUser.getUserAccount()));

        }
        if (xfBUser.getState()!=null) {
        queryWrapper.eq(XfBUser::getState, xfBUser.getState());
        }


        Page<XfBUser> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XfBUser> findXfBUserList (QueryRequest request, XfBUser xfBUser){
        try{
        Page<XfBUser> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXfBUser(page,xfBUser);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXfBUser(XfBUser xfBUser){
                xfBUser.setId(UUID.randomUUID().toString());
        xfBUser.setCreateTime(new Date());
        xfBUser.setIsDeletemark(1);
        this.save(xfBUser);
        }

@Override
@Transactional
public void updateXfBUser(XfBUser xfBUser){
        xfBUser.setModifyTime(new Date());
        this.baseMapper.updateXfBUser(xfBUser);
        }

@Override
@Transactional
public void deleteXfBUsers(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }