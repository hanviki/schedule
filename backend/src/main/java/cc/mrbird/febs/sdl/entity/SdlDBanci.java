package cc.mrbird.febs.sdl.entity;

import java.math.BigDecimal;
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
 * 系列名称
 * </p>
 *
 * @author viki
 * @since 2021-11-10
 */

@Excel("sdl_d_banci")
@Data
@Accessors(chain = true)
public class SdlDBanci implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Integer id;

    /**
     * 系列名称
     */
        
        @ExcelField(value ="系列名称")
    private String muduleName;

    /**
     * 列颜色
     */
        
        @ExcelField(value ="列颜色")
    private String colorName;

    /**
     * 次数
     */
        
        @ExcelField(value ="次数")
    private BigDecimal cishu;

    /**
     * 金额
     */
        
        @ExcelField(value ="金额")
    private String amount;

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
        
        @ExcelField(value ="创建时间", writeConverter = DateConverter.class)
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
        
        @ExcelField(value ="修改时间", writeConverter = DateConverter.class)
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
     * 父ID
     */
        
        @ExcelField(value ="父ID")
    private Integer parentId;

    /**
     * 排序字段
     */
        
        @ExcelField(value ="排序字段")
    private Integer displayIndex;

    /**
     * 是否需要显示的列
     */
    private transient String holiday;


    public static final String ID ="id" ;

    public static final String MUDULE_NAME ="mudule_name" ;

    public static final String COLOR_NAME ="color_name" ;

    public static final String CISHU ="cishu" ;

    public static final String AMOUNT ="amount" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

    public static final String PARENT_ID ="parent_id" ;

    public static final String DISPLAY_INDEX ="display_index" ;

        }