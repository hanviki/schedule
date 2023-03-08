package cc.mrbird.febs.asset.entity;

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

@Excel("asset_d_dictory")
@Data
public class AssetDDictory implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 类型ID
     */
                            
        @ExcelField(value ="类型ID")
    private String id;

    /**
     * 上级类型ID
     */
    @TableField("PARENT_ID")
        
        @ExcelField(value ="上级类型ID")
    private String parentId;

    /**
     * 类型名称
     */
    @TableField("NAME")
        
        @ExcelField(value ="类型名称")
    private String name;

    /**
     * 0是卫生1是环保2是个人防护用品3辅助防护用品
     */
        
        @ExcelField(value ="0是卫生1是环保2是个人防护用品3辅助防护用品")
    private Integer typeIndex;

    /**
     * 排序
     */
    @TableField("ORDER_NUM")
        
        @ExcelField(value ="排序")
    private Double orderNum;

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



    public static final String ID ="id" ;

    public static final String PARENT_ID ="PARENT_ID" ;

    public static final String NAME ="NAME" ;

    public static final String TYPE_INDEX ="type_index" ;

    public static final String ORDER_NUM ="ORDER_NUM" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

        }