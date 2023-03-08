package cc.mrbird.febs.xf.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xf.entity.XfBInnertalk;
import cc.mrbird.febs.xf.dao.XfBInnertalkMapper;
import cc.mrbird.febs.xf.service.IXfBInnertalkService;
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
@Service("IXfBInnertalkService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XfBInnertalkServiceImpl extends ServiceImpl<XfBInnertalkMapper, XfBInnertalk> implements IXfBInnertalkService {


@Override
public IPage<XfBInnertalk> findXfBInnertalks(QueryRequest request, XfBInnertalk xfBInnertalk){
        try{
        LambdaQueryWrapper<XfBInnertalk> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(XfBInnertalk::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(xfBInnertalk.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(XfBInnertalk::getUserAccount, xfBInnertalk.getUserAccount()).or()
        .like(XfBInnertalk::getUserAccountName, xfBInnertalk.getUserAccount()));

        }
        if (xfBInnertalk.getState()!=null) {
        queryWrapper.eq(XfBInnertalk::getState, xfBInnertalk.getState());
        }

                                if (StringUtils.isNotBlank(xfBInnertalk.getDeptNew())) {
                                queryWrapper.like(XfBInnertalk::getDeptNew, xfBInnertalk.getDeptNew());
                                }

        Page<XfBInnertalk> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<XfBInnertalk> findXfBInnertalkList (QueryRequest request, XfBInnertalk xfBInnertalk){
        try{
        Page<XfBInnertalk> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findXfBInnertalk(page,xfBInnertalk);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createXfBInnertalk(XfBInnertalk xfBInnertalk){
                xfBInnertalk.setCreateTime(new Date());
        xfBInnertalk.setIsDeletemark(1);
        this.save(xfBInnertalk);
        }

@Override
@Transactional
public void updateXfBInnertalk(XfBInnertalk xfBInnertalk){
        xfBInnertalk.setModifyTime(new Date());
        this.baseMapper.updateXfBInnertalk(xfBInnertalk);
        }

@Override
@Transactional
public void deleteXfBInnertalks(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
        @Override
        @Transactional
        public void  deleteByBaseId(String id){
                this.baseMapper.DeletByBaseId(id);
        }
        }