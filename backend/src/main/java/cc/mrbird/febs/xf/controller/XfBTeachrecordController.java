
package cc.mrbird.febs.xf.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.xf.service.IXfBTeachrecordService;
import cc.mrbird.febs.xf.entity.XfBTeachrecord;

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
@RequestMapping("xfBTeachrecord")

public class XfBTeachrecordController extends BaseController{

private String message;
@Autowired
public IXfBTeachrecordService iXfBTeachrecordService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/XfBTeachrecord/XfBTeachrecord','ass/XfBTeachrecord/XfBTeachrecord','xfBTeachrecord:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBTeachrecord:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBTeachrecord:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBTeachrecord:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBTeachrecord:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBTeachrecord:import',1,5,NOW());
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param xfBTeachrecord ????????????
 * @return
 */
@GetMapping

public Map<String, Object> List(QueryRequest request, XfBTeachrecord xfBTeachrecord){
        return getDataTable(this.iXfBTeachrecordService.findXfBTeachrecordList(request, xfBTeachrecord));
        }

/**
 * ??????
 * @param  xfBTeachrecord
 * @return
 */
@Log("??????/??????")
@PostMapping

public void addXfBTeachrecord(@Valid XfBTeachrecord xfBTeachrecord)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        xfBTeachrecord.setCreateUserId(currentUser.getUserId());
        this.iXfBTeachrecordService.createXfBTeachrecord(xfBTeachrecord);
        }catch(Exception e){
        message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * ??????
 * @param xfBTeachrecord
 * @return
 */
@Log("??????")
@PutMapping

public void updateXfBTeachrecord(@Valid XfBTeachrecord xfBTeachrecord)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      xfBTeachrecord.setModifyUserId(currentUser.getUserId());
        this.iXfBTeachrecordService.updateXfBTeachrecord(xfBTeachrecord);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("??????")
@DeleteMapping("/{ids}")

public void deleteXfBTeachrecords(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iXfBTeachrecordService.deleteXfBTeachrecords(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")

public void export(QueryRequest request, XfBTeachrecord xfBTeachrecord, HttpServletResponse response) throws FebsException {
        try {
        List<XfBTeachrecord> xfBTeachrecords = this.iXfBTeachrecordService.findXfBTeachrecords(request, xfBTeachrecord).getRecords();
        ExcelKit.$Export(XfBTeachrecord.class, response).downXlsx(xfBTeachrecords, false);
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)

public void downTemplate(HttpServletResponse response) {
        List<XfBTeachrecord> publishList = new ArrayList<>();
        ExcelKit.$Export(XfBTeachrecord.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)

public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<XfBTeachrecord> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(XfBTeachrecord.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<XfBTeachrecord>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, XfBTeachrecord entity) {
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
        for (XfBTeachrecord xfBTeachrecordImport:successList
        ) {
     //  XfBTeachrecord xfBTeachrecord =new XfBTeachrecord();
     //   BeanUtil.copyProperties(xfBTeachrecordImport,xfBTeachrecord, CopyOptions.create().setIgnoreNullValue(true));
        this.iXfBTeachrecordService.createXfBTeachrecord(xfBTeachrecordImport);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public XfBTeachrecord detail(@NotBlank(message = "{required}") @PathVariable String id) {
    XfBTeachrecord xfBTeachrecord=this.iXfBTeachrecordService.getById(id);
        return xfBTeachrecord;
        }
        }