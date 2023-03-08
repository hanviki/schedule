package cc.mrbird.febs.xf.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.xf.entity.XfBConference;
import cc.mrbird.febs.xf.dao.XfBConferenceMapper;
import cc.mrbird.febs.xf.service.IXfBConferenceService;
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
 * @since 2022-12-01
 */
@Slf4j
@Service("IXfBConferenceService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class XfBConferenceServiceImpl extends ServiceImpl<XfBConferenceMapper, XfBConference> implements IXfBConferenceService {


    @Override
    public IPage<XfBConference> findXfBConferences(QueryRequest request, XfBConference xfBConference) {
        try {
            LambdaQueryWrapper<XfBConference> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(XfBConference::getIsDeletemark, 1);//1是未删 0是已删

            if (xfBConference.getState() != null) {
                queryWrapper.eq(XfBConference::getState, xfBConference.getState());
            }

            if (StringUtils.isNotBlank(xfBConference.getName())) {
                queryWrapper.like(XfBConference::getName, xfBConference.getName());
            }
            if (StringUtils.isNotBlank(xfBConference.getPerson())) {
                queryWrapper.like(XfBConference::getPerson, xfBConference.getPerson());
            }

            Page<XfBConference> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<XfBConference> findXfBConferenceList(QueryRequest request, XfBConference xfBConference) {
        try {
            Page<XfBConference> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findXfBConference(page, xfBConference);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createXfBConference(XfBConference xfBConference) {
        xfBConference.setCreateTime(new Date());
        xfBConference.setIsDeletemark(1);
        this.save(xfBConference);
    }

    @Override
    @Transactional
    public void updateXfBConference(XfBConference xfBConference) {
        xfBConference.setModifyTime(new Date());
        this.baseMapper.updateXfBConference(xfBConference);
    }

    @Override
    @Transactional
    public void deleteXfBConferences(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

}