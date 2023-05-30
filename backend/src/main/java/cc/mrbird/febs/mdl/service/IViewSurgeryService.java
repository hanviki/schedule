package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.ViewSurgery;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author viki
 * @since 2023-04-20
 */
public interface IViewSurgeryService extends IService<ViewSurgery> {

        IPage<ViewSurgery> findViewSurgerys(QueryRequest request, ViewSurgery viewSurgery);

        IPage<ViewSurgery> findViewSurgeryList(QueryRequest request, ViewSurgery viewSurgery);

        void createViewSurgery(ViewSurgery viewSurgery);

        void updateViewSurgery(ViewSurgery viewSurgery);

        void deleteViewSurgerys(String[]Ids);


        }
