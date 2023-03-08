package cc.mrbird.febs.xf.service;

import cc.mrbird.febs.xf.entity.XfBAsset;
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
 * @since 2022-11-01
 */
public interface IXfBAssetService extends IService<XfBAsset> {

        IPage<XfBAsset> findXfBAssets(QueryRequest request, XfBAsset xfBAsset);

        IPage<XfBAsset> findXfBAssetList(QueryRequest request, XfBAsset xfBAsset);

        void createXfBAsset(XfBAsset xfBAsset);

        void updateXfBAsset(XfBAsset xfBAsset);

        void deleteXfBAssets(String[]Ids);


        }
