package cc.mrbird.febs.fs.dao;

import cc.mrbird.febs.fs.entity.FsBUser;
import cc.mrbird.febs.fs.entity.ViewFs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-07-06
 */
public interface FsBUserMapper extends BaseMapper<FsBUser> {
        void updateFsBUser(FsBUser fsBUser);
        IPage<FsBUser> findFsBUser(Page page, @Param("fsBUser") FsBUser fsBUser);

        IPage<ViewFs>  getUser(Page page, @Param("user") ViewFs user);

        IPage<ViewFs>  getUser2(Page page, @Param("user") ViewFs user);

        IPage<ViewFs>  getUserExameTip(Page page, @Param("user") ViewFs user);

        IPage<ViewFs>  getUserHealthTip(Page page, @Param("user") ViewFs user);
}
