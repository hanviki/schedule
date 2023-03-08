package cc.mrbird.febs.asset.service;

import cc.mrbird.febs.asset.entity.AssetBState;
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
public interface IAssetBStateService extends IService<AssetBState> {

        IPage<AssetBState> findAssetBStates(QueryRequest request, AssetBState assetBState);

        IPage<AssetBState> findAssetBStateList(QueryRequest request, AssetBState assetBState);

        void createAssetBState(AssetBState assetBState);

        void updateAssetBState(AssetBState assetBState);

        void deleteAssetBStates(String[]Ids);

        List<String> getAllBaseIdByState(String assetState);


        }
