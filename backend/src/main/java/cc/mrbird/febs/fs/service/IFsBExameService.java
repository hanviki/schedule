package cc.mrbird.febs.fs.service;

import cc.mrbird.febs.fs.entity.FsBExame;
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
public interface IFsBExameService extends IService<FsBExame> {

        IPage<FsBExame> findFsBExames(QueryRequest request, FsBExame fsBExame);

        IPage<FsBExame> findFsBExameList(QueryRequest request, FsBExame fsBExame);

        void createFsBExame(FsBExame fsBExame);

        void updateFsBExame(FsBExame fsBExame);

        void deleteFsBExames(String[]Ids);

        List<FsBExame> findExameAccounts( List<String> accountList);

        List<FsBExame> findExameAccountsAll( List<String> accountList);
        }
