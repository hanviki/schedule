package cc.mrbird.febs.xf.dao;

import cc.mrbird.febs.xf.entity.XfBRules;
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
public interface XfBRulesMapper extends BaseMapper<XfBRules> {
        void updateXfBRules(XfBRules xfBRules);
        IPage<XfBRules> findXfBRules(Page page, @Param("xfBRules") XfBRules xfBRules);
        }
