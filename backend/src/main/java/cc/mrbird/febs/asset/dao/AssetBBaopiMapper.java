package cc.mrbird.febs.asset.dao;

import cc.mrbird.febs.asset.entity.AssetBBaopi;
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
public interface AssetBBaopiMapper extends BaseMapper<AssetBBaopi> {
        void updateAssetBBaopi(AssetBBaopi assetBBaopi);
        IPage<AssetBBaopi> findAssetBBaopi(Page page, @Param("assetBBaopi") AssetBBaopi assetBBaopi);
        }
