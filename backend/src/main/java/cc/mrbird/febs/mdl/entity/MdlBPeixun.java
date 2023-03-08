package cc.mrbird.febs.mdl.entity;

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
 * @since 2023-01-05
 */

@Excel("mdl_b_peixun")
@Data
public class MdlBPeixun implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Long id;

    /**
     * 发薪号
     */
        
        @ExcelField(value ="发薪号")
    private String userAccount;

    /**
     * 排名
     */
        
        @ExcelField(value ="排名")
    private String paiming;

    /**
     * 医院
     */
        
        @ExcelField(value ="医院")
    private String hospital;

    /**
     * 院区
     */
        
        @ExcelField(value ="院区")
    private String yuanqu;

    /**
     * 快速搜索分类
     */
        
        @ExcelField(value ="快速搜索分类")
    private String ksssfl;

    /**
     * 临床科室分类
     */
        
        @ExcelField(value ="临床科室分类")
    private String lcksfl;

    /**
     * 单元
     */
        
        @ExcelField(value ="单元")
    private String danyuan;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 手机号码
     */
        
        @ExcelField(value ="手机号码")
    private String telphone;

    /**
     * 工号
     */
        
        @ExcelField(value ="工号")
    private String pernr;

    /**
     * 角色
     */
        
        @ExcelField(value ="角色")
    private String role;

    /**
     * 岗位
     */
        
        @ExcelField(value ="岗位")
    private String gangwei;

    /**
     * 岗位性质
     */
        
        @ExcelField(value ="岗位性质")
    private String hgangweixingzhi;

    /**
     * 开始考试时间
     */
        
        @ExcelField(value ="开始考试时间")
    private String starttime;

    /**
     * 结束考试时间
     */
        
        @ExcelField(value ="结束考试时间")
    private String endtime;

    /**
     * 考试时长
     */
        
        @ExcelField(value ="考试时长")
    private String kaoshishichang;

    /**
     * 考试成绩
     */
        
        @ExcelField(value ="考试成绩")
    private String score;

    /**
     * 考试次数
     */
        
        @ExcelField(value ="考试次数")
    private String cishu;

    /**
     * 是否合格
     */
        
        @ExcelField(value ="是否合格")
    private String ifhege;

    /**
     * 设备编码
     */
        
        @ExcelField(value ="设备编码")
    private String shebeibianhao;

    /**
     * 身份证
     */
        
        @ExcelField(value ="身份证")
    private String idcard;

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

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String PAIMING ="paiming" ;

    public static final String HOSPITAL ="hospital" ;

    public static final String YUANQU ="yuanqu" ;

    public static final String KSSSFL ="ksssfl" ;

    public static final String LCKSFL ="lcksfl" ;

    public static final String DANYUAN ="danyuan" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String TELPHONE ="telphone" ;

    public static final String PERNR ="pernr" ;

    public static final String ROLE ="role" ;

    public static final String GANGWEI ="gangwei" ;

    public static final String HGANGWEIXINGZHI ="hgangweixingzhi" ;

    public static final String STARTTIME ="starttime" ;

    public static final String ENDTIME ="endtime" ;

    public static final String KAOSHISHICHANG ="kaoshishichang" ;

    public static final String SCORE ="score" ;

    public static final String CISHU ="cishu" ;

    public static final String IFHEGE ="ifhege" ;

    public static final String SHEBEIBIANHAO ="shebeibianhao" ;

    public static final String IDCARD ="idcard" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }