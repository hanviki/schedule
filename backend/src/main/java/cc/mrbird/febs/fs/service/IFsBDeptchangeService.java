package cc.mrbird.febs.fs.service;

import cc.mrbird.febs.fs.entity.FsBDeptchange;
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
 * @since 2022-07-08
 */
public interface IFsBDeptchangeService extends IService<FsBDeptchange> {

        IPage<FsBDeptchange> findFsBDeptchanges(QueryRequest request, FsBDeptchange fsBDeptchange);

        IPage<FsBDeptchange> findFsBDeptchangeList(QueryRequest request, FsBDeptchange fsBDeptchange);

        void createFsBDeptchange(FsBDeptchange fsBDeptchange);

        void updateFsBDeptchange(FsBDeptchange fsBDeptchange);

        void deleteFsBDeptchanges(String[]Ids);


        }
