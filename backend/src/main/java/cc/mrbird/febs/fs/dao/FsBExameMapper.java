package cc.mrbird.febs.fs.dao;

import cc.mrbird.febs.fs.entity.FsBExame;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2022-07-12
 */
public interface FsBExameMapper extends BaseMapper<FsBExame> {
    void updateFsBExame(FsBExame fsBExame);

    IPage<FsBExame> findFsBExame(Page page, @Param("fsBExame") FsBExame fsBExame);

    List<FsBExame> findExameAccounts(@Param("accountList") List<String> accountList);

    List<FsBExame> findExameAccountsAll(@Param("accountList") List<String> accountList);
}
