
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.mdl.service.IMdlBPeixunService;
import cc.mrbird.febs.mdl.entity.MdlBPeixun;

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
 * @since 2023-01-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("mdlBPeixun")

public class MdlBPeixunController extends BaseController{

private String message;
@Autowired
public IMdlBPeixunService iMdlBPeixunService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/MdlBPeixun/MdlBPeixun','ass/MdlBPeixun/MdlBPeixun','mdlBPeixun:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBPeixun:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBPeixun:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBPeixun:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBPeixun:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBPeixun:import',1,5,NOW());
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param mdlBPeixun ????????????
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, MdlBPeixun mdlBPeixun){
        return getDataTable(this.iMdlBPeixunService.findMdlBPeixuns(request, mdlBPeixun));
        }

/**
 * ??????
 * @param  mdlBPeixun
 * @return
 */
@Log("??????/??????")
@PostMapping
@RequiresPermissions("mdlBPeixun:add")
public void addMdlBPeixun(@Valid MdlBPeixun mdlBPeixun)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        mdlBPeixun.setCreateUserId(currentUser.getUserId());
        this.iMdlBPeixunService.createMdlBPeixun(mdlBPeixun);
        }catch(Exception e){
        message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * ??????
 * @param mdlBPeixun
 * @return
 */
@Log("??????")
@PutMapping
@RequiresPermissions("mdlBPeixun:update")
public void updateMdlBPeixun(@Valid MdlBPeixun mdlBPeixun)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      mdlBPeixun.setModifyUserId(currentUser.getUserId());
        this.iMdlBPeixunService.updateMdlBPeixun(mdlBPeixun);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("??????")
@DeleteMapping("/{ids}")
public void deleteMdlBPeixuns(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iMdlBPeixunService.deleteMdlBPeixuns(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("mdlBPeixun:export")
public void export(QueryRequest request, MdlBPeixun mdlBPeixun, HttpServletResponse response) throws FebsException {
        try {
        List<MdlBPeixun> mdlBPeixuns = this.iMdlBPeixunService.findMdlBPeixuns(request, mdlBPeixun).getRecords();
        ExcelKit.$Export(MdlBPeixun.class, response).downXlsx(mdlBPeixuns, false);
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)

public void downTemplate(HttpServletResponse response) {
        List<MdlBPeixun> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlBPeixun.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)

public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlBPeixun> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlBPeixun.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlBPeixun>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, MdlBPeixun entity) {
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
        for (MdlBPeixun mdlBPeixunImport:successList
        ) {
     //  MdlBPeixun mdlBPeixun =new MdlBPeixun();
     //   BeanUtil.copyProperties(mdlBPeixunImport,mdlBPeixun, CopyOptions.create().setIgnoreNullValue(true));
        this.iMdlBPeixunService.createMdlBPeixun(mdlBPeixunImport);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public MdlBPeixun detail(@NotBlank(message = "{required}") @PathVariable String id) {
    MdlBPeixun mdlBPeixun=this.iMdlBPeixunService.getById(id);
        return mdlBPeixun;
        }
        }