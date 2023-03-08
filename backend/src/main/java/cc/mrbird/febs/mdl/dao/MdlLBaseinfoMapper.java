package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlLBaseinfo;
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
 * @since 2022-11-29
 */
public interface MdlLBaseinfoMapper extends BaseMapper<MdlLBaseinfo> {
        void updateMdlLBaseinfo(MdlLBaseinfo mdlLBaseinfo);
        IPage<MdlLBaseinfo> findMdlLBaseinfo(Page page, @Param("mdlLBaseinfo") MdlLBaseinfo mdlLBaseinfo);
        }
