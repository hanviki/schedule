package cc.mrbird.febs.xf.service;

import cc.mrbird.febs.xf.entity.XfBProjectreport;
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
public interface IXfBProjectreportService extends IService<XfBProjectreport> {

        IPage<XfBProjectreport> findXfBProjectreports(QueryRequest request, XfBProjectreport xfBProjectreport);

        IPage<XfBProjectreport> findXfBProjectreportList(QueryRequest request, XfBProjectreport xfBProjectreport);

        void createXfBProjectreport(XfBProjectreport xfBProjectreport);

        void updateXfBProjectreport(XfBProjectreport xfBProjectreport);

        void deleteXfBProjectreports(String[]Ids);


        }
