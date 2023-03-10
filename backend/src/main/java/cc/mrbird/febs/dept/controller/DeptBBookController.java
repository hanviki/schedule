
package cc.mrbird.febs.dept.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dept.service.IDeptBBookService;
import cc.mrbird.febs.dept.entity.DeptBBook;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author viki
 * @since 2023-02-28
 */
@Slf4j
@Validated
@RestController
@RequestMapping("deptBBook")

public class DeptBBookController extends BaseController{

private String message;
@Autowired
public IDeptBBookService iDeptBBookService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/DeptBBook/DeptBBook','ass/DeptBBook/DeptBBook','deptBBook:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','deptBBook:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','deptBBook:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','deptBBook:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','deptBBook:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','deptBBook:import',1,5,NOW());
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param deptBBook ????????????
 * @return
 */
@GetMapping
//@RequiresPermissions("deptBBook:view")
public Map<String, Object> List(QueryRequest request, DeptBBook deptBBook){
        return getDataTable(this.iDeptBBookService.findDeptBBooks(request, deptBBook));
        }

/**
 * ??????
 * @param  deptBBook
 * @return
 */
@Log("??????/??????")
@PostMapping
//@RequiresPermissions("deptBBook:add")
public void addDeptBBook(@Valid DeptBBook deptBBook)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        deptBBook.setCreateUserId(currentUser.getUserId());
        this.iDeptBBookService.createDeptBBook(deptBBook);
        }catch(Exception e){
        message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * ??????
 * @param deptBBook
 * @return
 */
@Log("??????")
@PutMapping
//@RequiresPermissions("deptBBook:update")
public void updateDeptBBook(@Valid DeptBBook deptBBook)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      deptBBook.setModifyUserId(currentUser.getUserId());
        this.iDeptBBookService.updateDeptBBook(deptBBook);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("??????")
@DeleteMapping("/{ids}")
//@RequiresPermissions("deptBBook:delete")
public void deleteDeptBBooks(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDeptBBookService.deleteDeptBBooks(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
//@RequiresPermissions("deptBBook:export")
public void export(QueryRequest request, DeptBBook deptBBook, HttpServletResponse response) throws FebsException {
        try {
        List<DeptBBook> deptBBooks = this.iDeptBBookService.findDeptBBooks(request, deptBBook).getRecords();
        ExcelKit.$Export(DeptBBook.class, response).downXlsx(deptBBooks, false);
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
//@RequiresPermissions("deptBBook:import")
public void downTemplate(HttpServletResponse response) {
        List<DeptBBook> publishList = new ArrayList<>();
        ExcelKit.$Export(DeptBBook.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
//@RequiresPermissions("deptBBook:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<DeptBBook> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(DeptBBook.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<DeptBBook>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, DeptBBook entity) {
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
        if(CollectionUtil.isEmpty(errorList)){
        for (DeptBBook deptBBookImport:successList
        ) {
     //  DeptBBook deptBBook =new DeptBBook();
     //   BeanUtil.copyProperties(deptBBookImport,deptBBook, CopyOptions.create().setIgnoreNullValue(true));
        this.iDeptBBookService.createDeptBBook(deptBBookImport);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public DeptBBook detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DeptBBook deptBBook=this.iDeptBBookService.getById(id);
        return deptBBook;
        }
        }