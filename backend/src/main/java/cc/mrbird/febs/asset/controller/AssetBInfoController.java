
package cc.mrbird.febs.asset.controller;


import cc.mrbird.febs.asset.entity.ViewAssetBInfo;
import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.asset.service.IAssetBInfoService;
import cc.mrbird.febs.asset.entity.AssetBInfo;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.Dept;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.system.service.DeptService;
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
 * @since 2023-02-06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("assetBInfo")

public class AssetBInfoController extends BaseController {

    private String message;
    @Autowired
    public IAssetBInfoService iAssetBInfoService;
    @Autowired
    public DeptService deptService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/AssetBInfo/AssetBInfo','ass/AssetBInfo/AssetBInfo','assetBInfo:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','assetBInfo:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','assetBInfo:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','assetBInfo:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','assetBInfo:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','assetBInfo:import',1,5,NOW());
 */


    /**
     * ??????????????????
     *
     * @param request    ????????????
     * @param assetBInfo ????????????
     * @return
     */
    @GetMapping
//@RequiresPermissions("assetBInfo:view")
    public Map<String, Object> List(QueryRequest request, AssetBInfo assetBInfo) {
        return getDataTable(this.iAssetBInfoService.findAssetBInfoList(request, assetBInfo));
    }

    @GetMapping("stastic")
//@RequiresPermissions("assetBInfo:view")
    public Map<String, Object> List3(QueryRequest request, ViewAssetBInfo assetBInfo) {
        return getDataTable(this.iAssetBInfoService.findAssetBInfoStatistic(request, assetBInfo));
    }

    /**
     * ??????
     *
     * @param assetBInfo
     * @return
     */
    @Log("??????/??????")
    @PostMapping
//@RequiresPermissions("assetBInfo:add")
    public void addAssetBInfo(@Valid AssetBInfo assetBInfo) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            assetBInfo.setCreateUserId(currentUser.getUserId());
            List<Dept> deptList = deptService.list();
            if (StringUtils.isNotEmpty(assetBInfo.getDeptName())) {
                List<Dept> list3 = deptList.stream().filter(p -> p.getDeptName().equals(assetBInfo.getDeptName())).collect(Collectors.toList());
                if (list3.size() > 0) {
                    assetBInfo.setDeptId(list3.get(0).getDeptId());
                }
            }
            this.iAssetBInfoService.createAssetBInfo(assetBInfo);
        } catch (Exception e) {
            message = "??????/????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * ??????
     *
     * @param assetBInfo
     * @return
     */
    @Log("??????")
    @PutMapping
//@RequiresPermissions("assetBInfo:update")
    public void updateAssetBInfo(@Valid AssetBInfo assetBInfo) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            assetBInfo.setModifyUserId(currentUser.getUserId());
            List<Dept> deptList = deptService.list();
            if (StringUtils.isNotEmpty(assetBInfo.getDeptName())) {
                List<Dept> list3 = deptList.stream().filter(p -> p.getDeptName().equals(assetBInfo.getDeptName())).collect(Collectors.toList());
                if (list3.size() > 0) {
                    assetBInfo.setDeptId(list3.get(0).getDeptId());
                }
            }
            this.iAssetBInfoService.updateAssetBInfo(assetBInfo);
        } catch (Exception e) {
            message = "????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("??????")
    @DeleteMapping("/{ids}")
//@RequiresPermissions("assetBInfo:delete")
    public void deleteAssetBInfos(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iAssetBInfoService.deleteAssetBInfos(arr_ids);
        } catch (Exception e) {
            message = "????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @PostMapping("excel")
    public void export(QueryRequest request,String dataJson,  ViewAssetBInfo fsBExame, HttpServletResponse response) throws FebsException {
        try {
            request.setPageNum(1);
            request.setPageSize(10000);
            request.setIsSearchCount(false);

            List<ViewAssetBInfo> fsBExames = this.iAssetBInfoService.findAssetBInfoStatistic(request, fsBExame).getRecords();
            ExportExcelUtils.exportCustomExcel_han(response, fsBExames,dataJson,"");
        } catch (Exception e) {
            message = "??????Excel??????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
//@RequiresPermissions("assetBInfo:import")
    public void downTemplate(HttpServletResponse response) {
        List<AssetBInfo> publishList = new ArrayList<>();
        ExcelKit.$Export(AssetBInfo.class, response).downXlsx(publishList, true);
    }

    @RequestMapping(value = "import", method = RequestMethod.POST)
//@RequiresPermissions("assetBInfo:import")
    public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<AssetBInfo> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser = FebsUtil.getCurrentUser();


        ExcelKit.$Import(AssetBInfo.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<AssetBInfo>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, AssetBInfo entity) {
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
        if (CollectionUtil.isEmpty(errorList)) {
            for (AssetBInfo assetBInfoImport : successList
            ) {
                //  AssetBInfo assetBInfo =new AssetBInfo();
                //   BeanUtil.copyProperties(assetBInfoImport,assetBInfo, CopyOptions.create().setIgnoreNullValue(true));
                this.iAssetBInfoService.createAssetBInfo(assetBInfoImport);
            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{id}")
    public AssetBInfo detail(@NotBlank(message = "{required}") @PathVariable String id) {
        AssetBInfo assetBInfo = this.iAssetBInfoService.getById(id);
        return assetBInfo;
    }
}