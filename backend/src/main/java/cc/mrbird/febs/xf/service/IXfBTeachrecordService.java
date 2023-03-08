package cc.mrbird.febs.xf.service;

import cc.mrbird.febs.xf.entity.XfBTeachrecord;
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
public interface IXfBTeachrecordService extends IService<XfBTeachrecord> {

        IPage<XfBTeachrecord> findXfBTeachrecords(QueryRequest request, XfBTeachrecord xfBTeachrecord);

        IPage<XfBTeachrecord> findXfBTeachrecordList(QueryRequest request, XfBTeachrecord xfBTeachrecord);

        void createXfBTeachrecord(XfBTeachrecord xfBTeachrecord);

        void updateXfBTeachrecord(XfBTeachrecord xfBTeachrecord);

        void deleteXfBTeachrecords(String[]Ids);


        }
