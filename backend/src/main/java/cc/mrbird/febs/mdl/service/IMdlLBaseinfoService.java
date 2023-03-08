package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlLBaseinfo;
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
 * @since 2022-11-29
 */
public interface IMdlLBaseinfoService extends IService<MdlLBaseinfo> {

        IPage<MdlLBaseinfo> findMdlLBaseinfos(QueryRequest request, MdlLBaseinfo mdlLBaseinfo);

        IPage<MdlLBaseinfo> findMdlLBaseinfoList(QueryRequest request, MdlLBaseinfo mdlLBaseinfo);

        void createMdlLBaseinfo(MdlLBaseinfo mdlLBaseinfo);

        void updateMdlLBaseinfo(MdlLBaseinfo mdlLBaseinfo);

        void deleteMdlLBaseinfos(String[]Ids);


        }
