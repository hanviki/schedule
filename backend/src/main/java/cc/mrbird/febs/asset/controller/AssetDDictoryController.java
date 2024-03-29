
package cc.mrbird.febs.asset.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.asset.service.IAssetDDictoryService;
import cc.mrbird.febs.asset.entity.AssetDDictory;

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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author viki
 * @since 2023-02-06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("assetDDictory")

public class AssetDDictoryController extends BaseController{

private String message;
@Autowired
public IAssetDDictoryService iAssetDDictoryService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/AssetDDictory/AssetDDictory','ass/AssetDDictory/AssetDDictory','assetDDictory:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','assetDDictory:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','assetDDictory:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','assetDDictory:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','assetDDictory:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','assetDDictory:import',1,5,NOW());
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param assetDDictory 查询条件
 * @return
 */
@GetMapping
//@RequiresPermissions("assetDDictory:view")
public Map<String, Object> List(QueryRequest request, AssetDDictory assetDDictory){
        return this.iAssetDDictoryService.findAssetDDictorys(request, assetDDictory);
        }
        @GetMapping("dic")
//@RequiresPermissions("assetDDictory:view")
        public Map<String, Object> List2(QueryRequest request, AssetDDictory assetDDictory){
                return getDataTable(this.iAssetDDictoryService.findAssetDDictoryList(request, assetDDictory));
        }
/**
 * 添加
 * @param  assetDDictory
 * @return
 */
@Log("新增/按钮")
@PostMapping
//@RequiresPermissions("assetDDictory:add")
public void addAssetDDictory(@Valid AssetDDictory assetDDictory)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();



        List<AssetDDictory> assetDDictoryList= this.iAssetDDictoryService.list();
        List<AssetDDictory> list1= assetDDictoryList.stream().filter(p->p.getParentId().equals(assetDDictory.getParentId())).sorted(Comparator.comparing(AssetDDictory::getId).reversed()).collect(Collectors.toList());
        if(list1.size()>0){
                AssetDDictory dic=     list1.get(0);
                String id= dic.getId();
                String[] arrIds= id.split("_");
                String code= arrIds[arrIds.length-1];
                int code_i= Integer.parseInt(code)+1;
                String newid="";
                for(int i=0;i<arrIds.length-1;i++){
                        newid+=arrIds[i]+"_";
                }
                newid+=String.valueOf(code_i);
                assetDDictory.setTypeIndex(dic.getTypeIndex());
                assetDDictory.setId(newid);
        }
        else{
                List<AssetDDictory> list2= assetDDictoryList.stream().filter(p->p.getId().equals(assetDDictory.getParentId())).sorted(Comparator.comparing(AssetDDictory::getId).reversed()).collect(Collectors.toList());
                if(list2.size()>0) {
                        assetDDictory.setTypeIndex(list2.get(0).getTypeIndex());
                }
                String parentId= assetDDictory.getParentId();
                String newid= parentId+"_1001";
                assetDDictory.setId(newid);
        }
                this.iAssetDDictoryService.createAssetDDictory(assetDDictory);

        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param assetDDictory
 * @return
 */
@Log("修改")
@PutMapping
//@RequiresPermissions("assetDDictory:update")
public void updateAssetDDictory(@Valid AssetDDictory assetDDictory)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        this.iAssetDDictoryService.updateAssetDDictory(assetDDictory);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
//@RequiresPermissions("assetDDictory:delete")
public void deleteAssetDDictorys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iAssetDDictoryService.deleteAssetDDictorys(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
//@RequiresPermissions("assetDDictory:import")
public void downTemplate(HttpServletResponse response) {
        List<AssetDDictory> publishList = new ArrayList<>();
        ExcelKit.$Export(AssetDDictory.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
//@RequiresPermissions("assetDDictory:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<AssetDDictory> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(AssetDDictory.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<AssetDDictory>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, AssetDDictory entity) {
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
        for (AssetDDictory assetDDictoryImport:successList
        ) {
     //  AssetDDictory assetDDictory =new AssetDDictory();
     //   BeanUtil.copyProperties(assetDDictoryImport,assetDDictory, CopyOptions.create().setIgnoreNullValue(true));
        this.iAssetDDictoryService.createAssetDDictory(assetDDictoryImport);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public AssetDDictory detail(@NotBlank(message = "{required}") @PathVariable String id) {
    AssetDDictory assetDDictory=this.iAssetDDictoryService.getById(id);
        return assetDDictory;
        }
        }