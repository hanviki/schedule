
package cc.mrbird.febs.mdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.mdl.entity.*;
import cc.mrbird.febs.mdl.service.IMdlBArchiveService;
import cc.mrbird.febs.mdl.service.IMdlBChufangService;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.sdl.entity.SdlBUser;
import cc.mrbird.febs.sdl.service.ISdlBUserService;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.beust.jcommander.internal.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author viki
 * @since 2022-01-19
 */
@Slf4j
@Validated
@RestController
@RequestMapping("mdlBChufang")

public class MdlBChufangController extends BaseController {

    private String message;
    @Autowired
    public IMdlBChufangService iMdlBChufangService;
    @Autowired
    public IMdlBArchiveService iMdlBArchiveService;
    @Autowired
    private ISdlBUserService iSdlBUserService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/MdlBChufang/MdlBChufang','ass/MdlBChufang/MdlBChufang','mdlBChufang:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','mdlBChufang:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','mdlBChufang:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','mdlBChufang:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','mdlBChufang:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','mdlBChufang:import',1,5,NOW());
 */


    /**
     * 分页查询数据
     *
     * @param request     分页信息
     * @param mdlBChufang 查询条件
     * @return
     */
    @GetMapping
    public Map<String, Object> List(QueryRequest request, MdlBChufang mdlBChufang) {
        return getDataTable(this.iMdlBChufangService.findMdlBChufangs(request, mdlBChufang));
    }

    @GetMapping("chufang")
    public Map<String, Object> List2(QueryRequest request, MdlBChufang mdlBChufang) {
        return getDataTable(this.iMdlBChufangService.findMdlBChufangList(request, mdlBChufang));
    }

