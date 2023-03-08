package cc.mrbird.febs.dept.service;

import cc.mrbird.febs.dept.entity.DeptBBook;
import cc.mrbird.febs.dept.entity.ViewDicType;
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
public interface IDeptBBookService extends IService<DeptBBook> {

        IPage<DeptBBook> findDeptBBooks(QueryRequest request, DeptBBook deptBBook);

        IPage<DeptBBook> findDeptBBookList(QueryRequest request, DeptBBook deptBBook);

        void createDeptBBook(DeptBBook deptBBook);

        void updateDeptBBook(DeptBBook deptBBook);

        void deleteDeptBBooks(String[]Ids);

        List<ViewDicType> findBook( String account, List<String> accountList);
        }
