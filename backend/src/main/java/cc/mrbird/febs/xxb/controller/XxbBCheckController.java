
package cc.mrbird.febs.xxb.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.properties.FebsProperties;
import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.export.pdf.XxbBPdfInfo;
import cc.mrbird.febs.scm.entity.ComFile;
import cc.mrbird.febs.scm.service.IComFileService;
import cc.mrbird.febs.xxb.entity.*;
import cc.mrbird.febs.xxb.service.*;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.beust.jcommander.internal.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.PropertySource;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author viki
 * @since 2022-03-29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("xxbBCheck")

public class XxbBCheckController extends BaseController {

    private String message;
    @Autowired
    public IXxbBCheckService iXxbBCheckService;
    @Autowired
    private FebsProperties febsProperties;

    @Autowired
    private IXxbBArchiveService iXxbBArchiveService;

    @Autowired
    private IXxbBDeptflowService iXxbBDeptflowService;

    @Autowired
    IXxbBProjdeptService iXxbBProjdeptService;

    @Autowired
    private IComFileService iComFileService;
    @Autowired
    private IXxbBCheckDService iXxbBCheckDService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/XxbBCheck/XxbBCheck','ass/XxbBCheck/XxbBCheck','xxbBCheck:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xxbBCheck:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xxbBCheck:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xxbBCheck:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xxbBCheck:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xxbBCheck:import',1,5,NOW());
 */


