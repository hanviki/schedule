
package cc.mrbird.febs.xf.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.mdl.entity.MdlBMdtD;
import cc.mrbird.febs.xf.entity.XfBInnertalkD;
import cc.mrbird.febs.xf.service.IXfBInnertalkDService;
import cc.mrbird.febs.xf.service.IXfBInnertalkService;
import cc.mrbird.febs.xf.entity.XfBInnertalk;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author viki
 * @since 2022-11-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("xfBInnertalk")

public class XfBInnertalkController extends BaseController {

    private String message;
    @Autowired
    public IXfBInnertalkService iXfBInnertalkService;
    @Autowired
    public IXfBInnertalkDService iXfBInnertalkDService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/XfBInnertalk/XfBInnertalk','ass/XfBInnertalk/XfBInnertalk','xfBInnertalk:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBInnertalk:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBInnertalk:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBInnertalk:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBInnertalk:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'??????','xfBInnertalk:import',1,5,NOW());
 */


    /**
     * ??????????????????
     *
     * @param request      ????????????
     * @param xfBInnertalk ????????????
     * @return
     */
    @GetMapping

    public Map<String, Object> List(QueryRequest request, XfBInnertalk xfBInnertalk) {
        return getDataTable(this.iXfBInnertalkService.findXfBInnertalkList(request, xfBInnertalk));
    }

    /**
     * ??????
     *
     * @param xfBInnertalk
     * @return
     */
    @Log("??????/??????")
    @PostMapping

    public void addXfBInnertalk(@Valid XfBInnertalk xfBInnertalk, String user) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            xfBInnertalk.setCreateUserId(currentUser.getUserId());
            this.iXfBInnertalkService.createXfBInnertalk(xfBInnertalk);
            List<XfBInnertalkD> list2 = JSON.parseObject(user, new TypeReference<List<XfBInnertalkD>>() {
            });
            list2.forEach(p -> {
                p.setBaseId(String.valueOf(xfBInnertalk.getId()));
                p.setCreateTime(new Date());
                p.setCreateUserId(currentUser.getUserId());
                this.iXfBInnertalkDService.createXfBInnertalkD(p);
            });
        } catch (Exception e) {
            message = "??????/????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * ??????
     *
     * @param xfBInnertalk
     * @return
     */
    @Log("??????")
    @PutMapping

    public void updateXfBInnertalk(@Valid XfBInnertalk xfBInnertalk, String user) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            xfBInnertalk.setModifyUserId(currentUser.getUserId());
            this.iXfBInnertalkService.updateXfBInnertalk(xfBInnertalk);
            List<XfBInnertalkD> list = JSON.parseObject(user, new TypeReference<List<XfBInnertalkD>>() {
            });

            this.iXfBInnertalkService.deleteByBaseId(String.valueOf(xfBInnertalk.getId()));
            list.forEach(p -> {
                p.setBaseId(String.valueOf(xfBInnertalk.getId()));
                p.setCreateTime(new Date());
                p.setCreateUserId(currentUser.getUserId());
                this.iXfBInnertalkDService.createXfBInnertalkD(p);
            });
        } catch (Exception e) {
            message = "????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("??????")
    @DeleteMapping("/{ids}")

    public void deleteXfBInnertalks(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iXfBInnertalkService.deleteXfBInnertalks(arr_ids);
        } catch (Exception e) {
            message = "????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")

    public void export(QueryRequest request, XfBInnertalk xfBInnertalk, HttpServletResponse response) throws FebsException {
        try {
            List<XfBInnertalk> xfBInnertalks = this.iXfBInnertalkService.findXfBInnertalks(request, xfBInnertalk).getRecords();
            ExcelKit.$Export(XfBInnertalk.class, response).downXlsx(xfBInnertalks, false);
        } catch (Exception e) {
            message = "??????Excel??????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)

    public void downTemplate(HttpServletResponse response) {
        List<XfBInnertalk> publishList = new ArrayList<>();
        ExcelKit.$Export(XfBInnertalk.class, response).downXlsx(publishList, true);
    }

    @RequestMapping(value = "import", method = RequestMethod.POST)

    public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<XfBInnertalk> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser = FebsUtil.getCurrentUser();


        ExcelKit.$Import(XfBInnertalk.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<XfBInnertalk>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, XfBInnertalk entity) {
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
            for (XfBInnertalk xfBInnertalkImport : successList
            ) {
                //  XfBInnertalk xfBInnertalk =new XfBInnertalk();
                //   BeanUtil.copyProperties(xfBInnertalkImport,xfBInnertalk, CopyOptions.create().setIgnoreNullValue(true));
                this.iXfBInnertalkService.createXfBInnertalk(xfBInnertalkImport);
            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{id}")
    public XfBInnertalk detail(@NotBlank(message = "{required}") @PathVariable String id) {
        XfBInnertalk xfBInnertalk = this.iXfBInnertalkService.getById(id);
        return xfBInnertalk;
    }
}