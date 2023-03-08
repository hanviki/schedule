
package cc.mrbird.febs.fs.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.fs.entity.FsBQyImport;
import cc.mrbird.febs.fs.entity.FsBQyReport;
import cc.mrbird.febs.fs.entity.FsBUser;
import cc.mrbird.febs.fs.service.IFsBQyService;
import cc.mrbird.febs.fs.entity.FsBQy;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.fs.service.IFsBUserService;
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
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author viki
 * @since 2022-07-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("fsBQy")

public class FsBQyController extends BaseController {

    private String message;
    @Autowired
    public IFsBQyService iFsBQyService;
    @Autowired
    public IFsBUserService iFsBUserService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/FsBQy/FsBQy','ass/FsBQy/FsBQy','fsBQy:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','fsBQy:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','fsBQy:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','fsBQy:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','fsBQy:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','fsBQy:import',1,5,NOW());
 */


    /**
     * 分页查询数据
     *
     * @param request 分页信息
     * @param fsBQy   查询条件
     * @return
     */
    @GetMapping
    public Map<String, Object> List(QueryRequest request, FsBQy fsBQy) {
        User currentUser = FebsUtil.getCurrentUser();
        fsBQy.setDeptId(currentUser.getDeptId());
        if(StringUtils.isNotEmpty(currentUser.getEmail())) {
            fsBQy.setYuanqu(currentUser.getEmail().trim());
        }
        return getDataTable(this.iFsBQyService.findFsBQyList(request, fsBQy));
    }

    @GetMapping("report")
    public Map<String, Object> List4(QueryRequest request, FsBQy fsBQy) {
        User currentUser = FebsUtil.getCurrentUser();
        fsBQy.setDeptId(currentUser.getDeptId());
        if(StringUtils.isNotEmpty(currentUser.getEmail())) {
            fsBQy.setYuanqu(currentUser.getEmail().trim());
        }
        return getDataTable(this.iFsBQyService.report(request, fsBQy));
    }

