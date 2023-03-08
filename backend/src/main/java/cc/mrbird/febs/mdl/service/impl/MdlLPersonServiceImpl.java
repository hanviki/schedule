package cc.mrbird.febs.mdl.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.mdl.entity.MdlLPerson;
import cc.mrbird.febs.mdl.dao.MdlLPersonMapper;
import cc.mrbird.febs.mdl.service.IMdlLPersonService;
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
@Service("IMdlLPersonService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MdlLPersonServiceImpl extends ServiceImpl<MdlLPersonMapper, MdlLPerson> implements IMdlLPersonService {


    @Override
    public IPage<MdlLPerson> findMdlLPersons(QueryRequest request, MdlLPerson mdlLPerson) {
        try {
            LambdaQueryWrapper<MdlLPerson> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MdlLPerson::getIsDeletemark, 1);//1是未删 0是已删
            if (StringUtils.isNotBlank(mdlLPerson.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(MdlLPerson::getUserAccount, mdlLPerson.getUserAccount()).or()
                        .like(MdlLPerson::getUserAccountName, mdlLPerson.getUserAccount()));

            }
            if (mdlLPerson.getState() != null) {
                queryWrapper.eq(MdlLPerson::getState, mdlLPerson.getState());
            }
            if (StringUtils.isNotBlank(mdlLPerson.getBaseId())) {
                queryWrapper.eq(MdlLPerson::getBaseId, mdlLPerson.getBaseId());
            }

            Page<MdlLPerson> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<MdlLPerson> findMdlLPersonList(QueryRequest request, MdlLPerson mdlLPerson) {
        try {
            Page<MdlLPerson> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findMdlLPerson(page, mdlLPerson);
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createMdlLPerson(MdlLPerson mdlLPerson) {
        mdlLPerson.setId(UUID.randomUUID().toString());
        mdlLPerson.setCreateTime(new Date());
        mdlLPerson.setIsDeletemark(1);
        this.save(mdlLPerson);
    }

    @Override
    @Transactional
    public void updateMdlLPerson(MdlLPerson mdlLPerson) {
        mdlLPerson.setModifyTime(new Date());
        this.baseMapper.updateMdlLPerson(mdlLPerson);
    }

    @Override
    @Transactional
    public void deleteMdlLPersons(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public void deleteByBaseId(String baseId){
        LambdaQueryWrapper<MdlLPerson> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.eq(MdlLPerson::getBaseId,baseId);
        this.baseMapper.delete(queryWrapper);
    }

}