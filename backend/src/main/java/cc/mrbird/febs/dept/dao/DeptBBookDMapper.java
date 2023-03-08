package cc.mrbird.febs.dept.dao;

import cc.mrbird.febs.dept.entity.DeptBBookD;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2023-02-28
 */
public interface DeptBBookDMapper extends BaseMapper<DeptBBookD> {
        void updateDeptBBookD(DeptBBookD deptBBookD);
        IPage<DeptBBookD> findDeptBBookD(Page page, @Param("deptBBookD") DeptBBookD deptBBookD);
        }
