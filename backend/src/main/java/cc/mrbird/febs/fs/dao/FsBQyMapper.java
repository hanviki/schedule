package cc.mrbird.febs.fs.dao;

import cc.mrbird.febs.fs.entity.FsBQy;
import cc.mrbird.febs.fs.entity.FsBQyReport;
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
 * @since 2022-07-13
 */
public interface FsBQyMapper extends BaseMapper<FsBQy> {
        void updateFsBQy(FsBQy fsBQy);
        IPage<FsBQy> findFsBQy(Page page, @Param("fsBQy") FsBQy fsBQy);
        IPage<FsBQyReport> report(Page page, @Param("fsBQy") FsBQy fsBQy);

        /**
         * 获取是否大剂量监测
         * @return
         */
        @Select("SELECT a.number from fs_b_qy a inner join (\n" +
                "select max(start_date) start_date,number  from fs_b_qy GROUP BY number)p\n" +
                "on a.start_date=p.start_date and a.number=p.number\n" +
                "where a.is_big='是'")
        List<String> getNumberBig();
        }
