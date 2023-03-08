package cc.mrbird.febs.xf.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xf.entity.XfBSocial;
import cc.mrbird.febs.xf.dao.XfBSocialMapper;
import cc.mrbird.febs.xf.service.IXfBSocialService;
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
 * @since 2022-11-09
 */
@Slf4j
@Service("IXfBSocialService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XfBSocialServiceImpl extends ServiceImpl<XfBSocialMapper, XfBSocial> implements IXfBSocialService {


@Override
public IPage<XfBSocial> findXfBSocials(QueryRequest request, XfBSocial xfBSocial){
        try{
        LambdaQueryWrapper<XfBSocial> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XfBSocial::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(xfBSocial.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(XfBSocial::getUserAccount, xfBSocial.getUserAccount()).or()
        .like(XfBSocial::getUserAccountName, xfBSocial.getUserAccount()));

        }
        if (xfBSocial.getState()!=null) {
        queryWrapper.eq(XfBSocial::getState, xfBSocial.getState());
        }

                                if (StringUtils.isNotBlank(xfBSocial.getDeptNew())) {
                                queryWrapper.like(XfBSocial::getDeptNew, xfBSocial.getDeptNew());
                                }

        Page<XfBSocial> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XfBSocial> findXfBSocialList (QueryRequest request, XfBSocial xfBSocial){
        try{
        Page<XfBSocial> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXfBSocial(page,xfBSocial);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXfBSocial(XfBSocial xfBSocial){
                xfBSocial.setCreateTime(new Date());
        xfBSocial.setIsDeletemark(1);
        this.save(xfBSocial);
        }

@Override
@Transactional
public void updateXfBSocial(XfBSocial xfBSocial){
        xfBSocial.setModifyTime(new Date());
        this.baseMapper.updateXfBSocial(xfBSocial);
        }

@Override
@Transactional
public void deleteXfBSocials(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }