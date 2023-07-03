
package cc.mrbird.febs.fs.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.fs.entity.FsBRecord;
import cc.mrbird.febs.fs.entity.FsBUser;
import cc.mrbird.febs.fs.service.IFsBChangerecordService;
import cc.mrbird.febs.fs.entity.FsBChangerecord;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.fs.service.IFsBRecordService;
import cc.mrbird.febs.fs.service.IFsBUserService;
import cc.mrbird.febs.system.domain.Dept;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.system.service.DeptService;
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
import net.bytebuddy.asm.Advice;
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
import java.util.stream.Collectors;

/**
 * @author viki
 * @since 2023-01-31
 */
@Slf4j
@Validated
@RestController
@RequestMapping("fsBChangerecord")

public class FsBChangerecordController extends BaseController {

    private String message;
    @Autowired
    public IFsBChangerecordService iFsBChangerecordService;

    @Autowired
    public IFsBUserService iFsBUserService;
    @Autowired
    public IFsBRecordService iFsBRecordService;
    @Autowired
    public DeptService deptService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/FsBChangerecord/FsBChangerecord','ass/FsBChangerecord/FsBChangerecord','fsBChangerecord:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','fsBChangerecord:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','fsBChangerecord:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','fsBChangerecord:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','fsBChangerecord:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','fsBChangerecord:import',1,5,NOW());
 */


    /**
     * 分页查询数据
     *
     * @param request         分页信息
     * @param fsBChangerecord 查询条件
     * @return
     */
    @GetMapping
    public Map<String, Object> List(QueryRequest request, FsBChangerecord fsBChangerecord) {
        return getDataTable(this.iFsBChangerecordService.findFsBChangerecords(request, fsBChangerecord));
    }

    /**
     * 添加
     *
     * @param fsBChangerecord
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    public void addFsBChangerecord(@Valid FsBChangerecord fsBChangerecord) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            fsBChangerecord.setCreateUserId(currentUser.getUserId());
            fsBChangerecord.setSubmitUser(currentUser.getUsername());

            Boolean isChang = false;
            if (StringUtils.equals(fsBChangerecord.getDeptOld(),fsBChangerecord.getDeptNew())) {
                fsBChangerecord.setIsDept("否");
            } else {
                isChang = true;
                fsBChangerecord.setIsDept("是");
            }
            if (StringUtils.equals(fsBChangerecord.getGwlbNew(),fsBChangerecord.getGwlbOld())) {
                fsBChangerecord.setIsGwlb("否");
            } else {
                isChang = true;
                fsBChangerecord.setIsGwlb("是");
            }
            if (StringUtils.equals(fsBChangerecord.getNumberNew(),fsBChangerecord.getNumberOld())) {
                fsBChangerecord.setIsNumber("否");
            } else {
                isChang = true;
                fsBChangerecord.setIsNumber("是");
            }
            if (StringUtils.equals(fsBChangerecord.getStateNew(),fsBChangerecord.getStateOld())) {
                fsBChangerecord.setIsState("否");
            } else {
                isChang = true;
                fsBChangerecord.setIsState("是");
            }
            if (isChang == false) {
                throw new FebsException("与原来信息未发生任何变化，请仔细核对后提交");
            }
            this.iFsBChangerecordService.createFsBChangerecord(fsBChangerecord);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
                throw new FebsException(e.getMessage());
        }
    }



    /**
     * 修改
     *
     * @param fsBChangerecord
     * @return
     */
    @Log("修改")
    @PutMapping
    public void updateFsBChangerecord(@Valid FsBChangerecord fsBChangerecord) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            fsBChangerecord.setModifyUserId(currentUser.getUserId());
          //  fsBChangerecord.setSubmitUser(currentUser.getUsername());
            Boolean isChang = false;
            if (StringUtils.equals(fsBChangerecord.getDeptOld(),fsBChangerecord.getDeptNew())) {
                fsBChangerecord.setIsDept("否");
            } else {
                isChang = true;
                fsBChangerecord.setIsDept("是");
            }
            if (StringUtils.equals(fsBChangerecord.getGwlbNew(),fsBChangerecord.getGwlbOld())) {
                fsBChangerecord.setIsGwlb("否");
            } else {
                isChang = true;
                fsBChangerecord.setIsGwlb("是");
            }
            if (StringUtils.equals(fsBChangerecord.getNumberNew(),fsBChangerecord.getNumberOld())) {
                fsBChangerecord.setIsNumber("否");
            } else {
                isChang = true;
                fsBChangerecord.setIsNumber("是");
            }
            if (StringUtils.equals(fsBChangerecord.getStateNew(),fsBChangerecord.getStateOld())) {
                fsBChangerecord.setIsState("否");
            } else {
                isChang = true;
                fsBChangerecord.setIsState("是");
            }
            if (isChang == false) {
                throw new FebsException("与原来信息未发生任何变化，请仔细核对后提交");
            }
            this.iFsBChangerecordService.updateFsBChangerecord(fsBChangerecord);
            if(fsBChangerecord.getState().equals(3)){
                List<Dept> deptList= deptService.list();
                LambdaQueryWrapper<FsBUser> lambdaQueryWrapper= new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(FsBUser::getUserAccount,fsBChangerecord.getUserAccount());
                FsBUser user= this.iFsBUserService.getOne(lambdaQueryWrapper);
                if(user==null){
                    user =new FsBUser();
                    user.setUserAccount(fsBChangerecord.getUserAccount());
                    user.setUserAccountName(fsBChangerecord.getUserAccountName());
                    if(fsBChangerecord.getIsDept().equals("是")) {
                       List<Dept> deptList1= deptList.stream().filter(p->p.getDeptName().equals(fsBChangerecord.getDeptNew())).collect(Collectors.toList());
                        if(deptList1.size()>0){
                            user.setDeptId(deptList1.get(0).getDeptId());
                        }
                        else{
                            user.setDeptId("");
                        }
                        user.setDeptName(fsBChangerecord.getDeptNew());
                    }
                    if(fsBChangerecord.getIsNumber().equals("是")) {
                        user.setHdjlpgs(Integer.parseInt(fsBChangerecord.getNumberNew()));
                    }
                    if(fsBChangerecord.getIsGwlb().equals("是")) {
                        user.setGwlb(fsBChangerecord.getGwlbNew());
                    }
                    this.iFsBUserService.createFsBUser(user);



                }
                else{
                    user =new FsBUser();
                    user.setUserAccount(fsBChangerecord.getUserAccount());
                    user.setUserAccountName(fsBChangerecord.getUserAccountName());
                    if(fsBChangerecord.getIsDept().equals("是")) {
                        List<Dept> deptList1= deptList.stream().filter(p->p.getDeptName().equals(fsBChangerecord.getDeptNew())).collect(Collectors.toList());
                        if(deptList1.size()>0){
                            user.setDeptId(deptList1.get(0).getDeptId());
                        }
                        else {
                            user.setDeptId("");
                        }
                        user.setDeptName(fsBChangerecord.getDeptNew());
                    }
                    if(fsBChangerecord.getIsNumber().equals("是")) {
                        user.setHdjlpgs(Integer.parseInt(fsBChangerecord.getNumberNew()));
                    }
                    if(fsBChangerecord.getIsGwlb().equals("是")) {
                        user.setGwlb(fsBChangerecord.getGwlbNew());
                    }


                    this.iFsBUserService.update(user,lambdaQueryWrapper);
                }

                if(fsBChangerecord.getIsState().equals("是")) {
                    FsBRecord fsBRecord= new FsBRecord();
                    fsBRecord.setIsDeletemark(1);
                    fsBRecord.setUserAccount(fsBChangerecord.getUserAccount());
                    fsBRecord.setUserAccountName(fsBChangerecord.getUserAccountName());
                    fsBRecord.setOperationDate(fsBChangerecord.getChangeDate());
                    fsBRecord.setNote(fsBChangerecord.getRemark());
                    fsBRecord.setState(Integer.parseInt(fsBChangerecord.getStateNew()));
                    this.iFsBRecordService.createFsBRecord(fsBRecord);
                }
            }

        } catch (Exception e) {
           // message = "修改失败";
            log.error(message, e);
            throw new FebsException(e.getMessage());
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    public void deleteFsBChangerecords(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iFsBChangerecordService.deleteFsBChangerecords(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    public void export(QueryRequest request, FsBChangerecord fsBChangerecord,String dataJson, HttpServletResponse response) throws FebsException {
        try {
            request.setPageNum(1);
            request.setPageSize(100000);
            request.setIsSearchCount(false);
            List<FsBChangerecord> fsBChangerecords = this.iFsBChangerecordService.findFsBChangerecords(request, fsBChangerecord).getRecords();

            ExportExcelUtils.exportCustomExcel_han(response, fsBChangerecords,dataJson,"");

        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
    public void downTemplate(HttpServletResponse response) {
        List<FsBChangerecord> publishList = new ArrayList<>();
        ExcelKit.$Export(FsBChangerecord.class, response).downXlsx(publishList, true);
    }

    @RequestMapping(value = "import", method = RequestMethod.POST)
    public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<FsBChangerecord> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser = FebsUtil.getCurrentUser();


        ExcelKit.$Import(FsBChangerecord.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<FsBChangerecord>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, FsBChangerecord entity) {
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
            for (FsBChangerecord fsBChangerecordImport : successList
            ) {
                //  FsBChangerecord fsBChangerecord =new FsBChangerecord();
                //   BeanUtil.copyProperties(fsBChangerecordImport,fsBChangerecord, CopyOptions.create().setIgnoreNullValue(true));
                this.iFsBChangerecordService.createFsBChangerecord(fsBChangerecordImport);
            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{id}")
    public FsBChangerecord detail(@NotBlank(message = "{required}") @PathVariable String id) {
        FsBChangerecord fsBChangerecord = this.iFsBChangerecordService.getById(id);
        return fsBChangerecord;
    }
}