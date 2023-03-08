package cc.mrbird.febs.dept.service;

import cc.mrbird.febs.dept.entity.DeptBBookD;
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
 * @since 2023-02-28
 */
public interface IDeptBBookDService extends IService<DeptBBookD> {

        IPage<DeptBBookD> findDeptBBookDs(QueryRequest request, DeptBBookD deptBBookD);

        IPage<DeptBBookD> findDeptBBookDList(QueryRequest request, DeptBBookD deptBBookD);

        void createDeptBBookD(DeptBBookD deptBBookD);

        void updateDeptBBookD(DeptBBookD deptBBookD);

        void deleteDeptBBookDs(String[]Ids);


        }
