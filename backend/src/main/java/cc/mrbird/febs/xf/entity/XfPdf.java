package cc.mrbird.febs.xf.entity;

import cc.mrbird.febs.mdl.entity.MdlBBadrecord;
import cc.mrbird.febs.mdl.entity.MdlBKaoshi;
import cc.mrbird.febs.mdl.entity.MdlBPeixun;
import cc.mrbird.febs.sdl.entity.SdlBUser;
import cc.mrbird.febs.sdl.entity.SdlBUserMg;
import lombok.Data;

import java.util.List;

@Data
public class XfPdf {
    private SdlBUserMg xfBUser;

    private List<XfBTousu>  tousuList;

    private List<MdlBBadrecord> badrecordList;

    private List<XfBInnertalk> xfBInnertalkList;

    private List<MdlBPeixun> peixunList;

    private List<MdlBKaoshi> kaoshiList;

}
