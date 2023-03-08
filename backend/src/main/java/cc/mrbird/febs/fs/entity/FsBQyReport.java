package cc.mrbird.febs.fs.entity;

import cc.mrbird.febs.common.converter.DateConverter;
import cc.mrbird.febs.common.converter.DateReadConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.util.Date;

@Data
@Excel("report")
public class FsBQyReport {
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
     * 佩戴天数
     */

    @ExcelField(value ="佩戴天数")
    private Integer days;

    /**
     * 铅衣外Hp(10)
     */

    @ExcelField(value ="铅衣外Hp(10)")
    private Double qyOut;

    /**
     * 铅衣内Hp(10)
     */

    @ExcelField(value ="铅衣内Hp(10)")
    private Double qyIn;

    /**
     * 未穿铅衣Hp(10)
     */

    @ExcelField(value ="未穿铅衣Hp(10)")
    private Double qyNo;




    /**
     * 总量
     */


    private Double toal;

}
