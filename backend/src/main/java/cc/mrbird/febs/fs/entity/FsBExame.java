package cc.mrbird.febs.fs.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import cc.mrbird.febs.common.converter.*;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 
 * </p>
 *
 * @author viki
 * @since 2022-07-12
 */

@Excel("fs_b_exame")
@Data
public class FsBExame implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */

    private String id;

    /**
     * 数据填写员
     */
        

    private String userNo;

    /**
     * 排班员的姓名
     */
        

    private String name;

    /**
     * 姓名
     */
        
        @ExcelField(value ="姓名")
    private String userAccountName;

    /**
     * 人事编号
     */
        
        @ExcelField(value ="发薪号")
    private String userAccount;

    /**
     * 计量牌编号
     */
        
        @ExcelField(value ="计量牌编号")
    private String number;

    /**
     * 考试类型
     */
        
        @ExcelField(value ="考试类型")
    private String exameType;

    /**
     * 考试时间
     */
        
        @ExcelField(value ="考试时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date exameDate;
    private transient String exameDateFrom;
    private transient String exameDateTo;

    /**
     * 考试编号
     */
        
        @ExcelField(value ="考试编号")
    private String exameNum;

    /**
     * 考试成绩
     */
        
        @ExcelField(value ="考试成绩")
    private String exameScore;

    /**
     * 考试总分
     */
        
        @ExcelField(value ="考试总分")
    private String exameTotal;

    /**
     * 考试专业
     */
        
        @ExcelField(value ="考试专业")
    private String exameZy;

    /**
     * 有效期
     */
        

    private String valid;

    public String getValid(){
        if(exameDate==null) return  "";
        if(StringUtils.isNotEmpty(exameType)) {
            if(exameType.equals("卫生考试")) {
                return DateUtil.format(DateUtil.offsetMonth(exameDate, 12 * 2), "yyyy-MM-dd");
            }
            if(exameType.equals("环保考试")) {
                return DateUtil.format(DateUtil.offsetMonth(exameDate, 12 * 5), "yyyy-MM-dd");
            }
            return  "";
        }
        else{
            return  "";
        }
    }
    /**
     * 状态
     */
        

    private Integer state;

    /**
     * 附件ID
     */


    private String fileId;

    /**
     * 附件地址
     */


    private String fileUrl;
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

    public static final String USER_NO ="user_no" ;

    public static final String NAME ="name" ;

    public static final String USER_ACCOUNT_NAME ="user_account_name" ;

    public static final String USER_ACCOUNT ="user_account" ;

    public static final String NUMBER ="number" ;

    public static final String EXAME_TYPE ="exame_type" ;

    public static final String EXAME_DATE ="exame_date" ;

    public static final String EXAME_NUM ="exame_num" ;

    public static final String EXAME_SCORE ="exame_score" ;

    public static final String EXAME_TOTAL ="exame_total" ;

    public static final String EXAME_ZY ="exame_zy" ;

    public static final String VALID ="valid" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String DISPLAY_INDEX ="display_index" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }