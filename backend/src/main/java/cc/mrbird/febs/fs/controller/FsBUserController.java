
package cc.mrbird.febs.fs.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.fs.entity.*;
import cc.mrbird.febs.fs.service.IFsBExameService;
import cc.mrbird.febs.fs.service.IFsBHealthService;
import cc.mrbird.febs.fs.service.IFsBRecordService;
import cc.mrbird.febs.fs.service.IFsBUserService;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.sdl.entity.SdlBUserMg;
import cc.mrbird.febs.sdl.service.ISdlBUserMgService;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
 * @since 2022-07-06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("fsBUser")

public class FsBUserController extends BaseController {

    private String message;
    @Autowired
    public IFsBUserService iFsBUserService;
    @Autowired
    public IFsBRecordService
            iFsBRecordService;

    @Autowired
    public IFsBExameService iFsBExameService;
    @Autowired
    public IFsBHealthService iFsBHealthService;

    @Autowired
    public ISdlBUserMgService iSdlBUserMgService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/FsBUser/FsBUser','ass/FsBUser/FsBUser','fsBUser:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','fsBUser:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','fsBUser:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','fsBUser:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','fsBUser:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','fsBUser:import',1,5,NOW());
 */


    /**
     * 分页查询数据
     *
     * @param request 分页信息
     * @param fsBUser 查询条件
     * @return
     */
    @GetMapping
    public Map<String, Object> List(QueryRequest request, ViewFs fsBUser) {

        IPage<ViewFs> page = this.iFsBUserService.findFsBUserList(request, fsBUser);
        if (page != null) {
            List<ViewFs> records = page.getRecords();
            List<String> accounts = records.stream().map(p -> p.getUserAccount()).collect(Collectors.toList());
            if (accounts.size() > 0) {
                List<FsBRecord> fsBRecordList = iFsBRecordService.findFsBRecord(accounts);
                for (ViewFs fs : records
                ) {
                    List<FsBRecord> list = fsBRecordList.stream().filter(p -> p.getUserAccount().equals(fs.getUserAccount())).collect(Collectors.toList());
                    if (list.size() > 0) {
                        fs.setJcState(list.get(0).getState().toString());
                    }

                }
            }
        }
        return getDataTable(page);
    }

    @GetMapping("all")
    public Map<String, Object> List2(QueryRequest request, ViewFs fsBUser) {
        return getDataTable(this.iFsBUserService.findFsBUserList2(request, fsBUser));
    }

    @GetMapping("dept")
    public Map<String, Object> List_Dept(QueryRequest request, ViewFs sdlBUser) {
        User currentUser = FebsUtil.getCurrentUser();
        if (StringUtils.isNotEmpty(currentUser.getDeptId())) {
            sdlBUser.setDeptId(currentUser.getDeptId());
        }

        if (StringUtils.isNotEmpty(currentUser.getEmail())) {
            sdlBUser.setYuanqu(currentUser.getEmail().trim());
        }
        IPage<ViewFs> page = this.iFsBUserService.findFsBUserList(request, sdlBUser);
        if (page != null) {
            List<ViewFs> records = page.getRecords();
            List<String> accounts = records.stream().map(p -> p.getUserAccount()).collect(Collectors.toList());
            if (accounts.size() > 0) {
                List<FsBRecord> fsBRecordList = iFsBRecordService.findFsBRecord(accounts);
                List<FsBHealth> healthList = this.iFsBHealthService.findHByAccounts(accounts);
                List<FsBExame> exameList = this.iFsBExameService.findExameAccounts(accounts);


                for (ViewFs fs : records
                ) {
                    List<FsBRecord> list = fsBRecordList.stream().filter(p -> p.getUserAccount().equals(fs.getUserAccount())).collect(Collectors.toList());
                    if (list.size() > 0) {
                        fs.setJcState(list.get(0).getState().toString());
                    }
                    List<FsBHealth> health = healthList.stream().filter(p -> p.getUserAccount().equals(fs.getUserAccount())).collect(Collectors.toList());
                    if (health.size() > 0) {
                        fs.setIsTj2(health.get(0).getJl());
                    }
                    List<FsBExame> exam = exameList.stream().filter(p -> p.getUserAccount().equals(fs.getUserAccount()) && p.getExameType().equals("卫生考试")
                            && p.getExameNum()!=null &&  regStr(p.getExameNum(),"H[0-9]{8}$") && p.getFileId()!=null && p.getFileId()!=""
                    ).collect(Collectors.toList());
                    if (exam.size() > 0 ) {
                        fs.setIsJl2("有");
                    } else {
                        fs.setIsJl2("无");
                    }
                    List<FsBExame> exam2 = exameList.stream().filter(p -> p.getUserAccount().equals(fs.getUserAccount()) && p.getExameType().equals("环保考试")
                            && p.getExameNum()!=null && regStr(p.getExameNum(),"FS[0-9]{2}HB[0-9]{7}$")   && p.getFileId()!=null && p.getFileId()!=""
                    ).collect(Collectors.toList());
                    if (exam2.size() > 0) {
                        fs.setIsHB5("有");
                    } else {
                        fs.setIsHB5("无");
                    }
                }
            }
        }
        return getDataTable(page);
    }


    @GetMapping("exameTip")
    public Map<String, Object> List_tip(QueryRequest request, ViewFs sdlBUser) {
        User currentUser = FebsUtil.getCurrentUser();
        sdlBUser.setDeptId(currentUser.getDeptId());
        if (StringUtils.isNotEmpty(currentUser.getEmail())) {
            sdlBUser.setYuanqu(currentUser.getEmail().trim());
        }
        IPage<ViewFs> page = this.iFsBUserService.findUserExameTip(request, sdlBUser);
        if (page != null) {
            List<ViewFs> records = page.getRecords();
            HandleRecords(records);
        }
        return getDataTable(page);
    }

    @GetMapping("healthTip")
    public Map<String, Object> List_tip2(QueryRequest request, ViewFs sdlBUser) {
        User currentUser = FebsUtil.getCurrentUser();
        sdlBUser.setDeptId(currentUser.getDeptId());
        if (StringUtils.isNotEmpty(currentUser.getEmail())) {
            sdlBUser.setYuanqu(currentUser.getEmail().trim());
        }
        IPage<ViewFs> page = this.iFsBUserService.findUserHealthTip(request, sdlBUser);
        if (page != null) {
            List<ViewFs> records = page.getRecords();
            HandleRecords(records);
        }
        return getDataTable(page);
    }

    public void HandleRecords(List<ViewFs> records) {
        List<String> accounts = records.stream().map(p -> p.getUserAccount()).collect(Collectors.toList());
        if (accounts.size() > 0) {
            List<FsBRecord> fsBRecordList = iFsBRecordService.findFsBRecord(accounts);
            //   List<FsBHealth> healthList =this.iFsBHealthService.findHByAccounts(accounts);
            List<FsBExame> exameList = this.iFsBExameService.findExameAccounts(accounts);


            for (ViewFs fs : records
            ) {
                List<FsBRecord> list = fsBRecordList.stream().filter(p -> p.getUserAccount().equals(fs.getUserAccount())).collect(Collectors.toList());
                if (list.size() > 0) {
                    fs.setJcState(list.get(0).getState().toString());
                }

                List<FsBExame> exam = exameList.stream().filter(p -> p.getUserAccount().equals(fs.getUserAccount()) && p.getExameType().equals("卫生考试")).collect(Collectors.toList());
                if (exam.size() > 0) {
                    if (DateUtil.compare(DateUtil.offsetMonth(DateUtil.parseDate(DateUtil.now()), -3), exam.get(0).getExameDate()) > 0) {
                        fs.setAuditMan("考试类型");
                        fs.setAuditManName(DateUtil.format(exam.get(0).getExameDate(), "yyyy-MM-dd"));
                    }

                }

                List<FsBExame> exam2 = exameList.stream().filter(p -> p.getUserAccount().equals(fs.getUserAccount()) && p.getExameType().equals("环保考试")).collect(Collectors.toList());
                if (exam2.size() > 0) {
                    if (DateUtil.compare(DateUtil.offsetMonth(DateUtil.parseDate(DateUtil.now()), -48), exam2.get(0).getExameDate()) > 0) {
                        fs.setBianhaoJx("环保类型");
                        fs.setBianhaoLc(DateUtil.format(exam2.get(0).getExameDate(), "yyyy-MM-dd"));
                    }
                }
            }
        }
    }


    @GetMapping("search")
    public List<FsBUser> List(SdlBUserMg sdlBUser) {
        List<SdlBUserMg> userList = this.iSdlBUserMgService.findSdlBUserMgs_search(sdlBUser);
        List<String> accounts = userList.stream().map(p -> p.getUserAccount()).collect(Collectors.toList());
        List<FsBUser> resultList = new ArrayList<>();
        if(accounts.size()>0) {
            List<FsBRecord> fsBRecordList = iFsBRecordService.findFsBRecord(accounts);

            LambdaQueryWrapper<FsBUser> fsBUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
            fsBUserLambdaQueryWrapper.in(FsBUser::getUserAccount, accounts);
            List<FsBUser> fsBUserList = this.iFsBUserService.list(fsBUserLambdaQueryWrapper);


            for (SdlBUserMg mg : userList
            ) {
                List<FsBUser> list2 = fsBUserList.stream().filter(p -> p.getUserAccount().equals(mg.getUserAccount())).collect(Collectors.toList());
                if (list2.size() > 0) {
                    List<FsBRecord> list = fsBRecordList.stream().filter(p -> p.getUserAccount().equals(mg.getUserAccount())).collect(Collectors.toList());
                    if (list.size() > 0) {
                        FsBUser user3= list2.get(0);
                        user3.setJcState(list.get(0).getState().toString());
                    }
                    resultList.addAll(list2);
                } else {
                    FsBUser user = new FsBUser();
                    user.setUserAccount(mg.getUserAccount());
                    user.setUserAccountName(mg.getUserAccountName());
                    resultList.add(user);
                }
            }
        }
       return  resultList;
    }

    /**
     * 添加
     *
     * @param fsBUser
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("fsBUser:add")
    public void addFsBUser(@Valid FsBUser fsBUser) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            fsBUser.setCreateUserId(currentUser.getUserId());
            this.iFsBUserService.createFsBUser(fsBUser);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param fsBUser
     * @return
     */
    @Log("修改")
    @PutMapping
    public void updateFsBUser(@Valid FsBUser fsBUser) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();

            LambdaQueryWrapper<FsBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FsBUser::getUserAccount, fsBUser.getUserAccount());
            if (this.iFsBUserService.count(queryWrapper) > 0) {
                fsBUser.setModifyUserId(currentUser.getUserId());
                this.iFsBUserService.update(fsBUser, queryWrapper);
            } else {
                fsBUser.setCreateUserId(currentUser.getUserId());
                this.iFsBUserService.createFsBUser(fsBUser);
            }

        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("fsBUser:delete")
    public void deleteFsBUsers(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iFsBUserService.deleteFsBUsers(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    public void export(QueryRequest request, ViewFs fsBUser, String dataJson, HttpServletResponse response) throws FebsException {
        try {
            request.setPageNum(1);
            request.setPageSize(20000);
            request.setIsSearchCount(false);
            User currentUser = FebsUtil.getCurrentUser();
            fsBUser.setDeptId(currentUser.getDeptId());
            if (StringUtils.isNotEmpty(currentUser.getEmail())) {
                fsBUser.setYuanqu(currentUser.getEmail().trim());
            }
            IPage<ViewFs> page = this.iFsBUserService.findFsBUserList(request, fsBUser);
            List<ViewFs> records = page.getRecords();
            List<String> accounts = records.stream().map(p -> p.getUserAccount()).collect(Collectors.toList());
            if (accounts.size() > 0) {
                List<FsBRecord> fsBRecordList = iFsBRecordService.findFsBRecord(accounts);
                List<FsBHealth> healthList = this.iFsBHealthService.findHByAccounts(accounts);
                List<FsBExame> exameList = this.iFsBExameService.findExameAccounts(accounts);


                for (ViewFs fs : records
                ) {
                    List<FsBRecord> list = fsBRecordList.stream().filter(p -> p.getUserAccount().equals(fs.getUserAccount())).collect(Collectors.toList());
                    if (list.size() > 0) {
                        fs.setJcState(list.get(0).getState().toString());
                    }
                    List<FsBHealth> health = healthList.stream().filter(p -> p.getUserAccount().equals(fs.getUserAccount())).collect(Collectors.toList());
                    if (health.size() > 0) {
                        fs.setIsTj2(health.get(0).getJl());
                    }

                    List<FsBExame> exam = exameList.stream().filter(p -> p.getUserAccount().equals(fs.getUserAccount()) && p.getExameType().equals("卫生考试")
                    && p.getExameNum()!=null  && regStr(p.getExameNum(),"H[0-9]{8}$") && p.getFileId()!=null && p.getFileId()!=""
                    ).collect(Collectors.toList());
                    if (exam.size() > 0 ) {
                        fs.setIsJl2("有");
                    } else {
                        fs.setIsJl2("无");
                    }
                    List<FsBExame> exam2 = exameList.stream().filter(p -> p.getUserAccount().equals(fs.getUserAccount()) && p.getExameType().equals("环保考试")
                            && p.getExameNum()!=null && regStr(p.getExameNum(),"FS[0-9]{2}HB[0-9]{7}$")  && p.getFileId()!=null && p.getFileId()!=""
                    ).collect(Collectors.toList());
                    if (exam2.size() > 0) {
                        fs.setIsHB5("有");
                    } else {
                        fs.setIsHB5("无");
                    }
                }
            }
            ExportExcelUtils.exportCustomExcel_han(response, records, dataJson, "");
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    public  Boolean regStr(String gf,String reg){
        if(gf!=null && gf.indexOf("参加医院组织的使用")==0){
            return  true;
        }
       return ReUtil.isMatch(reg,gf);
    }
    @PostMapping("excelExame")
    public void export2(QueryRequest request, ViewFs fsBUser, String dataJson, HttpServletResponse response) throws FebsException {
        try {
            request.setPageNum(1);
            request.setPageSize(20000);
            request.setIsSearchCount(false);
            User currentUser = FebsUtil.getCurrentUser();
            fsBUser.setDeptId(currentUser.getDeptId());
            if (StringUtils.isNotEmpty(currentUser.getEmail())) {
                fsBUser.setYuanqu(currentUser.getEmail().trim());
            }
            IPage<ViewFs> page = this.iFsBUserService.findUserExameTip(request, fsBUser);
            List<ViewFs> records = page.getRecords();
            HandleRecords(records);
            ExportExcelUtils.exportCustomExcel_han(response, records, dataJson, "");
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excelHealth")
    public void export5(QueryRequest request, ViewFs fsBUser, String dataJson, HttpServletResponse response) throws FebsException {
        try {
            request.setPageNum(1);
            request.setPageSize(20000);
            request.setIsSearchCount(false);
            User currentUser = FebsUtil.getCurrentUser();
            fsBUser.setDeptId(currentUser.getDeptId());
            if (StringUtils.isNotEmpty(currentUser.getEmail())) {
                fsBUser.setYuanqu(currentUser.getEmail().trim());
            }
            IPage<ViewFs> page = this.iFsBUserService.findUserHealthTip(request, fsBUser);
            List<ViewFs> records = page.getRecords();
            HandleRecords(records);
            ExportExcelUtils.exportCustomExcel_han(response, records, dataJson, "");
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
    @RequiresPermissions("fsBUser:import")
    public void downTemplate(HttpServletResponse response) {
        List<FsBUser> publishList = new ArrayList<>();
        ExcelKit.$Export(FsBUser.class, response).downXlsx(publishList, true);
    }

    @RequestMapping(value = "import", method = RequestMethod.POST)
    @RequiresPermissions("fsBUser:import")
    public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<FsBUser> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser = FebsUtil.getCurrentUser();


        ExcelKit.$Import(FsBUser.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<FsBUser>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, FsBUser entity) {
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

        // TODO: 执行successList的入库操作。
        if (CollectionUtil.isEmpty(errorList)) {
            for (FsBUser fsBUserImport : successList
            ) {
                FsBUser fsBUser = new FsBUser();
                BeanUtil.copyProperties(fsBUserImport, fsBUser, CopyOptions.create().setIgnoreNullValue(true));
                this.iFsBUserService.createFsBUser(fsBUser);
            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{id}")
    public FsBUser detail(@NotBlank(message = "{required}") @PathVariable String id) {
        FsBUser fsBUser = this.iFsBUserService.getById(id);
        return fsBUser;
    }
}