package cc.mrbird.febs.fs.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.fs.entity.FsBDeptchange;
import cc.mrbird.febs.fs.dao.FsBDeptchangeMapper;
import cc.mrbird.febs.fs.service.IFsBDeptchangeService;
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
 * @since 2022-07-08
 */
@Slf4j
@Service("IFsBDeptchangeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FsBDeptchangeServiceImpl extends ServiceImpl<FsBDeptchangeMapper, FsBDeptchange> implements IFsBDeptchangeService {


    @Override
    public IPage<FsBDeptchange> findFsBDeptchanges(QueryRequest request, FsBDeptchange fsBDeptchange) {
        try {
            LambdaQueryWrapper<FsBDeptchange> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FsBDeptchange::getIsDeletemark, 1);//1是未删 0是已删
            if (StringUtils.isNotBlank(fsBDeptchange.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(FsBDeptchange::getUserAccount, fsBDeptchange.getUserAccount()).or()
                        .like(FsBDeptchange::getUserAccountName, fsBDeptchange.getUserAccount()));

            }
            if (fsBDeptchange.getState() != null) {
                queryWrapper.eq(FsBDeptchange::getState, fsBDeptchange.getState());
            }

            if (StringUtils.isNotBlank(fsBDeptchange.getName())) {
                queryWrapper.like(FsBDeptchange::getName, fsBDeptchange.getName());
            }

            Page<FsBDeptchange> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<FsBDeptchange> findFsBDeptchangeList(QueryRequest request, FsBDeptchange fsBDeptchange) {
        try {
            Page<FsBDeptchange> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findFsBDeptchange(page, fsBDeptchange);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createFsBDeptchange(FsBDeptchange fsBDeptchange) {
        fsBDeptchange.setId(UUID.randomUUID().toString());
        fsBDeptchange.setCreateTime(new Date());
        fsBDeptchange.setIsDeletemark(1);
        this.save(fsBDeptchange);
        this.baseMapper.updateUserDept(fsBDeptchange.getUserAccount(), fsBDeptchange.getDeptNewId());
    }

    @Override
    @Transactional
    public void updateFsBDeptchange(FsBDeptchange fsBDeptchange) {
        fsBDeptchange.setModifyTime(new Date());
        this.baseMapper.updateFsBDeptchange(fsBDeptchange);
        this.baseMapper.deleteUserDept(fsBDeptchange.getUserAccount());
    }

    @Override
    @Transactional
    public void deleteFsBDeptchanges(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

}