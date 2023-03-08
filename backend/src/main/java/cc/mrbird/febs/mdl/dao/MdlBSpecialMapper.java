package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlBSpecial;
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
 * @since 2022-03-16
 */
public interface MdlBSpecialMapper extends BaseMapper<MdlBSpecial> {
        void updateMdlBSpecial(MdlBSpecial mdlBSpecial);
        IPage<MdlBSpecial> findMdlBSpecial(Page page, @Param("mdlBSpecial") MdlBSpecial mdlBSpecial);
        }
