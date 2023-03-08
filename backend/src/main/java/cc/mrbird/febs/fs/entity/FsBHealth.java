package cc.mrbird.febs.fs.entity;

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
 * @since 2022-07-13
 */

@Excel("fs_b_health")
@Data
public class FsBHealth implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                            

    private String id;



    /**
     * 发薪号
     */

    private String userAccount;

    /**
     * 是否复检
     */
        
        @ExcelField(value ="是否复检")
    private String isFj;


    /**
     * 初检号
     */

    @ExcelField(value ="初检号")
    private String frCode;
    /**
     * 体检号
     */
        
        @ExcelField(value ="体检号")
    private String tjCode;

    /**
     * 工号
     */
        
        @ExcelField(value ="工号")
    private String pernr;


    /**
     * 姓名
     */

    @ExcelField(value ="姓名")
    private String userAccountName;
    /**
     * 性别
     */
        
        @ExcelField(value ="性别")
    private String sexy;

    /**
     * 年龄
     */
        
        @ExcelField(value ="年龄")
    private String age;

    /**
     * 出生日期
     */
        
        @ExcelField(value ="出生日期")
    private String birthday;

    /**
     * 婚姻状况
     */
        
        @ExcelField(value ="婚姻状况")
    private String married;

    /**
     * 单位
     */
        
        @ExcelField(value ="单位")
    private String deptUnit;

    /**
     * 身份证
     */
        
        @ExcelField(value ="身份证号")
    private String idCard;

    /**
     * 危害因素
     */
        
        @ExcelField(value ="危害因素")
    private String whys;

    /**
     * 接害工龄(年)
     */
        
        @ExcelField(value ="接害工龄(年)")
    private String jhglYear;

    /**
     * 接害工龄(月)
     */
        
        @ExcelField(value ="接害工龄(月)")
    private String jhglMonth;

    /**
     * 工种
     */
        
        @ExcelField(value ="工种")
    private String gz;

    /**
     * 部门
     */
        
        @ExcelField(value ="部门")
    private String deparment;

    /**
     * 体检套餐
     */
        
        @ExcelField(value ="体检套餐")
    private String tjtc;

    /**
     * 日期
     */
        
        @ExcelField(value ="日期", writeConverter = DateConverter.class, readConverter = DateReadConverter2.class)
    private Date rq;
    private transient String rqFrom;
    private transient String rqTo;

    /**
     * 体检类别
     */
        
        @ExcelField(value ="体检类别")
    private String tjlb;

    /**
     * 结果
     */
        
        @ExcelField(value ="结果")
    private String result;

    /**
     * 结论
     */
        
        @ExcelField(value ="结论")
    private String jl;

    /**
     * 体检单位
     */

    @ExcelField(value ="体检单位")
    private String jcUnit;

    /**
     * 晶状体
     */

    @ExcelField(value ="晶状体")
    private String jcJzt;

    /**
     * WBC
     */

    @ExcelField(value ="WBC")
    private String jcWbc;

    /**
     * PLT
     */

    @ExcelField(value ="PLT")
    private String jcPlt;

    /**
     * 倒位率
     */

    @ExcelField(value ="倒位率")
    private String jcDwl;

    /**
     * 相互易位率
     */

    @ExcelField(value ="相互易位率")
    private String jcXhywl;

    /**
     * 双着丝粒染色体率
     */

    @ExcelField(value ="双着丝粒染色体率")
    private String jcSzssrstl;

    /**
     * 着丝粒环率
     */

    @ExcelField(value ="着丝粒环率")
    private String jcZssl;

    /**
     * 染色体畸变细胞率
     */

    @ExcelField(value ="染色体畸变细胞率")
    private String jcRstqbxbl;

    /**
     * 染色体畸变率
     */

    @ExcelField(value ="染色体畸变率")
    private String jcRstjbl;

    /**
     * 淋巴细胞微核细胞率
     */

    @ExcelField(value ="淋巴细胞微核细胞率")
    private String jcLbxbwhxpl;

    /**
     * 淋巴细胞微核率
     */

    @ExcelField(value ="淋巴细胞微核率")
    private String jcLbxbwhl;

    /**
     * 处理意见
     */
        
        @ExcelField(value ="处理意见")
    private String suggestion;

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


    private transient String deptId;

    private transient String yuanqu;


    public static final String ID ="id" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String IS_FJ ="is_fj" ;

    public static final String TJ_CODE ="tj_code" ;

    public static final String PERNR ="pernr" ;

    public static final String SEXY ="sexy" ;

    public static final String AGE ="age" ;

    public static final String BIRTHDAY ="birthday" ;

    public static final String MARRIED ="married" ;

    public static final String DEPT_UNIT ="dept_unit" ;

    public static final String ID_CARD ="id_card" ;

    public static final String WHYS ="whys" ;

    public static final String JHGL_YEAR ="jhgl_year" ;

    public static final String JHGL_MONTH ="jhgl_month" ;

    public static final String GZ ="gz" ;

    public static final String DEPARMENT ="deparment" ;

    public static final String TJTC ="tjtc" ;

    public static final String RQ ="rq" ;

    public static final String TJLB ="tjlb" ;

    public static final String RESULT ="result" ;

    public static final String JL ="jl" ;

    public static final String SUGGESTION ="suggestion" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }