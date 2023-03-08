package cc.mrbird.febs.asset.service;

import cc.mrbird.febs.asset.entity.AssetBBaopi;
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
public interface IAssetBBaopiService extends IService<AssetBBaopi> {

        IPage<AssetBBaopi> findAssetBBaopis(QueryRequest request, AssetBBaopi assetBBaopi);

        IPage<AssetBBaopi> findAssetBBaopiList(QueryRequest request, AssetBBaopi assetBBaopi);

        void createAssetBBaopi(AssetBBaopi assetBBaopi);

        void updateAssetBBaopi(AssetBBaopi assetBBaopi);

        void deleteAssetBBaopis(String[]Ids);


        }
