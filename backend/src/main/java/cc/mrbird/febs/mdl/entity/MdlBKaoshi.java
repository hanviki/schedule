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

@Excel("mdl_b_kaoshi")
@Data
public class MdlBKaoshi implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Long id;

    /**
     * 发薪号
     */
        
        @ExcelField(value ="发薪号")
    private String userAccount;

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
    private String gangweixingzhi;

    /**
     * 首次阅读时间
     */
        
        @ExcelField(value ="首次阅读时间")
    private String firstyuedu;

    /**
     * 最后一次阅读时间
     */
        
        @ExcelField(value ="最后一次阅读时间")
    private String lastyuedu;

    /**
     * 累计阅读时长
     */
        
        @ExcelField(value ="累计阅读时长")
    private String leijiyuedu;

    /**
     * 完成率
     */
        
        @ExcelField(value ="完成率")
    private String wanchenglv;

    /**
     * 练习次数
     */
        
        @ExcelField(value ="练习次数")
    private String lxcs;

    /**
     * 已获得学分
     */
        
        @ExcelField(value ="已获得学分")
    private String yihuoxuefen;

    /**
     * 是否计划内
     */
        
        @ExcelField(value ="是否计划内")
    private String ifjihuannei;

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

    public static final String GANGWEIXINGZHI ="gangweixingzhi" ;

    public static final String FIRSTYUEDU ="firstyuedu" ;

    public static final String LASTYUEDU ="lastyuedu" ;

    public static final String LEIJIYUEDU ="leijiyuedu" ;

    public static final String WANCHENGLV ="wanchenglv" ;

    public static final String LXCS ="lxcs" ;

    public static final String YIHUOXUEFEN ="yihuoxuefen" ;

    public static final String IFJIHUANNEI ="ifjihuannei" ;

    public static final String IDCARD ="idcard" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }