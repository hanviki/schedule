package cc.mrbird.febs.sdl.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.DateUtil;
import cc.mrbird.febs.sdl.entity.*;
import cc.mrbird.febs.sdl.service.*;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.Dept;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.system.service.DeptService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;

import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.OutputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author viki
 * @since 2021-10-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sdlBScheduleD")

public class SdlBScheduleDController extends BaseController {

    private String message;
    @Autowired
    public ISdlBScheduleDService iSdlBScheduleDService;
    @Autowired
    public ISdlBScheduleDetailService iSdlBScheduleDetailService;

    @Autowired
    public ISdlDeptBanciService iSdlDeptBanciService;

    @Autowired
    public ISdlDBanciService iSdlDBanciService;

    @Autowired
    public ISdlBUserService iSdlBUserService;

    @Autowired
    private ISdlBScheduleService iSdlBScheduleService;

    @Autowired
    private ISdlDHolidayService iSdlDHolidayService;
    @Autowired
    private  ISdlDWeekenddayService iSdlDWeekenddayService;

    @Autowired
    private DeptService deptService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'????????????','/dca/SdlBScheduleD/SdlBScheduleD','dca/SdlBScheduleD/SdlBScheduleD','sdlBScheduleD:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'??????????????????','sdlBScheduleD:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'??????????????????','sdlBScheduleD:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'??????????????????','sdlBScheduleD:delete',1,1,NOW())
 */


    /**
     * ??????????????????
     *
     * @param request       ????????????
     * @param sdlBScheduleD ????????????
     * @return
     */
    @GetMapping
    @RequiresPermissions("sdlBScheduleD:view")
    public Map<String, Object> List(QueryRequest request, SdlBScheduleD sdlBScheduleD) {
        return getDataTable(this.iSdlBScheduleDService.findSdlBScheduleDs(request, sdlBScheduleD));
    }

