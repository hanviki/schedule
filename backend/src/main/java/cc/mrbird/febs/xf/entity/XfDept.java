package cc.mrbird.febs.xf.entity;

import cc.mrbird.febs.mdl.entity.MdlBBadrecord;
import lombok.Data;

import java.util.List;

@Data
public class XfDept {
    private String  deptName;
    private List<XfBTousu> tousuList;

    private List<MdlBBadrecord> badrecordList;

    private String isZhongdianzhuanxiangdudaokeshi;
    private String hangfengcanyu;
    private String kaoshihege;
    private List<XfBErrordept> xfBErrordepts;

    private List<XfBInnertalk> xfBInnertalkList;
}
