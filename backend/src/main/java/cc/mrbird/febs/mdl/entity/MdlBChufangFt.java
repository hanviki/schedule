package cc.mrbird.febs.mdl.entity;

import cc.mrbird.febs.common.converter.DateConverter;
import cc.mrbird.febs.common.converter.DateReadConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.util.Date;

@Data
@Excel("考试管理")
public class MdlBChufangFt {
    /**
     * 发薪号
     */
    @ExcelField(value ="发薪号")
    private String userAccount;
    /**
     * 姓名
     */
    @ExcelField(value ="姓名")
    private String userAccountName;



    /**
     * 培训日期
     */

    @ExcelField(value ="考试日期",comment ="格式为：2022-09-08", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date trainDate;




    /**
     * 考核分数
     */

    @ExcelField(value ="考核分数")
    private String exiamScore;

    /**
     * 考核结果
     */

    @ExcelField(value ="考核结果")
    private String exiamResult;

}
