
package cc.mrbird.febs.asset.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.asset.service.IAssetBShebeifileService;
import cc.mrbird.febs.asset.entity.AssetBShebeifile;

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
@RequestMapping("assetBShebeifile")

public class AssetBShebeifileController extends BaseController{

private String message;
@Autowired
public IAssetBShebeifileService iAssetBShebeifileService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/AssetBShebeifile/AssetBShebeifile','ass/AssetBShebeifile/AssetBShebeifile','assetBShebeifile:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','assetBShebeifile:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','assetBShebeifile:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','assetBShebeifile:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','assetBShebeifile:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','assetBShebeifile:import',1,5,NOW());
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param assetBShebeifile 查询条件
 * @return
 */
@GetMapping
//@RequiresPermissions("assetBShebeifile:view")
public Map<String, Object> List(QueryRequest request, AssetBShebeifile assetBShebeifile){
        return getDataTable(this.iAssetBShebeifileService.findAssetBShebeifiles(request, assetBShebeifile));
        }

/**
 * 添加
 * @param  assetBShebeifile
 * @return
 */
@Log("新增/按钮")
@PostMapping
//@RequiresPermissions("assetBShebeifile:add")
public void addAssetBShebeifile(@Valid AssetBShebeifile assetBShebeifile)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        assetBShebeifile.setCreateUserId(currentUser.getUserId());
        this.iAssetBShebeifileService.createAssetBShebeifile(assetBShebeifile);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param assetBShebeifile
 * @return
 */
@Log("修改")
@PutMapping
//@RequiresPermissions("assetBShebeifile:update")
public void updateAssetBShebeifile(@Valid AssetBShebeifile assetBShebeifile)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      assetBShebeifile.setModifyUserId(currentUser.getUserId());
        this.iAssetBShebeifileService.updateAssetBShebeifile(assetBShebeifile);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
//@RequiresPermissions("assetBShebeifile:delete")
public void deleteAssetBShebeifiles(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iAssetBShebeifileService.deleteAssetBShebeifiles(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
//@RequiresPermissions("assetBShebeifile:export")
public void export(QueryRequest request, AssetBShebeifile assetBShebeifile, HttpServletResponse response) throws FebsException {
        try {
        List<AssetBShebeifile> assetBShebeifiles = this.iAssetBShebeifileService.findAssetBShebeifiles(request, assetBShebeifile).getRecords();
        ExcelKit.$Export(AssetBShebeifile.class, response).downXlsx(assetBShebeifiles, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
//@RequiresPermissions("assetBShebeifile:import")
public void downTemplate(HttpServletResponse response) {
        List<AssetBShebeifile> publishList = new ArrayList<>();
        ExcelKit.$Export(AssetBShebeifile.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
//@RequiresPermissions("assetBShebeifile:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<AssetBShebeifile> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(AssetBShebeifile.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<AssetBShebeifile>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, AssetBShebeifile entity) {
        successList.add(entity); // 单行读取成功，加入入库队列。
        }

@Override
public void onError(int sheetIndex, int rowIndex,
        java.util.List<ExcelErrorField> errorFields) {
        // 读取数据失败，记录了当前行所有失败的数据
        errorList.add(MapUtil.of("sheetIndex", sheetIndex));
        errorList.add(MapUtil.of("rowIndex", rowIndex));
        errorList.add(MapUtil.of("errorFields", errorFields));
        }
        });

        // TODO: 执行successList的入库操作。
        if(CollectionUtil.isEmpty(errorList)){
        for (AssetBShebeifile assetBShebeifileImport:successList
        ) {
     //  AssetBShebeifile assetBShebeifile =new AssetBShebeifile();
     //   BeanUtil.copyProperties(assetBShebeifileImport,assetBShebeifile, CopyOptions.create().setIgnoreNullValue(true));
        this.iAssetBShebeifileService.createAssetBShebeifile(assetBShebeifileImport);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public AssetBShebeifile detail(@NotBlank(message = "{required}") @PathVariable String id) {
    AssetBShebeifile assetBShebeifile=this.iAssetBShebeifileService.getById(id);
        return assetBShebeifile;
        }
        }