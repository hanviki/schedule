package cc.mrbird.febs.fs.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.fs.entity.FsBQuarter;
import cc.mrbird.febs.fs.dao.FsBQuarterMapper;
import cc.mrbird.febs.fs.service.IFsBQuarterService;
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
@Service("IFsBQuarterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FsBQuarterServiceImpl extends ServiceImpl<FsBQuarterMapper, FsBQuarter> implements IFsBQuarterService {


    @Override
    public IPage<FsBQuarter> findFsBQuarters(QueryRequest request, FsBQuarter fsBQuarter) {
        try {
            LambdaQueryWrapper<FsBQuarter> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FsBQuarter::getIsDeletemark, 1);//1是未删 0是已删
//        if (StringUtils.isNotBlank(fsBQuarter.getUserAccount())) {
//        queryWrapper.and(wrap->  wrap.eq(FsBQuarter::getUserAccount, fsBQuarter.getUserAccount()).or()
//        .like(FsBQuarter::getUserAccountName, fsBQuarter.getUserAccount()));
//
//        }
            if (fsBQuarter.getState() != null) {
                queryWrapper.eq(FsBQuarter::getState, fsBQuarter.getState());
            }
            if (fsBQuarter.getNumber() != null) {
                queryWrapper.eq(FsBQuarter::getNumber, fsBQuarter.getNumber());
            }
            if (fsBQuarter.getYear() != null) {
                queryWrapper.eq(FsBQuarter::getYear, fsBQuarter.getYear());
            }

            if (fsBQuarter.getQuarter() != null) {
                queryWrapper.eq(FsBQuarter::getQuarter, fsBQuarter.getQuarter());
            }

            Page<FsBQuarter> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<FsBQuarter> findFsBQuarterList(QueryRequest request, FsBQuarter fsBQuarter) {
        try {
            Page<FsBQuarter> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findFsBQuarter(page, fsBQuarter);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createFsBQuarter(FsBQuarter fsBQuarter) {
        fsBQuarter.setId(UUID.randomUUID().toString());
        fsBQuarter.setCreateTime(new Date());
        fsBQuarter.setIsDeletemark(1);
        this.save(fsBQuarter);
    }

    @Override
    @Transactional
    public void updateFsBQuarter(FsBQuarter fsBQuarter) {
        fsBQuarter.setModifyTime(new Date());
        this.baseMapper.updateFsBQuarter(fsBQuarter);
    }

    @Override
    @Transactional
    public void deleteFsBQuarters(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

}