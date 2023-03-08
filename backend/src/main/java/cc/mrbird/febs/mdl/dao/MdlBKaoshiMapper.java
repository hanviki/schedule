package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlBKaoshi;
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
 * @since 2023-01-05
 */
public interface MdlBKaoshiMapper extends BaseMapper<MdlBKaoshi> {
        void updateMdlBKaoshi(MdlBKaoshi mdlBKaoshi);
        IPage<MdlBKaoshi> findMdlBKaoshi(Page page, @Param("mdlBKaoshi") MdlBKaoshi mdlBKaoshi);
        }
