package cc.mrbird.febs.asset.service;

import cc.mrbird.febs.asset.entity.AssetBZicha;
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
public interface IAssetBZichaService extends IService<AssetBZicha> {

        IPage<AssetBZicha> findAssetBZichas(QueryRequest request, AssetBZicha assetBZicha);

        IPage<AssetBZicha> findAssetBZichaList(QueryRequest request, AssetBZicha assetBZicha);

        void createAssetBZicha(AssetBZicha assetBZicha);

        void updateAssetBZicha(AssetBZicha assetBZicha);

        void deleteAssetBZichas(String[]Ids);


        }
