package cc.mrbird.febs.job.task;

import cc.mrbird.febs.common.utils.SendMessUtil;
import cc.mrbird.febs.common.utils.WxMessage;

import cc.mrbird.febs.rfc.CustomUser;
import cc.mrbird.febs.rfc.RfcNoc;
import cc.mrbird.febs.sdl.entity.SdlBPersoninfo;
import cc.mrbird.febs.sdl.entity.SdlBUser;
import cc.mrbird.febs.sdl.entity.SdlBUserMg;
import cc.mrbird.febs.sdl.service.ISdlBPersoninfoService;
import cc.mrbird.febs.sdl.service.ISdlBScheduleDetailService;
import cc.mrbird.febs.sdl.service.ISdlBUserMgService;
import cc.mrbird.febs.sdl.service.ISdlBUserService;
import cc.mrbird.febs.sdl.service.impl.SdlBUserServiceImpl;
import cc.mrbird.febs.system.service.UserService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MsgValidTask {

    @Autowired
    private ISdlBUserService iSdlBUserService;

    @Autowired
    private ISdlBUserMgService iSdlBUserMgService;

    @Autowired
    private ISdlBPersoninfoService iSdlBPersoninfoService;

    @Autowired
    private ISdlBScheduleDetailService iSdlBScheduleDetailService;

    @Autowired
    private UserService userService;

    public void user() {
        RfcNoc rfcNoc = new RfcNoc();
        List<SdlBUser> sdlBUserList = rfcNoc.GetUserList();
        List<CustomUser> sdlUserIds = iSdlBUserService.getUserAccounts();
      //  List<String> listUserName= this.userService.getAllUserName();

        for (SdlBUser user : sdlBUserList
        ) {
           List<CustomUser> users= sdlUserIds.stream().filter(p->p.getUserAccount().equals(user.getUserAccount())).collect(Collectors.toList());
            if (users.size()>0) {
                user.setId(users.get(0).getId());
                iSdlBUserService.updateSdlBUser(user);
            } else {
                iSdlBUserService.createSdlBUser(user);
            }

        }
    }

    /**
     * ????????????????????????  ???????????????
     */
    public void user2() {
        RfcNoc rfcNoc = new RfcNoc();
        List<SdlBUserMg> sdlBUserList = rfcNoc.GetUserList2();
        List<CustomUser> sdlUserIds = iSdlBUserMgService.getUserAccounts();
        List<String> listUserName= this.userService.getAllUserName();
        for (SdlBUserMg user : sdlBUserList
        ) {
            List<CustomUser> users= sdlUserIds.stream().filter(p->p.getUserAccount().equals(user.getUserAccount())).collect(Collectors.toList());
            if (users.size()>0) {
                user.setId(users.get(0).getId());
                iSdlBUserMgService.updateSdlBUserMg(user);
            } else {
                iSdlBUserMgService.createSdlBUserMg(user);
            }
            if(!listUserName.contains(user.getUserAccount().trim())){
                try {
                    this.userService.regist2(user.getUserAccount().trim(),"XH"+ user.getIdCard().substring(user.getIdCard().length() - 6),user.getUserAccountName());
                }
                catch (Exception ex){
                    log.error(user.getUserAccount()+"??????????????????????????????"+ex.getMessage());
                }
            }
        }
    }
    public void sendMess(){
        String startDate = DateUtil.format(DateUtil.beginOfWeek(new Date()),"yyyy-MM-dd");
        String endDate = DateUtil.format(DateUtil.endOfWeek(new Date()),"yyyy-MM-dd");
        List<SdlBPersoninfo> listSend= iSdlBPersoninfoService.sendMess(startDate);
        SendMessUtil sendMessUtil =new SendMessUtil();
        for (SdlBPersoninfo person: listSend
             ) {
            String mess="?????????"+person.getDeptName()+"?????????????????????????????????"+startDate+"??????"+endDate+"????????????????????????????????????????????????"+endDate+"???????????????????????????????????????????????????http://whuhmedical.qm.whuh.com:8809/#/login????????????????????????????????????";
            log.info(mess);
            sendMessUtil.SendMessByTel(person.getTelephone(),mess);
        }
    }

    /**
     * ???15????????????????????????????????????????????????
     */
    public  void updateXulie(){
        String month =DateUtil.format(DateUtil.offsetMonth(new Date(),-1),"yyyy-MM");
        iSdlBScheduleDetailService.updateXuelie(month);
    }
}
