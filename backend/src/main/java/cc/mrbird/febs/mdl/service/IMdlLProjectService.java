package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlLProject;
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
 * @since 2022-11-29
 */
public interface IMdlLProjectService extends IService<MdlLProject> {

        IPage<MdlLProject> findMdlLProjects(QueryRequest request, MdlLProject mdlLProject);

        IPage<MdlLProject> findMdlLProjectList(QueryRequest request, MdlLProject mdlLProject);

        void createMdlLProject(MdlLProject mdlLProject);

        void updateMdlLProject(MdlLProject mdlLProject);

        void deleteMdlLProjects(String[]Ids);


        }
