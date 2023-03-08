package cc.mrbird.febs.xf.dao;

import cc.mrbird.febs.xf.entity.XfBErrordept;
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
 * @since 2022-11-01
 */
public interface XfBErrordeptMapper extends BaseMapper<XfBErrordept> {
        void updateXfBErrordept(XfBErrordept xfBErrordept);
        IPage<XfBErrordept> findXfBErrordept(Page page, @Param("xfBErrordept") XfBErrordept xfBErrordept);
        }
