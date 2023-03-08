package cc.mrbird.febs.fs.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.fs.entity.FsBQuarter;
import cc.mrbird.febs.fs.entity.FsBQuarterD;
import cc.mrbird.febs.fs.dao.FsBQuarterDMapper;
import cc.mrbird.febs.fs.service.IFsBQuarterDService;
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
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-07-12
 */
@Slf4j
@Service("IFsBQuarterDService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FsBQuarterDServiceImpl extends ServiceImpl<FsBQuarterDMapper, FsBQuarterD> implements IFsBQuarterDService {


    @Override
    public IPage<FsBQuarterD> findFsBQuarterDs(QueryRequest request, FsBQuarterD fsBQuarterD) {
        try {
            LambdaQueryWrapper<FsBQuarterD> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FsBQuarterD::getIsDeletemark, 1);//1是未删 0是已删
//        if (StringUtils.isNotBlank(fsBQuarterD.getUserAccount())) {
//        queryWrapper.and(wrap->  wrap.eq(FsBQuarterD::getUserAccount, fsBQuarterD.getUserAccount()).or()
//        .like(FsBQuarterD::getUserAccountName, fsBQuarterD.getUserAccount()));
//
//        }
            if (fsBQuarterD.getState() != null) {
                queryWrapper.eq(FsBQuarterD::getState, fsBQuarterD.getState());
            }
            if (fsBQuarterD.getNumber() != null) {
                queryWrapper.eq(FsBQuarterD::getNumber, fsBQuarterD.getNumber());
            }
            if (fsBQuarterD.getYear() != null) {
                queryWrapper.eq(FsBQuarterD::getYear, fsBQuarterD.getYear());
            }
            if (fsBQuarterD.getUserNo() != null) {
                queryWrapper.eq(FsBQuarterD::getUserNo, fsBQuarterD.getUserNo());
            }
            if (fsBQuarterD.getQuarter() != null) {
                queryWrapper.eq(FsBQuarterD::getQuarter, fsBQuarterD.getQuarter());
            }

            Page<FsBQuarterD> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<FsBQuarterD> findFsBQuarterDList(QueryRequest request, FsBQuarterD fsBQuarterD) {
        try {
            Page<FsBQuarterD> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findFsBQuarterD(page, fsBQuarterD);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }
    @Override
    public IPage<FsBQuarterD> findReport(QueryRequest request, FsBQuarterD fsBQuarterD) {
        try {
            Page<FsBQuarterD> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findReport(page, fsBQuarterD);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createFsBQuarterD(FsBQuarterD fsBQuarterD) {
        fsBQuarterD.setId(UUID.randomUUID().toString());
        fsBQuarterD.setCreateTime(new Date());
        fsBQuarterD.setIsDeletemark(1);
        this.save(fsBQuarterD);
    }

    @Override
    @Transactional
    public void updateFsBQuarterD(FsBQuarterD fsBQuarterD) {
        fsBQuarterD.setModifyTime(new Date());
        this.baseMapper.updateFsBQuarterD(fsBQuarterD);
    }

    @Override
    @Transactional
    public void deleteFsBQuarterDs(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

}