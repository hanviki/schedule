
package cc.mrbird.febs.fs.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.fs.service.IFsBHealthService;
import cc.mrbird.febs.fs.entity.FsBHealth;

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

/**
 *
 * @author viki
 * @since 2022-07-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("fsBHealth")

public class FsBHealthController extends BaseController{

private String message;
@Autowired
public IFsBHealthService iFsBHealthService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/FsBHealth/FsBHealth','ass/FsBHealth/FsBHealth','fsBHealth:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','fsBHealth:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','fsBHealth:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','fsBHealth:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','fsBHealth:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','fsBHealth:import',1,5,NOW());
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param fsBHealth ????????????
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, FsBHealth fsBHealth){
        User currentUser = FebsUtil.getCurrentUser();
        fsBHealth.setDeptId(currentUser.getDeptId());
        if(StringUtils.isNotEmpty(currentUser.getEmail())) {
                fsBHealth.setYuanqu(currentUser.getEmail().trim());
        }
        return getDataTable(this.iFsBHealthService.findFsBHealthList(request, fsBHealth));
        }

/**
 * ??????
 * @param  fsBHealth
 * @return
 */
@Log("??????/??????")
@PostMapping
public void addFsBHealth(@Valid FsBHealth fsBHealth)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        fsBHealth.setCreateUserId(currentUser.getUserId());
        this.iFsBHealthService.createFsBHealth(fsBHealth);
        }catch(Exception e){
        message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * ??????
 * @param fsBHealth
 * @return
 */
@Log("??????")
@PutMapping
public void updateFsBHealth(@Valid FsBHealth fsBHealth)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      fsBHealth.setModifyUserId(currentUser.getUserId());
        this.iFsBHealthService.updateFsBHealth(fsBHealth);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("??????")
@DeleteMapping("/{ids}")
public void deleteFsBHealths(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iFsBHealthService.deleteFsBHealths(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
public void export(QueryRequest request, FsBHealth fsBHealth,String dataJson, HttpServletResponse response) throws FebsException {
        try {
                request.setIsSearchCount(false);
                request.setPageSize(10000);
                request.setPageNum(1);
                User currentUser = FebsUtil.getCurrentUser();
                fsBHealth.setDeptId(currentUser.getDeptId());
                if(StringUtils.isNotEmpty(currentUser.getEmail())) {
                        fsBHealth.setYuanqu(currentUser.getEmail().trim());
                }
        List<FsBHealth> fsBHealths = this.iFsBHealthService.findFsBHealthList(request, fsBHealth).getRecords();
                ExportExcelUtils.exportCustomExcel_han(response, fsBHealths,dataJson,"");
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
public void downTemplate(HttpServletResponse response) {
        List<FsBHealth> publishList = new ArrayList<>();
        ExcelKit.$Export(FsBHealth.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<FsBHealth> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(FsBHealth.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<FsBHealth>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, FsBHealth entity) {
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
        for (FsBHealth fsBHealthImport:successList
        ) {
     //  FsBHealth fsBHealth =new FsBHealth();
     //   BeanUtil.copyProperties(fsBHealthImport,fsBHealth, CopyOptions.create().setIgnoreNullValue(true));

        this.iFsBHealthService.createFsBHealth(fsBHealthImport);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public FsBHealth detail(@NotBlank(message = "{required}") @PathVariable String id) {
    FsBHealth fsBHealth=this.iFsBHealthService.getById(id);
        return fsBHealth;
        }
        }