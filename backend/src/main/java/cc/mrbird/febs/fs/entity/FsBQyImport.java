package cc.mrbird.febs.fs.entity;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

@Excel("fs_b_qyImport")
@Data
public class FsBQyImport {
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

    @ExcelField(value ="工作期间是否有甲状腺屏蔽")
    private String isPb;
}
