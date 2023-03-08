package cc.mrbird.febs.xf.service;

import cc.mrbird.febs.xf.entity.XfBArchive;
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
public interface IXfBArchiveService extends IService<XfBArchive> {

        IPage<XfBArchive> findXfBArchives(QueryRequest request, XfBArchive xfBArchive);

        IPage<XfBArchive> findXfBArchiveList(QueryRequest request, XfBArchive xfBArchive);

        void createXfBArchive(XfBArchive xfBArchive);

        void updateXfBArchive(XfBArchive xfBArchive);

        void deleteXfBArchives(String[]Ids);


        }
