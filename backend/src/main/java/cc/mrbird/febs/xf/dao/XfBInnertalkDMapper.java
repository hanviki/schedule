package cc.mrbird.febs.xf.dao;

import cc.mrbird.febs.xf.entity.XfBInnertalkD;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-11-22
 */
public interface XfBInnertalkDMapper extends BaseMapper<XfBInnertalkD> {
        void updateXfBInnertalkD(XfBInnertalkD xfBInnertalkD);
        IPage<XfBInnertalkD> findXfBInnertalkD(Page page, @Param("xfBInnertalkD") XfBInnertalkD xfBInnertalkD);

@Update(" update xf_b_innertalk_d set IS_DELETEMARK=0 where user_account=#{useraccount}  and (state=0 or state=2)")
        void deleteByAccount(@Param("useraccount") String useraccount);
@Select(" select IFNULL(max(display_index),0) As maxIndex from  xf_b_innertalk_d  where user_account=#{useraccount} ")
        int getMaxDisplayIndexByuseraccount(@Param("useraccount") String useraccount);
        }