    @GetMapping("zizhi")
    public List<SdlBScheduleD> List_zizhi(SdlBScheduleD sdlBScheduleD) {
        User currentUser = FebsUtil.getCurrentUser();
        sdlBScheduleD.setDeptId(currentUser.getDeptId());
        List<SdlBScheduleD> zizhiList = this.iSdlBScheduleDService.getPaiBanZizhi(sdlBScheduleD);
        LambdaQueryWrapper<SdlBScheduleD> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SdlBScheduleD::getDeptId, currentUser.getDeptId());
        queryWrapper.ge(SdlBScheduleD::getScheduleDate,sdlBScheduleD.getStartDate());
        queryWrapper.le(SdlBScheduleD::getScheduleDate,sdlBScheduleD.getEndDate());
        List<SdlBScheduleD> sdlBScheduleDList = this.iSdlBScheduleDService.list(queryWrapper);
        zizhiList.stream().parallel().forEach(p -> {
                    List<SdlBScheduleD> subList = sdlBScheduleDList.stream().filter(
                            t -> t.getBqId().equals(p.getBqId()) && t.getZizhiId().equals(p.getZizhiId())
                            &&  t.getBaseId().equals(sdlBScheduleD.getBaseId())
                    ).collect(Collectors.toList());
                    p.setDynamicData(subList);
                }

        );
        return zizhiList;
    }
    @GetMapping("zizhi_edit")
    public Map<String, Object> List_zizhiForEdit(SdlBScheduleD sdlBScheduleD) {
        User currentUser = FebsUtil.getCurrentUser();
        sdlBScheduleD.setDeptId(currentUser.getDeptId());
        List<SdlBScheduleD> zizhiList = this.iSdlBScheduleDService.getPaiBanZizhi(sdlBScheduleD);
        LambdaQueryWrapper<SdlBScheduleD> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SdlBScheduleD::getDeptId, currentUser.getDeptId());
        queryWrapper.ge(SdlBScheduleD::getScheduleDate,sdlBScheduleD.getStartDate());
        queryWrapper.le(SdlBScheduleD::getScheduleDate,sdlBScheduleD.getEndDate());
        List<SdlBScheduleD> sdlBScheduleDList = this.iSdlBScheduleDService.list(queryWrapper);
        zizhiList.stream().parallel().forEach(p -> {
                    List<SdlBScheduleD> subList = sdlBScheduleDList.stream().filter(
                            t -> t.getBqId().equals(p.getBqId()) && t.getZizhiId().equals(p.getZizhiId())
                                    &&  t.getBaseId().equals(sdlBScheduleD.getBaseId())
                    ).collect(Collectors.toList());
                    p.setDynamicData(subList);
                }

        );
        SdlBUser sdlBUser =new SdlBUser();
        sdlBUser.setDeptId(currentUser.getDeptId());

        List<CustomUser> list= this.iSdlBUserService.findSdlBUsers(sdlBUser);

        Map<String, Object> rspData = new HashMap<>();
        rspData.put("user", list);
        rspData.put("zizhi", zizhiList);
       // return rspData;
       // return list;
        return rspData;
    }
    @GetMapping("zizhidept")
    public List<SdlBScheduleD> List_zizhi2(SdlBScheduleD sdlBScheduleD) {
        User currentUser = FebsUtil.getCurrentUser();
       // sdlBScheduleD.setDeptId(currentUser.getDeptId());
        List<SdlBScheduleD> zizhiList = this.iSdlBScheduleDService.getPaiBanZizhi(sdlBScheduleD);
        LambdaQueryWrapper<SdlBScheduleD> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SdlBScheduleD::getDeptId, sdlBScheduleD.getDeptId());
        queryWrapper.ge(SdlBScheduleD::getScheduleDate,sdlBScheduleD.getStartDate());
        queryWrapper.le(SdlBScheduleD::getScheduleDate,sdlBScheduleD.getEndDate());
        List<SdlBScheduleD> sdlBScheduleDList = this.iSdlBScheduleDService.list(queryWrapper);
        zizhiList.stream().parallel().forEach(p -> {
                    List<SdlBScheduleD> subList = sdlBScheduleDList.stream().filter(
                            t -> t.getBqId().equals(p.getBqId()) && t.getZizhiId().equals(p.getZizhiId())
                                    &&  t.getBaseId().equals(sdlBScheduleD.getBaseId())
                    ).collect(Collectors.toList());
                    p.setDynamicData(subList);
                }

        );
        return zizhiList;
    }

    private  List<String> handleHoliday(SdlDeptBanci sdlDeptBanci){
        List<SdlDHoliday> sdlDHolidayList= iSdlDHolidayService.list();
        List<String> holidays=new ArrayList<>();
        for (SdlDHoliday item:sdlDHolidayList
        ) {
            holidays.addAll(DateUtil.getDays(item.getStartDate(),item.getEndDate()));
        }
        List<String> sdlDWeekenddayList =iSdlDWeekenddayService.list().stream().map(p->p.getId()).collect(Collectors.toList());
        holidays.addAll(sdlDWeekenddayList);

        List<String> currentWeekDay= DateUtil.getDays(sdlDeptBanci.getStartDateFrom(),sdlDeptBanci.getStartDateTo());

        List<String> weekendDay= currentWeekDay.stream().filter(p->holidays.contains(p)).collect(Collectors.toList());
        List<String> arr=new ArrayList<>();
        for (String day :weekendDay
        ) {
            int d= cn.hutool.core.date.DateUtil.dayOfWeek(cn.hutool.core.date.DateUtil.parseDate(day));
            if(d==1){
                arr.add("7");
            }
            else {
                arr.add(String.valueOf(d-1));
            }
        }
        return  arr;
    }

    @GetMapping("banci")
    public List<SdlDBanci> List_banci(SdlDeptBanci sdlDeptBanci) {
        User currentUser = FebsUtil.getCurrentUser();
        sdlDeptBanci.setDeptId(currentUser.getDeptId());

        List<SdlDBanci> sdlDeptBanciList = iSdlDeptBanciService.findBanci(sdlDeptBanci);

        List<String> holidays= handleHoliday(sdlDeptBanci);
        String holidayString= StringUtils.join(holidays,",");

        for (SdlDBanci banci: sdlDeptBanciList
             ) {
            banci.setHoliday(holidayString);
        }
        return sdlDeptBanciList;
    }
    @GetMapping("bancidept")
    public List<SdlDBanci> List_banci2(SdlDeptBanci sdlDeptBanci) {
        User currentUser = FebsUtil.getCurrentUser();

        List<SdlDBanci> sdlDeptBanciList = iSdlDeptBanciService.findBanci(sdlDeptBanci);

        List<String> holidays= handleHoliday(sdlDeptBanci);
        String holidayString= StringUtils.join(holidays,",");

        for (SdlDBanci banci: sdlDeptBanciList
        ) {
            banci.setHoliday(holidayString);
        }

        return sdlDeptBanciList;
    }



    @GetMapping("report")
    public Map<String, Object> List_report(SdlBScheduleD sdlBScheduleD) {
        LambdaQueryWrapper<SdlBScheduleD> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.eq(SdlBScheduleD::getDeptId,sdlBScheduleD.getDeptId());
        queryWrapper.ge(SdlBScheduleD::getScheduleDate,sdlBScheduleD.getScheduleDateFrom());
        queryWrapper.le(SdlBScheduleD::getScheduleDate,sdlBScheduleD.getScheduleDateTo());
        List<SdlBScheduleD>  list= this.iSdlBScheduleDService.list(queryWrapper);
       // Map<Integer,String> banci =list.stream().collect(Collectors.toMap(SdlBScheduleD::getBanciId,SdlBScheduleD::getBanci));
       // List<String> bc2 =new ArrayList<>();
        List<CustomReportData> zizhi =new ArrayList<>();;

        List<SdlDBanci> banciList=this.iSdlDBanciService.list();

        List<Integer> bc2= list.stream().map(p-> p.getBanciId()).distinct().collect(Collectors.toList());

       List<SdlDBanci> bc= banciList.stream().filter(p->bc2.contains(p.getId())).sorted(Comparator.comparingInt(SdlDBanci::getDisplayIndex)).collect(Collectors.toList());

        zizhi= list.stream().map(p-> new CustomReportData(p.getZizhiId(),p.getZizhiName())).distinct().collect(Collectors.toList());
        Map<String, Object> rspData = new HashMap<>();
       // rspData.put("banci", bc);
       // rspData.put("zizhi", zizhi);
      //  rspData.put("rows", list);
       // List<String> days= DateUtil.getDays(sdlBScheduleD.getScheduleDateFrom(),sdlBScheduleD.getScheduleDateTo());
       // rspData.put("days",days);

        StringBuilder sb= new StringBuilder();
        sb.append("<table class='cuTable'>");

        Date startDate= cn.hutool.core.date.DateUtil.parseDate(sdlBScheduleD.getScheduleDateFrom());
        Date endDate= cn.hutool.core.date.DateUtil.parseDate(sdlBScheduleD.getScheduleDateTo());


        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(startDate);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(endDate);
        tempEnd.add(Calendar.DATE, +1);// ?????????1(????????????)
        while (tempStart.before(tempEnd)) {
            Date zhouyi= cn.hutool.core.date.DateUtil.beginOfWeek(tempStart.getTime());
            Date zhouri= cn.hutool.core.date.DateUtil.endOfWeek(tempStart.getTime());
            handleData(zhouyi,zhouri,list,sb,bc,zizhi);
            tempStart.setTime(zhouri);
            tempStart.add(Calendar.DATE, +1);// ?????????1(????????????)
        }
        sb.append("</table>");
        rspData.put("html",sb.toString());

        return  rspData;
    }
    private  void handleData(Date zhouyi,Date zhouri,List<SdlBScheduleD>  list,StringBuilder sb,List<SdlDBanci> bc,List<CustomReportData> zizhi) {
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(zhouyi);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(zhouri);
     //   tempEnd.add(Calendar.DATE, +1);// ?????????1(????????????)
        getHeader(zhouyi,sb);//????????????
        int banci_count= zizhi.size();

            for(SdlDBanci banci :bc){
                int i=0;

                for(CustomReportData zh:zizhi){
                    tempStart.setTime(zhouyi);
                    if(i==0){
                        sb.append("<tr><td rowspan='".concat(String.valueOf(banci_count)).concat("'>").concat(banci.getMuduleName()).concat("</td>"));
                    }
                    else {
                        sb.append("<tr>");
                    }
                    sb.append("<td>".concat(zh.getValue()).concat("</td>"));
                    while (tempStart.before(tempEnd)) {
                        String accounts= list.stream().filter(p->p.getZizhiId().equals(zh.getKey())&&p.getBanciId().equals(banci.getId())&&p.getScheduleDate().equals(tempStart.getTime())).map(p->p.getAccountName()).collect(Collectors.joining(",","",""));
                        sb.append("<td>".concat(accounts==null?"":accounts).concat("</td>"));
                        tempStart.add(Calendar.DAY_OF_YEAR, 1);
                    }
                    i=1;
                    sb.append("</tr>");
                }

            }
    }
    private  String getWeekDates(Date startDate,int offsetday){
        Date cu= cn.hutool.core.date.DateUtil.offsetDay(startDate,offsetday);
        return cn.hutool.core.date.DateUtil.format(cu,"yyyy-MM-dd");
    }
    private void  getHeader(Date zhouyi,StringBuilder sb){
        sb.append("<tr class='heaWeight'>");
        sb.append("<td colspan='2'>??????/??????</td>");
        sb.append("<td >??????"
                .concat(cn.hutool.core.date.DateUtil.format(zhouyi,"yyyy-MM-dd"))
                .concat("</td>"));
        sb.append("<td >??????"
                .concat(getWeekDates(zhouyi,1))
                .concat("</td>"));
        sb.append("<td >??????"
                .concat(getWeekDates(zhouyi,2))
                .concat("</td>"));

        sb.append("<td>??????"
                .concat(getWeekDates(zhouyi,3))
                .concat("</td>"));
        sb.append("<td >??????"
                .concat(getWeekDates(zhouyi,4))
                .concat("</td>"));
        sb.append("<td >??????"
                .concat(getWeekDates(zhouyi,5))
                .concat("</td>"));
        sb.append("<td >??????"
                .concat(getWeekDates(zhouyi,6))
                .concat("</td></tr>"));
    }
    /**
     * ??????
     *
     * @param sdlBScheduleD
     * @return
     */
    @Log("??????/??????")
    @PostMapping
    @RequiresPermissions("sdlBScheduleD:add")
    public void addSdlBScheduleD(@Valid SdlBScheduleD sdlBScheduleD) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            sdlBScheduleD.setCreateUserId(currentUser.getUserId());
            this.iSdlBScheduleDService.createSdlBScheduleD(sdlBScheduleD);
        } catch (Exception e) {
            message = "??????/????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("??????/??????")
    @PostMapping("add")
    public void addSdlBScheduleD_new(@Valid String jsonStr, String startDate, String endDate) throws FebsException {


        String message="";
        try {
            
            User currentUser = FebsUtil.getCurrentUser();

            log.info("??????????????????:"+currentUser.getUsername());
            List<SdlBScheduleD> list = JSON.parseObject(jsonStr, new TypeReference<List<SdlBScheduleD>>() {
            });

            /**
             * ????????????????????? ???????????? ???????????????
             */
            TimeInterval timer= cn.hutool.core.date.DateUtil.timer();
           // LambdaQueryWrapper<Dept> queryWrapper_dept =new LambdaQueryWrapper<>();
           // queryWrapper_dept.eq(Dept::getParentId,currentUser.getDeptId());
            //List<Dept> deptList = this.deptService.list(queryWrapper_dept);


            List<SdlBScheduleDetail> list_detail = JSON.parseObject(jsonStr, new TypeReference<List<SdlBScheduleDetail>>() {
            });
           long time111= timer.interval();
           log.info("???????????????list"+String.valueOf(time111));
           timer.intervalRestart();

            List<SdlBScheduleDetail> addListDetail=new ArrayList<>();
            if(list.size()>0) {
                List<SdlDBanci> banciList =this.iSdlDBanciService.list();
                LambdaQueryWrapper<SdlBUser> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.apply("sdl_b_user.dept_id in (select t_dept.dept_id from t_dept where t_dept.DEPT_ID='" + currentUser.getDeptId() + "' or t_dept.PARENT_ID='" + currentUser.getDeptId() + "' )");
                queryWrapper.ne(SdlBUser::getState, 0);//?????????2??????3???
                List<SdlBUser> users = this.iSdlBUserService.list(queryWrapper); //?????????????????????  ????????????????????????????????????

                long time222= timer.interval();
                log.info("?????????????????????????????????"+String.valueOf(time222));
                timer.intervalRestart();


              //  list_detail.forEach(sdlBScheduleDetail -> {
                    for(SdlBScheduleDetail sdlBScheduleDetail : list_detail){
                    sdlBScheduleDetail.setCreateUserId(currentUser.getUserId());
                    List<String> userAccounts = Arrays.asList(sdlBScheduleDetail.getAccountId().replace("[", "").replace("]", "").replace("\"", "").split(","));
                    String banciName = banciList.stream().filter(p -> p.getId().equals(sdlBScheduleDetail.getBanciId())).map(p -> p.getMuduleName()).findFirst().get();
                    sdlBScheduleDetail.setBanci(banciName);

                 //   userAccounts.forEach(accounts->
                        //
                     for(String accounts : userAccounts){
                        SdlBScheduleDetail nDetail =new SdlBScheduleDetail();
                        try {
                            BeanUtil.copyProperties(sdlBScheduleDetail,nDetail, CopyOptions.create().setIgnoreNullValue(true));
                        }
                        catch (Exception exr){

                        }
                        nDetail.setMonth(cn.hutool.core.date.DateUtil.format(sdlBScheduleDetail.getScheduleDate(),"yyyy-MM"));
                        nDetail.setSch(cn.hutool.core.date.DateUtil.format(sdlBScheduleDetail.getScheduleDate(),"yyyy-MM-dd"));
                        nDetail.setAccountId(accounts);
                        List<SdlBUser> users1=users.stream().filter(p -> accounts.equals(p.getUserAccount())).collect(Collectors.toList());
                        if(users1==null ||users1.size()==0){
                                throw new Exception(accounts+":??????????????????????????????????????????????????????");
                        }

                        SdlBUser user =users1.get(0);

                        String accountName = user.getUserAccountName();

                        nDetail.setXulie(user.getPatentGood());//??????
                        nDetail.setZhicheng(user.getZhicheng()); //??????
                        nDetail.setAccountName(accountName);
                        addListDetail.add(nDetail);  //?????????????????????????????? ?????????job?????????
                       // this.iSdlBScheduleDetailService.createSdlBScheduleDetail(sdlBScheduleDetail);
                    }

                }

                long time333= timer.interval();
                log.info("???????????????????????????????????????????????????????????????"+String.valueOf(time333));
                timer.intervalRestart();

                Function<SdlBScheduleDetail, List<Object>> compositeKey = personRecord ->
                        Arrays.asList(personRecord.getAccountId(), personRecord.getScheduleDate());

                Map<Object, List<SdlBScheduleDetail>> map =
                        addListDetail.stream().filter(p->p.getZizhiName().equals("?????????")).collect(Collectors.groupingBy(compositeKey));
                for (Map.Entry<Object, List<SdlBScheduleDetail>> entrykey23 : map.entrySet()){
                    if(entrykey23.getValue().size()>1){
                        List<SdlBScheduleDetail> bScheduleDS =entrykey23.getValue();
                        String acc= bScheduleDS.get(0).getAccountId();
                        String dach= cn.hutool.core.date.DateUtil.format(bScheduleDS.get(0).getScheduleDate(),"yyyy-MM-dd");
                        SdlBUser user234 =users.stream().filter(p -> p.getUserAccount().equals(acc)).findFirst().get();
                        String useraccountNAme = user234.getUserAccountName();
                        List<String> hh= bScheduleDS.stream().map(p->p.getBanci()).distinct().collect(Collectors.toList());//?????? ?????? ??????ID
                        //List<Integer> yuanqu= deptList.stream().filter(p->hh.contains(p.getDeptId())).map(p->p.getAreaIndex()).distinct().collect(Collectors.toList());
                        for (String bc23:hh
                             ) {
                           long areaCount =bScheduleDS.stream().filter(p->p.getBanci().equals(bc23)).map(p->p.getAreaIndex()).distinct().count();
                            if(areaCount>1){
                                throw new  Exception(useraccountNAme+ " "+dach+" ???????????????????????????????????????");
                            }
                        }

                    }
                }
                long time444= timer.interval();
                log.info("?????????????????????????????????????????????"+String.valueOf(time444));
                timer.intervalRestart();

                this.iSdlBScheduleDService.deleteByDeptAndDate(currentUser.getDeptId(), startDate, endDate);

                long time555= timer.interval();
                log.info("??????sdlBScheduleD???detail???"+String.valueOf(time555));
                timer.intervalRestart();

                list.parallelStream().forEach(sdlBScheduleD -> {
                    sdlBScheduleD.setCreateUserId(currentUser.getUserId());
                    List<String> userAccounts = Arrays.asList(sdlBScheduleD.getAccountId().replace("[", "").replace("]", "").replace("\"", "").split(","));
                    String userAccountNames = users.stream().filter(p -> userAccounts.contains(p.getUserAccount())).map(p ->p.getUserAccount()+"_"+ p.getUserAccountName()).collect(Collectors.joining(",", "", ""));
                    sdlBScheduleD.setAccountName(userAccountNames);
                    String banciName = banciList.stream().filter(p -> p.getId().equals(sdlBScheduleD.getBanciId())).map(p -> p.getMuduleName()).findFirst().get();
                    sdlBScheduleD.setBanci(banciName);
                    this.iSdlBScheduleDService.createSdlBScheduleD(sdlBScheduleD);
                });

                long time666= timer.interval();
                log.info("??????sdlBScheduleD???"+String.valueOf(time666));
                timer.intervalRestart();
                addListDetail.parallelStream().forEach(detail->{
                    this.iSdlBScheduleDetailService.createSdlBScheduleDetail(detail);
                });
//                for (SdlBScheduleDetail detail:addListDetail
//                     ) {
//                    this.iSdlBScheduleDetailService.createSdlBScheduleDetail(detail);
//                }
                long time777= timer.interval();
                log.info("??????Detail???"+String.valueOf(time777));
                timer.intervalRestart();
                this.iSdlBScheduleService.updateStateById(list.get(0).getBaseId(), 1);//??????????????????????????????????????????????????????NULL


            }

        } catch (Exception e) {
            throw new FebsException(e.getMessage());
        }
    }

    /**
     * ??????
     *
     * @param sdlBScheduleD
     * @return
     */
    @Log("??????")
    @PutMapping
    @RequiresPermissions("sdlBScheduleD:update")
    public void updateSdlBScheduleD(@Valid SdlBScheduleD sdlBScheduleD) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            sdlBScheduleD.setModifyUserId(currentUser.getUserId());
            this.iSdlBScheduleDService.updateSdlBScheduleD(sdlBScheduleD);
        } catch (Exception e) {
            message = "????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("??????")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("sdlBScheduleD:delete")
    public void deleteSdlBScheduleDs(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iSdlBScheduleDService.deleteSdlBScheduleDs(arr_ids);
        } catch (Exception e) {
            message = "????????????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     *
     * @param request
     * @param sdlBScheduleD
     * @param response
     * @param flag ????????????????????? 0?????? 1??????
     * @throws FebsException
     */
    @PostMapping("excel")
    public void export(QueryRequest request, SdlBScheduleD sdlBScheduleD, HttpServletResponse response,int flag) throws FebsException {
        try {
            ExcelWriter writer = ExcelUtil.getWriter(true);

            LambdaQueryWrapper<SdlBScheduleD> queryWrapper= new LambdaQueryWrapper<>();
            queryWrapper.eq(SdlBScheduleD::getDeptId,sdlBScheduleD.getDeptId());
            queryWrapper.ge(SdlBScheduleD::getScheduleDate,sdlBScheduleD.getScheduleDateFrom());
            queryWrapper.le(SdlBScheduleD::getScheduleDate,sdlBScheduleD.getScheduleDateTo());
            List<SdlBScheduleD>  list= this.iSdlBScheduleDService.list(queryWrapper);
           // List<CustomReportData> bc =new ArrayList<>();
            List<CustomReportData> zizhi =new ArrayList<>();;
            List<SdlDBanci> banciList=this.iSdlDBanciService.list();
            List<Integer> bc2= list.stream().map(p-> p.getBanciId()).distinct().collect(Collectors.toList());

            List<SdlDBanci> bc= banciList.stream().filter(p->bc2.contains(p.getId())).sorted(new Comparator<SdlDBanci>() {
                @Override
                public int compare(SdlDBanci o1, SdlDBanci o2) {
                    return o1.getDisplayIndex()-o2.getDisplayIndex();
                }
            }).collect(Collectors.toList());
            zizhi= list.stream().map(p-> new CustomReportData(p.getZizhiId(),p.getZizhiName())).distinct().collect(Collectors.toList());
            Map<String, Object> rspData = new HashMap<>();


            Date startDate= cn.hutool.core.date.DateUtil.parseDate(sdlBScheduleD.getScheduleDateFrom());
            Date endDate= cn.hutool.core.date.DateUtil.parseDate(sdlBScheduleD.getScheduleDateTo());


            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(startDate);

            writer.merge(0,0,0,8,sdlBScheduleD.getScheduleDateFrom()+"???"+sdlBScheduleD.getScheduleDateTo()+"?????????",false); //????????????
            int cureentRow=1;
            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(endDate);
            tempEnd.add(Calendar.DATE, +1);// ?????????1(????????????)


            writer.getCellStyle().setWrapText(true);
            writer.setColumnWidth(0,20);
            writer.setColumnWidth(1,20);
            writer.setColumnWidth(2,20);
            writer.setColumnWidth(3,20);
            writer.setColumnWidth(4,20);
            writer.setColumnWidth(5,20);
            writer.setColumnWidth(6,20);
            writer.setColumnWidth(7,20);
            writer.setColumnWidth(8,20);

            while (tempStart.before(tempEnd)) {
                Date zhouyi= cn.hutool.core.date.DateUtil.beginOfWeek(tempStart.getTime());
                Date zhouri= cn.hutool.core.date.DateUtil.endOfWeek(tempStart.getTime());
                cureentRow= handleData2(writer,zhouyi,zhouri,list,bc,zizhi,cureentRow,flag);
                tempStart.setTime(zhouri);
                tempStart.add(Calendar.DATE, +1);// ?????????1(????????????)
            }
            String fileName =sdlBScheduleD.getScheduleDateFrom()+"-"+sdlBScheduleD.getScheduleDateTo()+sdlBScheduleD.getDeptId();

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
            OutputStream out=response.getOutputStream();
            writer.flush(out, true);
            writer.close();
            IoUtil.close(out);
        } catch (Exception e) {
            message = "??????Excel??????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    private  int handleData2(ExcelWriter writer,Date zhouyi,Date zhouri,List<SdlBScheduleD>  list,List<SdlDBanci> bc,List<CustomReportData> zizhi,int currentRow,int flag) {
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(zhouyi);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(zhouri);
        //   tempEnd.add(Calendar.DATE, +1);// ?????????1(????????????)
        getHeader2(zhouyi,writer,currentRow);//????????????
        currentRow+=1;
        int banci_count= zizhi.size();


        for(SdlDBanci banci :bc){
            int i=0;

            for(CustomReportData zh:zizhi){

                tempStart.setTime(zhouyi);
                if(i==0){
                    writer.writeCellValue(0,currentRow,banci.getMuduleName());

                   // sb.append("<tr><td rowspan='".concat(String.valueOf(banci_count)).concat("'>").concat(banci.getValue()).concat("</td>"));
                }
                else {
                    writer.writeCellValue(0,currentRow,"");
                }
                writer.writeCellValue(1,currentRow,zh.getValue());

                int y=2;
                while (tempStart.before(tempEnd)) {
                    String accounts="";
                    if(flag==0) {
                        accounts = list.stream().filter(p -> p.getZizhiId().equals(zh.getKey()) && p.getBanciId().equals(banci.getId()) && p.getScheduleDate().equals(tempStart.getTime())).map(p -> p.getAccountName()).collect(Collectors.joining(",", "", ""));
                    }
                    else{
                        accounts = list.stream().filter(p -> p.getZizhiId().equals(zh.getKey()) && p.getBanciId().equals(banci.getId()) && p.getScheduleDate().equals(tempStart.getTime())).map(p -> RemoveAccountID(p.getAccountName())).collect(Collectors.joining(",", "", ""));
                    }
                    writer.writeCellValue(y,currentRow,accounts==null?"":accounts);
                    tempStart.add(Calendar.DAY_OF_YEAR, 1);
                    y=y+1;
                }
                i=1;

                currentRow+=1;

            }
            if(banci_count>1) {
                writer.merge(currentRow - banci_count, currentRow - 1, 0, 0, banci.getMuduleName(), false); //????????????
            }

        }
        return  currentRow;
    }

    private String RemoveAccountID(String accountName){
        String name="";
        String[] arr= accountName.split(",");
        for (String n:arr
             ) {
            if(name==""){
                name+=n.substring(n.indexOf("_")+1);
            }
            else{
                name+=","+n.substring(n.indexOf("_")+1);
            }

        }
        return  name;
    }
    private void  getHeader2(Date zhouyi,ExcelWriter writer,int x){
        writer.writeCellValue(0,x,"??????/??????");
        writer.writeCellValue(2,x,"??????"
                .concat(cn.hutool.core.date.DateUtil.format(zhouyi,"yyyy-MM-dd"))
                );
        writer.writeCellValue(3,x,("??????"
                .concat(getWeekDates(zhouyi,1))
                ));
        writer.writeCellValue(4,x,"??????"
                .concat(getWeekDates(zhouyi,2))
                );

        writer.writeCellValue(5,x,"??????"
                .concat(getWeekDates(zhouyi,3))
                );
        writer.writeCellValue(6,x,"??????"
                .concat(getWeekDates(zhouyi,4))
        );
        writer.writeCellValue(7,x,"??????"
                .concat(getWeekDates(zhouyi,5))
               );
        writer.writeCellValue(8,x,"??????"
                .concat(getWeekDates(zhouyi,6))
               );
        writer.merge(x,x,0,1,"??????/??????",false); //??????
    }

    @GetMapping("/{id}")
    public SdlBScheduleD detail(@NotBlank(message = "{required}") @PathVariable String id) {
        SdlBScheduleD sdlBScheduleD = this.iSdlBScheduleDService.getById(id);
        return sdlBScheduleD;
    }
}