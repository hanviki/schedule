package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBKaoshi;
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
public interface IMdlBKaoshiService extends IService<MdlBKaoshi> {

        IPage<MdlBKaoshi> findMdlBKaoshis(QueryRequest request, MdlBKaoshi mdlBKaoshi);

        IPage<MdlBKaoshi> findMdlBKaoshiList(QueryRequest request, MdlBKaoshi mdlBKaoshi);

        void createMdlBKaoshi(MdlBKaoshi mdlBKaoshi);

        void updateMdlBKaoshi(MdlBKaoshi mdlBKaoshi);

        void deleteMdlBKaoshis(String[]Ids);


        }
