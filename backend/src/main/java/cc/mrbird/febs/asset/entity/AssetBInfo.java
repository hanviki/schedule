package cc.mrbird.febs.asset.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;

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
 * @since 2023-02-06
 */

@Excel("asset_b_info")
@Data
public class AssetBInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)

    private Long id;

    /**
     * 设备名称
     */

    @ExcelField(value = "设备名称")
    private String assetName;

    /**
     * 设备类型（卫生）
     */

    @ExcelField(value = "设备类型（卫生）")
    private String assetWeis;

    private transient String assetWeisCustom;

    /**
     * 设备类型（环保）
     */

    @ExcelField(value = "设备类型（环保）")
    private String assetHuanb;

    private transient String assetHuanbCustom;

    /**
     * 生产厂家
     */

    @ExcelField(value = "生产厂家")
    private String productName;

    /**
     * 设备型号
     */

    @ExcelField(value = "设备型号")
    private String assetXingh;

    /**
     * 设备SN码
     */

    @ExcelField(value = "设备SN码")
    private String assetSn;

    /**
     * 设备院内编码
     */

    @ExcelField(value = "设备院内编码")
    private String assetYnbm;

    /**
     * 设备当前状态
     */

    @ExcelField(value = "设备当前状态")
    private String assetState;

    /**
     * 安装日期
     */

    @ExcelField(value = "安装日期", writeConverter = DateConverter.class, readConverter = DateReadConverter.class)
    private Date assetDate;
    private transient String assetDateFrom;
    private transient String assetDateTo;

    /**
     * 管理科室
     */

    @ExcelField(value = "管理科室")
    private String deptName;

    /**
     * 管理科室ID
     */

    @ExcelField(value = "管理科室ID")
    private String deptId;

    /**
     * 设备位置
     */

    @ExcelField(value = "设备位置")
    private String address;

    /**
     * 辐射安全许可证编号
     */

    @ExcelField(value = "辐射安全许可证编号")
    private String aqBh;

    /**
     * 辐射安全许可证
     */

    @ExcelField(value = "辐射安全许可证")
    private String aqFileUrl;

    /**
     * 辐射安全许可证证书
     */

    @ExcelField(value = "辐射安全许可证证书")
    private String aqFileId;

    /**
     * 放射诊疗许可证编号
     */

    @ExcelField(value = "放射诊疗许可证编号")
    private String fsBh;

    /**
     * 辐射安全许可证证书url
     */

    @ExcelField(value = "辐射安全许可证证书url")
    private String fsFileUrl;

    /**
     * 辐射安全许可证证书
     */

    @ExcelField(value = "辐射安全许可证证书")
    private String fsFileId;

    /**
     * 最近一次的场所检测结论
     */

    @ExcelField(value = "最近一次的场所检测结论")
    private String checkResult;

    /**
     * 最近一次的性能检测结论
     */

    @ExcelField(value = "最近一次的性能检测结论")
    private String performResult;

    /**
     * 备注
     */

    @ExcelField(value = "备注")
    private String remark;

    /**
     * 状态
     */

    @ExcelField(value = "状态")
    private Integer state;

    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")

    @ExcelField(value = "是否删除")
    private Integer isDeletemark;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")

    @ExcelField(value = "创建时间", writeConverter = DateConverter.class, readConverter = DateReadConverter.class)
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")

    @ExcelField(value = "修改时间", writeConverter = DateConverter.class, readConverter = DateReadConverter.class)
    private Date modifyTime;
    private transient String modifyTimeFrom;
    private transient String modifyTimeTo;

    /**
     * 创建人
     */
    @TableField("CREATE_USER_ID")

    @ExcelField(value = "创建人")
    private Long createUserId;

    /**
     * 修改人
     */
    @TableField("MODIFY_USER_ID")

    @ExcelField(value = "修改人")
    private Long modifyUserId;


    public static final String ID = "id";

    public static final String ASSET_NAME = "asset_name";

    public static final String ASSET_WEIS = "asset_weis";

    public static final String ASSET_HUANB = "asset_huanb";

    public static final String PRODUCT_NAME = "product_name";

    public static final String ASSET_XINGH = "asset_xingh";

    public static final String ASSET_SN = "asset_sn";

    public static final String ASSET_YNBM = "asset_ynbm";

    public static final String ASSET_STATE = "asset_state";

    public static final String ASSET_DATE = "asset_date";

    public static final String DEPT_NAME = "dept_name";

    public static final String DEPT_ID = "dept_id";

    public static final String ADDRESS = "address";

    public static final String AQ_BH = "aq_bh";

    public static final String AQ_FILE_URL = "aq_file_url";

    public static final String AQ_FILE_ID = "aq_file_id";

    public static final String FS_BH = "fs_bh";

    public static final String FS_FILE_URL = "fs_file_url";

    public static final String FS_FILE_ID = "fs_file_id";

    public static final String CHECK_RESULT = "check_result";

    public static final String PERFORM_RESULT = "perform_result";

    public static final String REMARK = "remark";

    public static final String STATE = "state";

    public static final String IS_DELETEMARK = "IS_DELETEMARK";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String MODIFY_TIME = "MODIFY_TIME";

    public static final String CREATE_USER_ID = "CREATE_USER_ID";

    public static final String MODIFY_USER_ID = "MODIFY_USER_ID";

}