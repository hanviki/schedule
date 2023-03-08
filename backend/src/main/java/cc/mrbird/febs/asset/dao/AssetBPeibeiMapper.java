package cc.mrbird.febs.asset.dao;

import cc.mrbird.febs.asset.entity.AssetBPeibei;
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
public interface AssetBPeibeiMapper extends BaseMapper<AssetBPeibei> {
        void updateAssetBPeibei(AssetBPeibei assetBPeibei);
        IPage<AssetBPeibei> findAssetBPeibei(Page page, @Param("assetBPeibei") AssetBPeibei assetBPeibei);
        }
