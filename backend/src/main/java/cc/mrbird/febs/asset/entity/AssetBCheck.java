package cc.mrbird.febs.asset.entity;

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
 * @since 2023-02-06
 */

@Excel("asset_b_check")
@Data
public class AssetBCheck implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                            
        @ExcelField(value ="主键")
    private String id;

    /**
     * 父Id
     */
        
        @ExcelField(value ="父Id")
    private Long baseId;

    /**
     * 监测类型
     */
        
        @ExcelField(value ="监测类型")
    private String checkType;

    /**
     * 监测编号
     */
        
        @ExcelField(value ="监测编号")
    private String checkNo;

    /**
     * 监测时间
     */
        
        @ExcelField(value ="监测时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date checkDate;
    private transient String checkDateFrom;
    private transient String checkDateTo;

    /**
     * 监测结论
     */
        
        @ExcelField(value ="监测结论")
    private String checkResult;

    /**
     * 状态
     */
        
        @ExcelField(value ="状态")
    private Integer state;

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
     * 备注
     */
        
        @ExcelField(value ="备注")
    private String note;

    /**
     * 涉及的场所
     */
        
        @ExcelField(value ="涉及的场所")
    private String checkCs;

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



    public static final String ID ="id" ;

    public static final String BASE_ID ="base_id" ;

    public static final String CHECK_TYPE ="check_type" ;

    public static final String CHECK_NO ="check_no" ;

    public static final String CHECK_DATE ="check_date" ;

    public static final String CHECK_RESULT ="check_result" ;

    public static final String STATE ="state" ;

    public static final String NOTE ="note" ;

    public static final String CHECK_CS ="check_cs" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }