package cc.mrbird.febs.dept.entity;

import cc.mrbird.febs.common.converter.DateConverter;
import cc.mrbird.febs.common.converter.DateReadConverter;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.util.Date;

@Data
public class ViewDicType {
    /**
     * 主键
     */

    @ExcelField(value ="主键")
    private String id;

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
     * 证件类型
     */

    @ExcelField(value ="证件类型")
    private String bookType;

    /**
     * 证件名称
     */

    @ExcelField(value ="证件名称")
    private String bookName;

    /**
     * 证件编号
     */

    @ExcelField(value ="证件编号")
    private String bookNo;

    /**
     * 发证时间
     */

    @ExcelField(value ="发证时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date bookDate;
    private transient String bookDateFrom;
    private transient String bookDateTo;

    /**
     * 状态
     */

    @ExcelField(value ="状态")
    private Integer state;

    /**
     * 备注
     */

    @ExcelField(value ="备注")
    private String note;

    @ExcelField(value ="附件ID")
    private String fileId;

    /**
     * 附件地址
     */

    @ExcelField(value ="附件地址")
    private String fileUrl;

    private String filedName;
    @ExcelField(value ="检测时间", writeConverter = DateConverter.class,readConverter = DateReadConverter.class)
    private Date checkDate;
}
