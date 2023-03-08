package cc.mrbird.febs.xf.service;

import cc.mrbird.febs.xf.entity.XfBConference;
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
 * @since 2022-12-01
 */
public interface IXfBConferenceService extends IService<XfBConference> {

        IPage<XfBConference> findXfBConferences(QueryRequest request, XfBConference xfBConference);

        IPage<XfBConference> findXfBConferenceList(QueryRequest request, XfBConference xfBConference);

        void createXfBConference(XfBConference xfBConference);

        void updateXfBConference(XfBConference xfBConference);

        void deleteXfBConferences(String[]Ids);


        }
