package cc.mrbird.febs.xf.dao;

import cc.mrbird.febs.xf.entity.XfBConference;
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
 * @since 2022-12-01
 */
public interface XfBConferenceMapper extends BaseMapper<XfBConference> {
        void updateXfBConference(XfBConference xfBConference);
        IPage<XfBConference> findXfBConference(Page page, @Param("xfBConference") XfBConference xfBConference);
        }
