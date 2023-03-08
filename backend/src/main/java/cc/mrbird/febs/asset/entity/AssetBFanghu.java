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

@Excel("asset_b_fanghu")
@Data
public class AssetBFanghu implements Serializable{

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
     * 类别
     */
        
        @ExcelField(value ="类别")
    private String lb;

    /**
     * 用品及辅助防护设施
     */
        
        @ExcelField(value ="用品及辅助防护设施")
    private String ypfzfh;

    /**
     * 数量
     */
        
        @ExcelField(value ="数量")
    private Integer shuliang;

    /**
     * 铅当量
     */
        
        @ExcelField(value ="铅当量")
    private String qiandangliang;

    /**
     * 出场日期
     */
        
        @ExcelField(value ="出场日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date chuchangDate;
    private transient String chuchangDateFrom;
    private transient String chuchangDateTo;

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

    public static final String BASE_ID ="base_id" ;

    public static final String LB ="lb" ;

    public static final String YPFZFH ="ypfzfh" ;

    public static final String SHULIANG ="shuliang" ;

    public static final String QIANDANGLIANG ="qiandangliang" ;

    public static final String CHUCHANG_DATE ="chuchang_date" ;

    public static final String STATE ="state" ;

    public static final String NOTE ="note" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }