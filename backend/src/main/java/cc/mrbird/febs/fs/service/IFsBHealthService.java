package cc.mrbird.febs.fs.service;

import cc.mrbird.febs.fs.entity.FsBHealth;
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
public interface IFsBHealthService extends IService<FsBHealth> {

        IPage<FsBHealth> findFsBHealths(QueryRequest request, FsBHealth fsBHealth);

        IPage<FsBHealth> findFsBHealthList(QueryRequest request, FsBHealth fsBHealth);

        void createFsBHealth(FsBHealth fsBHealth);

        void updateFsBHealth(FsBHealth fsBHealth);

        void deleteFsBHealths(String[]Ids);

        List<FsBHealth> findHByAccounts( List<String> accountList);
        }
