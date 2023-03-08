package cc.mrbird.febs.xf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2022-11-01
 */

@Excel("xf_b_person")
@Data
public class XfBPerson implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Long id;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 发薪号
     */
        
        @ExcelField(value ="发薪号")
    private String userAccount;

    /**
     * 科室
     */
        
        @ExcelField(value ="科室")
    private String deptNew;

    /**
     * 行风办审核结果
     */
        
        @ExcelField(value ="行风办审核结果")
    private String exiamResult;

    /**
     * 授权文件
     */
        
        @ExcelField(value ="授权文件")
    private Long archiveId;

    /**
     * 授权文件名称
     */
        
        @ExcelField(value ="授权文件名称")
    private String archiveName;

    /**
     * 授权文件编码
     */
        
        @ExcelField(value ="授权文件编码")
    private String archiveCode;

    /**
     * 附件ID
     */
        
        @ExcelField(value ="附件ID")
    private String fileId;

    /**
     * 附件地址
     */
        
        @ExcelField(value ="附件地址")
    private String fileUrl;

    /**
     * 日期
     */
        
        @ExcelField(value ="日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date powerDate;
    private transient String powerDateFrom;
    private transient String powerDateTo;

    /**
     * 备注
     */
        
        @ExcelField(value ="备注")
    private String remark;

    /**
     * 操作人
     */
        
        @ExcelField(value ="操作人")
    private String operationName;

    /**
     * 审核人
     */
        
        @ExcelField(value ="审核人")
    private String auditMan;

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



    public static final String ID ="id" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String DEPT_NEW ="dept_new" ;

    public static final String EXIAM_RESULT ="exiam_result" ;

    public static final String ARCHIVE_ID ="archive_id" ;

    public static final String ARCHIVE_NAME ="archive_name" ;

    public static final String ARCHIVE_CODE ="archive_code" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String POWER_DATE ="power_date" ;

    public static final String REMARK ="remark" ;

    public static final String OPERATION_NAME ="operation_name" ;

    public static final String AUDIT_MAN ="audit_man" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }