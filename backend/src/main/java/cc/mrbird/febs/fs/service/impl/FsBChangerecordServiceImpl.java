package cc.mrbird.febs.fs.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.fs.entity.FsBChangerecord;
import cc.mrbird.febs.fs.dao.FsBChangerecordMapper;
import cc.mrbird.febs.fs.service.IFsBChangerecordService;
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
 * @since 2023-01-31
 */
@Slf4j
@Service("IFsBChangerecordService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FsBChangerecordServiceImpl extends ServiceImpl<FsBChangerecordMapper, FsBChangerecord> implements IFsBChangerecordService {


    @Override
    public IPage<FsBChangerecord> findFsBChangerecords(QueryRequest request, FsBChangerecord fsBChangerecord) {
        try {
            LambdaQueryWrapper<FsBChangerecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FsBChangerecord::getIsDeletemark, 1);//1是未删 0是已删
            if (StringUtils.isNotBlank(fsBChangerecord.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(FsBChangerecord::getUserAccount, fsBChangerecord.getUserAccount()).or()
                        .like(FsBChangerecord::getUserAccountName, fsBChangerecord.getUserAccount()));

            }
            if (StringUtils.isNotBlank(fsBChangerecord.getSubmitUser())) {
                queryWrapper.eq(FsBChangerecord::getSubmitUser, fsBChangerecord.getSubmitUser());
            }
            if (StringUtils.isNotBlank(fsBChangerecord.getIsDept())) {
                queryWrapper.eq(FsBChangerecord::getIsDept, fsBChangerecord.getIsDept());
            }
            if (StringUtils.isNotBlank(fsBChangerecord.getIsGwlb())) {
                queryWrapper.eq(FsBChangerecord::getIsGwlb, fsBChangerecord.getIsGwlb());
            }
            if (StringUtils.isNotBlank(fsBChangerecord.getIsNumber())) {
                queryWrapper.eq(FsBChangerecord::getIsNumber, fsBChangerecord.getIsNumber());
            }
            if (StringUtils.isNotBlank(fsBChangerecord.getIsState())) {
                queryWrapper.eq(FsBChangerecord::getIsState, fsBChangerecord.getIsState());
            }
            if (fsBChangerecord.getState() != null) {
                queryWrapper.eq(FsBChangerecord::getState, fsBChangerecord.getState());
            }

            if (StringUtils.isNotBlank(fsBChangerecord.getDeptNew())) {
                queryWrapper.like(FsBChangerecord::getDeptNew, fsBChangerecord.getDeptNew());
            }

            if (StringUtils.isNotBlank(fsBChangerecord.getCreateTimeFrom()) ) {
                queryWrapper
                        .ge(FsBChangerecord::getCreateTime, fsBChangerecord.getCreateTimeFrom());

            }
            if (StringUtils.isNotBlank(fsBChangerecord.getCreateTimeTo()) ) {
                queryWrapper
                        .le(FsBChangerecord::getCreateTime, fsBChangerecord.getCreateTimeTo());
            }
            Page<FsBChangerecord> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<FsBChangerecord> findFsBChangerecordList(QueryRequest request, FsBChangerecord fsBChangerecord) {
        try {
            Page<FsBChangerecord> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findFsBChangerecord(page, fsBChangerecord);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createFsBChangerecord(FsBChangerecord fsBChangerecord) {
        fsBChangerecord.setCreateTime(new Date());
        fsBChangerecord.setIsDeletemark(1);
        this.save(fsBChangerecord);
    }

    @Override
    @Transactional
    public void updateFsBChangerecord(FsBChangerecord fsBChangerecord) {
        fsBChangerecord.setModifyTime(new Date());
        this.baseMapper.updateFsBChangerecord(fsBChangerecord);
    }

    @Override
    @Transactional
    public void deleteFsBChangerecords(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

}