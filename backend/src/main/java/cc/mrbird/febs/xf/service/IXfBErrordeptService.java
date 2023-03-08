package cc.mrbird.febs.xf.service;

import cc.mrbird.febs.xf.entity.XfBErrordept;
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
public interface IXfBErrordeptService extends IService<XfBErrordept> {

        IPage<XfBErrordept> findXfBErrordepts(QueryRequest request, XfBErrordept xfBErrordept);

        IPage<XfBErrordept> findXfBErrordeptList(QueryRequest request, XfBErrordept xfBErrordept);

        void createXfBErrordept(XfBErrordept xfBErrordept);

        void updateXfBErrordept(XfBErrordept xfBErrordept);

        void deleteXfBErrordepts(String[]Ids);


        }
