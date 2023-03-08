package cc.mrbird.febs.xf.service;

import cc.mrbird.febs.xf.entity.XfBProject;
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
public interface IXfBProjectService extends IService<XfBProject> {

        IPage<XfBProject> findXfBProjects(QueryRequest request, XfBProject xfBProject);

        IPage<XfBProject> findXfBProjectList(QueryRequest request, XfBProject xfBProject);

        void createXfBProject(XfBProject xfBProject);

        void updateXfBProject(XfBProject xfBProject);

        void deleteXfBProjects(String[]Ids);


        }
