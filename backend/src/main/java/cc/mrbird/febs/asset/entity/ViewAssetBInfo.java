package cc.mrbird.febs.asset.entity;

import java.math.BigDecimal;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import cc.mrbird.febs.common.converter.*;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author viki
 * @since 2023-02-13
 */

@Excel("view_asset_b_info")
@Data
public class ViewAssetBInfo implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 管理科室ID
     */
        
        @ExcelField(value ="管理科室ID")
    private String deptId;

    /**
     * 管理科室
     */
        
        @ExcelField(value ="管理科室")
    private String deptName;

    /**
     * 设备类型（卫生）
     */
        
        @ExcelField(value ="设备类型（卫生）")
    private String assetWeis;

    /**
     * 设备类型（环保）
     */
        
        @ExcelField(value ="设备类型（环保）")
    private String assetHuanb;


    private transient String assetWeisCustom;


    private transient String assetHuanbCustom;
    /**
     * 类别
     */
        
        @ExcelField(value ="类别")
    private String lb;

    /**
     * 用品及辅助防护设施
     */
        
        @ExcelField(value ="用品及辅助防护设施")
    private String ypfzfh;

        
    private BigDecimal shuliang;

        
    private Double qiandangliang;



    public static final String DEPT_ID ="dept_id" ;

    public static final String DEPT_NAME ="dept_name" ;

    public static final String ASSET_WEIS ="asset_weis" ;

    public static final String ASSET_HUANB ="asset_huanb" ;

    public static final String LB ="lb" ;

    public static final String YPFZFH ="ypfzfh" ;

    public static final String SHULIANG ="shuliang" ;

    public static final String QIANDANGLIANG ="qiandangliang" ;

        }