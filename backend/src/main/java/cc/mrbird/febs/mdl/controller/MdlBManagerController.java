
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.mdl.entity.MdlBManagerImport;
import cc.mrbird.febs.mdl.entity.MdlBManagerOut;
import cc.mrbird.febs.mdl.service.IMdlBManagerService;
import cc.mrbird.febs.mdl.entity.MdlBManager;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.sdl.entity.SdlBUser;
import cc.mrbird.febs.sdl.service.ISdlBUserService;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author viki
 * @since 2022-01-17
 */
@Slf4j
@Validated
@RestController
@RequestMapping("mdlBManager")

public class MdlBManagerController extends BaseController{

private String message;
@Autowired
public IMdlBManagerService iMdlBManagerService;
    @Autowired
    private ISdlBUserService iSdlBUserService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/MdlBManager/MdlBManager','ass/MdlBManager/MdlBManager','mdlBManager:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBManager:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBManager:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBManager:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBManager:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','mdlBManager:import',1,5,NOW());
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param mdlBManager ????????????
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, MdlBManager mdlBManager){
        return getDataTable(this.iMdlBManagerService.findMdlBManagers(request, mdlBManager));
        }

/**
 * ??????
 * @param  mdlBManager
 * @return
 */
@Log("??????/??????")
@PostMapping
public void addMdlBManager(@Valid MdlBManager mdlBManager)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        mdlBManager.setCreateUserId(currentUser.getUserId());
        this.iMdlBManagerService.createMdlBManager(mdlBManager);
        }catch(Exception e){
        message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * ??????
 * @param mdlBManager
 * @return
 */
@Log("??????")
@PutMapping
public void updateMdlBManager(@Valid MdlBManager mdlBManager)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      mdlBManager.setModifyUserId(currentUser.getUserId());
        this.iMdlBManagerService.updateMdlBManager(mdlBManager);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("??????")
@DeleteMapping("/{ids}")
public void deleteMdlBManagers(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iMdlBManagerService.deleteMdlBManagers(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
public void export(QueryRequest request, MdlBManager mdlBManager, HttpServletResponse response) throws FebsException {
        try {
            request.setPageSize(10000);
            request.setPageNum(1);
            request.setIsSearchCount(false);
        List<MdlBManager> mdlBManagers = this.iMdlBManagerService.findMdlBManagers(request, mdlBManager).getRecords();
        ExcelKit.$Export(MdlBManagerOut.class, response).downXlsx(mdlBManagers, false);
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
public void downTemplate(HttpServletResponse response) {
        List<MdlBManagerImport> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlBManagerImport.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
public ResponseEntity<?> importUser(@RequestParam MultipartFile file, String type)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlBManagerImport> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlBManagerImport.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlBManagerImport>() {


@Override
public void onSuccess(int sheetIndex, int rowIndex, MdlBManagerImport entity) {
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
        for (MdlBManagerImport mdlBManagerImport:successList
        ) {
    MdlBManager mdlBManager =new MdlBManager();
    mdlBManager.setDeptName(mdlBManagerImport.getDeptName());
            mdlBManager.setUserAccount(mdlBManagerImport.getUserAccount());
            mdlBManager.setEmail(mdlBManagerImport.getEmail());
            mdlBManager.setStartDate(DateUtil.parseDate(mdlBManagerImport.getStartDate()));
            LambdaQueryWrapper<SdlBUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SdlBUser::getUserAccount, mdlBManagerImport.getUserAccount());
            SdlBUser user = this.iSdlBUserService.getOne(queryWrapper);
            mdlBManager.setUserAccount(user.getUserAccount());
            mdlBManager.setUserAccountName(user.getUserAccountName());
            mdlBManager.setDeptId(user.getDeptNew());
            mdlBManager.setTel(user.getTelephone());
            mdlBManager.setBirthday(user.getBirthday());
            mdlBManager.setType(type);

            this.iMdlBManagerService.createMdlBManager(mdlBManager);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }

    @RequestMapping(value = "import0", method = RequestMethod.POST)
    public ResponseEntity<?> importUser0(@RequestParam MultipartFile file) throws IOException{
         return  importUser(file,"????????????");
    }
    @RequestMapping(value = "import1", method = RequestMethod.POST)
    public ResponseEntity<?> importUser1(@RequestParam MultipartFile file) throws IOException{
        return  importUser(file,"????????????");
    }
    @RequestMapping(value = "import2", method = RequestMethod.POST)
    public ResponseEntity<?> importUser2(@RequestParam MultipartFile file) throws IOException{
        return  importUser(file,"?????????");
    }

@GetMapping("/{id}")
public MdlBManager detail(@NotBlank(message = "{required}") @PathVariable String id) {
    MdlBManager mdlBManager=this.iMdlBManagerService.getById(id);
        return mdlBManager;
        }
        }