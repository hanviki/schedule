package cc.mrbird.febs.mdl.entity;

import cc.mrbird.febs.common.converter.DateConverter;
import cc.mrbird.febs.common.converter.DateReadConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.util.Date;
@Data
@Excel("麻精药物处方权")
public class MdlBChufangSc {
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
     * 是否处方
     */

    @ExcelField(value ="是否处方")
    private String isChufang;

    /**
     * 授权日期
     */

    @ExcelField(value ="授权日期",comment ="格式为：2022-09-08", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date powerDate;

    /**
     * 授权文件名称
     */

    @ExcelField(value ="授权文件名称")
    private String archiveName;

}
