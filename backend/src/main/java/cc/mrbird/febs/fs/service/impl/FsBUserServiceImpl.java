package cc.mrbird.febs.fs.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.fs.entity.FsBUser;
import cc.mrbird.febs.fs.dao.FsBUserMapper;
import cc.mrbird.febs.fs.entity.ViewFs;
import cc.mrbird.febs.fs.service.IFsBUserService;
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
 * @since 2022-07-06
 */
@Slf4j
@Service("IFsBUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FsBUserServiceImpl extends ServiceImpl<FsBUserMapper, FsBUser> implements IFsBUserService {


    @Override
    public IPage<FsBUser> findFsBUsers(QueryRequest request, FsBUser fsBUser) {
        try {
            LambdaQueryWrapper<FsBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FsBUser::getIsDeletemark, 1);//1是未删 0是已删
            if (StringUtils.isNotBlank(fsBUser.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(FsBUser::getUserAccount, fsBUser.getUserAccount()).or()
                        .like(FsBUser::getUserAccountName, fsBUser.getUserAccount()));

            }
            if (fsBUser.getState() != null) {
                queryWrapper.eq(FsBUser::getState, fsBUser.getState());
            }


            Page<FsBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<FsBUser> findFsBUserList(QueryRequest request, FsBUser fsBUser) {
        try {
            Page<FsBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findFsBUser(page, fsBUser);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    public IPage<ViewFs> findFsBUserList(QueryRequest request, ViewFs user) {
        try {
            Page<FsBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.getUser(page, user);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    public IPage<ViewFs> findFsBUserList2(QueryRequest request, ViewFs user) {
        try {
            Page<FsBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.getUser2(page, user);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    public IPage<ViewFs> findUserExameTip(QueryRequest request, ViewFs user) {
        try {
            Page<FsBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.getUserExameTip(page, user);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    public IPage<ViewFs> findUserHealthTip(QueryRequest request, ViewFs user) {
        try {
            Page<FsBUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.getUserHealthTip(page, user);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createFsBUser(FsBUser fsBUser) {
        fsBUser.setId(UUID.randomUUID().toString());
        fsBUser.setCreateTime(new Date());
        fsBUser.setIsDeletemark(1);
        this.save(fsBUser);
    }

    @Override
    @Transactional
    public void updateFsBUser(FsBUser fsBUser) {
        fsBUser.setModifyTime(new Date());
        this.baseMapper.updateFsBUser(fsBUser);
    }

    @Override
    @Transactional
    public void deleteFsBUsers(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

}