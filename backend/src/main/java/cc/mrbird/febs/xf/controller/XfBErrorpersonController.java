
package cc.mrbird.febs.xf.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.xf.service.IXfBErrorpersonService;
import cc.mrbird.febs.xf.entity.XfBErrorperson;

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
@RequestMapping("xfBErrorperson")

public class XfBErrorpersonController extends BaseController{

private String message;
@Autowired
public IXfBErrorpersonService iXfBErrorpersonService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/XfBErrorperson/XfBErrorperson','ass/XfBErrorperson/XfBErrorperson','xfBErrorperson:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','xfBErrorperson:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','xfBErrorperson:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','xfBErrorperson:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','xfBErrorperson:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','xfBErrorperson:import',1,5,NOW());
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param xfBErrorperson 查询条件
 * @return
 */
@GetMapping

public Map<String, Object> List(QueryRequest request, XfBErrorperson xfBErrorperson){
        return getDataTable(this.iXfBErrorpersonService.findXfBErrorpersonList(request, xfBErrorperson));
        }

/**
 * 添加
 * @param  xfBErrorperson
 * @return
 */
@Log("新增/按钮")
@PostMapping

public void addXfBErrorperson(@Valid XfBErrorperson xfBErrorperson)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        xfBErrorperson.setCreateUserId(currentUser.getUserId());
        this.iXfBErrorpersonService.createXfBErrorperson(xfBErrorperson);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param xfBErrorperson
 * @return
 */
@Log("修改")
@PutMapping

public void updateXfBErrorperson(@Valid XfBErrorperson xfBErrorperson)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      xfBErrorperson.setModifyUserId(currentUser.getUserId());
        this.iXfBErrorpersonService.updateXfBErrorperson(xfBErrorperson);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")

public void deleteXfBErrorpersons(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iXfBErrorpersonService.deleteXfBErrorpersons(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")

public void export(QueryRequest request, XfBErrorperson xfBErrorperson, HttpServletResponse response) throws FebsException {
        try {
        List<XfBErrorperson> xfBErrorpersons = this.iXfBErrorpersonService.findXfBErrorpersons(request, xfBErrorperson).getRecords();
        ExcelKit.$Export(XfBErrorperson.class, response).downXlsx(xfBErrorpersons, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)

public void downTemplate(HttpServletResponse response) {
        List<XfBErrorperson> publishList = new ArrayList<>();
        ExcelKit.$Export(XfBErrorperson.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)

public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<XfBErrorperson> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(XfBErrorperson.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<XfBErrorperson>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, XfBErrorperson entity) {
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
        for (XfBErrorperson xfBErrorpersonImport:successList
        ) {
     //  XfBErrorperson xfBErrorperson =new XfBErrorperson();
     //   BeanUtil.copyProperties(xfBErrorpersonImport,xfBErrorperson, CopyOptions.create().setIgnoreNullValue(true));
        this.iXfBErrorpersonService.createXfBErrorperson(xfBErrorpersonImport);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public XfBErrorperson detail(@NotBlank(message = "{required}") @PathVariable String id) {
    XfBErrorperson xfBErrorperson=this.iXfBErrorpersonService.getById(id);
        return xfBErrorperson;
        }
        }