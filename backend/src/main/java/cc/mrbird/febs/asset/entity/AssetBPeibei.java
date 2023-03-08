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

@Excel("asset_b_peibei")
@Data
public class AssetBPeibei implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                            
        @ExcelField(value ="主键")
    private String id;

    /**
     * 科室名称
     */
        
        @ExcelField(value ="科室名称")
    private String deptName;

    /**
     * 科室ID
     */
        
        @ExcelField(value ="科室ID")
    private String deptId;

    /**
     * 设备名称
     */
        
        @ExcelField(value ="设备名称")
    private String assetName;

    /**
     * 生产厂家
     */
        
        @ExcelField(value ="生产厂家")
    private String changjia;

    /**
     * 设备型号
     */
        
        @ExcelField(value ="设备型号")
    private String xinghao;

    /**
     * 单位管理编号
     */
        
        @ExcelField(value ="单位管理编号")
    private String bianhao;

    @ExcelField(value ="设备类型")
    private String type;


    /**
     * 开始使用日期
     */
        
        @ExcelField(value ="开始使用日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date startDate;
    private transient String startDateFrom;
    private transient String startDateTo;

    /**
     * 结束使用日期
     */
        
        @ExcelField(value ="结束使用日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date endDate;
    private transient String endDateFrom;
    private transient String endDateTo;

    /**
     * 状态
     */
        
        @ExcelField(value ="状态")
    private Integer state;

    /**
     * 备注
     */
        
        @ExcelField(value ="备注")
    private String note;

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

    public static final String DEPT_NAME ="dept_name" ;

    public static final String DEPT_ID ="dept_id" ;

    public static final String ASSET_NAME ="asset_name" ;

    public static final String CHANGJIA ="changjia" ;

    public static final String XINGHAO ="xinghao" ;

    public static final String BIANHAO ="bianhao" ;

    public static final String START_DATE ="start_date" ;

    public static final String END_DATE ="end_date" ;

    public static final String STATE ="state" ;

    public static final String NOTE ="note" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }