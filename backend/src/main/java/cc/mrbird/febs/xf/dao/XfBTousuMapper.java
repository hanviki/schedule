package cc.mrbird.febs.xf.dao;

import cc.mrbird.febs.xf.entity.XfBTousu;
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
public interface XfBTousuMapper extends BaseMapper<XfBTousu> {
        void updateXfBTousu(XfBTousu xfBTousu);
        IPage<XfBTousu> findXfBTousu(Page page, @Param("xfBTousu") XfBTousu xfBTousu);
        }
