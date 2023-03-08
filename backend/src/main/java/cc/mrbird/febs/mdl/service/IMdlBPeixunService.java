package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBPeixun;
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
 * @since 2023-01-05
 */
public interface IMdlBPeixunService extends IService<MdlBPeixun> {

        IPage<MdlBPeixun> findMdlBPeixuns(QueryRequest request, MdlBPeixun mdlBPeixun);

        IPage<MdlBPeixun> findMdlBPeixunList(QueryRequest request, MdlBPeixun mdlBPeixun);

        void createMdlBPeixun(MdlBPeixun mdlBPeixun);

        void updateMdlBPeixun(MdlBPeixun mdlBPeixun);

        void deleteMdlBPeixuns(String[]Ids);


        }
