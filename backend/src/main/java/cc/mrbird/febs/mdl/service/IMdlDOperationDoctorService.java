package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlDOperationDoctor;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 操作分级目录及资质授权 服务类
 * </p>
 *
 * @author viki
 * @since 2023-03-16
 */
public interface IMdlDOperationDoctorService extends IService<MdlDOperationDoctor> {

        IPage<MdlDOperationDoctor> findMdlDOperationDoctors(QueryRequest request, MdlDOperationDoctor mdlDOperationDoctor);

        IPage<MdlDOperationDoctor> findMdlDOperationDoctorList(QueryRequest request, MdlDOperationDoctor mdlDOperationDoctor);

        void createMdlDOperationDoctor(MdlDOperationDoctor mdlDOperationDoctor);

        void updateMdlDOperationDoctor(MdlDOperationDoctor mdlDOperationDoctor);

        void deleteMdlDOperationDoctors(String[]Ids);


        }
