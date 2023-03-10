
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.mdl.service.IMdlLPersonService;
import cc.mrbird.febs.mdl.entity.MdlLPerson;

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
 * @since 2022-11-29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("mdlLPerson")

public class MdlLPersonController extends BaseController{

private String message;
@Autowired
public IMdlLPersonService iMdlLPersonService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/MdlLPerson/MdlLPerson','ass/MdlLPerson/MdlLPerson','mdlLPerson:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlLPerson:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlLPerson:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlLPerson:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlLPerson:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlLPerson:import',1,5,NOW());
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param mdlLPerson ????????????
 * @return
 */
@GetMapping

public Map<String, Object> List(QueryRequest request, MdlLPerson mdlLPerson){
        return getDataTable(this.iMdlLPersonService.findMdlLPersons(request, mdlLPerson));
        }

/**
 * ??????
 * @param  mdlLPerson
 * @return
 */
@Log("??????/??????")
@PostMapping

public void addMdlLPerson(@Valid MdlLPerson mdlLPerson)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        mdlLPerson.setCreateUserId(currentUser.getUserId());
        this.iMdlLPersonService.createMdlLPerson(mdlLPerson);
        }catch(Exception e){
        message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * ??????
 * @param mdlLPerson
 * @return
 */
@Log("??????")
@PutMapping

public void updateMdlLPerson(@Valid MdlLPerson mdlLPerson)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      mdlLPerson.setModifyUserId(currentUser.getUserId());
        this.iMdlLPersonService.updateMdlLPerson(mdlLPerson);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("??????")
@DeleteMapping("/{ids}")

public void deleteMdlLPersons(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iMdlLPersonService.deleteMdlLPersons(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("mdlLPerson:export")
public void export(QueryRequest request, MdlLPerson mdlLPerson, HttpServletResponse response) throws FebsException {
        try {
        List<MdlLPerson> mdlLPersons = this.iMdlLPersonService.findMdlLPersons(request, mdlLPerson).getRecords();
        ExcelKit.$Export(MdlLPerson.class, response).downXlsx(mdlLPersons, false);
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
@RequiresPermissions("mdlLPerson:import")
public void downTemplate(HttpServletResponse response) {
        List<MdlLPerson> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlLPerson.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
@RequiresPermissions("mdlLPerson:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlLPerson> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlLPerson.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlLPerson>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, MdlLPerson entity) {
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
        for (MdlLPerson mdlLPersonImport:successList
        ) {
     //  MdlLPerson mdlLPerson =new MdlLPerson();
     //   BeanUtil.copyProperties(mdlLPersonImport,mdlLPerson, CopyOptions.create().setIgnoreNullValue(true));
        this.iMdlLPersonService.createMdlLPerson(mdlLPersonImport);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public MdlLPerson detail(@NotBlank(message = "{required}") @PathVariable String id) {
    MdlLPerson mdlLPerson=this.iMdlLPersonService.getById(id);
        return mdlLPerson;
        }
        }