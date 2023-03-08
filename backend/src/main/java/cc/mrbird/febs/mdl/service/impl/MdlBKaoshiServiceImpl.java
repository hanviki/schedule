package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlBKaoshi;
import cc.mrbird.febs.mdl.dao.MdlBKaoshiMapper;
import cc.mrbird.febs.mdl.service.IMdlBKaoshiService;
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
@Service("IMdlBKaoshiService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlBKaoshiServiceImpl extends ServiceImpl<MdlBKaoshiMapper, MdlBKaoshi> implements IMdlBKaoshiService {


@Override
public IPage<MdlBKaoshi> findMdlBKaoshis(QueryRequest request, MdlBKaoshi mdlBKaoshi){
        try{
        LambdaQueryWrapper<MdlBKaoshi> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlBKaoshi::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(mdlBKaoshi.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(MdlBKaoshi::getUserAccount, mdlBKaoshi.getUserAccount()).or()
        .like(MdlBKaoshi::getUserAccountName, mdlBKaoshi.getUserAccount()));

        }
        if (mdlBKaoshi.getState()!=null) {
        queryWrapper.eq(MdlBKaoshi::getState, mdlBKaoshi.getState());
        }


        Page<MdlBKaoshi> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<MdlBKaoshi> findMdlBKaoshiList (QueryRequest request, MdlBKaoshi mdlBKaoshi){
        try{
        Page<MdlBKaoshi> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findMdlBKaoshi(page,mdlBKaoshi);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createMdlBKaoshi(MdlBKaoshi mdlBKaoshi){
                mdlBKaoshi.setCreateTime(new Date());
        mdlBKaoshi.setIsDeletemark(1);
        this.save(mdlBKaoshi);
        }

@Override
@Transactional
public void updateMdlBKaoshi(MdlBKaoshi mdlBKaoshi){
        mdlBKaoshi.setModifyTime(new Date());
        this.baseMapper.updateMdlBKaoshi(mdlBKaoshi);
        }

@Override
@Transactional
public void deleteMdlBKaoshis(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }