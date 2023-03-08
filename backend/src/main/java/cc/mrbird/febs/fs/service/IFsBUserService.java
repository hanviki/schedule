package cc.mrbird.febs.fs.service;

import cc.mrbird.febs.fs.entity.FsBUser;
import cc.mrbird.febs.fs.entity.ViewFs;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2022-07-06
 */
public interface IFsBUserService extends IService<FsBUser> {

        IPage<FsBUser> findFsBUsers(QueryRequest request, FsBUser fsBUser);

        IPage<FsBUser> findFsBUserList(QueryRequest request, FsBUser fsBUser);

        void createFsBUser(FsBUser fsBUser);

        void updateFsBUser(FsBUser fsBUser);

        void deleteFsBUsers(String[]Ids);


        IPage<ViewFs> findFsBUserList(QueryRequest request, ViewFs user);
        IPage<ViewFs> findFsBUserList2(QueryRequest request, ViewFs user);
        IPage<ViewFs> findUserExameTip(QueryRequest request, ViewFs user);

        IPage<ViewFs> findUserHealthTip(QueryRequest request, ViewFs user);
        }
