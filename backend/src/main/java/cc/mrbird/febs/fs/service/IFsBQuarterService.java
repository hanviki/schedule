package cc.mrbird.febs.fs.service;

import cc.mrbird.febs.fs.entity.FsBQuarter;
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
 * @since 2022-07-12
 */
public interface IFsBQuarterService extends IService<FsBQuarter> {

        IPage<FsBQuarter> findFsBQuarters(QueryRequest request, FsBQuarter fsBQuarter);

        IPage<FsBQuarter> findFsBQuarterList(QueryRequest request, FsBQuarter fsBQuarter);

        void createFsBQuarter(FsBQuarter fsBQuarter);

        void updateFsBQuarter(FsBQuarter fsBQuarter);

        void deleteFsBQuarters(String[]Ids);


        }
