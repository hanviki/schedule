package cc.mrbird.febs.fs.dao;

import cc.mrbird.febs.fs.entity.FsBRecord;
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
 * @since 2022-07-11
 */
public interface FsBRecordMapper extends BaseMapper<FsBRecord> {
        void updateFsBRecord(FsBRecord fsBRecord);
        IPage<FsBRecord> findFsBRecord(Page page, @Param("fsBRecord") FsBRecord fsBRecord);

       List<FsBRecord> findFsBRecord2(@Param("accountList") List<String> accountList);
        }
