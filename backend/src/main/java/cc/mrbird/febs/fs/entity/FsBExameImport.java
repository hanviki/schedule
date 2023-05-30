package cc.mrbird.febs.fs.entity;

import cc.mrbird.febs.common.converter.DateConverter;
import cc.mrbird.febs.common.converter.DateReadConverter;
import cc.mrbird.febs.common.converter.DateReadConverter2;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.util.Date;

@Excel("fs_b_exame")
@Data
public class FsBExameImport {
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

    @ExcelField(value ="考试时间", writeConverter = DateConverter.class,readConverter = DateReadConverter2.class)
    private Date exameDate;

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

}
