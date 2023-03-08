package cc.mrbird.febs.fs.dao;

import cc.mrbird.febs.fs.entity.FsBQuarter;
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
 * @since 2022-07-12
 */
public interface FsBQuarterMapper extends BaseMapper<FsBQuarter> {
        void updateFsBQuarter(FsBQuarter fsBQuarter);
        IPage<FsBQuarter> findFsBQuarter(Page page, @Param("fsBQuarter") FsBQuarter fsBQuarter);
        }
