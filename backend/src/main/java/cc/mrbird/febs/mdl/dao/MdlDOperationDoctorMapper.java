package cc.mrbird.febs.mdl.dao;

import cc.mrbird.febs.mdl.entity.MdlDOperationDoctor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 操作分级目录及资质授权 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2023-03-16
 */
public interface MdlDOperationDoctorMapper extends BaseMapper<MdlDOperationDoctor> {
        void updateMdlDOperationDoctor(MdlDOperationDoctor mdlDOperationDoctor);
        IPage<MdlDOperationDoctor> findMdlDOperationDoctor(Page page, @Param("mdlDOperationDoctor") MdlDOperationDoctor mdlDOperationDoctor);
        }
