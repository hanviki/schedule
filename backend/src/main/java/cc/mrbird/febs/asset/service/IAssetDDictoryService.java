package cc.mrbird.febs.asset.service;

import cc.mrbird.febs.asset.entity.AssetDDictory;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;

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
public interface IAssetDDictoryService extends IService<AssetDDictory> {

        IPage<AssetDDictory> findAssetDDictoryList(QueryRequest request, AssetDDictory assetDDictory);

        void createAssetDDictory(AssetDDictory assetDDictory);

        void updateAssetDDictory(AssetDDictory assetDDictory);

        void deleteAssetDDictorys(String[]Ids);
        List<AssetDDictory> findAssetDDictorys(AssetDDictory dept, QueryRequest request);
        Map<String, Object> findAssetDDictorys(QueryRequest request, AssetDDictory assetDDictory);

        }
