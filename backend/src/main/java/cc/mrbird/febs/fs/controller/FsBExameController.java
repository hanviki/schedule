
package cc.mrbird.febs.fs.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.fs.entity.FsBExameImport;
import cc.mrbird.febs.fs.entity.FsBUser;
import cc.mrbird.febs.fs.service.IFsBExameService;
import cc.mrbird.febs.fs.entity.FsBExame;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.fs.service.IFsBUserService;
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
import org.apache.commons.lang3.StringUtils;
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
 * @since 2022-07-12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("fsBExame")

public class FsBExameController extends BaseController {

    private String message;
    @Autowired
    public IFsBExameService iFsBExameService;

    @Autowired
    public IFsBUserService iFsBUserService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/FsBExame/FsBExame','ass/FsBExame/FsBExame','fsBExame:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','fsBExame:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','fsBExame:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','fsBExame:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','fsBExame:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','fsBExame:import',1,5,NOW());
 */


    /**
     * 分页查询数据
     *
     * @param request  分页信息
     * @param fsBExame 查询条件
     * @return
     */
    @GetMapping
    public Map<String, Object> List(QueryRequest request, FsBExame fsBExame) {
        User currentUser = FebsUtil.getCurrentUser();
        fsBExame.setDeptId(currentUser.getDeptId());
        if(StringUtils.isNotEmpty(currentUser.getEmail())) {
            fsBExame.setYuanqu(currentUser.getEmail().trim());
        }
        return getDataTable(this.iFsBExameService.findFsBExameList(request, fsBExame));
    }

    /**
     * 添加
     *
     * @param fsBExame
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    public void addFsBExame(@Valid FsBExame fsBExame) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            fsBExame.setCreateUserId(currentUser.getUserId());
            fsBExame.setUserNo(currentUser.getUsername());

            LambdaQueryWrapper<FsBUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(FsBUser::getUserAccount, fsBExame.getUserAccount());
            FsBUser user = this.iFsBUserService.getOne(userLambdaQueryWrapper);
            fsBExame.setNumber(user.getGrjlbh());

            this.iFsBExameService.createFsBExame(fsBExame);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param fsBExame
     * @return
     */
    @Log("修改")
    @PutMapping

    public void updateFsBExame(@Valid FsBExame fsBExame) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            fsBExame.setModifyUserId(currentUser.getUserId());
            this.iFsBExameService.updateFsBExame(fsBExame);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")

    public void deleteFsBExames(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iFsBExameService.deleteFsBExames(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    public void export(QueryRequest request,String dataJson,  FsBExame fsBExame, HttpServletResponse response) throws FebsException {
        try {
            request.setPageNum(1);
            request.setPageSize(10000);
            request.setIsSearchCount(false);
            User currentUser = FebsUtil.getCurrentUser();
            fsBExame.setDeptId(currentUser.getDeptId());
            List<FsBExame> fsBExames = this.iFsBExameService.findFsBExameList(request, fsBExame).getRecords();
            ExportExcelUtils.exportCustomExcel_han(response, fsBExames,dataJson,"");
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
    public void downTemplate(HttpServletResponse response) {
        List<FsBExame> publishList = new ArrayList<>();
        ExcelKit.$Export(FsBExameImport.class, response).downXlsx(publishList, true);
    }

    @RequestMapping(value = "import", method = RequestMethod.POST)
    public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<FsBExameImport> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser = FebsUtil.getCurrentUser();


        ExcelKit.$Import(FsBExameImport.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<FsBExameImport>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, FsBExameImport entity) {
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
            for (FsBExameImport fsBExameImport : successList
            ) {

                  FsBExame fsBExame =new FsBExame();
                   BeanUtil.copyProperties(fsBExameImport,fsBExame, CopyOptions.create().setIgnoreNullValue(true));
                fsBExame.setCreateUserId(currentUser.getUserId());
                fsBExame.setUserNo(currentUser.getUsername());

                this.iFsBExameService.createFsBExame(fsBExame);
            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{id}")
    public FsBExame detail(@NotBlank(message = "{required}") @PathVariable String id) {
        FsBExame fsBExame = this.iFsBExameService.getById(id);
        return fsBExame;
    }
}