    /**
     * ??????????????????
     *
     * @param request   ????????????
     * @param xxbBCheck ????????????
     * @return
     */
    @GetMapping
    @RequiresPermissions("xxbBCheck:view")
    public Map<String, Object> List(QueryRequest request, XxbBCheck xxbBCheck) {
        User currentUser = FebsUtil.getCurrentUser();
        return getDataTable(this.iXxbBCheckService.findXxbBChecks(request, xxbBCheck, currentUser));
    }
    @GetMapping("report")
    public Map<String, Object> List10(QueryRequest request, XxbBCheck xxbBCheck) {
        User currentUser = FebsUtil.getCurrentUser();
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(xxbBCheck.getYggh())){
            LambdaQueryWrapper<XxbBDeptflow> xxbBDeptflowLambdaQueryWrapper= new LambdaQueryWrapper<>();
            xxbBDeptflowLambdaQueryWrapper.eq(XxbBDeptflow::getFlowAccount,xxbBCheck.getYggh());
            xxbBDeptflowLambdaQueryWrapper.eq(XxbBDeptflow::getIsDeletemark,1);
           List<XxbBDeptflow> xxbBDeptflows= this.iXxbBDeptflowService.list(xxbBDeptflowLambdaQueryWrapper);
            if(xxbBDeptflows.size()>0){
                List<String> pids= xxbBDeptflows.stream().map(p->p.getPid()).collect(Collectors.toList());
                xxbBCheck.setIdList(pids);
            }
        }
        return getDataTable(this.iXxbBCheckService.findXxbBCheckList(request, xxbBCheck));
    }
    @GetMapping("zqList")
    public Map<String, Object> List2(QueryRequest request, XxbBCheck xxbBCheck) {
        User currentUser = FebsUtil.getCurrentUser();
        xxbBCheck.setCreateUserId(currentUser.getUserId());
        return getDataTable(this.iXxbBCheckService.zqList(request, xxbBCheck));
    }
    @GetMapping("zqListAudit")
    public Map<String, Object> List3(QueryRequest request, XxbBCheck xxbBCheck) {
        User currentUser = FebsUtil.getCurrentUser();
        xxbBCheck.setCreateUserId(currentUser.getUserId());
        return getDataTable(this.iXxbBCheckService.zqListAudit(request, xxbBCheck));
    }
    @GetMapping("mqList")
    public Map<String, Object> List4(QueryRequest request, XxbBCheck xxbBCheck) {
        User currentUser = FebsUtil.getCurrentUser();
        xxbBCheck.setCreateUserId(currentUser.getUserId());
        return getDataTable(this.iXxbBCheckService.mqList(request, xxbBCheck));
    }
    @GetMapping("mqListAudit")
    public Map<String, Object> List5(QueryRequest request, XxbBCheck xxbBCheck) {
        User currentUser = FebsUtil.getCurrentUser();
        xxbBCheck.setCreateUserId(currentUser.getUserId());
        return getDataTable(this.iXxbBCheckService.mqListAudit(request, xxbBCheck));
    }
    @GetMapping("flowList")
    @RequiresPermissions("xxbBCheck:view")
    public Map<String, Object> flowList(QueryRequest request, XxbBCheck xxbBCheck) {
        User currentUser = FebsUtil.getCurrentUser();
        return getDataTable(this.iXxbBCheckService.findXxbBflow(request, xxbBCheck, currentUser));
    }

    @GetMapping("getId")
    public FebsResponse getId() {
        return new FebsResponse().data(UUID.randomUUID().toString());
    }

    @GetMapping("findCheckD")
    @RequiresPermissions("xxbBCheck:view")
    public FebsResponse findCheckD(String baseId) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<XxbBCheckD> xxbBCheckDataList = new ArrayList<>();
        try {
//            User currentUser = FebsUtil.getCurrentUser();
            xxbBCheckDataList = this.iXxbBCheckService.getCheckDataList(baseId);
            xxbBCheckDataList= xxbBCheckDataList.stream().sorted(Comparator.nullsLast(Comparator.comparing(XxbBCheckD::getDisplayIndex,Comparator.nullsLast(Integer::compareTo)))).collect(Collectors.toList());
            success = 1;
        } catch (Exception e) {
            message = "????????????.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", xxbBCheckDataList);
        return new FebsResponse().data(map);
    }

    @GetMapping("findProjDept")
    @RequiresPermissions("xxbBCheck:view")
    public FebsResponse findProjDept(String baseId) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<XxbBProjdept> xxbBCheckDataList = new ArrayList<>();
        try {
//            User currentUser = FebsUtil.getCurrentUser();
            xxbBCheckDataList = this.iXxbBCheckService.getProjDeptList(baseId);
            success = 1;
        } catch (Exception e) {
            message = "????????????.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", xxbBCheckDataList);
        return new FebsResponse().data(map);
    }

    @GetMapping("findDeptFlow")
    @RequiresPermissions("xxbBCheck:view")
    public FebsResponse findDeptFlow(String baseId,int state) {
        ModelMap map = new ModelMap();
        int success = 0;
        List<XxbBDeptflow> xxbBCheckDataList = new ArrayList<>();
        try {
            User currentUser = FebsUtil.getCurrentUser();
            xxbBCheckDataList = this.iXxbBCheckService.getDeptFlowList(baseId,currentUser,state);
            success = 1;
        } catch (Exception e) {
            message = "????????????.";
            log.error(message, e);
        }

        map.put("success", success);
        map.put("message", message);
        map.put("data", xxbBCheckDataList);
        return new FebsResponse().data(map);
    }


    @PutMapping("editDeptFlow")
    @RequiresPermissions("xxbBCheck:update")
    public FebsResponse editDeptFlow(@Valid XxbBDeptflow deptflow) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            User currentUser = FebsUtil.getCurrentUser();
            this.iXxbBCheckService.updateDeptflow(deptflow);
            success = 1;
        } catch (Exception e) {
            message = "????????????";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }

    @GetMapping("delCheckD")
    @RequiresPermissions("xxbBCheck:delete")
    public FebsResponse getDelId(String id) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            this.iXxbBCheckService.deleteCheckData(id);
            success = 1;
        } catch (Exception e) {
            message = "????????????.";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }

    @GetMapping("delProjDept")
    @RequiresPermissions("xxbBCheck:delete")
    public FebsResponse getDelProjDeptId(String id) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            this.iXxbBCheckService.deleteProjDeptData(id);
            success = 1;
        } catch (Exception e) {
            message = "????????????.";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        return new FebsResponse().data(map);
    }

    @GetMapping("addCheckD")
    @RequiresPermissions("xxbBCheck:add")
    public FebsResponse addCheckD(XxbBCheckD checkData) {
        ModelMap map = new ModelMap();
        int success = 0;
        try {
            checkData = this.iXxbBCheckService.addCheckData(checkData);
            if (checkData.getId() != null) {
                success = 1;
            } else {
                checkData = new XxbBCheckD();
            }
        } catch (Exception e) {
            message = "????????????.";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        map.put("data", checkData);
        return new FebsResponse().data(map);
    }

    /**
     * ??????
     *
     * @param xxbBCheck
     * @return
     */
    @Log("??????/??????")
    @PostMapping
    @RequiresPermissions("xxbBCheck:add")
    public void addXxbBCheck(@Valid XxbBCheck xxbBCheck) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            xxbBCheck.setCreateUserId(currentUser.getUserId());
            this.iXxbBCheckService.createXxbBCheck(xxbBCheck);
        } catch (Exception e) {
            message = "??????/????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("??????/??????")
    @PutMapping("editInfo")
    @RequiresPermissions("xxbBCheck:update")
    public FebsResponse AddInfo(String data) {
        ModelMap map = new ModelMap();
        int success = 0;
        String id = "";
        try {
            JSONObject json = JSONObject.parseObject(data);
            XxbBCheckInfo xxbBCheckInfo = JSON.toJavaObject(json, XxbBCheckInfo.class);
            User currentUser = FebsUtil.getCurrentUser();
            id = this.iXxbBCheckService.editXxbBCheck(xxbBCheckInfo, currentUser);
            success = 1;
        } catch (Exception e) {
            message = "??????/????????????";
            log.error(message, e);
        }
        map.put("success", success);
        map.put("message", message);
        map.put("data", id);
        return new FebsResponse().data(map);
    }

    /**
     * ??????
     *
     * @param xxbBCheck
     * @return
     */
    @Log("??????")
    @PutMapping
    @RequiresPermissions("xxbBCheck:update")
    public void updateXxbBCheck(@Valid XxbBCheck xxbBCheck) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            xxbBCheck.setModifyUserId(currentUser.getUserId());
            this.iXxbBCheckService.updateXxbBCheck(xxbBCheck);
        } catch (Exception e) {
            message = "????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("??????????????????")
    @PutMapping("updateShState")
    public void updateShState(String ids,int shstate, String archiveId) throws FebsException{
        List<String> list = Arrays.asList(ids);
        LambdaQueryWrapper<XxbBCheck> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(XxbBCheck::getState, 2);
        wrapper.in(XxbBCheck::getId, list);
        List<XxbBCheck> updateList = this.iXxbBCheckService.list(wrapper);
        XxbBArchive archive =new XxbBArchive();
        if(StringUtils.isNotEmpty(archiveId)) {
             archive = iXxbBArchiveService.getById(Integer.parseInt(archiveId));
            if (archive == null) {
                throw new FebsException("?????????????????????");
            }
        }
        for (XxbBCheck check:updateList
             ) {
            XxbBCheck update= new XxbBCheck();
            update.setId(check.getId());
            update.setShstate(shstate);
            if(shstate==1){
            update.setArchiveId(archive.getId().toString());
            update.setFileId(archive.getFileId());
            update.setFileUrl(archive.getFileUrl());
            update.setSrtdat(archive.getFileDate());

            if(check.getProjectLevel().equals(1)||check.getProjectLevel().equals(2)) {
                update.setEnddat(DateUtil.offsetMonth(archive.getFileDate(), 48));
                update.setZqDate(DateUtil.offsetMonth(archive.getFileDate(), 23));
                update.setMqDate(DateUtil.offsetMonth(archive.getFileDate(), 47));
            }
            else {
                update.setEnddat(DateUtil.offsetMonth(archive.getFileDate(), 24));
                update.setZqDate(DateUtil.offsetMonth(archive.getFileDate(), 11));
                update.setMqDate(DateUtil.offsetMonth(archive.getFileDate(), 23));
            }

                update.setXmjdstate(1);
            }
            this.iXxbBCheckService.updateXxbBCheck(update);
        }

    }

    @Log("??????")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("xxbBCheck:delete")
    public void deleteXxbBChecks(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iXxbBCheckService.deleteXxbBChecks(arr_ids);
        } catch (Exception e) {
            message = "????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    public void export(QueryRequest request, XxbBCheck xxbBCheck, HttpServletResponse response) throws FebsException {
        try {
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(xxbBCheck.getYggh())){
                LambdaQueryWrapper<XxbBDeptflow> xxbBDeptflowLambdaQueryWrapper= new LambdaQueryWrapper<>();
                xxbBDeptflowLambdaQueryWrapper.eq(XxbBDeptflow::getFlowAccount,xxbBCheck.getYggh());
                xxbBDeptflowLambdaQueryWrapper.eq(XxbBDeptflow::getIsDeletemark,1);
                List<XxbBDeptflow> xxbBDeptflows= this.iXxbBDeptflowService.list(xxbBDeptflowLambdaQueryWrapper);
                if(xxbBDeptflows.size()>0){
                    List<String> pids= xxbBDeptflows.stream().map(p->p.getPid()).collect(Collectors.toList());
                    xxbBCheck.setIdList(pids);
                }
            }
            List<XxbBCheck> xxbBChecks= this.iXxbBCheckService.findXxbBCheckList(request, xxbBCheck).getRecords();
          //  List<XxbBCheck> xxbBChecks = this.iXxbBCheckService.findXxbBChecks(request, xxbBCheck, null).getRecords();

            ArrayList<Map<String, Object>> rows = new ArrayList<>();

             int index= 1;
            for (XxbBCheck check:xxbBChecks
                 ) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("??????",index);
                if(check.getProjectType().equals(0)){
                    row.put("????????????","???????????????");
                }
                else if(check.getProjectType().equals(1)){
                    row.put("????????????","?????????-????????????");
                }
                else{
                    row.put("????????????","?????????-??????????????????");
                }

                row.put("????????????",check.getProjectName());
                row.put("???????????????",check.getUserAccountName());
                row.put("????????????",check.getDeptNew());
                row.put("???????????????",check.getYggh());
                row.put("?????????????????????",check.getZhichenglc());

                if(check.getProjectLb().equals(0)){
                    row.put("????????????","???????????????");
                }
                else if(check.getProjectLb().equals(1)){
                    row.put("????????????","?????????");
                }
                else if(check.getProjectLb().equals(2)){
                    row.put("????????????","???????????????");
                }
                else{
                    row.put("????????????","?????????");
                }
                row.put("??????????????????????????????",check.getIsxzyljs().equals(1)?"???":"???");
                /**
                 * c???????????????
                 */
                LambdaQueryWrapper<XxbBCheckD> xxbBCheckDLambdaQueryWrapper= new LambdaQueryWrapper<>();
                xxbBCheckDLambdaQueryWrapper.eq(XxbBCheckD::getPid,check.getId());
                List<XxbBCheckD> xxbBCheckDList= this.iXxbBCheckDService.list(xxbBCheckDLambdaQueryWrapper);
                int dx=1;
                for (XxbBCheckD checkD:xxbBCheckDList
                     ) {
                    row.put("?????????"+dx,checkD.getUserAccountName()+"_"+checkD.getUserAccount());
                    dx+=1;
                }
                for(;dx<=10;dx++){
                    row.put("?????????"+dx,"");
                }
                LambdaQueryWrapper<ComFile> fileLambdaQueryWrapper= new LambdaQueryWrapper<>();
                fileLambdaQueryWrapper.eq(ComFile::getRefTabId,check.getId());
                List<ComFile> comFiles=iComFileService.list(fileLambdaQueryWrapper);

                String isXy= comFiles.stream().filter(p->p.getRefTabTable().equals("xxbcheck_lcyyzqtys")).count()>0?"???":"???";
                String isNew= comFiles.stream().filter(p->p.getRefTabTable().equals("xxbcheck_xmcxbg")).count()>0?"???":"???";
                row.put("??????????????????????????????????????????",isXy);
                row.put("????????????????????????",isNew);


                rows.add(row);
                index+=1;
            }

            ExportExcelUtils.exportRows(response,rows,"?????????????????????????????????","D:\\check.xlsx",1);
        } catch (Exception e) {
            message = "??????Excel??????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("downloadFile")
    public void findFiles(QueryRequest request, String id, HttpServletResponse response) throws Exception {
        String filePath = febsProperties.getUploadPath(); // ??????????????????
        String fileName = filePath + "down/" + UUID.randomUUID().toString() + ".pdf";
        String name = UUID.randomUUID().toString() + ".pdf";
        String mergeFileName = filePath + "down/" + name;
        List<String> mergeAddPdfList = new ArrayList<>();
        mergeAddPdfList.add(fileName);
        try {
            XxbBCheck xxbBCheck = this.iXxbBCheckService.getById(id);
            if (xxbBCheck != null) {
                XxbBPdfInfo pdf = new XxbBPdfInfo();
                LambdaQueryWrapper<XxbBCheckD> renyWrapper = new LambdaQueryWrapper<>();
                renyWrapper.eq(XxbBCheckD::getPid, xxbBCheck.getId());
                List<XxbBCheckD> renyList = iXxbBCheckDService.list(renyWrapper);
                List<XxbBProjdept> projDeptList = new ArrayList<>();
                if (xxbBCheck.getProjectType() == 2) {
                    LambdaQueryWrapper<XxbBProjdept> pdWrapper = new LambdaQueryWrapper<>();
                    pdWrapper.eq(XxbBProjdept::getPid, xxbBCheck.getId());
                    projDeptList = iXxbBProjdeptService.list(pdWrapper);
                }

                /*
                LambdaQueryWrapper<ComFile> fileWrapper = new LambdaQueryWrapper<>();
                fileWrapper.eq(ComFile::getRefTabId, xxbBCheck.getId());
                List<ComFile> fileList = iComFileService.list(fileWrapper);
                List<ComFile> fileQuery = new ArrayList<>();

                // xxbcheck_aqxfx ?????????????????????????????????
                fileQuery = fileList.stream().filter(s -> s.getRefTabTable() != null &&
                        s.getRefTabTable().equals("xxbcheck_aqxfx")).collect(Collectors.toList());
                if (fileQuery.size() > 0) {
                    mergeAddPdfList.add(filePath + fileQuery.get(0).getServerName());
                }
                //xxbcheck_yxxfx  ?????????????????????????????????
                fileQuery = fileList.stream().filter(s -> s.getRefTabTable() != null &&
                        s.getRefTabTable().equals("xxbcheck_yxxfx")).collect(Collectors.toList());
                if (fileQuery.size() > 0) {
                    mergeAddPdfList.add(filePath + fileQuery.get(0).getServerName());
                }
                //xxbcheck_xmcxbg ??????????????????
                fileQuery = fileList.stream().filter(s -> s.getRefTabTable() != null &&
                        s.getRefTabTable().equals("xxbcheck_xmcxbg")).collect(Collectors.toList());
                if (fileQuery.size() > 0) {
                    mergeAddPdfList.add(filePath + fileQuery.get(0).getServerName());
                }
                //xxbcheck_lcyyzqtys ??????????????????????????????
                fileQuery = fileList.stream().filter(s -> s.getRefTabTable() != null &&
                        s.getRefTabTable().equals("xxbcheck_lcyyzqtys")).collect(Collectors.toList());
                if (fileQuery.size() > 0) {
                    mergeAddPdfList.add(filePath + fileQuery.get(0).getServerName());
                }
*/
                pdf.writeXxbPdf(fileName, mergeFileName, mergeAddPdfList, xxbBCheck, renyList, projDeptList);

                if (mergeAddPdfList.size() == 1) {
                    mergeFileName = mergeAddPdfList.get(0);
                }
                this.downFile(response, mergeFileName, name, true);
                this.deleteFile(fileName);
            }
        } catch (Exception e) {
            message = "????????????.";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("downloadFile2")
    public void findFiles3(QueryRequest request, String id, HttpServletResponse response) throws Exception {
        String filePath = febsProperties.getUploadPath(); // ??????????????????
        String fileName = filePath + "down/" + UUID.randomUUID().toString() + ".pdf";
        String name = UUID.randomUUID().toString() + ".pdf";
        List<String> mergeAddPdfList = new ArrayList<>();
        try {
            XxbBCheck xxbBCheck = this.iXxbBCheckService.getById(id);
                if (xxbBCheck != null) {
                    XxbBPdfInfo pdf = new XxbBPdfInfo();
                LambdaQueryWrapper<ComFile> fileWrapper = new LambdaQueryWrapper<>();
                fileWrapper.eq(ComFile::getRefTabId, xxbBCheck.getId());
                List<ComFile> fileList = iComFileService.list(fileWrapper);
                List<ComFile> fileQuery = new ArrayList<>();

                // xxbcheck_aqxfx ?????????????????????????????????
                fileQuery = fileList.stream().filter(s -> s.getRefTabTable() != null &&
                        s.getRefTabTable().equals("xxbcheck_aqxfx")).collect(Collectors.toList());
                if (fileQuery.size() > 0) {
                    mergeAddPdfList.add(filePath + fileQuery.get(0).getServerName());
                }
                //xxbcheck_yxxfx  ?????????????????????????????????
                fileQuery = fileList.stream().filter(s -> s.getRefTabTable() != null &&
                        s.getRefTabTable().equals("xxbcheck_yxxfx")).collect(Collectors.toList());
                if (fileQuery.size() > 0) {
                    mergeAddPdfList.add(filePath + fileQuery.get(0).getServerName());
                }
                //xxbcheck_xmcxbg ??????????????????
                fileQuery = fileList.stream().filter(s -> s.getRefTabTable() != null &&
                        s.getRefTabTable().equals("xxbcheck_xmcxbg")).collect(Collectors.toList());
                if (fileQuery.size() > 0) {
                    mergeAddPdfList.add(filePath + fileQuery.get(0).getServerName());
                }
                //xxbcheck_lcyyzqtys ??????????????????????????????
                fileQuery = fileList.stream().filter(s -> s.getRefTabTable() != null &&
                        s.getRefTabTable().equals("xxbcheck_lcyyzqtys")).collect(Collectors.toList());
                if (fileQuery.size() > 0) {
                    mergeAddPdfList.add(filePath + fileQuery.get(0).getServerName());
                }
                    //xxbcheck_lcyyzqtys ??????????????????????????????
                    fileQuery = fileList.stream().filter(s -> s.getRefTabTable() != null &&
                            s.getRefTabTable().equals("xxbcheck_czgz")).collect(Collectors.toList());
                    if (fileQuery.size() > 0) {
                        mergeAddPdfList.add(filePath + fileQuery.get(0).getServerName());
                    }

               pdf.mergePdfFiles(mergeAddPdfList,fileName);

                this.downFile(response, fileName, name, true);
                this.deleteFile(fileName);
            }
        } catch (Exception e) {
            message = "????????????.";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
    @RequiresPermissions("xxbBCheck:import")
    public void downTemplate(HttpServletResponse response) {
        List<XxbBCheck> publishList = new ArrayList<>();
        ExcelKit.$Export(XxbBCheck.class, response).downXlsx(publishList, true);
    }

    @RequestMapping(value = "import", method = RequestMethod.POST)
    @RequiresPermissions("xxbBCheck:import")
    public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<XxbBCheck> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser = FebsUtil.getCurrentUser();


        ExcelKit.$Import(XxbBCheck.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<XxbBCheck>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, XxbBCheck entity) {
                        successList.add(entity); // ??????????????????????????????????????????
                    }

                    @Override
                    public void onError(int sheetIndex, int rowIndex,
                                        java.util.List<ExcelErrorField> errorFields) {
                        // ????????????????????????????????????????????????????????????
                        errorList.add(MapUtil.of("sheetIndex", sheetIndex));
                        errorList.add(MapUtil.of("rowIndex", rowIndex));
                        errorList.add(MapUtil.of("errorFields", errorFields));
                    }
                });

        // TODO: ??????successList??????????????????
        if (CollectionUtil.isEmpty(errorList)) {
            for (XxbBCheck xxbBCheckImport : successList
            ) {
                XxbBCheck xxbBCheck = new XxbBCheck();
                BeanUtil.copyProperties(xxbBCheckImport, xxbBCheck, CopyOptions.create().setIgnoreNullValue(true));
                this.iXxbBCheckService.createXxbBCheck(xxbBCheck);
            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{id}")
    public XxbBCheck detail(@NotBlank(message = "{required}") @PathVariable String id) {
        XxbBCheck xxbBCheck = this.iXxbBCheckService.getById(id);
        return xxbBCheck;
    }

    private void downFile(HttpServletResponse response, String filePath, String fileName, boolean isDel) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                InputStream ins = new FileInputStream(filePath);
                BufferedInputStream bins = new BufferedInputStream(ins);// ?????????????????????
                OutputStream outs = response.getOutputStream();// ??????????????????IO???
                BufferedOutputStream bouts = new BufferedOutputStream(outs);
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));

                int bytesRead = 0;
                byte[] buffer = new byte[512];
                //??????????????????????????????
                while ((bytesRead = bins.read(buffer, 0, 512)) != -1) {
                    bouts.write(buffer, 0, bytesRead);
                }
                bouts.flush();// ?????????????????????flush()??????
                ins.close();
                bins.close();
                outs.close();
                bouts.close();
            } else {
//                response.sendRedirect("../error.jsp");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isDel) {
                this.deleteFile(filePath);
            }
        }
    }

    public boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // ??????????????????????????????????????????
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
                flag = true;
            }
        } else {
            flag = true;
        }
        return flag;
    }
}