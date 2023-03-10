
package cc.mrbird.febs.dept.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dept.service.IDeptBBookDService;
import cc.mrbird.febs.dept.entity.DeptBBookD;

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
@RequestMapping("deptBBookD")

public class DeptBBookDController extends BaseController{

private String message;
@Autowired
public IDeptBBookDService iDeptBBookDService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/DeptBBookD/DeptBBookD','ass/DeptBBookD/DeptBBookD','deptBBookD:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','deptBBookD:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','deptBBookD:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','deptBBookD:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','deptBBookD:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','deptBBookD:import',1,5,NOW());
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param deptBBookD ????????????
 * @return
 */
@GetMapping
//@RequiresPermissions("deptBBookD:view")
public Map<String, Object> List(QueryRequest request, DeptBBookD deptBBookD){
        return getDataTable(this.iDeptBBookDService.findDeptBBookDs(request, deptBBookD));
        }

/**
 * ??????
 * @param  deptBBookD
 * @return
 */
@Log("??????/??????")
@PostMapping
//@RequiresPermissions("deptBBookD:add")
public void addDeptBBookD(@Valid DeptBBookD deptBBookD)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        deptBBookD.setCreateUserId(currentUser.getUserId());
        this.iDeptBBookDService.createDeptBBookD(deptBBookD);
        }catch(Exception e){
        message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * ??????
 * @param deptBBookD
 * @return
 */
@Log("??????")
@PutMapping
//@RequiresPermissions("deptBBookD:update")
public void updateDeptBBookD(@Valid DeptBBookD deptBBookD)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      deptBBookD.setModifyUserId(currentUser.getUserId());
        this.iDeptBBookDService.updateDeptBBookD(deptBBookD);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("??????")
@DeleteMapping("/{ids}")
//@RequiresPermissions("deptBBookD:delete")
public void deleteDeptBBookDs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDeptBBookDService.deleteDeptBBookDs(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
//@RequiresPermissions("deptBBookD:export")
public void export(QueryRequest request, DeptBBookD deptBBookD, HttpServletResponse response) throws FebsException {
        try {
        List<DeptBBookD> deptBBookDs = this.iDeptBBookDService.findDeptBBookDs(request, deptBBookD).getRecords();
        ExcelKit.$Export(DeptBBookD.class, response).downXlsx(deptBBookDs, false);
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
//@RequiresPermissions("deptBBookD:import")
public void downTemplate(HttpServletResponse response) {
        List<DeptBBookD> publishList = new ArrayList<>();
        ExcelKit.$Export(DeptBBookD.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
//@RequiresPermissions("deptBBookD:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<DeptBBookD> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(DeptBBookD.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<DeptBBookD>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, DeptBBookD entity) {
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
        for (DeptBBookD deptBBookDImport:successList
        ) {
     //  DeptBBookD deptBBookD =new DeptBBookD();
     //   BeanUtil.copyProperties(deptBBookDImport,deptBBookD, CopyOptions.create().setIgnoreNullValue(true));
        this.iDeptBBookDService.createDeptBBookD(deptBBookDImport);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public DeptBBookD detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DeptBBookD deptBBookD=this.iDeptBBookDService.getById(id);
        return deptBBookD;
        }
        }