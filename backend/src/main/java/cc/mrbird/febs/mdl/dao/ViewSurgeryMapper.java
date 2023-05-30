package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.ViewSurgery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2023-04-20
 */
public interface ViewSurgeryMapper extends BaseMapper<ViewSurgery> {
        void updateViewSurgery(ViewSurgery viewSurgery);
        IPage<ViewSurgery> findViewSurgery(Page page, @Param("viewSurgery") ViewSurgery viewSurgery);
        }
