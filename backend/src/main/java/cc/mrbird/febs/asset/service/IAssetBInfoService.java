package cc.mrbird.febs.asset.service;

import cc.mrbird.febs.asset.entity.AssetBInfo;
import cc.mrbird.febs.asset.entity.ViewAssetBInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2023-02-06
 */
public interface IAssetBInfoService extends IService<AssetBInfo> {

        IPage<AssetBInfo> findAssetBInfos(QueryRequest request, AssetBInfo assetBInfo);

        IPage<AssetBInfo> findAssetBInfoList(QueryRequest request, AssetBInfo assetBInfo);

        IPage<ViewAssetBInfo> findAssetBInfoStatistic(QueryRequest request, ViewAssetBInfo assetBInfo);

        void createAssetBInfo(AssetBInfo assetBInfo);

        void updateAssetBInfo(AssetBInfo assetBInfo);

        void deleteAssetBInfos(String[]Ids);


        }
