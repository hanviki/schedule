
package cc.mrbird.febs.xf.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.xf.service.IXfBInnertalkDService;
import cc.mrbird.febs.xf.entity.XfBInnertalkD;

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
 * @since 2022-11-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("xfBInnertalkD")

public class XfBInnertalkDController extends BaseController{

private String message;
@Autowired
public IXfBInnertalkDService iXfBInnertalkDService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/XfBInnertalkD/XfBInnertalkD','ass/XfBInnertalkD/XfBInnertalkD','xfBInnertalkD:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBInnertalkD:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBInnertalkD:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBInnertalkD:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBInnertalkD:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBInnertalkD:import',1,5,NOW());
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param xfBInnertalkD ????????????
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, XfBInnertalkD xfBInnertalkD){
        return getDataTable(this.iXfBInnertalkDService.findXfBInnertalkDs(request, xfBInnertalkD));
        }

/**
 * ??????
 * @param  xfBInnertalkD
 * @return
 */
@Log("??????/??????")
@PostMapping
public void addXfBInnertalkD(@Valid XfBInnertalkD xfBInnertalkD)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        xfBInnertalkD.setCreateUserId(currentUser.getUserId());
        this.iXfBInnertalkDService.createXfBInnertalkD(xfBInnertalkD);
        }catch(Exception e){
        message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * ??????
 * @param xfBInnertalkD
 * @return
 */
@Log("??????")
@PutMapping
@RequiresPermissions("xfBInnertalkD:update")
public void updateXfBInnertalkD(@Valid XfBInnertalkD xfBInnertalkD)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      xfBInnertalkD.setModifyUserId(currentUser.getUserId());
        this.iXfBInnertalkDService.updateXfBInnertalkD(xfBInnertalkD);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("??????")
@DeleteMapping("/{ids}")
@RequiresPermissions("xfBInnertalkD:delete")
public void deleteXfBInnertalkDs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iXfBInnertalkDService.deleteXfBInnertalkDs(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("xfBInnertalkD:export")
public void export(QueryRequest request, XfBInnertalkD xfBInnertalkD, HttpServletResponse response) throws FebsException {
        try {
        List<XfBInnertalkD> xfBInnertalkDs = this.iXfBInnertalkDService.findXfBInnertalkDs(request, xfBInnertalkD).getRecords();
        ExcelKit.$Export(XfBInnertalkD.class, response).downXlsx(xfBInnertalkDs, false);
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
@RequiresPermissions("xfBInnertalkD:import")
public void downTemplate(HttpServletResponse response) {
        List<XfBInnertalkD> publishList = new ArrayList<>();
        ExcelKit.$Export(XfBInnertalkD.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
@RequiresPermissions("xfBInnertalkD:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<XfBInnertalkD> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(XfBInnertalkD.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<XfBInnertalkD>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, XfBInnertalkD entity) {
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
        for (XfBInnertalkD xfBInnertalkDImport:successList
        ) {
     //  XfBInnertalkD xfBInnertalkD =new XfBInnertalkD();
     //   BeanUtil.copyProperties(xfBInnertalkDImport,xfBInnertalkD, CopyOptions.create().setIgnoreNullValue(true));
        this.iXfBInnertalkDService.createXfBInnertalkD(xfBInnertalkDImport);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public XfBInnertalkD detail(@NotBlank(message = "{required}") @PathVariable String id) {
    XfBInnertalkD xfBInnertalkD=this.iXfBInnertalkDService.getById(id);
        return xfBInnertalkD;
        }
        }