package cc.mrbird.febs.xf.service;

import cc.mrbird.febs.xf.entity.XfBSocial;
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
 * @since 2022-11-09
 */
public interface IXfBSocialService extends IService<XfBSocial> {

        IPage<XfBSocial> findXfBSocials(QueryRequest request, XfBSocial xfBSocial);

        IPage<XfBSocial> findXfBSocialList(QueryRequest request, XfBSocial xfBSocial);

        void createXfBSocial(XfBSocial xfBSocial);

        void updateXfBSocial(XfBSocial xfBSocial);

        void deleteXfBSocials(String[]Ids);


        }
