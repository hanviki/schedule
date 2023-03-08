package cc.mrbird.febs.xf.service;

import cc.mrbird.febs.xf.entity.XfBErrorperson;
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
public interface IXfBErrorpersonService extends IService<XfBErrorperson> {

        IPage<XfBErrorperson> findXfBErrorpersons(QueryRequest request, XfBErrorperson xfBErrorperson);

        IPage<XfBErrorperson> findXfBErrorpersonList(QueryRequest request, XfBErrorperson xfBErrorperson);

        void createXfBErrorperson(XfBErrorperson xfBErrorperson);

        void updateXfBErrorperson(XfBErrorperson xfBErrorperson);

        void deleteXfBErrorpersons(String[]Ids);


        }
