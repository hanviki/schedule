
package cc.mrbird.febs.xf.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.xf.service.IXfBMeetingService;
import cc.mrbird.febs.xf.entity.XfBMeeting;

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
@RequestMapping("xfBMeeting")

public class XfBMeetingController extends BaseController{

private String message;
@Autowired
public IXfBMeetingService iXfBMeetingService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/XfBMeeting/XfBMeeting','ass/XfBMeeting/XfBMeeting','xfBMeeting:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBMeeting:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBMeeting:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBMeeting:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBMeeting:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBMeeting:import',1,5,NOW());
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param xfBMeeting ????????????
 * @return
 */
@GetMapping

public Map<String, Object> List(QueryRequest request, XfBMeeting xfBMeeting){
        return getDataTable(this.iXfBMeetingService.findXfBMeetingList(request, xfBMeeting));
        }

/**
 * ??????
 * @param  xfBMeeting
 * @return
 */
@Log("??????/??????")
@PostMapping

public void addXfBMeeting(@Valid XfBMeeting xfBMeeting)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        xfBMeeting.setCreateUserId(currentUser.getUserId());
        this.iXfBMeetingService.createXfBMeeting(xfBMeeting);
        }catch(Exception e){
        message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * ??????
 * @param xfBMeeting
 * @return
 */
@Log("??????")
@PutMapping

public void updateXfBMeeting(@Valid XfBMeeting xfBMeeting)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      xfBMeeting.setModifyUserId(currentUser.getUserId());
        this.iXfBMeetingService.updateXfBMeeting(xfBMeeting);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("??????")
@DeleteMapping("/{ids}")

public void deleteXfBMeetings(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iXfBMeetingService.deleteXfBMeetings(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")

public void export(QueryRequest request, XfBMeeting xfBMeeting, HttpServletResponse response) throws FebsException {
        try {
        List<XfBMeeting> xfBMeetings = this.iXfBMeetingService.findXfBMeetings(request, xfBMeeting).getRecords();
        ExcelKit.$Export(XfBMeeting.class, response).downXlsx(xfBMeetings, false);
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)

public void downTemplate(HttpServletResponse response) {
        List<XfBMeeting> publishList = new ArrayList<>();
        ExcelKit.$Export(XfBMeeting.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)

public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<XfBMeeting> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(XfBMeeting.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<XfBMeeting>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, XfBMeeting entity) {
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
        for (XfBMeeting xfBMeetingImport:successList
        ) {
     //  XfBMeeting xfBMeeting =new XfBMeeting();
     //   BeanUtil.copyProperties(xfBMeetingImport,xfBMeeting, CopyOptions.create().setIgnoreNullValue(true));
        this.iXfBMeetingService.createXfBMeeting(xfBMeetingImport);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public XfBMeeting detail(@NotBlank(message = "{required}") @PathVariable String id) {
    XfBMeeting xfBMeeting=this.iXfBMeetingService.getById(id);
        return xfBMeeting;
        }
        }