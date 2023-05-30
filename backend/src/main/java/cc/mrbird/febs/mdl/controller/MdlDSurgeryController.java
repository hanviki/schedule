
package cc.mrbird.febs.mdl.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsConstant;
import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.mdl.entity.MdlBSurgeryinfoD;
import cc.mrbird.febs.mdl.entity.MdlDSurgeryImport;
import cc.mrbird.febs.mdl.service.IMdlBSurgeryinfoDService;
import cc.mrbird.febs.mdl.service.IMdlDSurgeryService;
import cc.mrbird.febs.mdl.entity.MdlDSurgery;

import cc.mrbird.febs.common.utils.FebsUtil;
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
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 *
 * @author viki
 * @since 2022-05-10
 */
@Slf4j
@Validated
@RestController
@RequestMapping("mdlDSurgery")

public class MdlDSurgeryController extends BaseController{

private String message;
@Autowired
public IMdlDSurgeryService iMdlDSurgeryService;

@Autowired
public IMdlBSurgeryinfoDService iMdlBSurgeryinfoDService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'手术目录','/ass/MdlDSurgery/MdlDSurgery','ass/MdlDSurgery/MdlDSurgery','mdlDSurgery:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'手术目录新增','mdlDSurgery:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'手术目录编辑','mdlDSurgery:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'手术目录删除','mdlDSurgery:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'手术目录导出','mdlDSurgery:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'手术目录导入','mdlDSurgery:import',1,5,NOW());
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param mdlDSurgery 查询条件
 * @return
 */
@GetMapping
//@RequiresPermissions("mdlDSurgery:view")
public Map<String, Object> List(QueryRequest request, MdlDSurgery mdlDSurgery){
        return getDataTable(this.iMdlDSurgeryService.findMdlDSurgerys(request, mdlDSurgery));
        }
    @GetMapping("tree")
    public Map<String, Object> findMenus(String jb,String deptName , Long baseId) {
        Map<String, Object> result = new HashMap<>();
        try {
            LambdaQueryWrapper<MdlDSurgery> queryWrapper = new LambdaQueryWrapper<>();
            if(jb.equals("一级")){
                queryWrapper.eq(MdlDSurgery::getLevel,jb);
            }
            if(jb.equals("二级")){
                queryWrapper.and(wrap->wrap.eq(MdlDSurgery::getLevel,"一级").or().eq(MdlDSurgery::getLevel,"二级" ));
            }
            if(jb.equals("三级")){
                queryWrapper.and(wrap->wrap.eq(MdlDSurgery::getLevel,"一级").or().eq(MdlDSurgery::getLevel,"二级" ).or().eq(MdlDSurgery::getLevel,"三级" ));
            }
            queryWrapper.eq(MdlDSurgery::getIsDeletemark,1);
            queryWrapper.eq(MdlDSurgery::getDeptNew,deptName);
            queryWrapper.apply("mdl_d_surgery.end_date is NULL");
            List<MdlDSurgery> menus = this.iMdlDSurgeryService.list(queryWrapper);
            List<String> codeList= menus.stream().map(p->p.getCode()).collect(Collectors.toList());

            if(jb.equals("一级")) {
                menus.add(getSurgery("一级"));
            }
            if(jb.equals("二级")) {
                menus.add(getSurgery("一级"));
                menus.add(getSurgery("二级"));
            }
            if(jb.equals("三级")) {
                menus.add(getSurgery("一级"));
                menus.add(getSurgery("二级"));
                menus.add(getSurgery("三级"));
            }
            if(jb.equals("四级")) {
                menus.add(getSurgery("一级"));
                menus.add(getSurgery("二级"));
                menus.add(getSurgery("三级"));
                menus.add(getSurgery("四级"));
            }

            /**
             * 子表中有 主表没有的
             */
            QueryRequest request =new QueryRequest();
            request.setPageNum(1);
            request.setPageSize(10000);
            MdlBSurgeryinfoD mdlBSurgeryinfoD= new MdlBSurgeryinfoD();
            mdlBSurgeryinfoD.setBaseId(baseId);
            request.setIsSearchCount(false);
            List<MdlBSurgeryinfoD> list = this.iMdlBSurgeryinfoDService.findMdlBSurgeryinfoDList(request,mdlBSurgeryinfoD).getRecords();

            List<MdlBSurgeryinfoD> leftList= list.stream().filter(p->!codeList.contains(p.getCode())).collect(Collectors.toList());
            if(leftList.size()>0){
                List<String> levelList= leftList.stream().map(p->p.getLevel()).distinct().collect(Collectors.toList());
                for (String level:levelList
                     ) {
                    menus.add(getSurgery(level));
                }
                for (MdlBSurgeryinfoD d:leftList
                     ) {
                    MdlDSurgery surgery =new MdlDSurgery();
                    surgery.setLevel(d.getLevel());
                    surgery.setLb(d.getLb());
                    surgery.setName(d.getName());
                    surgery.setCode(d.getCode());
                    surgery.setId(d.getSugeryId());
                    surgery.setDeptNew(d.getDeptNew());
                    menus.add(surgery);
                }
            }

            List<Tree<MdlDSurgery>> trees = new ArrayList<>();
            List<String> ids = new ArrayList<>();
            buildTrees(trees, menus, ids);

            result.put("ids", ids);

            Tree<MdlDSurgery> menuTree = TreeUtil.build(trees);
            result.put("rows", menuTree);

            result.put("total", menus.size());
        } catch (NumberFormatException e) {
            log.error("查询菜单失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }
    private  MdlDSurgery getSurgery(String lb){
        MdlDSurgery mdlDSurgery= new MdlDSurgery();
        if(lb.equals("一级")) {
            mdlDSurgery.setId(1000000L);
            mdlDSurgery.setName("一级");

        }
        if(lb.equals("二级")) {
            mdlDSurgery.setId(2000000L);
            mdlDSurgery.setName("二级");

        }
        if(lb.equals("三级")) {
            mdlDSurgery.setId(3000000L);
            mdlDSurgery.setName("三级");

        }
        if(lb.equals("四级")) {
            mdlDSurgery.setId(4000000L);
            mdlDSurgery.setName("四级");

        }
        mdlDSurgery.setCode("");
        mdlDSurgery.setLb("");
        mdlDSurgery.setLevel("");
        return  mdlDSurgery;
    }
    private  String getParentId(String lb,Long id){
      if(id==1000000||id==2000000||id==3000000||id==4000000){
          return "0";
      }
       if(lb.equals("一级")){
           return "1000000";
       }
        if(lb.equals("二级")){
            return "2000000";
        }
        if(lb.equals("三级")){
            return "3000000";
        }

            return "4000000";

    }
    private void buildTrees(List<Tree<MdlDSurgery>> trees, List<MdlDSurgery> menus, List<String> ids) {
        menus.forEach(menu -> {
            ids.add(menu.getId().toString());
            Tree<MdlDSurgery> tree = new Tree<>();
            tree.setId(menu.getId().toString());
            tree.setKey(tree.getId());
            tree.setParentId(getParentId(menu.getLevel(),menu.getId()));
            if(!StringUtils.isNotEmpty(menu.getCode())){
                tree.setText(menu.getName());
                tree.setTitle(menu.getName());
            }
            else {
                tree.setText(menu.getName() + "_" + menu.getCode() + "_" + menu.getLevel() + "_" + menu.getLb());
                tree.setTitle(menu.getName() + "_" + menu.getCode() + "_" + menu.getLevel() + "_" + menu.getLb());
            }

            trees.add(tree);
        });
    }

    /**
 * 添加
 * @param  mdlDSurgery
 * @return
 */
@Log("新增/按钮")
@PostMapping
//@RequiresPermissions("mdlDSurgery:add")
public void addMdlDSurgery(@Valid MdlDSurgery mdlDSurgery)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        mdlDSurgery.setCreateUserId(currentUser.getUserId());
        this.iMdlDSurgeryService.createMdlDSurgery(mdlDSurgery);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param mdlDSurgery
 * @return
 */
@Log("修改")
@PutMapping
//@RequiresPermissions("mdlDSurgery:update")
public void updateMdlDSurgery(@Valid MdlDSurgery mdlDSurgery)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      mdlDSurgery.setModifyUserId(currentUser.getUserId());
        this.iMdlDSurgeryService.updateMdlDSurgery(mdlDSurgery);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PutMapping("batch")
public void updateEndDate(String ids, Date endDate){
    String[] list = ids.split(",");
    LambdaQueryWrapper<MdlDSurgery> wrapper = new LambdaQueryWrapper<>();
    wrapper.in(MdlDSurgery::getId, list);
    MdlDSurgery update = new MdlDSurgery();
    update.setEndDate(endDate);
    this.iMdlDSurgeryService.update(update, wrapper);

}
@Log("删除")
@DeleteMapping("/{ids}")
//@RequiresPermissions("mdlDSurgery:delete")
public void deleteMdlDSurgerys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iMdlDSurgeryService.deleteMdlDSurgerys(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
//@RequiresPermissions("mdlDSurgery:export")
public void export(QueryRequest request ,String dataJson,MdlDSurgery mdlDSurgery, HttpServletResponse response) throws FebsException {
        try {
            request.setPageNum(1);
            request.setPageSize(10000);
            request.setIsSearchCount(false);
        List<MdlDSurgery> mdlDSurgerys = this.iMdlDSurgeryService.findMdlDSurgerys(request, mdlDSurgery).getRecords();
        //ExcelKit.$Export(MdlDSurgery.class, response).downXlsx(mdlDSurgerys, false);
            ExportExcelUtils.exportCustomExcel_han(response, mdlDSurgerys,dataJson,"");
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)
public void downTemplate(HttpServletResponse response) {
        List<MdlDSurgeryImport> publishList = new ArrayList<>();
        ExcelKit.$Export(MdlDSurgeryImport.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)
//@RequiresPermissions("mdlDSurgery:import")
public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<MdlDSurgeryImport> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(MdlDSurgeryImport.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<MdlDSurgeryImport>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, MdlDSurgeryImport entity) {
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
        for (MdlDSurgeryImport mdlDSurgeryImport:successList
        ) {
    MdlDSurgery mdlDSurgery =new MdlDSurgery();
        BeanUtil.copyProperties(mdlDSurgeryImport,mdlDSurgery, CopyOptions.create().setIgnoreNullValue(true));
        mdlDSurgery.setStartDate(DateUtil.parseDate(mdlDSurgeryImport.getStartDate()));
        try {
            this.iMdlDSurgeryService.createMdlDSurgery(mdlDSurgery);
        }
        catch (Exception ex){

        }

        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public MdlDSurgery detail(@NotBlank(message = "{required}") @PathVariable String id) {
    MdlDSurgery mdlDSurgery=this.iMdlDSurgeryService.getById(id);
        return mdlDSurgery;
        }
        }