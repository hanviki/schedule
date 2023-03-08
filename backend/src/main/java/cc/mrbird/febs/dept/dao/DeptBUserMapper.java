package cc.mrbird.febs.dept.dao;

import cc.mrbird.febs.dept.entity.DeptBUser;
import cc.mrbird.febs.dept.entity.DicType;
import cc.mrbird.febs.dept.entity.ViewDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2023-02-28
 */
public interface DeptBUserMapper extends BaseMapper<DeptBUser> {
        void updateDeptBUser(DeptBUser deptBUser);
        IPage<DeptBUser> findDeptBUser(Page page, @Param("deptBUser") DeptBUser deptBUser);

        IPage<ViewDept>  getUser2(Page page, @Param("user") ViewDept user);

        @Select("select *  from dept_d_dic where account_name=#{account}")
    List<DicType>  getDicType(@Param("account") String account);
        }
