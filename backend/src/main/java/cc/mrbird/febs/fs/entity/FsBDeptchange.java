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
 * @since 2022-07-08
 */

@Excel("fs_b_deptchange")
@Data
@Accessors(chain = true)
public class FsBDeptchange implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                            
        @ExcelField(value ="主键")
    private String id;

    /**
     * 排班员的发薪号
     */
        
        @ExcelField(value ="排班员的发薪号")
    private String userNo;

    /**
     * 排班员的姓名
     */
        
        @ExcelField(value ="排班员的姓名")
    private String name;

    /**
     * 排班员的科室
     */
        
        @ExcelField(value ="排班员的科室")
    private String userDept;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 人事编号
     */
        
        @ExcelField(value ="人事编号")
    private String userAccount;

    /**
     * 操作类型
     */
        
        @ExcelField(value ="操作类型")
    private String operationName;

    /**
     * 科室ID
     */
        
        @ExcelField(value ="科室ID")
    private String deptId;

    /**
     * 科室新ID
     */
        
        @ExcelField(value ="科室新ID")
    private String deptNewId;

    /**
     * 原科室名称
     */
        
        @ExcelField(value ="原科室名称")
    private String deptName;

    /**
     * 当前科室名称
     */
        
        @ExcelField(value ="当前科室名称")
    private String deptNewName;

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

    public static final String USER_NO ="user_no" ;

    public static final String NAME ="name" ;

    public static final String USER_DEPT ="user_dept" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String OPERATION_NAME ="operation_name" ;

    public static final String DEPT_ID ="dept_id" ;

    public static final String DEPT_NEW_ID ="dept_new_id" ;

    public static final String DEPT_NAME ="dept_name" ;

    public static final String DEPT_NEW_NAME ="dept_new_name" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }