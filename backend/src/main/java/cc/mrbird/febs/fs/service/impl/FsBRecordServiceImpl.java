package cc.mrbird.febs.fs.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.fs.entity.FsBRecord;
import cc.mrbird.febs.fs.dao.FsBRecordMapper;
import cc.mrbird.febs.fs.service.IFsBRecordService;
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
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2022-07-11
 */
@Slf4j
@Service("IFsBRecordService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FsBRecordServiceImpl extends ServiceImpl<FsBRecordMapper, FsBRecord> implements IFsBRecordService {


@Override
public IPage<FsBRecord> findFsBRecords(QueryRequest request, FsBRecord fsBRecord){
        try{
        LambdaQueryWrapper<FsBRecord> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(FsBRecord::getIsDeletemark, 1);//1是未删 0是已删
        if (StringUtils.isNotBlank(fsBRecord.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(FsBRecord::getUserAccount, fsBRecord.getUserAccount()).or()
        .like(FsBRecord::getUserAccountName, fsBRecord.getUserAccount()));

        }
        if (fsBRecord.getState()!=null) {
        queryWrapper.eq(FsBRecord::getState, fsBRecord.getState());
        }


        Page<FsBRecord> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }

        @Override
        public  List<FsBRecord> findFsBRecord(List<String> accountList){
            return  this.baseMapper.findFsBRecord2(accountList);
        }
@Override
public IPage<FsBRecord> findFsBRecordList (QueryRequest request, FsBRecord fsBRecord){
        try{
        Page<FsBRecord> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findFsBRecord(page,fsBRecord);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createFsBRecord(FsBRecord fsBRecord){
                fsBRecord.setId(UUID.randomUUID().toString());
        fsBRecord.setCreateTime(new Date());
        fsBRecord.setIsDeletemark(1);
        this.save(fsBRecord);
        }

@Override
@Transactional
public void updateFsBRecord(FsBRecord fsBRecord){
        fsBRecord.setModifyTime(new Date());
        this.baseMapper.updateFsBRecord(fsBRecord);
        }

@Override
@Transactional
public void deleteFsBRecords(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        }