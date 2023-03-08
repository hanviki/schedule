package cc.mrbird.febs.fs.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.fs.entity.FsBQy;
import cc.mrbird.febs.fs.dao.FsBQyMapper;
import cc.mrbird.febs.fs.entity.FsBQyReport;
import cc.mrbird.febs.fs.service.IFsBQyService;
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
 * @since 2022-07-13
 */
@Slf4j
@Service("IFsBQyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FsBQyServiceImpl extends ServiceImpl<FsBQyMapper, FsBQy> implements IFsBQyService {


    @Override
    public IPage<FsBQy> findFsBQys(QueryRequest request, FsBQy fsBQy) {
        try {
            LambdaQueryWrapper<FsBQy> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FsBQy::getIsDeletemark, 1);//1是未删 0是已删
            if (StringUtils.isNotBlank(fsBQy.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(FsBQy::getUserAccount, fsBQy.getUserAccount()).or()
                        .like(FsBQy::getUserAccountName, fsBQy.getUserAccount()));

            }
            if (fsBQy.getState() != null) {
                queryWrapper.eq(FsBQy::getState, fsBQy.getState());
            }


            Page<FsBQy> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<FsBQy> findFsBQyList(QueryRequest request, FsBQy fsBQy) {
        try {
            Page<FsBQy> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findFsBQy(page, fsBQy);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    public IPage<FsBQyReport> report(QueryRequest request, FsBQy fsBQy) {
        try {
            Page<FsBQy> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.report(page, fsBQy);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createFsBQy(FsBQy fsBQy) {
        fsBQy.setId(UUID.randomUUID().toString());
        fsBQy.setCreateTime(new Date());
        fsBQy.setIsDeletemark(1);
        this.save(fsBQy);
    }

    @Override
    @Transactional
    public void updateFsBQy(FsBQy fsBQy) {
        fsBQy.setModifyTime(new Date());
        this.baseMapper.updateFsBQy(fsBQy);
    }

    @Override
    @Transactional
    public void deleteFsBQys(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

}