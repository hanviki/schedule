
package cc.mrbird.febs.xf.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.xf.service.IXfBPersonService;
import cc.mrbird.febs.xf.entity.XfBPerson;

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
 * @since 2022-11-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("xfBPerson")

public class XfBPersonController extends BaseController{

private String message;
@Autowired
public IXfBPersonService iXfBPersonService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/XfBPerson/XfBPerson','ass/XfBPerson/XfBPerson','xfBPerson:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBPerson:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBPerson:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBPerson:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBPerson:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBPerson:import',1,5,NOW());
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param xfBPerson ????????????
 * @return
 */
@GetMapping

public Map<String, Object> List(QueryRequest request, XfBPerson xfBPerson){
        return getDataTable(this.iXfBPersonService.findXfBPersonList(request, xfBPerson));
        }

/**
 * ??????
 * @param  xfBPerson
 * @return
 */
@Log("??????/??????")
@PostMapping

public void addXfBPerson(@Valid XfBPerson xfBPerson)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        xfBPerson.setCreateUserId(currentUser.getUserId());
        this.iXfBPersonService.createXfBPerson(xfBPerson);
        }catch(Exception e){
        message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * ??????
 * @param xfBPerson
 * @return
 */
@Log("??????")
@PutMapping

public void updateXfBPerson(@Valid XfBPerson xfBPerson)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      xfBPerson.setModifyUserId(currentUser.getUserId());
        this.iXfBPersonService.updateXfBPerson(xfBPerson);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("??????")
@DeleteMapping("/{ids}")

public void deleteXfBPersons(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iXfBPersonService.deleteXfBPersons(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")

public void export(QueryRequest request, XfBPerson xfBPerson, HttpServletResponse response) throws FebsException {
        try {
        List<XfBPerson> xfBPersons = this.iXfBPersonService.findXfBPersons(request, xfBPerson).getRecords();
        ExcelKit.$Export(XfBPerson.class, response).downXlsx(xfBPersons, false);
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)

public void downTemplate(HttpServletResponse response) {
        List<XfBPerson> publishList = new ArrayList<>();
        ExcelKit.$Export(XfBPerson.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)

public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<XfBPerson> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(XfBPerson.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<XfBPerson>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, XfBPerson entity) {
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
        for (XfBPerson xfBPersonImport:successList
        ) {
     //  XfBPerson xfBPerson =new XfBPerson();
     //   BeanUtil.copyProperties(xfBPersonImport,xfBPerson, CopyOptions.create().setIgnoreNullValue(true));
        this.iXfBPersonService.createXfBPerson(xfBPersonImport);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public XfBPerson detail(@NotBlank(message = "{required}") @PathVariable String id) {
    XfBPerson xfBPerson=this.iXfBPersonService.getById(id);
        return xfBPerson;
        }
        }