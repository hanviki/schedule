package cc.mrbird.febs.fs.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.fs.entity.FsBExame;
import cc.mrbird.febs.fs.dao.FsBExameMapper;
import cc.mrbird.febs.fs.service.IFsBExameService;
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
@Service("IFsBExameService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FsBExameServiceImpl extends ServiceImpl<FsBExameMapper, FsBExame> implements IFsBExameService {


    @Override
    public IPage<FsBExame> findFsBExames(QueryRequest request, FsBExame fsBExame) {
        try {
            LambdaQueryWrapper<FsBExame> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FsBExame::getIsDeletemark, 1);//1是未删 0是已删
            if (StringUtils.isNotBlank(fsBExame.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(FsBExame::getUserAccount, fsBExame.getUserAccount()).or()
                        .like(FsBExame::getUserAccountName, fsBExame.getUserAccount()));

            }
            if (fsBExame.getState() != null) {
                queryWrapper.eq(FsBExame::getState, fsBExame.getState());
            }

            if (fsBExame.getNumber() != null) {
                queryWrapper.eq(FsBExame::getNumber, fsBExame.getNumber());
            }
            if (StringUtils.isNotBlank(fsBExame.getName())) {
                queryWrapper.like(FsBExame::getName, fsBExame.getName());
            }
            if (StringUtils.isNotBlank(fsBExame.getExameType())) {
                queryWrapper.eq(FsBExame::getExameType, fsBExame.getExameType());
            }
            if (StringUtils.isNotBlank(fsBExame.getExameDateFrom())) {
                queryWrapper.ge(FsBExame::getExameDate, fsBExame.getExameDateFrom());
            }
            if (StringUtils.isNotBlank(fsBExame.getExameDateTo())) {
                queryWrapper.le(FsBExame::getExameDate, fsBExame.getExameDateTo());
            }


            Page<FsBExame> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<FsBExame> findFsBExameList(QueryRequest request, FsBExame fsBExame) {
        try {
            Page<FsBExame> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findFsBExame(page, fsBExame);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }
    @Override
    public IPage<FsBExame> findExameNote(QueryRequest request, FsBExame fsBExame) {
        try {
            Page<FsBExame> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findExameNote(page, fsBExame);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createFsBExame(FsBExame fsBExame) {
        fsBExame.setId(UUID.randomUUID().toString());
        fsBExame.setCreateTime(new Date());
        fsBExame.setIsDeletemark(1);
        this.save(fsBExame);
    }

    @Override
    @Transactional
    public void updateFsBExame(FsBExame fsBExame) {
        fsBExame.setModifyTime(new Date());
        this.baseMapper.updateFsBExame(fsBExame);
    }

    @Override
    @Transactional
    public void deleteFsBExames(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public List<FsBExame> findExameAccounts( List<String> accountList){
        return  this.baseMapper.findExameAccounts(accountList);
    }

    @Override
    @Transactional
    public List<FsBExame> findExameAccountsNote( List<String> accountList){
        return  this.baseMapper.findExameAccountsNote(accountList);
    }

    @Override
    @Transactional
    public List<FsBExame> findExameAccountsAll( List<String> accountList){
        return  this.baseMapper.findExameAccountsAll(accountList);
    }
}