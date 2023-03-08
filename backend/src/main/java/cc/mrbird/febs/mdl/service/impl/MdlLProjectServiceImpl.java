package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlLProject;
import cc.mrbird.febs.mdl.dao.MdlLProjectMapper;
import cc.mrbird.febs.mdl.service.IMdlLProjectService;
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
 * @since 2022-11-29
 */
@Slf4j
@Service("IMdlLProjectService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlLProjectServiceImpl extends ServiceImpl<MdlLProjectMapper, MdlLProject> implements IMdlLProjectService {


    @Override
    public IPage<MdlLProject> findMdlLProjects(QueryRequest request, MdlLProject mdlLProject) {
        try {
            LambdaQueryWrapper<MdlLProject> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MdlLProject::getIsDeletemark, 1);//1是未删 0是已删

            if (mdlLProject.getState() != null) {
                queryWrapper.eq(MdlLProject::getState, mdlLProject.getState());
            }

            if (StringUtils.isNotBlank(mdlLProject.getCode())) {
                queryWrapper.like(MdlLProject::getCode, mdlLProject.getCode());
            }
            if (StringUtils.isNotBlank(mdlLProject.getBaseId())) {
                queryWrapper.eq(MdlLProject::getBaseId, mdlLProject.getBaseId());
            }
            if (StringUtils.isNotBlank(mdlLProject.getName())) {
                queryWrapper.like(MdlLProject::getName, mdlLProject.getName());
            }

            Page<MdlLProject> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<MdlLProject> findMdlLProjectList(QueryRequest request, MdlLProject mdlLProject) {
        try {
            Page<MdlLProject> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findMdlLProject(page, mdlLProject);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createMdlLProject(MdlLProject mdlLProject) {
        mdlLProject.setId(UUID.randomUUID().toString());
        mdlLProject.setCreateTime(new Date());
        mdlLProject.setIsDeletemark(1);
        this.save(mdlLProject);
    }

    @Override
    @Transactional
    public void updateMdlLProject(MdlLProject mdlLProject) {
        mdlLProject.setModifyTime(new Date());
        this.baseMapper.updateMdlLProject(mdlLProject);
    }

    @Override
    @Transactional
    public void deleteMdlLProjects(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

}