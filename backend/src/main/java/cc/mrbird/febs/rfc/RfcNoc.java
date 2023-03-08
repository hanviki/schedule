package cc.mrbird.febs.rfc;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.properties.FebsProperties;
import cc.mrbird.febs.common.properties.JcoProperties;

import cc.mrbird.febs.sdl.entity.SdlBUser;
import cc.mrbird.febs.sdl.entity.SdlBUserMg;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.rfc.api.IRfcFunction;
import com.sap.conn.rfc.api.IRfcTable;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class RfcNoc {
    private static final String ABAP_AS_POOLED = "ECC";

    @Autowired
    private JcoProperties jcoproperties;
    static {
        Properties connectProperties = new Properties();
        /**
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, JcoProperties.getAshost());//服务器
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  JcoProperties.getSysnr());        //系统编号
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, JcoProperties.getClient());       //SAP集团
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, JcoProperties.getUser());  //SAP用户名
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, JcoProperties.getPassw());     //密码
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG, JcoProperties.getLang());        //登录语言
*/

           connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "192.168.64.26");//服务器
           connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "01");        //系统编号
           connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "800");       //SAP集团
           connectProperties.setProperty(DestinationDataProvider.JCO_USER, "COM_SCM");  //SAP用户名
           connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "822019");     //密码
           connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "ZH");        //登录语言


        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "5");  //最大连接数
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, "10");     //最大连接线程

        createDataFile(ABAP_AS_POOLED, "jcoDestination", connectProperties);
    }

    /**
     * 创建SAP接口属性文件。
     *
     * @param name       ABAP管道名称
     * @param suffix     属性文件后缀
     * @param properties 属性文件内容
     */
    private static void createDataFile(String name, String suffix, Properties properties) {
        File cfg = new File(name + "." + suffix);
        if (cfg.exists()) {
            cfg.deleteOnExit();
        }
        try {
            FileOutputStream fos = new FileOutputStream(cfg, false);
            properties.store(fos, "for tests only !");
            fos.close();
        } catch (Exception e) {
            log.error("Create Data file fault, error msg: " + e.toString());
            throw new RuntimeException("Unable to create the destination file " + cfg.getName(), e);
        }
    }

    /**
     * 获取SAP连接
     *
     * @return SAP连接对象
     */
    public static JCoDestination GetDestination() {
        JCoDestination destination = null;

        try {
            destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
        } catch (JCoException e) {
            log.error("Connect SAP fault, error msg: " + e.toString());

        }
        return destination;
    }

    /**
     * 从sap获取用户，并更新用户的数据
     * @return
     */
    public List<SdlBUser> GetUserList() {
        String fuName = "ZHR00_FM_YSDAXX";
        String nowDate= DateUtil.format((new Date()),"yyyy-MM-dd");
        log.info("获取用户 begin:"+ nowDate);
        List<SdlBUser> list = new ArrayList<>();
        JCoDestination destination;
        try {

            destination = RfcNoc.GetDestination();
            if (destination == null) {
                log.error("配置信息出错");
                return null;
            }

            // JCoRepository rfcrep = destination.getRepository();
            JCoFunction myfun = null;
            myfun = destination.getRepository().getFunction(fuName);

            //  myfun.SetValue("IS_SELCOND", "0");//SAP里面的传入参数
            if (myfun == null)
                log.info(fuName + " is null");

            myfun.getImportParameterList().setValue("DATUM", nowDate);

            //提前实例化一个空的表结构出来
            myfun.execute(destination);//执行
            log.info(fuName + "myfun.Invoke succeed");
            JCoTable rfcReturn = myfun.getTableParameterList().getTable("T_YSXX"); //此处返回类型为Structure 如果是Single类型 则直接调用myfun.GetString("RETURN");
            if (rfcReturn == null) {
                log.info("rfcReturn is null");
            }
            // log.info(String.format("rfcReturn.Count is %s", rfcReturn.getNumRows()));


            for (int i = 0; i < rfcReturn.getNumRows(); i++) {
                rfcReturn.setRow(i);

                SdlBUser user = new SdlBUser();


                user.setRylx((rfcReturn.getString("ZRYLXMS")).replace("0000-00-00",""));
                user.setUserAccount((rfcReturn.getString("PERNR")).replace("0000-00-00",""));
                user.setUserAccountName((rfcReturn.getString("NACHN")).replace("0000-00-00",""));
                user.setYggh((rfcReturn.getString("YGGH")).replace("0000-00-00",""));
                user.setGhHk((rfcReturn.getString("HKRSBH")).replace("0000-00-00",""));
                user.setSexName((rfcReturn.getString("GESCTXT")).replace("0000-00-00",""));
                user.setBirthday((rfcReturn.getString("GBDAT")).replace("0000-00-00",""));
                user.setYuangongzu((rfcReturn.getString("PTEXT")).replace("0000-00-00",""));
                user.setZizu((rfcReturn.getString("PERSK_TXT")).replace("0000-00-00",""));
                user.setRenshizifw((rfcReturn.getString("BTEXT")).replace("0000-00-00",""));
                user.setRenshizfenlei((rfcReturn.getString("ZHRBTTXT")).replace("0000-00-00",""));
                user.setIdCard((rfcReturn.getString("ICNUM")).replace("0000-00-00",""));
                user.setXingzhengjiebie((rfcReturn.getString("zhrzwtxtxt".toUpperCase())).replace("0000-00-00",""));
                user.setZhicheng((rfcReturn.getString("ZHRZCJBTXT")).replace("0000-00-00",""));
                user.setXrgwjb((rfcReturn.getString("TRFGR")).replace("0000-00-00",""));
                user.setXrgwjbprsj((rfcReturn.getString("zhrzykssj".toUpperCase())).replace("0000-00-00",""));
                user.setZyjsgwLc((rfcReturn.getString("ZHR_ZYZWTXT")).replace("0000-00-00",""));
                user.setAppointedDateLc((rfcReturn.getString("zhrzcsj".toUpperCase())).replace("0000-00-00",""));
                user.setBianhaoLc((rfcReturn.getString("zhrcyzkzh".toUpperCase())).replace("0000-00-00",""));
                user.setZyjsgw((rfcReturn.getString("ZHR_ZYZWTXT2")).replace("0000-00-00",""));
                user.setAppointedDate((rfcReturn.getString("zhrzcsj2".toUpperCase())).replace("0000-00-00",""));
                user.setBianhaoJx((rfcReturn.getString("zhrcyzkzh2".toUpperCase())).replace("0000-00-00",""));
                user.setZyjsNp((rfcReturn.getString("ZHR_ZYZWTXT3")).replace("0000-00-00",""));
                user.setZyjsDateNp((rfcReturn.getString("ZHRZCSJ3")).replace("0000-00-00",""));
                user.setZyjsNpjx((rfcReturn.getString("zhr_zyzwtxt4".toUpperCase())).replace("0000-00-00",""));
                user.setZyjsDateNpjx((rfcReturn.getString("ZHRZCSJ4")).replace("0000-00-00",""));
                user.setEdu((rfcReturn.getString("trfst".toUpperCase())).replace("0000-00-00",""));
                user.setEduSchool((rfcReturn.getString("INSTI2")).replace("0000-00-00",""));
                user.setTelephone((rfcReturn.getString("BGSTEL")).replace("0000-00-00",""));
                user.setGuoji((rfcReturn.getString("NATTX")).replace("0000-00-00",""));
                user.setJiguan((rfcReturn.getString("ZHRJG")).replace("0000-00-00",""));
                user.setMinzu((rfcReturn.getString("LTEXT")).replace("0000-00-00",""));
                user.setPoliticalStatus((rfcReturn.getString("PTEXT1")).replace("0000-00-00",""));
                user.setSchoolDate((rfcReturn.getString("DAT02")).replace("0000-00-00",""));
                user.setWorkDate((rfcReturn.getString("DAT01")).replace("0000-00-00",""));
                user.setYishiLb((rfcReturn.getString("ZHRYSLB")).replace("0000-00-00",""));
                user.setYishiJb((rfcReturn.getString("ZHRYSJB")).replace("0000-00-00",""));
                user.setYishiZhiyefanwei((rfcReturn.getString("ZHRZYFW")).replace("0000-00-00",""));
                user.setYishiZgzsbianhao((rfcReturn.getString("ZHRZGZS")).replace("0000-00-00",""));
                user.setYishiZiyebianhao((rfcReturn.getString("ZHRZYZS")).replace("0000-00-00",""));
                user.setPatentGood((rfcReturn.getString("KHXL")).replace("0000-00-00",""));
                user.setDocType((rfcReturn.getString("DOC_TYPE")).replace("0000-00-00",""));
                user.setDeptNew((rfcReturn.getString("DEPT_NEW")).replace("0000-00-00",""));
                user.setState(Convert.toInt(rfcReturn.getString("STAT2")));
                if(rfcReturn.getString("STAT2").equals("0")){
                    user.setDeptId("");
                    user.setDeptName("");
                    user.setBqName("");
                }



                list.add(user);
            }

            log.info("获取用户数据结束");
        } catch (Exception ex) {

            log.error(ex.getMessage());
        } finally {
            destination = null;
        }

        return list;
    }


    /**
     * 从sap获取用户，并更新用户的数据
     * @return
     */
    public List<SdlBUserMg> GetUserList2() {
        String fuName = "ZHR00_FM_YSDAXX2";
        String nowDate= DateUtil.format((new Date()),"yyyy-MM-dd");
        log.info("获取用户 begin:"+ nowDate);
        List<SdlBUserMg> list = new ArrayList<>();
        JCoDestination destination;
        try {

            destination = RfcNoc.GetDestination();
            if (destination == null) {
                log.error("配置信息出错");
                return null;
            }

            // JCoRepository rfcrep = destination.getRepository();
            JCoFunction myfun = null;
            myfun = destination.getRepository().getFunction(fuName);

            //  myfun.SetValue("IS_SELCOND", "0");//SAP里面的传入参数
            if (myfun == null)
                log.info(fuName + " is null");

            myfun.getImportParameterList().setValue("DATUM", nowDate);

            //提前实例化一个空的表结构出来
            myfun.execute(destination);//执行
            log.info(fuName + "myfun.Invoke succeed");
            JCoTable rfcReturn = myfun.getTableParameterList().getTable("T_YSXX"); //此处返回类型为Structure 如果是Single类型 则直接调用myfun.GetString("RETURN");
            if (rfcReturn == null) {
                log.info("rfcReturn is null");
            }
            // log.info(String.format("rfcReturn.Count is %s", rfcReturn.getNumRows()));


            for (int i = 0; i < rfcReturn.getNumRows(); i++) {
                rfcReturn.setRow(i);

                SdlBUserMg user = new SdlBUserMg();


                user.setRylx((rfcReturn.getString("ZRYLXMS")).replace("0000-00-00",""));
                user.setUserAccount((rfcReturn.getString("PERNR")).replace("0000-00-00",""));
                user.setUserAccountName((rfcReturn.getString("NACHN")).replace("0000-00-00",""));
                user.setYggh((rfcReturn.getString("YGGH")).replace("0000-00-00",""));
                user.setGhHk((rfcReturn.getString("HKRSBH")).replace("0000-00-00",""));
                user.setSexName((rfcReturn.getString("GESCTXT")).replace("0000-00-00",""));
                user.setBirthday((rfcReturn.getString("GBDAT")).replace("0000-00-00",""));
                user.setYuangongzu((rfcReturn.getString("PTEXT")).replace("0000-00-00",""));
                user.setZizu((rfcReturn.getString("PERSK_TXT")).replace("0000-00-00",""));
                user.setRenshizifw((rfcReturn.getString("BTEXT")).replace("0000-00-00",""));
                user.setRenshizfenlei((rfcReturn.getString("ZHRBTTXT")).replace("0000-00-00",""));
                user.setIdCard((rfcReturn.getString("ICNUM")).replace("0000-00-00",""));
                user.setXingzhengjiebie((rfcReturn.getString("zhrzwtxtxt".toUpperCase())).replace("0000-00-00",""));
                user.setZhicheng((rfcReturn.getString("ZHRZCJBTXT")).replace("0000-00-00",""));
                user.setXrgwjb((rfcReturn.getString("TRFGR")).replace("0000-00-00",""));
                user.setXrgwjbprsj((rfcReturn.getString("zhrzykssj".toUpperCase())).replace("0000-00-00",""));
                user.setZyjsgwLc((rfcReturn.getString("ZHR_ZYZWTXT")).replace("0000-00-00",""));
                user.setAppointedDateLc((rfcReturn.getString("zhrzcsj".toUpperCase())).replace("0000-00-00",""));
                user.setBianhaoLc((rfcReturn.getString("zhrcyzkzh".toUpperCase())).replace("0000-00-00",""));
                user.setZyjsgw((rfcReturn.getString("ZHR_ZYZWTXT2")).replace("0000-00-00",""));
                user.setAppointedDate((rfcReturn.getString("zhrzcsj2".toUpperCase())).replace("0000-00-00",""));
                user.setBianhaoJx((rfcReturn.getString("zhrcyzkzh2".toUpperCase())).replace("0000-00-00",""));
                user.setZyjsNp((rfcReturn.getString("ZHR_ZYZWTXT3")).replace("0000-00-00",""));
                user.setZyjsDateNp((rfcReturn.getString("ZHRZCSJ3")).replace("0000-00-00",""));
                user.setZyjsNpjx((rfcReturn.getString("zhr_zyzwtxt4".toUpperCase())).replace("0000-00-00",""));
                user.setZyjsDateNpjx((rfcReturn.getString("ZHRZCSJ4")).replace("0000-00-00",""));
                user.setEdu((rfcReturn.getString("trfst".toUpperCase())).replace("0000-00-00",""));
                user.setEduSchool((rfcReturn.getString("INSTI2")).replace("0000-00-00",""));
                user.setTelephone((rfcReturn.getString("BGSTEL")).replace("0000-00-00",""));
                user.setGuoji((rfcReturn.getString("NATTX")).replace("0000-00-00",""));
                user.setJiguan((rfcReturn.getString("ZHRJG")).replace("0000-00-00",""));
                user.setMinzu((rfcReturn.getString("LTEXT")).replace("0000-00-00",""));
                user.setPoliticalStatus((rfcReturn.getString("PTEXT1")).replace("0000-00-00",""));
                user.setSchoolDate((rfcReturn.getString("DAT02")).replace("0000-00-00",""));
                user.setWorkDate((rfcReturn.getString("DAT01")).replace("0000-00-00",""));
                user.setYishiLb((rfcReturn.getString("ZHRYSLB")).replace("0000-00-00",""));
                user.setYishiJb((rfcReturn.getString("ZHRYSJB")).replace("0000-00-00",""));
                user.setYishiZhiyefanwei((rfcReturn.getString("ZHRZYFW")).replace("0000-00-00",""));
                user.setYishiZgzsbianhao((rfcReturn.getString("ZHRZGZS")).replace("0000-00-00",""));
                user.setYishiZiyebianhao((rfcReturn.getString("ZHRZYZS")).replace("0000-00-00",""));
                user.setPatentGood((rfcReturn.getString("KHXL")).replace("0000-00-00",""));
                user.setDocType((rfcReturn.getString("DOC_TYPE")).replace("0000-00-00",""));
                user.setDeptNew((rfcReturn.getString("DEPT_NEW")).replace("0000-00-00",""));
                user.setState(Convert.toInt(rfcReturn.getString("STAT2")));
                user.setAuditDate(rfcReturn.getDate("DATLK"));
                if(rfcReturn.getString("STAT2").equals("0")){
                    user.setDeptId("");
                    user.setDeptName("");
                    user.setBqName("");
                }



                list.add(user);
            }

            log.info("获取用户数据结束");
        } catch (Exception ex) {

            log.error(ex.getMessage());
        } finally {
            destination = null;
        }

        return list;
    }


    
     
    public String SendInfo(String userAccount,String yslb,String ysjb,String zyfw,String zgbh,String zsbh) {
        String fuName = "ZHR00_FM_YSLZXX";
        log.info("传送人员证书编号等给sap");

        JCoDestination destination;
        try {
            destination = RfcNoc.GetDestination();
            if (destination == null) {
                log.error("配置信息出错");
                return  "SAP配置信息出错";
            }
            JCoRepository rfcrep = destination.getRepository();
            JCoFunction myfun = null;
            myfun = rfcrep.getFunction(fuName);
            //  myfun.SetValue("IS_SELCOND", "0");//SAP里面的传入参数
            if (myfun == null) {
                log.info("ZHR00_FM_YSLZXX is NULL");
                return "SAP配置信息出错2";
            }

            myfun.getImportParameterList().setValue("PERNR", userAccount);
            myfun.getImportParameterList().setValue("ZHRYSLB", yslb);
            myfun.getImportParameterList().setValue("ZHRYSJB", ysjb);
            myfun.getImportParameterList().setValue("ZHRZYFW", zyfw);
            myfun.getImportParameterList().setValue("ZHRZGZS", zgbh);
            myfun.getImportParameterList().setValue("ZHRZYZS", zsbh);

            // myfun.SetValue("IT_SUPLAN", IrfTable_IT_SUPLAN);
            //提前实例化一个空的表结构出来
            myfun.execute(destination);//执行
            log.info("测试到了这里");
           String message= myfun.getExportParameterList().getString("MES");
            return  message;
        } catch (Exception ex) {
            log.info("ZHR00_FM_YSLZXX出现问题：" + ex.getMessage(), 1);

            return  "调用ZHR00_FM_YSLZXX失败";

        } finally {
            destination = null;
        }
    }
    /**
    public static Boolean SendUploadInfo_RFC(String GysName, String matnr, String charge, String serverName, String I_Type)
    {
        log.info("SendUploadInfo_RFC(发送附件信息) begin");
        List<BackFromSAP_SubPlan> list = new ArrayList<>();
        JCoDestination destination;
        String fuName = "ZMM00_FM_SCM003";
        try
        {
            destination =RfcNoc.GetDestination();
            if(destination==null)
            {
                log.error("SAP 链接失败");
                return  false;
            }

            JCoRepository rfcrep = destination.getRepository();
            JCoFunction myfun = null;
            myfun = rfcrep.getFunction(fuName);
            //  myfun.SetValue("IS_SELCOND", "0");//SAP里面的传入参数
            if (myfun == null)
            {
                log.info("ZMM00_FM_SCM003 is NULL");
            }


            myfun.getImportParameterList().setValue("I_OBJECT", GysName.trim() + matnr.trim() + charge.trim());
            myfun.getImportParameterList().setValue("I_FILENAME", serverName);
            myfun.getImportParameterList().setValue("I_MODE", I_Type);
            //提前实例化一个空的表结构出来
            myfun.execute(destination);//执行


            log.info("上传文件，调用成功。");

            log.info("SendUploadInfo_RFC(发送附件信息) END SUCCESS!", 1);
            return  true;
        }
        catch (Exception ex)
        {
            log.error("SendUploadInfo_RFC(发送附件信息)出现问题：" + ex.getMessage());
            return  false;
        }
        finally
        {
            destination = null;
        }

    }

    public List<BackInfo> GetInfoList(String url) {
        String fuName = "ZHR00_FM_HSLIST";
        log.info("ZHR00_FM_HSLIST begin");
        List<BackInfo> list = new ArrayList<>();
        JCoDestination destination;
        try {

            destination = RfcNoc.GetDestination();
            if (destination == null) {
                log.error("配置信息出错");
                return null;
            }

            // JCoRepository rfcrep = destination.getRepository();
            JCoFunction myfun = null;
            myfun = destination.getRepository().getFunction(fuName);
            //  myfun.SetValue("IS_SELCOND", "0");//SAP里面的传入参数
            if (myfun == null)
                log.info(fuName + " is null");
//            JCoStructure IrfStruct = myfun.getImportParameterList().getStructure("IS_SELCOND");
//            if (IrfStruct == null)
//                log.info("IrfStruct is null");
//            IrfStruct.setValue("URL", url);


            JCoParameterList paramList = myfun.getImportParameterList();
            paramList.setValue("URL", url);

            //  myfun.setValue("IS_SELCOND", IrfStruct);
            //提前实例化一个空的表结构出来
            myfun.execute(destination);//执行
            log.info(fuName + "myfun.Invoke succeed");
            JCoTable rfcReturn = myfun.getTableParameterList().getTable("T_LIST"); //此处返回类型为Structure 如果是Single类型 则直接调用myfun.GetString("RETURN");
            if ((rfcReturn == null)
                log.info("rfcReturn is null");
            // log.info(String.format("rfcReturn.Count is %s", rfcReturn.getNumRows()));

            for (int i = 0; i < rfcReturn.getNumRows(); i++) {
                rfcReturn.setRow(i);

                BackInfo backInfo =new BackInfo();


                backInfo.setGESCH((rfcReturn.getString("GESCH"));
                backInfo.setSERNO((rfcReturn.getString("SERNO"));
                backInfo.setKS((rfcReturn.getString("KS"));
                backInfo.setNACHN((rfcReturn.getString("NACHN"));
                backInfo.setRYXZ((rfcReturn.getString("RYXZ"));

                list.add(backInfo);
            }
            log.info("list fill succeed ,GetPurcharseList end");
        } catch (Exception ex) {

            log.error(ex.getMessage());
        } finally {
            destination = null;
        }

        return list;
    }
     */
}

