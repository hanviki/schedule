package cc.mrbird.febs.asset.service;

import cc.mrbird.febs.asset.entity.AssetBCheck;
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
public interface IAssetBCheckService extends IService<AssetBCheck> {

        IPage<AssetBCheck> findAssetBChecks(QueryRequest request, AssetBCheck assetBCheck);

        IPage<AssetBCheck> findAssetBCheckList(QueryRequest request, AssetBCheck assetBCheck);

        void createAssetBCheck(AssetBCheck assetBCheck);

        void updateAssetBCheck(AssetBCheck assetBCheck);

        void deleteAssetBChecks(String[]Ids);


        }
