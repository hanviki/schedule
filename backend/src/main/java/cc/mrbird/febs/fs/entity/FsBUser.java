package cc.mrbird.febs.fs.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import cc.mrbird.febs.common.converter.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author viki
 * @since 2022-07-06
 */

@Excel("fs_b_user")
@Data
@Accessors(chain = true)
public class FsBUser implements Serializable{

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

    @ExcelField(value ="院区")
    private String yuanqu;

    @ExcelField(value ="岗位类别修改原因")
    private String note;
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

    /**
     * 状态
     */
        
        @ExcelField(value ="状态")
    private Integer state;

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

    @ExcelField(value="监控状态" , writeConverterExp = "0=开始监测,1=暂停监测,-1= ")
    private transient  String jcState="";



    public static final String ID ="id" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

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

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }