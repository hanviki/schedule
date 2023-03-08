package cc.mrbird.febs.fs.service;

import cc.mrbird.febs.fs.entity.FsBQy;
import cc.mrbird.febs.fs.entity.FsBQyReport;
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
 * @since 2022-07-13
 */
public interface IFsBQyService extends IService<FsBQy> {

        IPage<FsBQy> findFsBQys(QueryRequest request, FsBQy fsBQy);

        IPage<FsBQy> findFsBQyList(QueryRequest request, FsBQy fsBQy);

        IPage<FsBQyReport> report(QueryRequest request, FsBQy fsBQy);

        void createFsBQy(FsBQy fsBQy);

        void updateFsBQy(FsBQy fsBQy);

        void deleteFsBQys(String[]Ids);


        }
