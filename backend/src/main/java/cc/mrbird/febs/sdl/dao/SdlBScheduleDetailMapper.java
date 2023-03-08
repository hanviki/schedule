package cc.mrbird.febs.sdl.dao;

import cc.mrbird.febs.sdl.entity.CustomData;
import cc.mrbird.febs.sdl.entity.SdlBScheduleDetail;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 排班子表明细 Mapper 接口
 * </p>
 *
 * @author viki
 * @since 2021-10-13
 */
public interface SdlBScheduleDetailMapper extends BaseMapper<SdlBScheduleDetail> {
        void updateSdlBScheduleDetail(SdlBScheduleDetail sdlBScheduleDetail);
        IPage<SdlBScheduleDetail> findSdlBScheduleDetail(Page page, @Param("sdlBScheduleDetail") SdlBScheduleDetail sdlBScheduleDetail);

        List<CustomData>  findSdlBScheduleReport( @Param("sdlBScheduleDetail")SdlBScheduleDetail sdlBScheduleDetail);
        List<CustomData>  findSdlBScheduleReport2( @Param("sdlBScheduleDetail")SdlBScheduleDetail sdlBScheduleDetail);
        List<String> findAllBaseId(@Param("sdlBScheduleDetail")SdlBScheduleDetail sdlBScheduleDetail);
        List<CustomData>  findYeBanReport( @Param("sdlBScheduleDetail")SdlBScheduleDetail sdlBScheduleDetail);
        List<CustomData>  findYeBanSubReport( @Param("sdlBScheduleDetail")SdlBScheduleDetail sdlBScheduleDetail);
        List<CustomData>  findMenZhenReport( @Param("sdlBScheduleDetail")SdlBScheduleDetail sdlBScheduleDetail);
        List<CustomData>  findMenZhenSubReport( @Param("sdlBScheduleDetail")SdlBScheduleDetail sdlBScheduleDetail);

        List<SdlBScheduleDetail>  findSdlBScheduleReportAccount2( @Param("sdlBScheduleDetail")SdlBScheduleDetail sdlBScheduleDetail);
        List<SdlBScheduleDetail>  findSdlBScheduleReportAccount( @Param("sdlBScheduleDetail")SdlBScheduleDetail sdlBScheduleDetail);
        List<SdlBScheduleDetail>  findYeBanSubReportAccount( @Param("sdlBScheduleDetail")SdlBScheduleDetail sdlBScheduleDetail);
        List<SdlBScheduleDetail>  findMenZhenSubReportAccount( @Param("sdlBScheduleDetail")SdlBScheduleDetail sdlBScheduleDetail);

        void updateXuelie(@Param("month") String month);
}
