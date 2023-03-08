package cc.mrbird.febs.asset.service;

import cc.mrbird.febs.asset.entity.AssetBShebeifile;
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
public interface IAssetBShebeifileService extends IService<AssetBShebeifile> {

        IPage<AssetBShebeifile> findAssetBShebeifiles(QueryRequest request, AssetBShebeifile assetBShebeifile);

        IPage<AssetBShebeifile> findAssetBShebeifileList(QueryRequest request, AssetBShebeifile assetBShebeifile);

        void createAssetBShebeifile(AssetBShebeifile assetBShebeifile);

        void updateAssetBShebeifile(AssetBShebeifile assetBShebeifile);

        void deleteAssetBShebeifiles(String[]Ids);


        }
