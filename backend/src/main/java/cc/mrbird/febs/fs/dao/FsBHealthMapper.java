package cc.mrbird.febs.fs.dao;

import cc.mrbird.febs.fs.entity.FsBHealth;
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
 * @since 2022-07-13
 */
public interface FsBHealthMapper extends BaseMapper<FsBHealth> {
        void updateFsBHealth(FsBHealth fsBHealth);
        IPage<FsBHealth> findFsBHealth(Page page, @Param("fsBHealth") FsBHealth fsBHealth);

       List<FsBHealth> findHByAccounts(@Param("accountList") List<String> accountList);
        }
