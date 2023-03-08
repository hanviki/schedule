package cc.mrbird.febs.dept.service;

import cc.mrbird.febs.dept.entity.DeptBUser;
import cc.mrbird.febs.dept.entity.DicType;
import cc.mrbird.febs.dept.entity.ViewDept;
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
public interface IDeptBUserService extends IService<DeptBUser> {

        IPage<DeptBUser> findDeptBUsers(QueryRequest request, DeptBUser deptBUser);

        IPage<ViewDept> findDeptBUserList(QueryRequest request, ViewDept deptBUser);

        void createDeptBUser(DeptBUser deptBUser);

        void updateDeptBUser(DeptBUser deptBUser);

        void deleteDeptBUsers(String[]Ids);

    List<DicType>  getDicType(String account);
        }
