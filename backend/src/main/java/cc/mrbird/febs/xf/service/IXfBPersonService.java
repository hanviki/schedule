package cc.mrbird.febs.xf.service;

import cc.mrbird.febs.xf.entity.XfBPerson;
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
public interface IXfBPersonService extends IService<XfBPerson> {

        IPage<XfBPerson> findXfBPersons(QueryRequest request, XfBPerson xfBPerson);

        IPage<XfBPerson> findXfBPersonList(QueryRequest request, XfBPerson xfBPerson);

        void createXfBPerson(XfBPerson xfBPerson);

        void updateXfBPerson(XfBPerson xfBPerson);

        void deleteXfBPersons(String[]Ids);


        }
