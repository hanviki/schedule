package cc.mrbird.febs.fs.entity;

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
 * @since 2022-07-12
 */

@Excel("fs_b_quarter_d")
@Data
public class FsBQuarterD implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                            

    private String id;

    /**
     * 年度
     */
        
        @ExcelField(value ="年度")
    private String year;

    /**
     * 季度
     */
        
        @ExcelField(value ="季度")
    private Integer quarter;

    /**
     * 计量牌编号
     */
        
        @ExcelField(value ="计量牌编号")
    private String number;

    /**
     * 操作
     */
        
        @ExcelField(value ="操作")
    private String operation;

    @TableField("user_no")
    private String userNo;

    private transient  String userAccount;

    private transient  String userAccountName;

    private transient  int count;
    /**
     * 状态
     */
        

    private Integer state;

    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")
        

    private Integer isDeletemark;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
        

    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 排序
     */
        

    private Integer displayIndex;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
        

    private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

    /**
     * 创建人
     */
    @TableField("CREATE_USER_ID")
        

    private Long createUserId;

    /**
     * 修改人
     */
    @TableField("MODIFY_USER_ID")
        

    private Long modifyUserId;



    public static final String ID ="id" ;

    public static final String YEAR ="year" ;

    public static final String QUARTER ="quarter" ;

    public static final String NUMBER ="number" ;

    public static final String OPERATION ="operation" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }