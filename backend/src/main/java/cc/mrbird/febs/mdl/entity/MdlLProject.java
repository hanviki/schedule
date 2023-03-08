package cc.mrbird.febs.mdl.entity;

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
 * @since 2022-11-29
 */

@Excel("mdl_l_project")
@Data
public class MdlLProject implements Serializable{

private static final long serialVersionUID=1L;

                            
    private String id;

    /**
     * 父ID
     */
        
        @ExcelField(value ="父ID")
    private String baseId;

    /**
     * 实验室编码
     */
        
        @ExcelField(value ="实验室编码")
    private String labCode;

    /**
     * 实验室名称
     */
        
        @ExcelField(value ="实验室名称")
    private String labName;

    /**
     * 医嘱编码
     */
        
        @ExcelField(value ="医嘱编码")
    private String code;

    /**
     * 医嘱名称
     */
        
        @ExcelField(value ="医嘱名称")
    private String name;

    /**
     * 明细项目名称
     */
        
        @ExcelField(value ="明细项目名称")
    private String detailName;

    /**
     * 使用范围
     */
        
        @ExcelField(value ="使用范围")
    private String fanwei;

    /**
     * 明细项目编码
     */
        
        @ExcelField(value ="明细项目编码")
    private String detailCode;

    /**
     * 物价编号
     */
        
        @ExcelField(value ="物价编号")
    private String priceCode;

    /**
     * 金额
     */
        
        @ExcelField(value ="金额")
    private Double amount;

    /**
     * 是接入LIS
     */
        
        @ExcelField(value ="是接入LIS")
    private String isLis;

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

    public static final String BASE_ID ="base_id" ;

    public static final String LAB_CODE ="lab_code" ;

    public static final String LAB_NAME ="lab_name" ;

    public static final String CODE ="code" ;

    public static final String NAME ="name" ;

    public static final String DETAIL_NAME ="detail_name" ;

    public static final String FANWEI ="fanwei" ;

    public static final String DETAIL_CODE ="detail_code" ;

    public static final String PRICE_CODE ="price_code" ;

    public static final String AMOUNT ="amount" ;

    public static final String IS_LIS ="is_lis" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }