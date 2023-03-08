package cc.mrbird.febs.fs.service;

import cc.mrbird.febs.fs.entity.FsBQuarterD;
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
public interface IFsBQuarterDService extends IService<FsBQuarterD> {

        IPage<FsBQuarterD> findFsBQuarterDs(QueryRequest request, FsBQuarterD fsBQuarterD);

        IPage<FsBQuarterD> findFsBQuarterDList(QueryRequest request, FsBQuarterD fsBQuarterD);

        IPage<FsBQuarterD> findReport(QueryRequest request, FsBQuarterD fsBQuarterD);

        void createFsBQuarterD(FsBQuarterD fsBQuarterD);

        void updateFsBQuarterD(FsBQuarterD fsBQuarterD);

        void deleteFsBQuarterDs(String[]Ids);


        }
