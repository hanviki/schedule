
package cc.mrbird.febs.asset.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.asset.service.IAssetBFsjbxxService;
import cc.mrbird.febs.asset.entity.AssetBFsjbxx;

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
 * @since 2023-02-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("assetBFsjbxx")

public class AssetBFsjbxxController extends BaseController{

private String message;
@Autowired
public IAssetBFsjbxxService iAssetBFsjbxxService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/AssetBFsjbxx/AssetBFsjbxx','ass/AssetBFsjbxx/AssetBFsjbxx','assetBFsjbxx:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','assetBFsjbxx:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','assetBFsjbxx:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','assetBFsjbxx:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','assetBFsjbxx:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','assetBFsjbxx:import',1,5,NOW());
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param assetBFsjbxx ????????????
 * @return
 */
@GetMapping
//@RequiresPermissions("assetBFsjbxx:view")
public Map<String, Object> List(QueryRequest request, AssetBFsjbxx assetBFsjbxx){
        return getDataTable(this.iAssetBFsjbxxService.findAssetBFsjbxxs(request, assetBFsjbxx));
        }

/**
 * ??????
 * @param  assetBFsjbxx
 * @return
 */
@Log("??????/??????")
@PostMapping
//@RequiresPermissions("assetBFsjbxx:add")
public void addAssetBFsjbxx(@Valid AssetBFsjbxx assetBFsjbxx)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        assetBFsjbxx.setCreateUserId(currentUser.getUserId());
        this.iAssetBFsjbxxService.createAssetBFsjbxx(assetBFsjbxx);
        }catch(Exception e){
        message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * ??????
 * @param assetBFsjbxx
 * @return
 */
@Log("??????")
@PutMapping
//@RequiresPermissions("assetBFsjbxx:update")
public void updateAssetBFsjbxx(@Valid AssetBFsjbxx assetBFsjbxx)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      assetBFsjbxx.setModifyUserId(currentUser.getUserId());
        this.iAssetBFsjbxxService.updateAssetBFsjbxx(assetBFsjbxx);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("??????")
@DeleteMapping("/{ids}")
//@RequiresPermissions("assetBFsjbxx:delete")
public void deleteAssetBFsjbxxs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iAssetBFsjbxxService.deleteAssetBFsjbxxs(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
//@RequiresPermissions("assetBFsjbxx:export")
public void export(QueryRequest request, AssetBFsjbxx assetBFsjbxx, HttpServletResponse response) throws FebsException {
        try {
        List<AssetBFsjbxx> assetBFsjbxxs = this.iAssetBFsjbxxService.findAssetBFsjbxxs(request, assetBFsjbxx).getRecords();
        ExcelKit.$Export(AssetBFsjbxx.class, response).downXlsx(assetBFsjbxxs, false);
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
//@RequiresPermissions("assetBFsjbxx:import")
public void downTemplate(HttpServletResponse response) {
        List<AssetBFsjbxx> publishList = new ArrayList<>();
        ExcelKit.$Export(AssetBFsjbxx.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
//@RequiresPermissions("assetBFsjbxx:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<AssetBFsjbxx> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(AssetBFsjbxx.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<AssetBFsjbxx>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, AssetBFsjbxx entity) {
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
        for (AssetBFsjbxx assetBFsjbxxImport:successList
        ) {
     //  AssetBFsjbxx assetBFsjbxx =new AssetBFsjbxx();
     //   BeanUtil.copyProperties(assetBFsjbxxImport,assetBFsjbxx, CopyOptions.create().setIgnoreNullValue(true));
        this.iAssetBFsjbxxService.createAssetBFsjbxx(assetBFsjbxxImport);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public AssetBFsjbxx detail(@NotBlank(message = "{required}") @PathVariable String id) {
    AssetBFsjbxx assetBFsjbxx=this.iAssetBFsjbxxService.getById(id);
        return assetBFsjbxx;
        }
        }