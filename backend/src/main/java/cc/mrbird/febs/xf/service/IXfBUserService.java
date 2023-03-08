package cc.mrbird.febs.xf.service;

import cc.mrbird.febs.xf.entity.XfBUser;
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
 * @since 2022-11-01
 */
public interface IXfBUserService extends IService<XfBUser> {

        IPage<XfBUser> findXfBUsers(QueryRequest request, XfBUser xfBUser);

        IPage<XfBUser> findXfBUserList(QueryRequest request, XfBUser xfBUser);

        void createXfBUser(XfBUser xfBUser);

        void updateXfBUser(XfBUser xfBUser);

        void deleteXfBUsers(String[]Ids);


        }
