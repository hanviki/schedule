package cc.mrbird.febs.fs.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.fs.entity.FsBHealth;
import cc.mrbird.febs.fs.dao.FsBHealthMapper;
import cc.mrbird.febs.fs.service.IFsBHealthService;
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
 * @since 2022-07-13
 */
@Slf4j
@Service("IFsBHealthService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FsBHealthServiceImpl extends ServiceImpl<FsBHealthMapper, FsBHealth> implements IFsBHealthService {


@Override
public IPage<FsBHealth> findFsBHealths(QueryRequest request, FsBHealth fsBHealth){
        try{
        LambdaQueryWrapper<FsBHealth> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(FsBHealth::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(fsBHealth.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(FsBHealth::getUserAccount, fsBHealth.getUserAccount()).or()
        .like(FsBHealth::getUserAccountName, fsBHealth.getUserAccount()));

        }
        if (fsBHealth.getState()!=null) {
        queryWrapper.eq(FsBHealth::getState, fsBHealth.getState());
        }


        Page<FsBHealth> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<FsBHealth> findFsBHealthList (QueryRequest request, FsBHealth fsBHealth){
        try{
        Page<FsBHealth> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findFsBHealth(page,fsBHealth);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createFsBHealth(FsBHealth fsBHealth){
                fsBHealth.setId(UUID.randomUUID().toString());
        fsBHealth.setCreateTime(new Date());
        fsBHealth.setIsDeletemark(1);
        this.save(fsBHealth);
        }

@Override
@Transactional
public void updateFsBHealth(FsBHealth fsBHealth){
        fsBHealth.setModifyTime(new Date());
        this.baseMapper.updateFsBHealth(fsBHealth);
        }

@Override
@Transactional
public void deleteFsBHealths(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        @Override
        @Transactional
        public      List<FsBHealth> findHByAccounts( List<String> accountList){
         return  this.baseMapper.findHByAccounts(accountList);
        }
        }