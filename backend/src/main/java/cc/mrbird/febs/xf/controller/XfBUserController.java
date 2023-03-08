
package cc.mrbird.febs.xf.controller;


import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.PdfDoc;
import cc.mrbird.febs.mdl.entity.MdlBBadrecord;
import cc.mrbird.febs.mdl.entity.MdlBKaoshi;
import cc.mrbird.febs.mdl.entity.MdlBPeixun;
import cc.mrbird.febs.mdl.service.IMdlBBadrecordService;
import cc.mrbird.febs.mdl.service.IMdlBKaoshiService;
import cc.mrbird.febs.mdl.service.IMdlBPeixunService;
import cc.mrbird.febs.sdl.entity.SdlBUserMg;
import cc.mrbird.febs.sdl.service.ISdlBUserMgService;
import cc.mrbird.febs.xf.entity.*;
import cc.mrbird.febs.xf.service.*;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.beust.jcommander.internal.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author viki
 * @since 2022-11-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("xfBUser")

public class XfBUserController extends BaseController{

private String message;
@Autowired
public IXfBUserService iXfBUserService;

        @Autowired
        public ISdlBUserMgService iSdlBUserMgService;
        @Autowired
        public IMdlBBadrecordService iMdlBBadrecordService;
        @Autowired
        public IXfBInnertalkService iXfBInnertalkService;
        @Autowired
        public IXfBInnertalkDService iXfBInnertalkDService;
        @Autowired
        public IXfBTousuService iXfBTousuService;
        @Autowired
        public IMdlBPeixunService iMdlBPeixunService;
        @Autowired
        public IMdlBKaoshiService iMdlBKaoshiService;
        @Autowired
        public IXfBTeachrecordService iXfBTeachrecordService;
        @Autowired
        public IXfBErrordeptService iXfBErrordeptService;


/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/ass/XfBUser/XfBUser','ass/XfBUser/XfBUser','xfBUser:view','fork',0,1,NOW());
 SELECT @maxId:=MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'新增','xfBUser:add',1,1,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'编辑','xfBUser:update',1,2,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'删除','xfBUser:delete',1,3,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导出','xfBUser:export',1,4,NOW());
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(@maxId,'导入','xfBUser:import',1,5,NOW());
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param xfBUser 查询条件
 * @return
 */
@GetMapping

public Map<String, Object> List(QueryRequest request, XfBUser xfBUser){
        return getDataTable(this.iXfBUserService.findXfBUserList(request, xfBUser));
        }

/**
 * 添加
 * @param  xfBUser
 * @return
 */
@Log("新增/按钮")
@PostMapping

public void addXfBUser(@Valid XfBUser xfBUser)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        xfBUser.setCreateUserId(currentUser.getUserId());
        this.iXfBUserService.createXfBUser(xfBUser);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param xfBUser
 * @return
 */
@Log("修改")
@PutMapping

public void updateXfBUser(@Valid XfBUser xfBUser)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      xfBUser.setModifyUserId(currentUser.getUserId());
        this.iXfBUserService.updateXfBUser(xfBUser);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")

public void deleteXfBUsers(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iXfBUserService.deleteXfBUsers(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")

public void export(QueryRequest request, XfBUser xfBUser, HttpServletResponse response) throws FebsException {
        try {
        List<XfBUser> xfBUsers = this.iXfBUserService.findXfBUsers(request, xfBUser).getRecords();
        ExcelKit.$Export(XfBUser.class, response).downXlsx(xfBUsers, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }
@RequestMapping(value = "downTemplate", method = RequestMethod.POST)

public void downTemplate(HttpServletResponse response) {
        List<XfBUser> publishList = new ArrayList<>();
        ExcelKit.$Export(XfBUser.class, response).downXlsx(publishList, true);
        }
@RequestMapping(value = "import", method = RequestMethod.POST)

public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
        throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<XfBUser> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser=FebsUtil.getCurrentUser();


        ExcelKit.$Import(XfBUser.class)
        .readXlsx(file.getInputStream(), new ExcelReadHandler<XfBUser>() {

@Override
public void onSuccess(int sheetIndex, int rowIndex, XfBUser entity) {
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
        for (XfBUser xfBUserImport:successList
        ) {
     //  XfBUser xfBUser =new XfBUser();
     //   BeanUtil.copyProperties(xfBUserImport,xfBUser, CopyOptions.create().setIgnoreNullValue(true));
        this.iXfBUserService.createXfBUser(xfBUserImport);
        }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
        }
@GetMapping("/{id}")
public XfBUser detail(@NotBlank(message = "{required}") @PathVariable String id) {
    XfBUser xfBUser=this.iXfBUserService.getById(id);
        return xfBUser;
        }
        @PostMapping("personPdf")
        public void export2(QueryRequest request, SdlBUserMg sdlBUser, HttpServletResponse response) throws FebsException {
                try {
                        PdfDoc pdfDemo = new PdfDoc();
                        String fileName = sdlBUser.getUserAccount() + ".pdf";
                        String filePath = "D://scm//uploadPdf//" + UUID.randomUUID().toString() +".pdf";
                      //  String filePath2 = "D://scm//uploadPdf//" + UUID.randomUUID().toString() + ".pdf";

                        XfPdf pdf =new XfPdf();
                        LambdaQueryWrapper<SdlBUserMg> userMgLambdaQueryWrapper =new LambdaQueryWrapper<>();
                        userMgLambdaQueryWrapper.eq(SdlBUserMg::getUserAccount,sdlBUser.getUserAccount());
                        pdf.setXfBUser(this.iSdlBUserMgService.list(userMgLambdaQueryWrapper).get(0));

                        LambdaQueryWrapper<XfBTousu> lambdaQueryWrapper= new LambdaQueryWrapper<>();
                        lambdaQueryWrapper.eq(XfBTousu::getUserAccount,sdlBUser.getUserAccount());
                        List<XfBTousu> tousuList= this.iXfBTousuService.list(lambdaQueryWrapper);
                        tousuList= tousuList.stream().sorted(Comparator.comparing(XfBTousu::getTousuDate)).collect(Collectors.toList());

                        LambdaQueryWrapper<MdlBBadrecord> lambdaQueryWrapper1= new LambdaQueryWrapper<>();
                        lambdaQueryWrapper1.eq(MdlBBadrecord::getUserAccount,sdlBUser.getUserAccount());
                        List<MdlBBadrecord> badrecordList= this.iMdlBBadrecordService.list(lambdaQueryWrapper1);
                        badrecordList=  badrecordList.stream().sorted(Comparator.comparing(MdlBBadrecord::getStartDate)).collect(Collectors.toList());
                        pdf.setBadrecordList(badrecordList);
                        pdf.setTousuList(tousuList);

                        LambdaQueryWrapper<MdlBPeixun>  lambdaQueryWrapper9= new LambdaQueryWrapper<>();
                        lambdaQueryWrapper9.eq(MdlBPeixun::getUserAccount,sdlBUser.getUserAccount());
                        List<MdlBPeixun> peixunList= this.iMdlBPeixunService.list(lambdaQueryWrapper9);
                        peixunList= peixunList.stream().sorted(Comparator.comparing(MdlBPeixun::getStarttime)).collect(Collectors.toList());
                        pdf.setPeixunList(peixunList);

                        LambdaQueryWrapper<MdlBKaoshi>  lambdaQueryWrapper10= new LambdaQueryWrapper<>();
                        lambdaQueryWrapper10.eq(MdlBKaoshi::getUserAccount,sdlBUser.getUserAccount());
                        List<MdlBKaoshi> kaoshiList= this.iMdlBKaoshiService.list(lambdaQueryWrapper10);
                        kaoshiList= kaoshiList.stream().sorted(Comparator.comparing(MdlBKaoshi::getFirstyuedu)).collect(Collectors.toList());
                        pdf.setKaoshiList(kaoshiList);

                        LambdaQueryWrapper<XfBInnertalkD>  lambdaQueryWrapper2= new LambdaQueryWrapper<>();
                        lambdaQueryWrapper2.eq(XfBInnertalkD::getUserAccount,sdlBUser.getUserAccount());
                        List<XfBInnertalkD> innertalkDs= this.iXfBInnertalkDService.list(lambdaQueryWrapper2);

                        List<String> baseIds= innertalkDs.stream().map(p->p.getBaseId()).collect(Collectors.toList());

                        List<XfBInnertalk> innertalks= new ArrayList<>();
                        if(baseIds.size()>0) {
                                LambdaQueryWrapper<XfBInnertalk> lambdaQueryWrapper8 = new LambdaQueryWrapper<>();
                                lambdaQueryWrapper8.in(XfBInnertalk::getId, baseIds);
                                innertalks= this.iXfBInnertalkService.list(lambdaQueryWrapper8);
                                innertalks= innertalks.stream().sorted(Comparator.comparing(XfBInnertalk::getTalkDate)).collect(Collectors.toList());

                        }
                        //List<XfBInnertalk>
//                        for (XfBInnertalk talk: innertalks
//                             ) {
//                                String oprionNames=innertalkDs.stream().filter(p->p.getBaseId().trim().equals(talk.getId().toString())).map(p->p.getUserAccountName()).collect(Collectors.joining(","));
//                              talk.setOperationName(oprionNames);
//                        }

                        pdf.setXfBInnertalkList(innertalks);

                         PdfDoc pdfDoc= new PdfDoc();
                         pdfDoc.writePdf2021_1(sdlBUser.getUserAccount(),filePath,pdf);

                        File file = new File(filePath);
                        OutputStream out = response.getOutputStream();
                        response.setContentType("application/pdf");
                        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                        log.info("开始读取");
                        try {
                                InputStream bis = new BufferedInputStream(new FileInputStream(file));
               /* byte[] b = new byte[bis.available() + 1000];
                int i = 0;

                while ((i = bis.read(b)) != -1) {
                    out.write(b, 0, i);
                }*/

                                byte [] b = new byte[1024];
                                long k = 0;
                                while(k < file.length()){
                                        int j = bis.read(b,0,1024);
                                        k += j;
                                        out.write(b,0,j);
                                        //out.flush();
                                }
                                bis.close();
                                log.info("读取完成");
                                out.flush();
                                out.close();
                        } catch (IOException e) {
                                log.error("读取pdf失败");
                                e.printStackTrace();
                        } finally {
                                if (out != null) {
                                        try {
                                                out.close();
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                }
                        }

                } catch (Exception e) {
                        message = "导出个人PDF失败";
                        log.error(message, e);
                        throw new FebsException(message);
                }
        }


        @PostMapping("deptPdf")
        public void export3(QueryRequest request, String deptId,String deptName,String startDate,String endDate, HttpServletResponse response) throws FebsException {
                try {
                        PdfDoc pdfDemo = new PdfDoc();
                        String fileName = deptName + ".pdf";
                        String filePath = "D://scm//uploadPdf//" + UUID.randomUUID().toString() +".pdf";
                        //  String filePath2 = "D://scm//uploadPdf//" + UUID.randomUUID().toString() + ".pdf";

                        XfDept pdf =new XfDept();
                        pdf.setDeptName(deptName);

                        LambdaQueryWrapper<SdlBUserMg> userMgLambdaQueryWrapper =new LambdaQueryWrapper<>();
                        userMgLambdaQueryWrapper.eq(SdlBUserMg::getDeptNew,deptName);
                        List<SdlBUserMg> userMgList= this.iSdlBUserMgService.list(userMgLambdaQueryWrapper);

                        List<String> accounts= userMgList.stream().map(p->p.getUserAccount()).collect(Collectors.toList());


                        LambdaQueryWrapper<XfBTousu> lambdaQueryWrapper= new LambdaQueryWrapper<>();
                        lambdaQueryWrapper.eq(XfBTousu::getDeptNew,deptName);
                        List<XfBTousu> tousuList= this.iXfBTousuService.list(lambdaQueryWrapper);

                        LambdaQueryWrapper<MdlBBadrecord> lambdaQueryWrapper1= new LambdaQueryWrapper<>();
                        lambdaQueryWrapper1.eq(MdlBBadrecord::getDeptNew,deptName);
                        List<MdlBBadrecord> badrecordList= this.iMdlBBadrecordService.list(lambdaQueryWrapper1);
                        pdf.setBadrecordList(badrecordList);
                        pdf.setTousuList(tousuList);

                        LambdaQueryWrapper<XfBTeachrecord>  lambdaQueryWrapper9= new LambdaQueryWrapper<>();
                        lambdaQueryWrapper9.eq(XfBTeachrecord::getDeptNew,deptName);
                        List<XfBTeachrecord> peixunList= this.iXfBTeachrecordService.list(lambdaQueryWrapper9);
                        if(peixunList.size()>0) {
                                pdf.setIsZhongdianzhuanxiangdudaokeshi("是");
                        }
                        else{
                                pdf.setIsZhongdianzhuanxiangdudaokeshi("否");
                        }


                        LambdaQueryWrapper<XfBInnertalkD>  lambdaQueryWrapper2= new LambdaQueryWrapper<>();
                        lambdaQueryWrapper2.in(XfBInnertalkD::getUserAccount,accounts);
                        List<XfBInnertalkD> innertalkDs= this.iXfBInnertalkDService.list(lambdaQueryWrapper2);

                        List<String> baseIds= innertalkDs.stream().map(p->p.getBaseId()).collect(Collectors.toList());

                        List<XfBInnertalk> innertalks= new ArrayList<>();
                        if(baseIds.size()>0) {
                                LambdaQueryWrapper<XfBInnertalk> lambdaQueryWrapper8 = new LambdaQueryWrapper<>();
                                lambdaQueryWrapper8.in(XfBInnertalk::getId, baseIds);
                                innertalks= this.iXfBInnertalkService.list(lambdaQueryWrapper8);

                                for (XfBInnertalk ta:innertalks
                                     ) {
                                        ta.setUserAccountName(innertalkDs.stream().filter(p->p.getBaseId().equals(ta.getId().toString())).map(p->p.getUserAccountName()).distinct().collect(Collectors.joining(",")));
                                }

                                innertalks= innertalks.stream().sorted(Comparator.comparing(XfBInnertalk::getTalkDate)).collect(Collectors.toList());

                        }

                        pdf.setXfBInnertalkList(innertalks);


                        LambdaQueryWrapper<MdlBKaoshi>  lambdaQueryWrapper10= new LambdaQueryWrapper<>();
                        lambdaQueryWrapper10.in(MdlBKaoshi::getUserAccount,accounts);
                        List<MdlBKaoshi> kaoshiList= this.iMdlBKaoshiService.list(lambdaQueryWrapper10);

                        long kaoshi_count= kaoshiList.stream().map(p->p.getUserAccount()).distinct().count();


                                double d = NumberUtil.div(kaoshi_count, accounts.size(), 2);
                                pdf.setHangfengcanyu(String.format("%.2f",d*100) + "%");

                        LambdaQueryWrapper<MdlBPeixun>  lambdaQueryWrapper11= new LambdaQueryWrapper<>();
                        lambdaQueryWrapper11.in(MdlBPeixun::getUserAccount,accounts);
                        List<MdlBPeixun> pxList= this.iMdlBPeixunService.list(lambdaQueryWrapper11);

                        long peixun_count= pxList.stream().map(p->p.getUserAccount()).distinct().count();

                        if(kaoshi_count<=0){
                                pdf.setKaoshihege(" %");
                        }
                        else {
                                double d2 = NumberUtil.div(peixun_count, kaoshi_count, 2);
                                pdf.setKaoshihege(String.format("%.2f",d2*100) + "%");
                        }

                        LambdaQueryWrapper<XfBErrordept>  lambdaQueryWrapper12= new LambdaQueryWrapper<>();
                        lambdaQueryWrapper12.in(XfBErrordept::getDeptNew,deptName);
                        List<XfBErrordept> errordepts= this.iXfBErrordeptService.list(lambdaQueryWrapper12);
                        pdf.setXfBErrordepts(errordepts);

                        PdfDoc pdfDoc= new PdfDoc();
                        pdfDoc.writePdfDept(filePath,pdf);

                        File file = new File(filePath);
                        OutputStream out = response.getOutputStream();
                        response.setContentType("application/pdf");
                        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                        log.info("开始读取");
                        try {
                                InputStream bis = new BufferedInputStream(new FileInputStream(file));
               /* byte[] b = new byte[bis.available() + 1000];
                int i = 0;

                while ((i = bis.read(b)) != -1) {
                    out.write(b, 0, i);
                }*/

                                byte [] b = new byte[1024];
                                long k = 0;
                                while(k < file.length()){
                                        int j = bis.read(b,0,1024);
                                        k += j;
                                        out.write(b,0,j);
                                        //out.flush();
                                }
                                bis.close();
                                log.info("读取完成");
                                out.flush();
                                out.close();
                        } catch (IOException e) {
                                log.error("读取pdf失败");
                                e.printStackTrace();
                        } finally {
                                if (out != null) {
                                        try {
                                                out.close();
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                }
                        }

                } catch (Exception e) {
                        message = "导出个人PDF失败";
                        log.error(message, e);
                        throw new FebsException(message);
                }
        }

}