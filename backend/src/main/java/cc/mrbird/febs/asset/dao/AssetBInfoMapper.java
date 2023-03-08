package cc.mrbird.febs.asset.dao;

import cc.mrbird.febs.asset.entity.AssetBInfo;
import cc.mrbird.febs.asset.entity.ViewAssetBInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2023-02-06
 */
public interface AssetBInfoMapper extends BaseMapper<AssetBInfo> {
        void updateAssetBInfo(AssetBInfo assetBInfo);
        IPage<AssetBInfo> findAssetBInfo(Page page, @Param("assetBInfo") AssetBInfo assetBInfo);
        IPage<ViewAssetBInfo> findAssetBInfoStatistic(Page page, @Param("assetBInfo") ViewAssetBInfo assetBInfo);
        }
