package cc.mrbird.febs.xf.service;

import cc.mrbird.febs.xf.entity.XfBReportrecord;
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
public interface IXfBReportrecordService extends IService<XfBReportrecord> {

        IPage<XfBReportrecord> findXfBReportrecords(QueryRequest request, XfBReportrecord xfBReportrecord);

        IPage<XfBReportrecord> findXfBReportrecordList(QueryRequest request, XfBReportrecord xfBReportrecord);

        void createXfBReportrecord(XfBReportrecord xfBReportrecord);

        void updateXfBReportrecord(XfBReportrecord xfBReportrecord);

        void deleteXfBReportrecords(String[]Ids);


        }
