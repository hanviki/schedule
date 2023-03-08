
package cc.mrbird.febs.fs.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dept.entity.DeptBUser;
import cc.mrbird.febs.dept.service.IDeptBUserService;
import cc.mrbird.febs.fs.entity.FsBUser;
import cc.mrbird.febs.fs.service.IFsBDeptchangeService;
import cc.mrbird.febs.fs.entity.FsBDeptchange;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.fs.service.IFsBUserService;
import cc.mrbird.febs.system.domain.Dept;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
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
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author viki
 * @since 2022-07-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("fsBDeptchange")

public class FsBDeptchangeController extends BaseController {

    private String message;
    @Autowired
    public IFsBDeptchangeService iFsBDeptchangeService;
    @Autowired
    public IDeptBUserService iDeptBUserService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/FsBDeptchange/FsBDeptchange','ass/FsBDeptchange/FsBDeptchange','fsBDeptchange:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','fsBDeptchange:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','fsBDeptchange:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','fsBDeptchange:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','fsBDeptchange:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','fsBDeptchange:import',1,5,NOW());
 */


    /**
     * 分页查询数据
     *
     * @param request       分页信息
     * @param fsBDeptchange 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("fsBDeptchange:view")
    public Map<String, Object> List(QueryRequest request, FsBDeptchange fsBDeptchange) {
        return getDataTable(this.iFsBDeptchangeService.findFsBDeptchanges(request, fsBDeptchange));
    }


    /**
     * 添加
     *
     * @param fsBDeptchange
     * @return
     */
    @Log("移入用户")
    @PostMapping
    public void addSdlBDeptchange(@Valid FsBDeptchange fsBDeptchange) throws FebsException {
        try {

            LambdaQueryWrapper<DeptBUser> lambdaQueryWrapper= new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DeptBUser::getUserAccount,fsBDeptchange.getUserAccount());
            DeptBUser user= this.iDeptBUserService.getOne(lambdaQueryWrapper);
            if(user==null){
                user =new DeptBUser();
                 user.setUserAccount(fsBDeptchange.getUserAccount());
                 this.iDeptBUserService.createDeptBUser(user);
            }
            User currentUser = FebsUtil.getCurrentUser();
            fsBDeptchange.setCreateUserId(currentUser.getUserId());
            fsBDeptchange.setUserDept(currentUser.getDeptId());
            this.iFsBDeptchangeService.createFsBDeptchange(fsBDeptchange);
        } catch (Exception e) {
            message = "移入失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }



    /**
     * 修改
     *
     * @param fsBDeptchange
     * @return
     */
    @Log("修改")
    @PostMapping("delete")
    public void updateFsBDeptchange(@Valid FsBDeptchange fsBDeptchange) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            fsBDeptchange.setModifyUserId(currentUser.getUserId());

            fsBDeptchange.setDeptNewId("");
            this.iFsBDeptchangeService.updateFsBDeptchange(fsBDeptchange);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("fsBDeptchange:delete")
    public void deleteFsBDeptchanges(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iFsBDeptchangeService.deleteFsBDeptchanges(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("fsBDeptchange:export")
    public void export(QueryRequest request, FsBDeptchange fsBDeptchange, HttpServletResponse response) throws FebsException {
        try {
            List<FsBDeptchange> fsBDeptchanges = this.iFsBDeptchangeService.findFsBDeptchanges(request, fsBDeptchange).getRecords();
            ExcelKit.$Export(FsBDeptchange.class, response).downXlsx(fsBDeptchanges, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
    @RequiresPermissions("fsBDeptchange:import")
    public void downTemplate(HttpServletResponse response) {
        List<FsBDeptchange> publishList = new ArrayList<>();
        ExcelKit.$Export(FsBDeptchange.class, response).downXlsx(publishList, true);
    }

    @RequestMapping(value = "import", method = RequestMethod.POST)
    @RequiresPermissions("fsBDeptchange:import")
    public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<FsBDeptchange> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser = FebsUtil.getCurrentUser();


        ExcelKit.$Import(FsBDeptchange.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<FsBDeptchange>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, FsBDeptchange entity) {
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
        if (CollectionUtil.isEmpty(errorList)) {
            for (FsBDeptchange fsBDeptchangeImport : successList
            ) {
                FsBDeptchange fsBDeptchange = new FsBDeptchange();
                BeanUtil.copyProperties(fsBDeptchangeImport, fsBDeptchange, CopyOptions.create().setIgnoreNullValue(true));
                this.iFsBDeptchangeService.createFsBDeptchange(fsBDeptchange);
            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{id}")
    public FsBDeptchange detail(@NotBlank(message = "{required}") @PathVariable String id) {
        FsBDeptchange fsBDeptchange = this.iFsBDeptchangeService.getById(id);
        return fsBDeptchange;
    }
}