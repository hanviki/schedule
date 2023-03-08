package cc.mrbird.febs.asset.service.impl;

import cc.mrbird.febs.asset.entity.AssetDDictory;
import cc.mrbird.febs.asset.entity.ViewAssetBInfo;
import cc.mrbird.febs.asset.service.IAssetDDictoryService;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.asset.entity.AssetBInfo;
import cc.mrbird.febs.asset.dao.AssetBInfoMapper;
import cc.mrbird.febs.asset.service.IAssetBInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2023-02-06
 */
@Slf4j
@Service("IAssetBInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AssetBInfoServiceImpl extends ServiceImpl<AssetBInfoMapper, AssetBInfo> implements IAssetBInfoService {

    @Autowired
    private IAssetDDictoryService iAssetDDictoryService;

    @Override
    public IPage<AssetBInfo> findAssetBInfos(QueryRequest request, AssetBInfo assetBInfo) {
        try {
            LambdaQueryWrapper<AssetBInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AssetBInfo::getIsDeletemark, 1);//1是未删 0是已删

            if (assetBInfo.getState() != null) {
                queryWrapper.eq(AssetBInfo::getState, assetBInfo.getState());
            }


            Page<AssetBInfo> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个

            IPage<AssetBInfo> result = this.page(page, queryWrapper);
            List<AssetDDictory> dictoryList = this.iAssetDDictoryService.list();
            List<AssetBInfo> records = result.getRecords();
            for (AssetBInfo ass : records
            ) {
                String dicWeis = dictoryList.stream().filter(p -> p.getTypeIndex().equals(0) && ass.getAssetWeis().contains(p.getId())).sorted(Comparator.comparing(AssetDDictory::getId)).map(p -> p.getName()).collect(Collectors.joining("_"));
                ass.setAssetWeisCustom(dicWeis);
                String dicHuanbao = dictoryList.stream().filter(p -> p.getTypeIndex().equals(1) && ass.getAssetHuanb().contains(p.getId())).sorted(Comparator.comparing(AssetDDictory::getId)).map(p -> p.getName()).collect(Collectors.joining("_"));
                ass.setAssetHuanbCustom(dicHuanbao);
            }

            return result;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<AssetBInfo> findAssetBInfoList(QueryRequest request, AssetBInfo assetBInfo) {
        try {
            Page<AssetBInfo> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            // return  this.baseMapper.findAssetBInfo(page,assetBInfo);

            IPage<AssetBInfo> result = this.baseMapper.findAssetBInfo(page, assetBInfo);
            List<AssetDDictory> dictoryList = this.iAssetDDictoryService.list();
            List<AssetBInfo> records = result.getRecords();
            for (AssetBInfo ass : records
            ) {
                String dicWeis = dictoryList.stream().filter(p -> p.getTypeIndex().equals(0) && ass.getAssetWeis().contains(p.getId())).sorted(Comparator.comparing(AssetDDictory::getId)).map(p -> p.getName()).collect(Collectors.joining("_"));
                ass.setAssetWeisCustom(dicWeis);
                String dicHuanbao = dictoryList.stream().filter(p -> p.getTypeIndex().equals(1) && ass.getAssetHuanb().contains(p.getId())).sorted(Comparator.comparing(AssetDDictory::getId)).map(p -> p.getName()).collect(Collectors.joining("_"));
                ass.setAssetHuanbCustom(dicHuanbao);
            }

            return result;
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }
    @Override
    public IPage<ViewAssetBInfo> findAssetBInfoStatistic(QueryRequest request, ViewAssetBInfo assetBInfo){
        try {
            Page<ViewAssetBInfo> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            // return  this.baseMapper.findAssetBInfo(page,assetBInfo);

            IPage<ViewAssetBInfo> result = this.baseMapper.findAssetBInfoStatistic(page, assetBInfo);
            List<AssetDDictory> dictoryList = this.iAssetDDictoryService.list();
            List<ViewAssetBInfo> records = result.getRecords();
            for (ViewAssetBInfo ass : records
            ) {
                String dicWeis = dictoryList.stream().filter(p -> p.getTypeIndex().equals(0) && ass.getAssetWeis().contains(p.getId())).sorted(Comparator.comparing(AssetDDictory::getId)).map(p -> p.getName()).collect(Collectors.joining("_"));
                ass.setAssetWeisCustom(dicWeis);
                String dicHuanbao = dictoryList.stream().filter(p -> p.getTypeIndex().equals(1) && ass.getAssetHuanb().contains(p.getId())).sorted(Comparator.comparing(AssetDDictory::getId)).map(p -> p.getName()).collect(Collectors.joining("_"));
                ass.setAssetHuanbCustom(dicHuanbao);
            }

            return result;
        } catch (Exception e) {
            log.error("获取失败", e);
            return null;
        }
    }
    @Override
    @Transactional
    public void createAssetBInfo(AssetBInfo assetBInfo) {
        assetBInfo.setCreateTime(new Date());
        assetBInfo.setIsDeletemark(1);
        this.save(assetBInfo);
    }

    @Override
    @Transactional
    public void updateAssetBInfo(AssetBInfo assetBInfo) {
        assetBInfo.setModifyTime(new Date());
        this.baseMapper.updateAssetBInfo(assetBInfo);
    }

    @Override
    @Transactional
    public void deleteAssetBInfos(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

}