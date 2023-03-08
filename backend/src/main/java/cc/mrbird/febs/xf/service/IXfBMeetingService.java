package cc.mrbird.febs.xf.service;

import cc.mrbird.febs.xf.entity.XfBMeeting;
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
public interface IXfBMeetingService extends IService<XfBMeeting> {

        IPage<XfBMeeting> findXfBMeetings(QueryRequest request, XfBMeeting xfBMeeting);

        IPage<XfBMeeting> findXfBMeetingList(QueryRequest request, XfBMeeting xfBMeeting);

        void createXfBMeeting(XfBMeeting xfBMeeting);

        void updateXfBMeeting(XfBMeeting xfBMeeting);

        void deleteXfBMeetings(String[]Ids);


        }
