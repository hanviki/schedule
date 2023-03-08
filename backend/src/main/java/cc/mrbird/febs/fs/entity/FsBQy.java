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
import org.apache.commons.lang3.StringUtils;

import static jdk.nashorn.internal.objects.NativeMath.round;

/**
 * <p>
 * 
 * </p>
 *
 * @author viki
 * @since 2022-07-13
 */

@Excel("fs_b_qy")
@Data
public class FsBQy implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                            

    private String id;
    /**
     * 计量编号
     */

    @ExcelField(value ="计量编号")
    private String number;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 编号
     */
        
        @ExcelField(value ="发薪号")
    private String userAccount;

    /**
     * 年度
     */
        
        @ExcelField(value ="年度")
    private String year;

    /**
     * 季度
     */
        
        @ExcelField(value ="季度")
    private String quarter;

    /**
     * 性别
     */
        
        @ExcelField(value ="性别")
    private String sexName;

    /**
     * 职业类别
     */
        
        @ExcelField(value ="职业类别")
    private String jobType;

    /**
     * 剂量计佩戴起始日期
     */
        
        @ExcelField(value ="剂量计佩戴起始日期", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date startDate;
    private transient String startDateFrom;
    private transient String startDateTo;

    /**
     * 佩戴天数
     */
        
        @ExcelField(value ="佩戴天数")
    private Integer days;

    /**
     * 铅衣外Hp(10)
     */
        
        @ExcelField(value ="铅衣外Hp(10)",readConverter = DoubleConverter.class)
    private Double qyOut;

    /**
     * 铅衣内Hp(10)
     */
        
        @ExcelField(value ="铅衣内Hp(10)",readConverter = DoubleConverter.class)
    private Double qyIn;

    /**
     * 未穿铅衣Hp(10)
     */
        
        @ExcelField(value ="未穿铅衣Hp(10)",readConverter = DoubleConverter.class)
    private Double qyNo;

    /**
     * 是否大剂量
     */

    @ExcelField("是否大剂量")
    private String isBig;

    /**
     * 工作期间是否有甲状腺屏蔽
     */
        

    private String isPb;

    /**
     * 总量
     */
        

    private Double toal;

    public Double getToal(){
        if(qyNo!=null){
            return  qyNo;
        }
        if(qyIn!=null&& qyOut!=null && qyIn>0 &&qyOut>0) {
            if(StringUtils.isNotEmpty(isPb)) {
                if (isPb.equals("是")) {
                    return Math.round((0.79 * qyIn + 0.051 * qyOut) * 100000) * 0.00001d;
                } else {
                    return Math.round((0.84 * qyIn + 0.1 * qyOut) * 100000) * 0.00001d;
                }
            }
            else {
                return  null;
            }
        }
        else{
            return  null;
        }
    }


    /**
     * 附件ID
     */
        

    private String fileId;

    /**
     * 附件Url
     */
        

    private String fileUrl;

    /**
     * 状态
     */
        

    private Integer state;

    /**
     * 是否删除
     */

        

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
        
        @ExcelField(value ="排序")
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

    public static final String YEAR ="year" ;

    public static final String QUARTER ="quarter" ;

    public static final String SEX_NAME ="sex_name" ;

    public static final String JOB_TYPE ="job_type" ;

    public static final String START_DATE ="start_date" ;

    public static final String DAYS ="days" ;

    public static final String QY_OUT ="qy_out" ;

    public static final String QY_IN ="qy_in" ;

    public static final String QY_NO ="qy_no" ;

    public static final String IS_BIG ="is_big" ;

    public static final String IS_PB ="is_pb" ;

    public static final String TOAL ="toal" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }