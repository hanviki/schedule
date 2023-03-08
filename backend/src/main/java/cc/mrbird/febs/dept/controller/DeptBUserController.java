
package cc.mrbird.febs.dept.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dept.entity.*;
import cc.mrbird.febs.dept.service.IDeptBBookService;
import cc.mrbird.febs.dept.service.IDeptBUserService;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.beust.jcommander.internal.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.View;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author viki
 * @since 2023-02-28
 */
@Slf4j
@Validated
@RestController
@RequestMapping("deptBUser")

public class DeptBUserController extends BaseController{

private String message;
@Autowired
public IDeptBUserService iDeptBUserService;

    @Autowired
    public IDeptBBookService  iDeptBBookService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/DeptBUser/DeptBUser','ass/DeptBUser/DeptBUser','deptBUser:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','deptBUser:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','deptBUser:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','deptBUser:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','deptBUser:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','deptBUser:import',1,5,NOW());
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param deptBUser 查询条件
 * @return
 */
@GetMapping
//@RequiresPermissions("deptBUser:view")
public Map<String, Object> List(QueryRequest request, ViewDept deptBUser){
    IPage<ViewDept> list=this.iDeptBUserService.findDeptBUserList(request, deptBUser);
    List<ViewDept> viewDeptList =list.getRecords();
    List<String> accountList= viewDeptList.stream().map(p->p.getUserAccount()).collect(Collectors.toList());
   if(accountList.size()>0) {
       User currentUser = FebsUtil.getCurrentUser();
       List<ViewDicType> dynamicList = this.iDeptBBookService.findBook(currentUser.getUsername(), accountList);
       for (ViewDept dept : viewDeptList
       ) {
           List<DynamicData> dynamicDataList = new ArrayList<>();
           List<ViewDicType> dl = dynamicList.stream().filter(p -> p.getUserAccount().equals(dept.getUserAccount())).collect(Collectors.toList());
           for (ViewDicType dic : dl
           ) {
               DynamicData data = new DynamicData();
               data.setAuditTitletype(dic.getFiledName() + "1");
               data.setAuditResult(dic.getBookType());
               dynamicDataList.add(data);

               DynamicData data2 = new DynamicData();
               data2.setAuditTitletype(dic.getFiledName() + "2");
               data2.setAuditResult(dic.getBookNo());
               dynamicDataList.add(data2);

               DynamicData data3 = new DynamicData();
               data3.setAuditTitletype(dic.getFiledName() + "3");
               data3.setAuditResult(dic.getBookDate() == null ? "" : DateUtil.format(dic.getBookDate(), "yyyy-MM-dd"));
               dynamicDataList.add(data3);

               DynamicData data4 = new DynamicData();
               data4.setAuditTitletype(dic.getFiledName() + "4");
               data4.setAuditResult(dic.getCheckDate() == null ? "" : DateUtil.format(dic.getCheckDate(), "yyyy-MM-dd"));
               dynamicDataList.add(data4);

               DynamicData data5 = new DynamicData();
               data5.setAuditTitletype(dic.getFiledName() + "5");
               data5.setAuditResult(dic.getFileId());
               dynamicDataList.add(data5);

               DynamicData data6 = new DynamicData();
               data6.setAuditTitletype(dic.getFiledName() + "6");
               data6.setAuditResult(dic.getFileUrl());
               dynamicDataList.add(data6);

               DynamicData data7= new DynamicData();
               data7.setAuditTitletype(dic.getFiledName() + "7");

               if(dic.getCheckDate()==null){
                   if(dic.getBookDate()==null){
                       data7.setAuditResult("");
                   }
                   else {
                       data7.setAuditResult( DateUtil.format(DateUtil.offsetMonth( dic.getBookDate(),24), "yyyy-MM-dd"));
                   }
               }
               else {
                   data7.setAuditResult( DateUtil.format(DateUtil.offsetMonth( dic.getCheckDate(),24), "yyyy-MM-dd"));
               }

               dynamicDataList.add(data7);

           }

           dept.setDcaBAuditdynamicList(dynamicDataList);
       }
   }
        return getDataTable(list);
        }

/**
 * 添加
 * @param  deptBUser
 * @return
 */
@Log("新增/按钮")
@PostMapping
//@RequiresPermissions("deptBUser:add")
public void addDeptBUser(@Valid DeptBUser deptBUser)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        deptBUser.setCreateUserId(currentUser.getUserId());
        this.iDeptBUserService.createDeptBUser(deptBUser);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param deptBUser
 * @return
 */
@Log("修改")
@PutMapping
//@RequiresPermissions("deptBUser:update")
public void updateDeptBUser(@Valid DeptBUser deptBUser)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      deptBUser.setModifyUserId(currentUser.getUserId());
        this.iDeptBUserService.updateDeptBUser(deptBUser);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
//@RequiresPermissions("deptBUser:delete")
public void deleteDeptBUsers(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDeptBUserService.deleteDeptBUsers(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
//@RequiresPermissions("deptBUser:export")
public void export(QueryRequest request, ViewDept deptBUser, String dataJson, HttpServletResponse response) throws FebsException {
    try {
        IPage<ViewDept> list=this.iDeptBUserService.findDeptBUserList(request, deptBUser);
        List<ViewDept> viewDeptList =list.getRecords();
        List<String> accountList= viewDeptList.stream().map(p->p.getUserAccount()).collect(Collectors.toList());
        List<DynamicData> dynamicDataList = new ArrayList<>();
        if(accountList.size()>0) {
            User currentUser = FebsUtil.getCurrentUser();
            List<ViewDicType> dynamicList = this.iDeptBBookService.findBook(currentUser.getUsername(), accountList);

            for (ViewDept dept : viewDeptList
            ) {

                List<ViewDicType> dl = dynamicList.stream().filter(p -> p.getUserAccount().equals(dept.getUserAccount())).collect(Collectors.toList());
                for (ViewDicType dic : dl
                ) {
                    DynamicData data = new DynamicData();
                    data.setAuditTitletype(dic.getFiledName() + "1");
                    data.setAuditResult(dic.getBookType());
                    data.setUserAccount(dic.getUserAccount());
                    dynamicDataList.add(data);

                    DynamicData data2 = new DynamicData();
                    data2.setAuditTitletype(dic.getFiledName() + "2");
                    data2.setAuditResult(dic.getBookNo());
                    data2.setUserAccount(dic.getUserAccount());
                    dynamicDataList.add(data2);

                    DynamicData data3 = new DynamicData();
                    data3.setAuditTitletype(dic.getFiledName() + "3");
                    data3.setAuditResult(dic.getBookDate() == null ? "" : DateUtil.format(dic.getBookDate(), "yyyy-MM-dd"));
                    data3.setUserAccount(dic.getUserAccount());
                    dynamicDataList.add(data3);

                    DynamicData data4 = new DynamicData();
                    data4.setAuditTitletype(dic.getFiledName() + "4");
                    data4.setAuditResult(dic.getCheckDate() == null ? "" : DateUtil.format(dic.getCheckDate(), "yyyy-MM-dd"));
                    data4.setUserAccount(dic.getUserAccount());
                    dynamicDataList.add(data4);

                    DynamicData data5 = new DynamicData();
                    data5.setAuditTitletype(dic.getFiledName() + "5");
                    data5.setAuditResult(dic.getFileId());
                    data5.setUserAccount(dic.getUserAccount());
                    dynamicDataList.add(data5);

                    DynamicData data6 = new DynamicData();
                    data6.setAuditTitletype(dic.getFiledName() + "6");
                    data6.setAuditResult(dic.getFileUrl());
                    data6.setUserAccount(dic.getUserAccount());
                    dynamicDataList.add(data6);

                    DynamicData data7= new DynamicData();
                    data7.setAuditTitletype(dic.getFiledName() + "7");

                    if(dic.getCheckDate()==null){
                        if(dic.getBookDate()==null){
                            data7.setAuditResult("");
                        }
                        else {
                            data7.setAuditResult( DateUtil.format(DateUtil.offsetMonth( dic.getBookDate(),24), "yyyy-MM-dd"));
                        }
                    }
                    else {
                        data7.setAuditResult( DateUtil.format(DateUtil.offsetMonth( dic.getCheckDate(),24), "yyyy-MM-dd"));
                    }
                    data7.setUserAccount(dic.getUserAccount());
                    dynamicDataList.add(data7);

                }

               // dept.setDcaBAuditdynamicList(dynamicDataList);
            }
        }

        ExportExcelUtils.exportCustomExcelCutome(response, viewDeptList, dataJson,dynamicDataList,"");
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
//@RequiresPermissions("deptBUser:import")
public void downTemplate(HttpServletResponse response) {
        List<DeptBUser> publishList = new ArrayList<>();
        ExcelKit.$Export(DeptBUser.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
//@RequiresPermissions("deptBUser:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<DeptBUser> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(DeptBUser.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<DeptBUser>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, DeptBUser entity) {
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
        if(CollectionUtil.isEmpty(errorList)){
        for (DeptBUser deptBUserImport:successList
        ) {
     //  DeptBUser deptBUser =new DeptBUser();
     //   BeanUtil.copyProperties(deptBUserImport,deptBUser, CopyOptions.create().setIgnoreNullValue(true));
        this.iDeptBUserService.createDeptBUser(deptBUserImport);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public DeptBUser detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DeptBUser deptBUser=this.iDeptBUserService.getById(id);
        return deptBUser;
        }

        @GetMapping("type")
        public List<DicType> getDicType(){
                User currentUser= FebsUtil.getCurrentUser();
        return   this.iDeptBUserService.getDicType(currentUser.getUsername());
        }
        }