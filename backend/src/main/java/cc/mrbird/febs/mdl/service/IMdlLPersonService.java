package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlLPerson;
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
public interface IMdlLPersonService extends IService<MdlLPerson> {

        IPage<MdlLPerson> findMdlLPersons(QueryRequest request, MdlLPerson mdlLPerson);

        IPage<MdlLPerson> findMdlLPersonList(QueryRequest request, MdlLPerson mdlLPerson);

        void createMdlLPerson(MdlLPerson mdlLPerson);

        void updateMdlLPerson(MdlLPerson mdlLPerson);

        void deleteMdlLPersons(String[]Ids);

        void deleteByBaseId(String baseId);


        }
