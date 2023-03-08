package cc.mrbird.febs.asset.service;

import cc.mrbird.febs.asset.entity.AssetBFsjbxx;
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
 * @since 2023-02-08
 */
public interface IAssetBFsjbxxService extends IService<AssetBFsjbxx> {

        IPage<AssetBFsjbxx> findAssetBFsjbxxs(QueryRequest request, AssetBFsjbxx assetBFsjbxx);

        IPage<AssetBFsjbxx> findAssetBFsjbxxList(QueryRequest request, AssetBFsjbxx assetBFsjbxx);

        void createAssetBFsjbxx(AssetBFsjbxx assetBFsjbxx);

        void updateAssetBFsjbxx(AssetBFsjbxx assetBFsjbxx);

        void deleteAssetBFsjbxxs(String[]Ids);


        }
