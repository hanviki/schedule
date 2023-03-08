package cc.mrbird.febs.asset.dao;

import cc.mrbird.febs.asset.entity.AssetDDictory;
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
public interface AssetDDictoryMapper extends BaseMapper<AssetDDictory> {
        void updateAssetDDictory(AssetDDictory assetDDictory);
        IPage<AssetDDictory> findAssetDDictory(Page page, @Param("assetDDictory") AssetDDictory assetDDictory);
        }
