package cc.mrbird.febs.xf.service;

import cc.mrbird.febs.xf.entity.XfBInnertalkD;
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
 * @since 2022-11-22
 */
public interface IXfBInnertalkDService extends IService<XfBInnertalkD> {

        IPage<XfBInnertalkD> findXfBInnertalkDs(QueryRequest request, XfBInnertalkD xfBInnertalkD);

        IPage<XfBInnertalkD> findXfBInnertalkDList(QueryRequest request, XfBInnertalkD xfBInnertalkD);

        void createXfBInnertalkD(XfBInnertalkD xfBInnertalkD);

        void updateXfBInnertalkD(XfBInnertalkD xfBInnertalkD);

        void deleteXfBInnertalkDs(String[]Ids);


        }