    /**
     * 添加
     *
     * @param fsBQy
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    public void addFsBQy(@Valid FsBQy fsBQy) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            fsBQy.setCreateUserId(currentUser.getUserId());

            this.iFsBQyService.createFsBQy(fsBQy);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param fsBQy
     * @return
     */
    @Log("修改")
    @PutMapping
    public void updateFsBQy(@Valid FsBQy fsBQy) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            fsBQy.setModifyUserId(currentUser.getUserId());
            this.iFsBQyService.updateFsBQy(fsBQy);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    public void deleteFsBQys(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iFsBQyService.deleteFsBQys(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excelReport")
    public void export3(QueryRequest request, FsBQy fsBQy,String dataJson, HttpServletResponse response) throws FebsException {
        try {

            request.setPageNum(1);
            request.setPageSize(20000);
            request.setIsSearchCount(false);
            User currentUser = FebsUtil.getCurrentUser();
            fsBQy.setDeptId(currentUser.getDeptId());
            if(StringUtils.isNotEmpty(currentUser.getEmail())) {
                fsBQy.setYuanqu(currentUser.getEmail().trim());
            }
            List<FsBQyReport> fsBQys = this.iFsBQyService.report(request, fsBQy).getRecords();

            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, fsBQys,dataJson,"");
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    public void export(QueryRequest request, FsBQy fsBQy, HttpServletResponse response) throws FebsException {
        try {
            List<FsBQy> fsBQys = this.iFsBQyService.findFsBQys(request, fsBQy).getRecords();
            ExcelKit.$Export(FsBQy.class, response).downXlsx(fsBQys, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
    public void downTemplate(HttpServletResponse response) {
        List<FsBQy> publishList = new ArrayList<>();
        ExcelKit.$Export(FsBQy.class, response).downXlsx(publishList, true);
    }

    @RequestMapping(value = "downTemplate2", method = RequestMethod.POST)
    public void downTemplate2(HttpServletResponse response,QueryRequest request, FsBQy fsBQy) {
        User currentUser = FebsUtil.getCurrentUser();
        fsBQy.setDeptId(currentUser.getDeptId());
        if(StringUtils.isNotEmpty(currentUser.getEmail())) {
            fsBQy.setYuanqu(currentUser.getEmail().trim());
        }
        request.setPageNum(1);
        request.setPageSize(20000);
        request.setIsSearchCount(false);
        List<FsBQyImport> publishList= new ArrayList<>();
        List<FsBQy> publishList2 = this.iFsBQyService.findFsBQyList(request, fsBQy).getRecords();
        for (FsBQy qy:publishList2
             ) {
            FsBQyImport fsBQyImport =new FsBQyImport();
            fsBQyImport.setIsPb(qy.getIsPb());
            fsBQyImport.setNumber(qy.getNumber());
            fsBQyImport.setQuarter(qy.getQuarter());
            fsBQyImport.setUserAccount(qy.getUserAccount());
            fsBQyImport.setUserAccountName(qy.getUserAccountName());
            fsBQyImport.setYear(qy.getYear());
            publishList.add(fsBQyImport);
        }

        ExcelKit.$Export(FsBQyImport.class, response).downXlsx(publishList, false);
    }

    @RequestMapping(value = "import", method = RequestMethod.POST)
    public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<FsBQy> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser = FebsUtil.getCurrentUser();


        ExcelKit.$Import(FsBQy.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<FsBQy>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, FsBQy entity) {

                        successList.add(entity); // 单行读取成功，加入入库队列。
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

        List<String> accounts = successList.stream().map(p -> p.getNumber()).collect(Collectors.toList());
        LambdaQueryWrapper<FsBQy> fsBQyLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fsBQyLambdaQueryWrapper.eq(FsBQy::getQuarter, successList.get(0).getQuarter());
        fsBQyLambdaQueryWrapper.eq(FsBQy::getYear, successList.get(0).getYear());
        fsBQyLambdaQueryWrapper.in(FsBQy::getNumber, accounts);
        List<String> fsList = this.iFsBQyService.list(fsBQyLambdaQueryWrapper).stream().map(p -> p.getNumber()).collect(Collectors.toList());

        // TODO: 执行successList的入库操作。
        if (CollectionUtil.isEmpty(errorList)) {
            for (FsBQy fsBQyImport : successList
            ) {
                if(fsBQyImport.getQyIn()==0d){
                    fsBQyImport.setQyIn(null);
                }
                if(fsBQyImport.getQyOut()==0d){
                    fsBQyImport.setQyOut(null);
                }
                if(fsBQyImport.getQyNo()==0d){
                    fsBQyImport.setQyNo(null);
                }
                //  FsBQy fsBQy =new FsBQy();
                //   BeanUtil.copyProperties(fsBQyImport,fsBQy, CopyOptions.create().setIgnoreNullValue(true));
                if (fsList.contains(fsBQyImport.getNumber())) {
                    LambdaQueryWrapper<FsBQy> fsBQyLambdaQueryWrapper2 = new LambdaQueryWrapper<>();
                    fsBQyLambdaQueryWrapper2.eq(FsBQy::getQuarter, successList.get(0).getQuarter());
                    fsBQyLambdaQueryWrapper2.eq(FsBQy::getYear, successList.get(0).getYear());
                    fsBQyLambdaQueryWrapper2.eq(FsBQy::getNumber, fsBQyImport.getNumber());
                    this.iFsBQyService.update(fsBQyImport, fsBQyLambdaQueryWrapper2);

                } else {

                    this.iFsBQyService.createFsBQy(fsBQyImport);
                }
            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }

    @RequestMapping(value = "import2", method = RequestMethod.POST)
    public ResponseEntity<?> importUser2(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<FsBQyImport> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser = FebsUtil.getCurrentUser();


        ExcelKit.$Import(FsBQyImport.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<FsBQyImport>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, FsBQyImport entity) {
                        successList.add(entity); // 单行读取成功，加入入库队列。
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




//        List<String> accounts = successList.stream().map(p -> p.getNumber()).collect(Collectors.toList());
//        LambdaQueryWrapper<FsBQy> fsBQyLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        fsBQyLambdaQueryWrapper.eq(FsBQy::getQuarter, successList.get(0).getQuarter());
//        fsBQyLambdaQueryWrapper.eq(FsBQy::getYear, successList.get(0).getYear());
//        fsBQyLambdaQueryWrapper.in(FsBQy::getNumber, accounts);
//        List<String> fsList = this.iFsBQyService.list(fsBQyLambdaQueryWrapper).stream().map(p -> p.getNumber()).collect(Collectors.toList());


        // TODO: 执行successList的入库操作。
        if (CollectionUtil.isEmpty(errorList)) {
            for (FsBQyImport fsBQyImport : successList
            ) {
                FsBQy fsBQy = new FsBQy();
                BeanUtil.copyProperties(fsBQyImport, fsBQy, CopyOptions.create().setIgnoreNullValue(true));


                    LambdaQueryWrapper<FsBQy> fsBQyLambdaQueryWrapper2 = new LambdaQueryWrapper<>();
                    fsBQyLambdaQueryWrapper2.eq(FsBQy::getQuarter, fsBQyImport.getQuarter());
                    fsBQyLambdaQueryWrapper2.eq(FsBQy::getYear, fsBQyImport.getYear());
                    fsBQyLambdaQueryWrapper2.eq(FsBQy::getNumber, fsBQyImport.getNumber());
                    this.iFsBQyService.update(fsBQy, fsBQyLambdaQueryWrapper2);


            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{id}")
    public FsBQy detail(@NotBlank(message = "{required}") @PathVariable String id) {
        FsBQy fsBQy = this.iFsBQyService.getById(id);
        return fsBQy;
    }
}