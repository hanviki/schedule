package cc.mrbird.febs.fs.service;

import cc.mrbird.febs.fs.entity.FsBRecord;
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
 * @since 2022-07-11
 */
public interface IFsBRecordService extends IService<FsBRecord> {

        IPage<FsBRecord> findFsBRecords(QueryRequest request, FsBRecord fsBRecord);

        IPage<FsBRecord> findFsBRecordList(QueryRequest request, FsBRecord fsBRecord);

        void createFsBRecord(FsBRecord fsBRecord);

        void updateFsBRecord(FsBRecord fsBRecord);

        void deleteFsBRecords(String[]Ids);

        List<FsBRecord> findFsBRecord( List<String> accountList);
        }
