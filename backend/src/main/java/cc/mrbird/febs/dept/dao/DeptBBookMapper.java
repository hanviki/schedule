package cc.mrbird.febs.dept.dao;

import cc.mrbird.febs.dept.entity.DeptBBook;
import cc.mrbird.febs.dept.entity.ViewDicType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2023-02-28
 */
public interface DeptBBookMapper extends BaseMapper<DeptBBook> {
        void updateDeptBBook(DeptBBook deptBBook);
        IPage<DeptBBook> findDeptBBook(Page page, @Param("deptBBook") DeptBBook deptBBook);

       List<ViewDicType> findBook(@Param("account") String account,@Param("accountList")List<String> accountList);
        }
