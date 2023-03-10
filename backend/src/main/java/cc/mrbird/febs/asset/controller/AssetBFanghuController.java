
package cc.mrbird.febs.asset.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.asset.service.IAssetBFanghuService;
import cc.mrbird.febs.asset.entity.AssetBFanghu;

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
 * @since 2023-02-06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("assetBFanghu")

public class AssetBFanghuController extends BaseController{

private String message;
@Autowired
public IAssetBFanghuService iAssetBFanghuService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/AssetBFanghu/AssetBFanghu','ass/AssetBFanghu/AssetBFanghu','assetBFanghu:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','assetBFanghu:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','assetBFanghu:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','assetBFanghu:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','assetBFanghu:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','assetBFanghu:import',1,5,NOW());
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param assetBFanghu ????????????
 * @return
 */
@GetMapping
//@RequiresPermissions("assetBFanghu:view")
public Map<String, Object> List(QueryRequest request, AssetBFanghu assetBFanghu){
        return getDataTable(this.iAssetBFanghuService.findAssetBFanghus(request, assetBFanghu));
        }

/**
 * ??????
 * @param  assetBFanghu
 * @return
 */
@Log("??????/??????")
@PostMapping
//@RequiresPermissions("assetBFanghu:add")
public void addAssetBFanghu(@Valid AssetBFanghu assetBFanghu)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        assetBFanghu.setCreateUserId(currentUser.getUserId());
        this.iAssetBFanghuService.createAssetBFanghu(assetBFanghu);
        }catch(Exception e){
        message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * ??????
 * @param assetBFanghu
 * @return
 */
@Log("??????")
@PutMapping
//@RequiresPermissions("assetBFanghu:update")
public void updateAssetBFanghu(@Valid AssetBFanghu assetBFanghu)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      assetBFanghu.setModifyUserId(currentUser.getUserId());
        this.iAssetBFanghuService.updateAssetBFanghu(assetBFanghu);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("??????")
@DeleteMapping("/{ids}")
//@RequiresPermissions("assetBFanghu:delete")
public void deleteAssetBFanghus(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iAssetBFanghuService.deleteAssetBFanghus(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
//@RequiresPermissions("assetBFanghu:export")
public void export(QueryRequest request, AssetBFanghu assetBFanghu, HttpServletResponse response) throws FebsException {
        try {
        List<AssetBFanghu> assetBFanghus = this.iAssetBFanghuService.findAssetBFanghus(request, assetBFanghu).getRecords();
        ExcelKit.$Export(AssetBFanghu.class, response).downXlsx(assetBFanghus, false);
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
//@RequiresPermissions("assetBFanghu:import")
public void downTemplate(HttpServletResponse response) {
        List<AssetBFanghu> publishList = new ArrayList<>();
        ExcelKit.$Export(AssetBFanghu.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
//@RequiresPermissions("assetBFanghu:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<AssetBFanghu> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(AssetBFanghu.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<AssetBFanghu>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, AssetBFanghu entity) {
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
        for (AssetBFanghu assetBFanghuImport:successList
        ) {
     //  AssetBFanghu assetBFanghu =new AssetBFanghu();
     //   BeanUtil.copyProperties(assetBFanghuImport,assetBFanghu, CopyOptions.create().setIgnoreNullValue(true));
        this.iAssetBFanghuService.createAssetBFanghu(assetBFanghuImport);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public AssetBFanghu detail(@NotBlank(message = "{required}") @PathVariable String id) {
    AssetBFanghu assetBFanghu=this.iAssetBFanghuService.getById(id);
        return assetBFanghu;
        }
        }