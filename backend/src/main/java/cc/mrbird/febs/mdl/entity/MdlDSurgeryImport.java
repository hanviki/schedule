package cc.mrbird.febs.mdl.entity;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;



@Data
@Excel("mdl_d_surgery")
public class MdlDSurgeryImport {
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
     * 手术名称
     */

    @ExcelField(value ="手术名称")
    private String name;

    @ExcelField(value ="ICD编码")
    private String icdCode;
    @ExcelField(value ="ICD手术操作名称")
    private String icdName;
    /**
     * 手术等级
     */

    @ExcelField(value ="手术等级")
    private String level;
    @ExcelField(value ="手术类别")
    private String shlb;

    /**
     * 手术切口类别
     */

    @ExcelField(value ="手术切口类别")
    private String lb;

    @ExcelField(value ="启用时间")
    private String startDate;
    /**
     * 备注
     */

    @ExcelField(value ="备注")
    private String note;
}
