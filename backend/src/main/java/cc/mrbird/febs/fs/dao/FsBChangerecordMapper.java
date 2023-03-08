package cc.mrbird.febs.fs.dao;

import cc.mrbird.febs.fs.entity.FsBChangerecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2023-02-01
 */
public interface FsBChangerecordMapper extends BaseMapper<FsBChangerecord> {
        void updateFsBChangerecord(FsBChangerecord fsBChangerecord);
        IPage<FsBChangerecord> findFsBChangerecord(Page page, @Param("fsBChangerecord") FsBChangerecord fsBChangerecord);
        }
