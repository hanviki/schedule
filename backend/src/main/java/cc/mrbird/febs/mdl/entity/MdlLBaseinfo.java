package cc.mrbird.febs.mdl.entity;

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
 * @since 2022-11-29
 */

@Excel("mdl_l_baseinfo")
@Data
public class MdlLBaseinfo implements Serializable{

private static final long serialVersionUID=1L;

                            
    private String id;

    /**
     * 序号
     */
        
        @ExcelField(value ="序号")
    private String labCode;

    /**
     * 实验室名称
     */
        
        @ExcelField(value ="实验室名称")
    private String labName;

    /**
     * 所属科室
     */
        
        @ExcelField(value ="所属科室")
    private String deptNew;

    /**
     * 负责人
     */
        
        @ExcelField(value ="负责人")
    private String leader;

    /**
     * 联系方式
     */
        
        @ExcelField(value ="联系方式")
    private String telphone;

    /**
     * 实验室联络人
     */
        
        @ExcelField(value ="实验室联络人")
    private String linker;

    /**
     * 联系方式
     */
        
        @ExcelField(value ="联系方式")
    private String linkTel;

    /**
     * 实验室地点
     */
        
        @ExcelField(value ="实验室地点")
    private String address;

    /**
     * 实验室资质
     */
        
        @ExcelField(value ="实验室资质")
    private String labZz;

    /**
     * 附件ID
     */
        
        @ExcelField(value ="附件ID")
    private String fileId;

    /**
     * 附件地址
     */
        
        @ExcelField(value ="附件地址")
    private String fileUrl;

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

    public static final String LAB_CODE ="lab_code" ;

    public static final String LAB_NAME ="lab_name" ;

    public static final String DEPT_NEW ="dept_new" ;

    public static final String LEADER ="leader" ;

    public static final String TELPHONE ="telphone" ;

    public static final String LINKER ="linker" ;

    public static final String LINK_TEL ="link_tel" ;

    public static final String ADDRESS ="address" ;

    public static final String LAB_ZZ ="lab_zz" ;

    public static final String FILE_ID ="file_id" ;

    public static final String FILE_URL ="file_url" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }