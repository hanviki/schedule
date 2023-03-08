package cc.mrbird.febs.asset.service;

import cc.mrbird.febs.asset.entity.AssetBFanghu;
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
public interface IAssetBFanghuService extends IService<AssetBFanghu> {

        IPage<AssetBFanghu> findAssetBFanghus(QueryRequest request, AssetBFanghu assetBFanghu);

        IPage<AssetBFanghu> findAssetBFanghuList(QueryRequest request, AssetBFanghu assetBFanghu);

        void createAssetBFanghu(AssetBFanghu assetBFanghu);

        void updateAssetBFanghu(AssetBFanghu assetBFanghu);

        void deleteAssetBFanghus(String[]Ids);


        }
