package cc.mrbird.febs.sdl.service;

import cc.mrbird.febs.sdl.entity.SdlBSchedule;
import cc.mrbird.febs.sdl.entity.SdlBScheduleD;
import cc.mrbird.febs.sdl.entity.SdlDBanci;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 排班子表 服务类
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
public interface ISdlBScheduleDService extends IService<SdlBScheduleD> {

        IPage<SdlBScheduleD> findSdlBScheduleDs(QueryRequest request, SdlBScheduleD sdlBScheduleD);

        IPage<SdlBScheduleD> findSdlBScheduleDList(QueryRequest request, SdlBScheduleD sdlBScheduleD);

        void createSdlBScheduleD(SdlBScheduleD sdlBScheduleD);

        void updateSdlBScheduleD(SdlBScheduleD sdlBScheduleD);

        void deleteSdlBScheduleDs(String[]Ids);


        List<SdlBScheduleD> getPaiBanZizhi(SdlBScheduleD sdlBScheduleD);



        void deleteByDeptAndDate(String deptId,String startDate,String endDate);
        void deleteByDeptAndBaseId(String deptId,String baseID);
        }
