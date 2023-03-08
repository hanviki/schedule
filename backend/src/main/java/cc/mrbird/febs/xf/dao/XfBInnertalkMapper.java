package cc.mrbird.febs.xf.dao;

import cc.mrbird.febs.xf.entity.XfBInnertalk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-11-01
 */
public interface XfBInnertalkMapper extends BaseMapper<XfBInnertalk> {
        void updateXfBInnertalk(XfBInnertalk xfBInnertalk);
        IPage<XfBInnertalk> findXfBInnertalk(Page page, @Param("xfBInnertalk") XfBInnertalk xfBInnertalk);

        @Delete("delete from xf_b_innertalk_d where base_id=#{baseId}" )
        void DeletByBaseId(@Param("baseId") String baseId);
}
