package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlLPerson;
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
public interface MdlLPersonMapper extends BaseMapper<MdlLPerson> {
        void updateMdlLPerson(MdlLPerson mdlLPerson);
        IPage<MdlLPerson> findMdlLPerson(Page page, @Param("mdlLPerson") MdlLPerson mdlLPerson);
        }
