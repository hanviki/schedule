package cc.mrbird.febs.fs.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import java.util.List;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import cc.mrbird.febs.common.converter.*;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author viki
 * @since 2022-07-06
 */

@Excel("view_fs")
@Data
@Accessors(chain = true)
public class ViewFs implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
        
        @ExcelField(value ="主键")
    private String id;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 人事编号
     */
        
        @ExcelField(value ="人事编号")
    private String userAccount;

    /**
     * 病区名称
     */
        
        @ExcelField(value ="病区名称")
    private String bqName;

    /**
     * 资质类型id
     */
        
        @ExcelField(value ="资质类型id")
    private Integer userType;

    /**
     * 资质类型
     */
        
        @ExcelField(value ="资质类型")
    private String userTypeName;

    /**
     * 手机号
     */
        
        @ExcelField(value ="手机号")
    private String telephone;

    /**
     * 现岗位职务
     */
        
        @ExcelField(value ="现岗位职务")
    private String positionName;

    /**
     * 拟聘岗位职务
     */
        
        @ExcelField(value ="拟聘岗位职务")
    private String npPositionName;

    /**
     * 人员类型
     */
        
        @ExcelField(value ="人员类型")
    private String rylx;

    /**
     * 员工工号
     */
        
        @ExcelField(value ="员工工号")
    private String yggh;

    /**
     * 华科人事编号
     */
        
        @ExcelField(value ="华科人事编号")
    private String ghHk;

    /**
     * 性别
     */
        
        @ExcelField(value ="性别")
    private String sexName;

    /**
     * 资质证号
     */
        
        @ExcelField(value ="资质证号")
    private String qualifyNo;

    /**
     * 出生年月
     */
        
        @ExcelField(value ="出生年月")
    private String birthday;

    /**
     * 来校工作时间
     */
        
        @ExcelField(value ="来校工作时间")
    private String schoolDate;

    /**
     * 教学职称
     */
        
        @ExcelField(value ="教学职称")
    private String zyjsgw;

    /**
     * 教学证书编号
     */
        
        @ExcelField(value ="教学证书编号")
    private String bianhaoJx;

    /**
     * 临床职称
     */
        
        @ExcelField(value ="临床职称")
    private String zyjsgwLc;

    /**
     * 现从事专业及专长
     */
        
        @ExcelField(value ="现从事专业及专长")
    private String xcszyjzc;

    /**
     * 聘任时间
     */
        
        @ExcelField(value ="聘任时间")
    private String appointedDate;

    /**
     * 聘任时间（临床）
     */
        
        @ExcelField(value ="聘任时间（临床）")
    private String appointedDateLc;

    /**
     * 员工子组
     */
        
        @ExcelField(value ="员工子组")
    private String zizu;

    /**
     * 员工组
     */
        
        @ExcelField(value ="员工组")
    private String yuangongzu;

    /**
     * 现任岗位级别
     */
        
        @ExcelField(value ="现任岗位级别")
    private String xrgwjb;

    /**
     * 现任岗位级别聘任时间
     */
        
        @ExcelField(value ="现任岗位级别聘任时间")
    private String xrgwjbprsj;

    /**
     * 职称
     */
        
        @ExcelField(value ="职称")
    private String zhicheng;

    /**
     * 内聘临床专业技术职务
     */
        
        @ExcelField(value ="内聘临床专业技术职务")
    private String zyjsNp;

    /**
     * 内聘临床时间
     */
        
        @ExcelField(value ="内聘临床时间")
    private String zyjsDateNp;

    /**
     * 内聘教学专业技术职务
     */
        
        @ExcelField(value ="内聘教学专业技术职务")
    private String zyjsNpjx;

    /**
     * 内聘教学时间
     */
        
        @ExcelField(value ="内聘教学时间")
    private String zyjsDateNpjx;

    /**
     * 学历
     */
        
        @ExcelField(value ="学历")
    private String edu;

    /**
     * 毕业学校
     */
        
        @ExcelField(value ="毕业学校")
    private String eduSchool;

    /**
     * 国籍
     */
        
        @ExcelField(value ="国籍")
    private String guoji;

    /**
     * 民族
     */
        
        @ExcelField(value ="民族")
    private String minzu;

    /**
     * 籍贯
     */
        
        @ExcelField(value ="籍贯")
    private String jiguan;

    /**
     * 政治面貌
     */
        
        @ExcelField(value ="政治面貌")
    private String politicalStatus;

    /**
     * 临床证书编号
     */
        
        @ExcelField(value ="临床证书编号")
    private String bianhaoLc;

    /**
     * 行政级别
     */
        
        @ExcelField(value ="行政级别")
    private String xingzhengjiebie;

    /**
     * 身份证号
     */
        
        @ExcelField(value ="身份证号")
    private String idCard;

    /**
     * 参加工作时间
     */
        
        @ExcelField(value ="参加工作时间")
    private String workDate;

    /**
     * 医师类别
     */
        
        @ExcelField(value ="医师类别")
    private String yishiLb;

    /**
     * 医师级别
     */
        
        @ExcelField(value ="医师级别")
    private String yishiJb;

    /**
     * 医师执业范围
     */
        
        @ExcelField(value ="医师执业范围")
    private String yishiZhiyefanwei;

    /**
     * 医师资格证书编号
     */
        
        @ExcelField(value ="医师资格证书编号")
    private String yishiZgzsbianhao;

    /**
     * 医师执业证书编码
     */
        
        @ExcelField(value ="医师执业证书编码")
    private String yishiZiyebianhao;

    /**
     * 人事子范围
     */
        
        @ExcelField(value ="人事子范围")
    private String renshizifw;

    /**
     * 人事子范围分类
     */
        
        @ExcelField(value ="人事子范围分类")
    private String renshizfenlei;

    /**
     * 是否授权
     */
        
        @ExcelField(value ="是否授权")
    private String isAuthority;

    /**
     * 是否用于本次评审
     */
    @TableField("IsUse")
        
        @ExcelField(value ="是否用于本次评审")
    private Boolean IsUse;

    /**
     * 医技
     */
        
        @ExcelField(value ="医技")
    private String patentGood;

    /**
     * 本人排名
     */
        
        @ExcelField(value ="本人排名")
    private Integer patentRanknum;

    /**
     * 状态
     */
        
        @ExcelField(value ="状态", writeConverterExp = "2=退休,0=离职,3=在职",readConverterExp = "2=退休,0=离职,3=在职")
    private String state;

    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")
        
        @ExcelField(value ="是否删除")
    private Integer isDeletemark;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
        
        @ExcelField(value ="创建时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 排序
     */
        
        @ExcelField(value ="排序")
    private Integer displayIndex;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
        
        @ExcelField(value ="修改时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

    /**
     * 创建人
     */
    @TableField("CREATE_USER_ID")
        
        @ExcelField(value ="创建人")
    private Long createUserId;

    /**
     * 修改人
     */
    @TableField("MODIFY_USER_ID")
        
        @ExcelField(value ="修改人")
    private Long modifyUserId;

    /**
     * 审核人
     */
    @TableField("auditMan")
        
        @ExcelField(value ="审核人")
    private String auditMan;

    /**
     * neipinqingkuang
     */
    @TableField("auditManName")
        
        @ExcelField(value ="neipinqingkuang")
    private String auditManName;

    /**
     * 审核时间
     */
    @TableField("auditDate")
        
        @ExcelField(value ="审核时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date auditDate;
    private transient String auditDateFrom;
    private transient String auditDateTo;

    /**
     * 审核意见
     */
    @TableField("auditSuggestion")
        
        @ExcelField(value ="审核意见")
    private String auditSuggestion;

    /**
     * 附件（临床）
     */
        
        @ExcelField(value ="附件（临床）")
    private String fileIdLc;

    /**
     * 附件地址（临床）
     */
        
        @ExcelField(value ="附件地址（临床）")
    private String fileUrlLc;

    /**
     * 自定义科室
     */
        
        @ExcelField(value ="自定义科室")
    private String deptNew;

    /**
     * 临床或者医技
     */
        
        @ExcelField(value ="临床或者医技")
    private String docType;

    /**
     * 通过初级考核时间
     */
        
        @ExcelField(value ="通过初级考核时间")
    private String chujikhDate;

    /**
     * 是否通过中级考核
     */
        
        @ExcelField(value ="是否通过中级考核")
    private Boolean isZhongjikh;

    /**
     * 通过中级考核时间
     */
        
        @ExcelField(value ="通过中级考核时间")
    private String zhongjikhDate;

    /**
     * 职位时间
     */
        
        @ExcelField(value ="职位时间")
    private String staffDate;

    /**
     * 照片id
     */
        
        @ExcelField(value ="照片id")
    private String pictureId;

    /**
     * 照片url
     */
        
        @ExcelField(value ="照片url")
    private String pictureUrl;

    /**
     * 岗前培训情况
     */
        
        @ExcelField(value ="岗前培训情况")
    private String gqpxqk;

    /**
     * 规范化医师培训情况
     */
        
        @ExcelField(value ="规范化医师培训情况")
    private String gfhyspxqk;

    /**
     * 中级水平能力测试情况
     */
        
        @ExcelField(value ="中级水平能力测试情况")
    private String zjspnlceqk;

    /**
     * 年度
     */
        
        @ExcelField(value ="年度")
    private String dcaYear;

    /**
     * 个人剂量计编号
     */
        
        @ExcelField(value ="个人剂量计编号")
    private String grjlbh;

    /**
     * 放射工作人员证职业类别
     */
        
        @ExcelField(value ="放射工作人员证职业类别")
    private String zylb;

    /**
     * 岗位类别
     */
        
        @ExcelField(value ="岗位类别")
    private String gwlb;

    /**
     * 核定计量牌个数
     */
        
        @ExcelField(value ="核定计量牌个数")
    private Integer hdjlpgs;

    /**
     * 放射工龄
     */
        
        @ExcelField(value ="放射工龄")
    private BigDecimal fsgl;

    /**
     * 开始从事放射工作时间
     */
        
        @ExcelField(value ="开始从事放射工作时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date fsDate;
    private transient String fsDateFrom;
    private transient String fsDateTo;

    /**
     * 开始从事放射工作时间
     */
        
        @ExcelField(value ="开始从事放射工作时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date jcksDate;
    private transient String jcksDateFrom;
    private transient String jcksDateTo;

    /**
     * 放射工作人员证号码
     */
        
        @ExcelField(value ="放射工作人员证号码")
    private String fsgzzh;

    /**
     * 办证时间
     */
        
        @ExcelField(value ="办证时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date bzDate;
    private transient String bzDateFrom;
    private transient String bzDateTo;

    /**
     * 科室ID
     */

    @ExcelField(value ="科室ID")
    private String deptId;
    /**
     * 科室名称
     */
        
        @ExcelField(value ="科室名称")
    private String deptName;

    @ExcelField(value ="婚姻状况")
        private String married;
    /**
     * 附件
     */
        
        @ExcelField(value ="附件")
    private String fileId;

    /**
     * 附件地址
     */
        
        @ExcelField(value ="附件地址")
    private String fileUrl;

    @ExcelField(value="监控状态" , writeConverterExp = "0=开始监测,1=暂停监测,-1= ")
        private transient  String jcState="-1";

    /**
     * 考试类型
     */

    @ExcelField(value ="考试类型")
    private String exameType;

    /**
     * 考试时间
     */

    @ExcelField(value ="考试时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date exameDate;

    private String valid;

    public String getValid(){
        if(exameDate==null) return  "";
        if(StringUtils.isNotEmpty(exameType) && exameDate!=null) {
            if(exameType.equals("卫生考试")) {
                return DateUtil.format(DateUtil.offsetMonth(exameDate, 12 * 2), "yyyy-MM-dd");
            }
            if(exameType.equals("环保考试")) {
                return DateUtil.format(DateUtil.offsetMonth(exameDate, 12 * 5), "yyyy-MM-dd");
            }
            return  "";
        }
        else{
            return  "";
        }
    }

    @ExcelField(value ="结论")
    private   String isJl2;
    @ExcelField(value ="环保考试")
    private   String isHB5;
    @ExcelField(value ="卫生考试")
    private   String isTj2;

    @ExcelField(value ="日期", writeConverter = DateConverter.class, readConverter = DateReadConverter2.class)
    private Date rq;
    private transient String rqFrom;
    private transient String rqTo;



    @ExcelField(value ="是否大剂量")
    private String   isBig;

    private String healthValid;
    public String getHealthValid(){
        if(rq==null) return  "";
        if(StringUtils.isNotEmpty(isBig) && isBig.equals("是")) {
            return DateUtil.format(DateUtil.offsetMonth(rq, 12 * 1), "yyyy-MM-dd");
        }
        else{
            return DateUtil.format(DateUtil.offsetMonth(rq, 12 * 2), "yyyy-MM-dd");
        }
    }

    @ExcelField(value ="体检类型")
    private String tjType;

    public String getTjType(){

        if(auditDate!=null && !state.equals("3")) return "离岗";
        if(rq==null){
            return  "岗前";
        }
        else{
            return  "在岗";
        }
    }

    @ExcelField(value ="院区")
    private String yuanqu;

    @ExcelField(value ="岗位类别修改原因")
    private String note;

    public static final String ID ="id" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String BQ_NAME ="bq_name" ;

    public static final String USER_TYPE ="user_type" ;

    public static final String USER_TYPE_NAME ="user_type_name" ;

    public static final String TELEPHONE ="telephone" ;

    public static final String POSITION_NAME ="position_name" ;

    public static final String NP_POSITION_NAME ="np_position_name" ;

    public static final String RYLX ="rylx" ;

    public static final String YGGH ="yggh" ;

    public static final String GH_HK ="gh_hk" ;

    public static final String SEX_NAME ="sex_name" ;

    public static final String QUALIFY_NO ="qualify_no" ;

    public static final String BIRTHDAY ="birthday" ;

    public static final String SCHOOL_DATE ="school_date" ;

    public static final String ZYJSGW ="zyjsgw" ;

    public static final String BIANHAO_JX ="bianhao_jx" ;

    public static final String ZYJSGW_LC ="zyjsgw_lc" ;

    public static final String XCSZYJZC ="xcszyjzc" ;

    public static final String APPOINTED_DATE ="appointed_date" ;

    public static final String APPOINTED_DATE_LC ="appointed_date_lc" ;

    public static final String ZIZU ="zizu" ;

    public static final String YUANGONGZU ="yuangongzu" ;

    public static final String XRGWJB ="xrgwjb" ;

    public static final String XRGWJBPRSJ ="xrgwjbprsj" ;

    public static final String ZHICHENG ="zhicheng" ;

    public static final String ZYJS_NP ="zyjs_np" ;

    public static final String ZYJS_DATE_NP ="zyjs_date_np" ;

    public static final String ZYJS_NPJX ="zyjs_npjx" ;

    public static final String ZYJS_DATE_NPJX ="zyjs_date_npjx" ;

    public static final String EDU ="edu" ;

    public static final String EDU_SCHOOL ="edu_school" ;

    public static final String GUOJI ="guoji" ;

    public static final String MINZU ="minzu" ;

    public static final String JIGUAN ="jiguan" ;

    public static final String POLITICAL_STATUS ="political_status" ;

    public static final String BIANHAO_LC ="bianhao_lc" ;

    public static final String XINGZHENGJIEBIE ="xingzhengjiebie" ;

    public static final String ID_CARD ="id_card" ;

    public static final String WORK_DATE ="work_date" ;

    public static final String YISHI_LB ="yishi_lb" ;

    public static final String YISHI_JB ="yishi_jb" ;

    public static final String YISHI_ZHIYEFANWEI ="yishi_zhiyefanwei" ;

    public static final String YISHI_ZGZSBIANHAO ="yishi_zgzsbianhao" ;

    public static final String YISHI_ZIYEBIANHAO ="yishi_ziyebianhao" ;

    public static final String RENSHIZIFW ="renshizifw" ;

    public static final String RENSHIZFENLEI ="renshizfenlei" ;

    public static final String IS_AUTHORITY ="is_authority" ;

    public static final String ISUSE ="IsUse" ;

    public static final String PATENT_GOOD ="patent_good" ;

    public static final String PATENT_RANKNUM ="patent_ranknum" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String AUDITMAN ="auditMan" ;

    public static final String AUDITMANNAME ="auditManName" ;

    public static final String AUDITDATE ="auditDate" ;

    public static final String AUDITSUGGESTION ="auditSuggestion" ;

    public static final String FILE_ID_LC ="file_id_lc" ;

    public static final String FILE_URL_LC ="file_url_lc" ;

    public static final String DEPT_NEW ="dept_new" ;

    public static final String DOC_TYPE ="doc_type" ;

    public static final String CHUJIKH_DATE ="chujikh_date" ;

    public static final String IS_ZHONGJIKH ="is_zhongjikh" ;

    public static final String ZHONGJIKH_DATE ="zhongjikh_date" ;

    public static final String STAFF_DATE ="staff_date" ;

    public static final String PICTURE_ID ="picture_id" ;

    public static final String PICTURE_URL ="picture_url" ;

    public static final String GQPXQK ="gqpxqk" ;

    public static final String GFHYSPXQK ="gfhyspxqk" ;

    public static final String ZJSPNLCEQK ="zjspnlceqk" ;

    public static final String DCA_YEAR ="dca_year" ;

    public static final String GRJLBH ="grjlbh" ;

    public static final String ZYLB ="zylb" ;

    public static final String GWLB ="gwlb" ;

    public static final String HDJLPGS ="hdjlpgs" ;

    public static final String FSGL ="fsgl" ;

    public static final String FS_DATE ="fs_date" ;

    public static final String JCKS_DATE ="jcks_date" ;

    public static final String FSGZZH ="fsgzzh" ;

    public static final String BZ_DATE ="bz_date" ;

    public static final String DEPT_NAME ="dept_name" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

        }