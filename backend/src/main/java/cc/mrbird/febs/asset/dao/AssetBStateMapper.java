package cc.mrbird.febs.asset.dao;

import cc.mrbird.febs.asset.entity.AssetBState;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2023-02-06
 */
public interface AssetBStateMapper extends BaseMapper<AssetBState> {
        void updateAssetBState(AssetBState assetBState);
        IPage<AssetBState> findAssetBState(Page page, @Param("assetBState") AssetBState assetBState);

       @Select("select b.base_id  from asset_b_state b left join (\n" +
               "select a.base_id, max(a.operation_date) operation_date  from asset_b_state a where a.asset_state=#{assetState} GROUP BY  a.base_id )p\n" +
               "on b.base_id=p.base_id and b.operation_date=p.operation_date where b.asset_state=#{assetState}")
       List<String> getAllBaseIdByState(@Param("assetState")String assetState);
}
