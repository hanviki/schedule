package cc.mrbird.febs.asset.service;

import cc.mrbird.febs.asset.entity.AssetBPeibei;
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
public interface IAssetBPeibeiService extends IService<AssetBPeibei> {

        IPage<AssetBPeibei> findAssetBPeibeis(QueryRequest request, AssetBPeibei assetBPeibei);

        IPage<AssetBPeibei> findAssetBPeibeiList(QueryRequest request, AssetBPeibei assetBPeibei);

        void createAssetBPeibei(AssetBPeibei assetBPeibei);

        void updateAssetBPeibei(AssetBPeibei assetBPeibei);

        void deleteAssetBPeibeis(String[]Ids);


        }
