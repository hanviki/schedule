package cc.mrbird.febs.fs.dao;

import cc.mrbird.febs.fs.entity.FsBDeptchange;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-07-08
 */
public interface FsBDeptchangeMapper extends BaseMapper<FsBDeptchange> {
    void updateFsBDeptchange(FsBDeptchange fsBDeptchange);

    IPage<FsBDeptchange> findFsBDeptchange(Page page, @Param("fsBDeptchange") FsBDeptchange fsBDeptchange);

    @Update("update dept_b_user a,t_dept b set a.dept_id=b.DEPT_ID ,a.dept_name=b.DEPT_NAME where a.user_account =#{userAccount} and b.DEPT_ID =#{deptId}")
    void updateUserDept(@Param("userAccount") String userAccount, @Param("deptId") String deptId);

    @Update("update dept_b_user set dept_id='',dept_name='' where user_account =#{userAccount}")
    void deleteUserDept(@Param("userAccount") String userAccount);
}