    /**
     * 添加
     *
     * @param mdlBChufang
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    public void addMdlBChufang(@Valid MdlBChufang mdlBChufang) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            mdlBChufang.setCreateUserId(currentUser.getUserId());

            if (!mdlBChufang.getType().equals("考试管理")) {
                LambdaQueryWrapper<MdlBChufang> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(MdlBChufang::getUserAccount, mdlBChufang.getUserAccount());
                queryWrapper.eq(MdlBChufang::getExiamResult, "通过");
                queryWrapper.eq(MdlBChufang::getType, "考试管理");
                List<MdlBChufang> mdlBChufangList = this.iMdlBChufangService.list(queryWrapper);
                if (mdlBChufangList.size() <= 0) {
                    throw new FebsException("考试结果尚未通过，请添加考试管理结果");
                } else {
                    mdlBChufang.setExiamResult(mdlBChufangList.get(0).getExiamResult());
                    mdlBChufang.setExiamScore(mdlBChufangList.get(0).getExiamScore());
                    mdlBChufang.setTrainDate(mdlBChufangList.get(0).getTrainDate());
                }
            }
            this.iMdlBChufangService.createMdlBChufang(mdlBChufang);
        } catch (Exception e) {
            //  message="新增/按钮失败" ;
            log.error(message, e);
            throw new FebsException(e.getMessage());
        }
    }

    /**
     * 修改
     *
     * @param mdlBChufang
     * @return
     */
    @Log("修改")
    @PutMapping
    public void updateMdlBChufang(@Valid MdlBChufang mdlBChufang) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            mdlBChufang.setModifyUserId(currentUser.getUserId());
            this.iMdlBChufangService.updateMdlBChufang(mdlBChufang);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    public void deleteMdlBChufangs(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iMdlBChufangService.deleteMdlBChufangs(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    public void export(QueryRequest request, MdlBChufang mdlBChufang, HttpServletResponse response) throws FebsException {
        try {
            request.setPageSize(100000);
            request.setPageNum(1);
            List<MdlBChufang> mdlBChufangs = this.iMdlBChufangService.findMdlBChufangs(request, mdlBChufang).getRecords();
            ExcelKit.$Export(MdlBChufang.class, response).downXlsx(mdlBChufangs, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excelChuFang")
    public void export33(QueryRequest request, MdlBChufang mdlBChufang, String dataJson, HttpServletResponse response) throws FebsException {
        try {
            request.setPageNum(1);
            request.setPageSize(20000);
            request.setSortField("user_account");
            request.setSortOrder("ascend");
            List<MdlBChufang> mdlBChufangs = this.iMdlBChufangService.findMdlBChufangList(request, mdlBChufang).getRecords();
            ExportExcelUtils.exportCustomExcel_han(response, mdlBChufangs, dataJson, "");
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @RequestMapping(value = "downTemplate/{type}", method = RequestMethod.POST)
    public void downTemplate(HttpServletResponse response, @PathVariable int type) {
        List<MdlBChufang> publishList = new ArrayList<>();
        if (type == 0) {
            ExcelKit.$Export(MdlBChufangFt.class, response).downXlsx(publishList, true);
        }
        if (type == 1) {
            ExcelKit.$Export(MdlBChufangSc.class, response).downXlsx(publishList, true);
        }
        if (type == 2) {
            ExcelKit.$Export(MdlBChufangTh.class, response).downXlsx(publishList, true);
        }
    }


    @RequestMapping(value = "import/{type}", method = RequestMethod.POST)
    public ResponseEntity<?> importUser(@RequestParam MultipartFile file,@PathVariable int type)
            throws IOException {
           if(type==0){
               return  getData0(file.getInputStream());
           }
           else if(type==1){
               return  getData1(file.getInputStream());
           }
           else{
               return  getData2(file.getInputStream());
           }
    }



    public  ResponseEntity<?> getData0(InputStream inputStream){
        long beginMillis = System.currentTimeMillis();

        List<MdlBChufangFt> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();
        //List<MdlBArchive> fileList=this.iMdlBArchiveService.list();

        User currentUser = FebsUtil.getCurrentUser();
        List<MdlBChufang> addlist =new ArrayList<>();

            ExcelKit.$Import(MdlBChufangFt.class)
                    .readXlsx(inputStream, new ExcelReadHandler<MdlBChufangFt>() {
                        @Override
                        public void onSuccess(int sheetIndex, int rowIndex, MdlBChufangFt entity) {
                            if(StringUtils.isNotEmpty(entity.getUserAccount())){
                                successList.add(entity); // 单行读取成功，加入入库队列。
                            }
                        }

                        @Override
                        public void onError(int sheetIndex, int rowIndex,
                                            java.util.List<ExcelErrorField> errorFields) {
                            // 读取数据失败，记录了当前行所有失败的数据
                            errorList.add(MapUtil.of("sheetIndex", sheetIndex));
                            errorList.add(MapUtil.of("rowIndex", rowIndex));
                            errorList.add(MapUtil.of("errorFields", errorFields));
                        }
                    });

            // TODO: 执行successList的入库操作。
            if (CollectionUtil.isEmpty(errorList)) {
                for (MdlBChufangFt mdlBChufangImport : successList
                ) {
                    MdlBChufang mdlBChufang = new MdlBChufang();
                    mdlBChufang.setType("考试管理");
                    LambdaQueryWrapper<SdlBUser> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(SdlBUser::getUserAccount, mdlBChufangImport.getUserAccount());
                    SdlBUser user = this.iSdlBUserService.getOne(queryWrapper);



                    BeanUtil.copyProperties(mdlBChufangImport, mdlBChufang, CopyOptions.create().setIgnoreNullValue(true));
                    if (!(mdlBChufangImport.getExiamResult().equals("通过") || mdlBChufangImport.getExiamResult().equals("未通过"))) {
                        errorList.add(MapUtil.of(//
                                "name", mdlBChufangImport.getUserAccountName() + ":考核结果填写通过或未通过"));
                    }  else if (user ==null){
                        errorList.add(MapUtil.of(//
                                "name", mdlBChufangImport.getUserAccountName() + ":此发薪号不存在"));
                    }else {
                        mdlBChufang.setUserAccount(user.getUserAccount());
                        mdlBChufang.setUserAccountName(user.getUserAccountName());
                        mdlBChufang.setDeptNew(user.getDeptNew());

                        mdlBChufang.setZhicheng(user.getZhicheng());
                        mdlBChufang.setYishiZgzsbianhao(user.getYishiZgzsbianhao());
                        mdlBChufang.setYishiZiyebianhao(user.getYishiZiyebianhao());
                        addlist.add(mdlBChufang);
                    }

                }

            }
        if (CollectionUtil.isEmpty(errorList)) {
            for (MdlBChufang add : addlist){
                this.iMdlBChufangService.createMdlBChufang(add);
            }
        }
        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }
    public  ResponseEntity<?> getData1(InputStream inputStream){
        long beginMillis = System.currentTimeMillis();

        List<MdlBChufangSc> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();
        List<MdlBArchive> fileList=this.iMdlBArchiveService.list();

        User currentUser = FebsUtil.getCurrentUser();
        List<MdlBChufang> addlist =new ArrayList<>();

        ExcelKit.$Import(MdlBChufangSc.class)
                .readXlsx(inputStream, new ExcelReadHandler<MdlBChufangSc>() {
                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, MdlBChufangSc entity) {
                        if(StringUtils.isNotEmpty(entity.getUserAccount())){
                            successList.add(entity); // 单行读取成功，加入入库队列。
                        }
                    }

                    @Override
                    public void onError(int sheetIndex, int rowIndex,
                                        java.util.List<ExcelErrorField> errorFields) {
                        // 读取数据失败，记录了当前行所有失败的数据
                        errorList.add(MapUtil.of("sheetIndex", sheetIndex));
                        errorList.add(MapUtil.of("rowIndex", rowIndex));
                        errorList.add(MapUtil.of("errorFields", errorFields));
                    }
                });

        // TODO: 执行successList的入库操作。
        if (CollectionUtil.isEmpty(errorList)) {
            for (MdlBChufangSc mdlBChufangImport : successList
            ) {


                LambdaQueryWrapper<MdlBChufang> queryWrapper2 = new LambdaQueryWrapper<>();
                queryWrapper2.eq(MdlBChufang::getUserAccount, mdlBChufangImport.getUserAccount());
                queryWrapper2.eq(MdlBChufang::getExiamResult, "通过");
                queryWrapper2.eq(MdlBChufang::getType, "考试管理");
                List<MdlBChufang> mdlBChufangList = this.iMdlBChufangService.list(queryWrapper2);

                MdlBChufang mdlBChufang = new MdlBChufang();
                mdlBChufang.setType("麻精药物处方权");
                LambdaQueryWrapper<SdlBUser> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SdlBUser::getUserAccount, mdlBChufangImport.getUserAccount());
                SdlBUser user = this.iSdlBUserService.getOne(queryWrapper);

                List<MdlBArchive> archives= fileList.stream().filter(p->p.getFileType().equals("麻精药物")&&mdlBChufangImport.getArchiveName()!=null&&p.getFileName().equals(mdlBChufangImport.getArchiveName())).collect(Collectors.toList());
                if (!(mdlBChufangImport.getIsChufang().equals("是") || mdlBChufangImport.getIsChufang().equals("否")|| mdlBChufangImport.getIsChufang().equals("调剂权"))) {
                    errorList.add(MapUtil.of(//
                            "name", mdlBChufangImport.getUserAccountName() + ":是否处方填写是或不是或调剂权"));
                }
                else if (user ==null){
                    errorList.add(MapUtil.of(//
                            "name", mdlBChufangImport.getUserAccountName() + ":此发薪号不存在"));
                }
                else if (mdlBChufangList.size() <= 0) {
                    errorList.add(MapUtil.of(//
                            "name", mdlBChufangImport.getUserAccountName() + ":考试结果尚未通过，请添加考试管理结果"));
                }
                else if(archives.size()<=0) {
                    errorList.add(MapUtil.of(//
                            "name", mdlBChufangImport.getUserAccountName() + ":授权文件不存在"));
                }else
                 {
                     BeanUtil.copyProperties(mdlBChufangImport, mdlBChufang, CopyOptions.create().setIgnoreNullValue(true));
                     mdlBChufang.setUserAccount(user.getUserAccount());
                     mdlBChufang.setUserAccountName(user.getUserAccountName());
                     mdlBChufang.setDeptNew(user.getDeptNew());
                     mdlBChufang.setTrainDate(mdlBChufangList.get(0).getTrainDate());
                     mdlBChufang.setExiamScore(mdlBChufangList.get(0).getExiamScore());
                     mdlBChufang.setExiamResult(mdlBChufangList.get(0).getExiamResult());

                     mdlBChufang.setArchiveId(archives.get(0).getId());
                     mdlBChufang.setArchiveCode(archives.get(0).getFileCode());
                     mdlBChufang.setArchiveName(archives.get(0).getFileName());
                     mdlBChufang.setFileId(archives.get(0).getFileId());
                     mdlBChufang.setFileUrl(archives.get(0).getFileUrl());

                     mdlBChufang.setZhicheng(user.getZhicheng());
                     mdlBChufang.setYishiZgzsbianhao(user.getYishiZgzsbianhao());
                     mdlBChufang.setYishiZiyebianhao(user.getYishiZiyebianhao());

                    addlist.add(mdlBChufang);
                }

            }

        }
        if (CollectionUtil.isEmpty(errorList)) {
            for (MdlBChufang add : addlist){
                this.iMdlBChufangService.createMdlBChufang(add);
            }
        }
        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }

    public  ResponseEntity<?> getData2(InputStream inputStream){
        long beginMillis = System.currentTimeMillis();

        List<MdlBChufangTh> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();
        List<MdlBArchive> fileList=this.iMdlBArchiveService.list();

        User currentUser = FebsUtil.getCurrentUser();
        List<MdlBChufang> addlist =new ArrayList<>();

        ExcelKit.$Import(MdlBChufangTh.class)
                .readXlsx(inputStream, new ExcelReadHandler<MdlBChufangTh>() {
                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, MdlBChufangTh entity) {
                        if(StringUtils.isNotEmpty(entity.getUserAccount())){
                            successList.add(entity); // 单行读取成功，加入入库队列。
                        }
                    }

                    @Override
                    public void onError(int sheetIndex, int rowIndex,
                                        java.util.List<ExcelErrorField> errorFields) {
                        // 读取数据失败，记录了当前行所有失败的数据
                        errorList.add(MapUtil.of("sheetIndex", sheetIndex));
                        errorList.add(MapUtil.of("rowIndex", rowIndex));
                        errorList.add(MapUtil.of("errorFields", errorFields));
                    }
                });

        // TODO: 执行successList的入库操作。
        if (CollectionUtil.isEmpty(errorList)) {
            for (MdlBChufangTh mdlBChufangImport : successList
            ) {
                MdlBChufang mdlBChufang = new MdlBChufang();
                LambdaQueryWrapper<MdlBChufang> queryWrapper2 = new LambdaQueryWrapper<>();
                queryWrapper2.eq(MdlBChufang::getUserAccount, mdlBChufangImport.getUserAccount());
                queryWrapper2.eq(MdlBChufang::getExiamResult, "通过");
                queryWrapper2.eq(MdlBChufang::getType, "考试管理");
                List<MdlBChufang> mdlBChufangList = this.iMdlBChufangService.list(queryWrapper2);

                mdlBChufang.setType("抗菌药物分级管理");
                LambdaQueryWrapper<SdlBUser> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SdlBUser::getUserAccount, mdlBChufangImport.getUserAccount());
                SdlBUser user = this.iSdlBUserService.getOne(queryWrapper);

                List<MdlBArchive> archives= fileList.stream().filter(p->p.getFileType().equals("抗菌药物")&&mdlBChufangImport.getArchiveName()!=null&&p.getFileName().equals(mdlBChufangImport.getArchiveName())).collect(Collectors.toList());

                if (!(mdlBChufangImport.getLevel().equals("限制级") || mdlBChufangImport.getLevel().equals("非限制级")|| mdlBChufangImport.getLevel().equals("特殊使用")|| mdlBChufangImport.getLevel().equals("调剂权"))) {
                    errorList.add(MapUtil.of(//
                            "name", mdlBChufangImport.getUserAccountName() + ":级别填写限制级或非限制级或特殊使用"));
                }
                else if (user ==null){
                    errorList.add(MapUtil.of(//
                            "name", mdlBChufangImport.getUserAccountName() + ":此发薪号不存在"));
                }
                else if (mdlBChufangList.size() <= 0) {
                    errorList.add(MapUtil.of(//
                            "name", mdlBChufangImport.getUserAccountName() + ":考试结果尚未通过，请添加考试管理结果"));
                }
                else if(archives.size()<=0) {
                    errorList.add(MapUtil.of(//
                            "name", mdlBChufangImport.getUserAccountName() + ":授权文件不存在"));
                }else
                {
                    BeanUtil.copyProperties(mdlBChufangImport, mdlBChufang, CopyOptions.create().setIgnoreNullValue(true));
                    mdlBChufang.setUserAccount(user.getUserAccount());
                    mdlBChufang.setUserAccountName(user.getUserAccountName());
                    mdlBChufang.setDeptNew(user.getDeptNew());

                    mdlBChufang.setTrainDate(mdlBChufangList.get(0).getTrainDate());
                    mdlBChufang.setExiamScore(mdlBChufangList.get(0).getExiamScore());
                    mdlBChufang.setExiamResult(mdlBChufangList.get(0).getExiamResult());
                    mdlBChufang.setArchiveId(archives.get(0).getId());
                    mdlBChufang.setArchiveCode(archives.get(0).getFileCode());
                    mdlBChufang.setArchiveName(archives.get(0).getFileName());
                    mdlBChufang.setFileId(archives.get(0).getFileId());
                    mdlBChufang.setFileUrl(archives.get(0).getFileUrl());

                    mdlBChufang.setZhicheng(user.getZhicheng());
                    mdlBChufang.setYishiZgzsbianhao(user.getYishiZgzsbianhao());
                    mdlBChufang.setYishiZiyebianhao(user.getYishiZiyebianhao());

                    addlist.add(mdlBChufang);
                }

            }

        }
        if (CollectionUtil.isEmpty(errorList)) {
            for (MdlBChufang add : addlist){
                this.iMdlBChufangService.createMdlBChufang(add);
            }
        }
        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }
    @GetMapping("/{id}")
    public MdlBChufang detail(@NotBlank(message = "{required}") @PathVariable String id) {
        MdlBChufang mdlBChufang = this.iMdlBChufangService.getById(id);
        return mdlBChufang;
    }
}