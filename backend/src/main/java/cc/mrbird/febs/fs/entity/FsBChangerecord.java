package cc.mrbird.febs.fs.entity;

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
 * @since 2023-01-31
 */

@Excel("fs_b_changerecord")
@Data
public class FsBChangerecord implements Serializable{

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
     * 新科室
     */
        
        @ExcelField(value ="新科室")
    private String deptNew;

    /**
     * 原科室
     */
        
        @ExcelField(value ="原科室")
    private String deptOld;

    /**
     * 原岗位类别
     */
        
        @ExcelField(value ="原岗位类别")
    private String gwlbNew;

    /**
     * 新岗位类别
     */
        
        @ExcelField(value ="新岗位类别")
    private String gwlbOld;

    /**
     * 原核定计量牌个数
     */
        
        @ExcelField(value ="原核定计量牌个数")
    private String numberNew;

    /**
     * 新核定计量牌个数
     */
        
        @ExcelField(value ="新核定计量牌个数")
    private String numberOld;

    /**
     * 原监测状态
     */
        
        @ExcelField(value ="原监测状态",writeConverterExp = "0=开始检测,1=停止检测")
    private String stateNew;

    /**
     * 新监测状态
     */
        
        @ExcelField(value ="新监测状态",writeConverterExp = "0=开始检测,1=停止检测")
    private String stateOld;

    /**
     * 状态变更日期
     */
        
        @ExcelField(value ="状态变更日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date changeDate;
    private transient String changeDateFrom;
    private transient String changeDateTo;

    /**
     * 科室是否变化
     */
        
        @ExcelField(value ="科室是否变化")
    private String isDept;

    /**
     * 岗位类别是否变化
     */
        
        @ExcelField(value ="岗位类别是否变化")
    private String isGwlb;

    /**
     * 剂量牌个数是否变化
     */
        
        @ExcelField(value ="剂量牌个数是否变化")
    private String isNumber;

    /**
     * 检测状态是否变化
     */
        
        @ExcelField(value ="检测状态是否变化")
    private String isState;

    /**
     * 备注
     */
        
        @ExcelField(value ="备注")
    private String remark;

    @ExcelField(value ="提交人")
    private String submitUser;

    /**
     * 状态
     */
        
        @ExcelField(value ="状态",writeConverterExp = "0=未提交,1=已提交,2=审核未通过,3=已审核")
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

    public static final String DEPT_OLD ="dept_old" ;

    public static final String GWLB_NEW ="gwlb_new" ;

    public static final String GWLB_OLD ="gwlb_old" ;

    public static final String NUMBER_NEW ="number_new" ;

    public static final String NUMBER_OLD ="number_old" ;

    public static final String STATE_NEW ="state_new" ;

    public static final String STATE_OLD ="state_old" ;

    public static final String CHANGE_DATE ="change_date" ;

    public static final String IS_DEPT ="is_dept" ;

    public static final String IS_GWLB ="is_gwlb" ;

    public static final String IS_NUMBER ="is_number" ;

    public static final String IS_STATE ="is_state" ;

    public static final String REMARK ="remark" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }