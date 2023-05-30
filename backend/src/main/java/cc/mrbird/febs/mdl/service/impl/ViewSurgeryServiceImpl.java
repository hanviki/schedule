package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.ViewSurgery;
import cc.mrbird.febs.mdl.dao.ViewSurgeryMapper;
import cc.mrbird.febs.mdl.service.IViewSurgeryService;
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
 * VIEW 服务实现类
 * </p>
 *
 * @author viki
 * @since 2023-04-20
 */
@Slf4j
@Service("IViewSurgeryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ViewSurgeryServiceImpl extends ServiceImpl<ViewSurgeryMapper, ViewSurgery> implements IViewSurgeryService {


@Override
public IPage<ViewSurgery> findViewSurgerys(QueryRequest request, ViewSurgery viewSurgery){
        try{
        LambdaQueryWrapper<ViewSurgery> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ViewSurgery::getIsDeletemark, 1);//1是未删 0是已删
                if (StringUtils.isNotBlank(viewSurgery.getUserAccount())) {
                        queryWrapper.and(wrap -> wrap.eq(ViewSurgery::getUserAccount, viewSurgery.getUserAccount()).or()
                                .like(ViewSurgery::getUserAccountName, viewSurgery.getUserAccount()));

                }
                if (viewSurgery.getState() != null) {
                        queryWrapper.eq(ViewSurgery::getState, viewSurgery.getState());
                }

                if (StringUtils.isNotBlank(viewSurgery.getDeptName())) {
                        queryWrapper.like(ViewSurgery::getDeptName, viewSurgery.getDeptName());
                }

                if (StringUtils.isNotBlank(viewSurgery.getValidDatePot())) {

                        queryWrapper.apply("valid_date in (" +
                                "SELECT MAX(valid_date) from mdl_b_surgeryinfo b " +
                                " where b.valid_date<='" + viewSurgery.getValidDatePot() + "'  and b.user_account= mdl_b_surgeryinfo.user_account  GROUP BY user_account)");
                }
                if (StringUtils.isNotBlank(viewSurgery.getValidDateFrom())) {
                        queryWrapper
                                .ge(ViewSurgery::getValidDate, viewSurgery.getValidDateFrom());

                }
                if (StringUtils.isNotBlank(viewSurgery.getValidDateTo())) {
                        queryWrapper

                                .le(ViewSurgery::getValidDate, viewSurgery.getValidDateTo());
                }

                Page<ViewSurgery> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<ViewSurgery> findViewSurgeryList (QueryRequest request, ViewSurgery viewSurgery){
        try{
        Page<ViewSurgery> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findViewSurgery(page,viewSurgery);
        }catch(Exception e){
        log.error("获取VIEW失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createViewSurgery(ViewSurgery viewSurgery){
        viewSurgery.setCreateTime(new Date());
        viewSurgery.setIsDeletemark(1);
        this.save(viewSurgery);
        }

@Override
@Transactional
public void updateViewSurgery(ViewSurgery viewSurgery){
        viewSurgery.setModifyTime(new Date());
        this.baseMapper.updateViewSurgery(viewSurgery);
        }

@Override
@Transactional
public void deleteViewSurgerys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }