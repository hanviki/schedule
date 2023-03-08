package cc.mrbird.febs.xf.service;

import cc.mrbird.febs.xf.entity.XfBInnertalk;
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
public interface IXfBInnertalkService extends IService<XfBInnertalk> {

        IPage<XfBInnertalk> findXfBInnertalks(QueryRequest request, XfBInnertalk xfBInnertalk);

        IPage<XfBInnertalk> findXfBInnertalkList(QueryRequest request, XfBInnertalk xfBInnertalk);

        void createXfBInnertalk(XfBInnertalk xfBInnertalk);

        void updateXfBInnertalk(XfBInnertalk xfBInnertalk);

        void deleteXfBInnertalks(String[]Ids);
        void  deleteByBaseId(String id);


        }
