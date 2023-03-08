package cc.mrbird.febs.xf.service;

import cc.mrbird.febs.xf.entity.XfBRules;
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
 * @since 2022-11-01
 */
public interface IXfBRulesService extends IService<XfBRules> {

        IPage<XfBRules> findXfBRuless(QueryRequest request, XfBRules xfBRules);

        IPage<XfBRules> findXfBRulesList(QueryRequest request, XfBRules xfBRules);

        void createXfBRules(XfBRules xfBRules);

        void updateXfBRules(XfBRules xfBRules);

        void deleteXfBRuless(String[]Ids);


        }
