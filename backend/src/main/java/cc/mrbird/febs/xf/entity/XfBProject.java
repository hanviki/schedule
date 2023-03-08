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

@Excel("xf_b_project")
@Data
public class XfBProject implements Serializable{

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
     * 专项治理名称
     */
        
        @ExcelField(value ="专项治理名称")
    private String name;

    /**
     * 类型
     */
        
        @ExcelField(value ="类型")
    private String type;

    /**
     * 回复单位
     */
        
        @ExcelField(value ="回复单位")
    private String unit;

    /**
     * 要求时间
     */
        
        @ExcelField(value ="要求时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date pojectDate;
    private transient String pojectDateFrom;
    private transient String pojectDateTo;

    /**
     * 回复时间
     */
        
        @ExcelField(value ="回复时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date backDate;
    private transient String backDateFrom;
    private transient String backDateTo;

    /**
     * 附件ID
     */
        
        @ExcelField(value ="附件ID")
    private String fileId;

    /**
     * 附件
     */
        
        @ExcelField(value ="附件")
    private String fileUrl;

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

    public static final String NAME ="name" ;

    public static final String TYPE ="type" ;

    public static final String UNIT ="unit" ;

    public static final String POJECT_DATE ="poject_date" ;

    public static final String BACK_DATE ="back_date" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String REMARK ="remark" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }