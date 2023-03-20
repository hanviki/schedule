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
 * 操作分级目录及资质授权
 * </p>
 *
 * @author viki
 * @since 2023-03-16
 */

@Excel("mdl_d_operation_doctor")
@Data
public class MdlDOperationDoctor implements Serializable{

private static final long serialVersionUID=1L;

                    @TableId(value = "id" , type = IdType.AUTO)
                
    private Long id;

    /**
     * 科室
     */
        
        @ExcelField(value ="科室")
    private String deptNew;

    /**
     * 编码
     */
        
        @ExcelField(value ="编码")
    private String code;

    /**
     * 操作名称
     */
        
        @ExcelField(value ="操作名称")
    private String name;

    /**
     * 级别
     */
        
        @ExcelField(value ="级别")
    private String level;

    /**
     * 授权医师
     */
        
        @ExcelField(value ="授权医师")
    private String docto;

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

    public static final String DEPT_NEW ="dept_new" ;

    public static final String CODE ="code" ;

    public static final String NAME ="name" ;

    public static final String LEVEL ="level" ;

    public static final String DOCTO ="docto" ;

    public static final String STATE ="state" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

        }