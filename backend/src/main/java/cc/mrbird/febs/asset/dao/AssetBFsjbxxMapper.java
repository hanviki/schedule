package cc.mrbird.febs.asset.dao;

import cc.mrbird.febs.asset.entity.AssetBFsjbxx;
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
 * @since 2023-02-08
 */
public interface AssetBFsjbxxMapper extends BaseMapper<AssetBFsjbxx> {
        void updateAssetBFsjbxx(AssetBFsjbxx assetBFsjbxx);
        IPage<AssetBFsjbxx> findAssetBFsjbxx(Page page, @Param("assetBFsjbxx") AssetBFsjbxx assetBFsjbxx);
        }
