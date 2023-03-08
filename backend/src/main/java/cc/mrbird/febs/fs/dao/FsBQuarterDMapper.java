package cc.mrbird.febs.fs.dao;

import cc.mrbird.febs.fs.entity.FsBQuarterD;
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
public interface FsBQuarterDMapper extends BaseMapper<FsBQuarterD> {
        void updateFsBQuarterD(FsBQuarterD fsBQuarterD);
        IPage<FsBQuarterD> findFsBQuarterD(Page page, @Param("fsBQuarterD") FsBQuarterD fsBQuarterD);
        IPage<FsBQuarterD> findReport(Page page, @Param("fsBQuarterD") FsBQuarterD fsBQuarterD);
        }
