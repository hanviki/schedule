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

@Excel("xf_b_meeting")
@Data
public class XfBMeeting implements Serializable{

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
     * 会议内容
     */
        
        @ExcelField(value ="会议内容")
    private String content;

    /**
     * 工作要点
     */
        
        @ExcelField(value ="工作要点")
    private String summary;

    /**
     * 发文内容简介
     */
        
        @ExcelField(value ="发文内容简介")
    private String brife;

    /**
     * 参与人
     */
        
        @ExcelField(value ="参与人")
    private String joinPerson;

    /**
     * 会议日期
     */
        
        @ExcelField(value ="会议日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date meDate;
    private transient String meDateFrom;
    private transient String meDateTo;

    /**
     * 医院发文日期
     */
        
        @ExcelField(value ="医院发文日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date sendDate;
    private transient String sendDateFrom;
    private transient String sendDateTo;

    /**
     * 政策文件附件
     */
        
        @ExcelField(value ="政策文件附件")
    private String fileId;

    /**
     * 政策文件附件
     */
        
        @ExcelField(value ="政策文件附件")
    private String fileUrl;

    /**
     * 医院下文文件附件ID
     */
        
        @ExcelField(value ="医院下文文件附件ID")
    private String fileId2;

    /**
     * 医院下文文件附件
     */
        
        @ExcelField(value ="医院下文文件附件")
    private String fileUrl2;

    /**
     * 备注
     */
        
        @ExcelField(value ="备注")
    private String remark;

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

    public static final String CONTENT ="content" ;

    public static final String SUMMARY ="summary" ;

    public static final String BRIFE ="brife" ;

    public static final String JOIN_PERSON ="join_person" ;

    public static final String ME_DATE ="me_date" ;

    public static final String SEND_DATE ="send_date" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String FILE_ID2 ="file_id2" ;

    public static final String FILE_URL2 ="file_url2" ;

    public static final String REMARK ="remark" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }