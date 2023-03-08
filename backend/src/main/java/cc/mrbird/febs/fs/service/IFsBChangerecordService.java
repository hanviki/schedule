package cc.mrbird.febs.fs.service;

import cc.mrbird.febs.fs.entity.FsBChangerecord;
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
 * @since 2023-01-31
 */
public interface IFsBChangerecordService extends IService<FsBChangerecord> {

        IPage<FsBChangerecord> findFsBChangerecords(QueryRequest request, FsBChangerecord fsBChangerecord);

        IPage<FsBChangerecord> findFsBChangerecordList(QueryRequest request, FsBChangerecord fsBChangerecord);

        void createFsBChangerecord(FsBChangerecord fsBChangerecord);

        void updateFsBChangerecord(FsBChangerecord fsBChangerecord);

        void deleteFsBChangerecords(String[]Ids);


        }
