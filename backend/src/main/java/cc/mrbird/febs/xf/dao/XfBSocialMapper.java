package cc.mrbird.febs.xf.dao;

import cc.mrbird.febs.xf.entity.XfBSocial;
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
 * @since 2022-11-09
 */
public interface XfBSocialMapper extends BaseMapper<XfBSocial> {
        void updateXfBSocial(XfBSocial xfBSocial);
        IPage<XfBSocial> findXfBSocial(Page page, @Param("xfBSocial") XfBSocial xfBSocial);
        }
