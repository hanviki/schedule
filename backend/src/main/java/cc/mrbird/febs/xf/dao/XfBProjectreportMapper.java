package cc.mrbird.febs.xf.dao;

import cc.mrbird.febs.xf.entity.XfBProjectreport;
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
 * @since 2022-11-01
 */
public interface XfBProjectreportMapper extends BaseMapper<XfBProjectreport> {
        void updateXfBProjectreport(XfBProjectreport xfBProjectreport);
        IPage<XfBProjectreport> findXfBProjectreport(Page page, @Param("xfBProjectreport") XfBProjectreport xfBProjectreport);
        }
