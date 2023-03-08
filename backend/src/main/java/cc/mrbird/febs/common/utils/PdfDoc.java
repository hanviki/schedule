package cc.mrbird.febs.common.utils;

import cc.mrbird.febs.mdl.entity.MdlBBadRecordImport;
import cc.mrbird.febs.mdl.entity.MdlBBadrecord;
import cc.mrbird.febs.mdl.entity.MdlBKaoshi;
import cc.mrbird.febs.mdl.entity.MdlBPeixun;
import cc.mrbird.febs.model.PdfStyle;
import cc.mrbird.febs.model.PdfValue;
import cc.mrbird.febs.sdl.entity.SdlBUser;
import cc.mrbird.febs.sdl.entity.SdlBUserMg;
import cc.mrbird.febs.xf.entity.*;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j2
public class PdfDoc {
    BaseColor black = BaseColor.BLACK;
    BaseColor red = BaseColor.RED;
    BaseColor blue = BaseColor.BLUE;
    private int bold = Font.BOLD; // 粗体
    private int normal = Font.NORMAL; // 正常字体
    private int italic = Font.ITALIC; // 斜体
    private int boldItalic = Font.BOLDITALIC; // 粗斜体
    private float setting = 100; // 首行缩进参数


    /**
     * 科室行风建设报告
     * @param fileName
     * @param dataDept
     * @throws Exception
     */
    public void writePdfDept( String fileName, XfDept dataDept) throws Exception {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        FileOutputStream out = new FileOutputStream(fileName);
        PdfWriter writer = PdfWriter.getInstance(document, out);

        final String projectPath = System.getProperty("user.dir");
        String fontpath = projectPath + "\\font\\";
        //region 关闭写入
        document.open(); // 文档里写入
        //  BaseFont baseFontChinese = BaseFont.createFont("宋体", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        //  BaseFont baseFontChinese = BaseFont.createFont("D:/font/simsun.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont baseFontChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFontChinese, 11, normal, black);
        Font fontgoal = new Font(baseFontChinese, 9, normal, black);

        int numColumns = 24;
        int totalWidth = 520;
        int[] setWids = new int[numColumns];
        PdfPTable table = null;
        Font font2_bold = new Font(baseFontChinese, 11, bold, black);
        Font fontCover1 = new Font(baseFontChinese, 15, bold, black);
        document.newPage();
        numColumns = 24;
        table = new PdfPTable(numColumns);
        setWids = new int[numColumns];

        //table总Width宽度
        table.setTotalWidth(totalWidth);
        //设置总Width宽度 生效
        table.setLockedWidth(true);

        //列布局
        for (int i = 0; i < numColumns; i++) {
            setWids[i] = 1;
        }
        table.setWidths(setWids);

        //列一
        //填表说明

        PdfPCell cell = new PdfPCell(new Phrase(dataDept.getDeptName()+"行风建设报告", fontCover1));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setFixedHeight(50f);
        cell.setPaddingBottom(10);
        cell.setColspan(numColumns);
        table.addCell(cell);





        table.addCell(GeneratePdfCell("科室：", 6, font));
        table.addCell(GeneratePdfCell(dataDept.getDeptName(), 18, font));


        table.addCell(GeneratePdfCell("行风投诉：", 6, font));

        String ts= "行风投诉"+dataDept.getTousuList().size()+"件，涉及人员："+ dataDept.getTousuList().stream().map(p->p.getUserAccountName()).collect(Collectors.joining(","));

        table.addCell(GeneratePdfCell(ts, 18, font));

        table.addCell(GeneratePdfCell("有效行风投诉：", 6, font));

        List<XfBTousu> valisTousu= dataDept.getTousuList().stream().filter(p->p.getIsValid().equals("是")).collect(Collectors.toList());

        String ts2= "有效行风投诉"+valisTousu.size()+"件，涉及人员："+ valisTousu.stream().map(p->p.getUserAccountName()).collect(Collectors.joining(","));

        table.addCell(GeneratePdfCell(ts2, 18, font));


        table.addCell(GeneratePdfCell("行风谈话:", 24, font2_bold));
        table.addCell(GeneratePdfCell("姓名", 4, font));
        table.addCell(GeneratePdfCell("谈话日期", 3, font));
        table.addCell(GeneratePdfCell("谈话部门", 3, font));
        table.addCell(GeneratePdfCell("谈话缘由", 8, font));
        table.addCell(GeneratePdfCell("参与人", 3, font));
        table.addCell(GeneratePdfCell("备注", 3, font));
        List<XfBInnertalk> innertalks= dataDept.getXfBInnertalkList();

        if(innertalks.size()>0) {
            for (XfBInnertalk talk : innertalks
            ) {
                table.addCell(GeneratePdfCell(talk.getUserAccountName(), 4, font));
                table.addCell(GeneratePdfCell(DateStr(talk.getTalkDate(), "yyyy-MM-dd"), 3, font));
                table.addCell(GeneratePdfCell(talk.getDeptNew(), 3, font));
                table.addCell(GeneratePdfCell(talk.getContent(), 8, font));
                table.addCell(GeneratePdfCell(talk.getOperationName(), 3, font));
                table.addCell(GeneratePdfCell(talk.getRemark(), 3, font));
            }
        }
        else{

            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 3, font));
            table.addCell(GeneratePdfCell("", 3, font));
            table.addCell(GeneratePdfCell("", 8, font));
            table.addCell(GeneratePdfCell("", 3, font));
            table.addCell(GeneratePdfCell("", 3, font));
        }


        List<MdlBBadrecord> jobList = dataDept.getBadrecordList();

        table.addCell(GeneratePdfCell("不良执业记分情况:", 24, font2_bold));
        table.addCell(GeneratePdfCell("姓名", 6, font));
        table.addCell(GeneratePdfCell("年度", 6, font));
        table.addCell(GeneratePdfCell("不良记分", 6, font));
        table.addCell(GeneratePdfCell("行风记分", 6, font));

       Map<String,List<MdlBBadrecord>> pp= jobList.stream().collect(Collectors.groupingBy(p->p.getYear()+"_"+p.getUserAccountName()));

        if (jobList.size() > 0) {
            for (String key : pp.keySet()
            ) {
                table.addCell(GeneratePdfCell(key.replace(key.substring(0,5),""), 6, font));
                table.addCell(GeneratePdfCell(key.substring(0,4), 6, font));
                table.addCell(GeneratePdfCell(String.valueOf( pp.get(key).stream().mapToInt(p->p.getScore()).sum()), 6, font));
                table.addCell(GeneratePdfCell(String.valueOf( pp.get(key).stream().filter(p->p.getDeptName().equals("行风建设办公室")).mapToInt(p->p.getScore()).sum()), 6, font));
            }
        } else {
            table.addCell(GeneratePdfCell("", 6, font));
            table.addCell(GeneratePdfCell("", 6, font));
            table.addCell(GeneratePdfCell("", 6, font));
            table.addCell(GeneratePdfCell("", 6, font));
        }


        table.addCell(GeneratePdfCell("是否近期重点/专项督导科室：", 6, font));
        table.addCell(GeneratePdfCell(dataDept.getIsZhongdianzhuanxiangdudaokeshi(), 18, font));

        table.addCell(GeneratePdfCell("科室行风培训参与率：", 6, font));
        table.addCell(GeneratePdfCell(dataDept.getHangfengcanyu(), 18, font));

        table.addCell(GeneratePdfCell("科室行风考核合格率：", 6, font));
        table.addCell(GeneratePdfCell(dataDept.getKaoshihege(), 18, font));

        table.addCell(GeneratePdfCell("自查自纠完成情况:", 24, font2_bold));
        table.addCell(GeneratePdfCell("年度", 6, font));
        table.addCell(GeneratePdfCell("自查结果", 18, font));


        List<XfBErrordept> xfBErrordepts = dataDept.getXfBErrordepts();
        if (xfBErrordepts.size() > 0) {
            for (XfBErrordept pp2 : xfBErrordepts
            ) {


                table.addCell(GeneratePdfCell(pp2.getYear(), 6, font));
                table.addCell(GeneratePdfCell(pp2.getResult(), 18, font));

            }
        } else {
            table.addCell(GeneratePdfCell("", 6, font));
            table.addCell(GeneratePdfCell("", 18, font));

        }
Map<String,List<XfBTousu>> listTousu= valisTousu.stream().collect(Collectors.groupingBy(
        p->DateUtil.format(p.getTousuDate(),"yyyyMMdd")
));
        String isSameDay="否";
        for (String key : listTousu.keySet()){
            if(listTousu.get(key).stream().count()>2){
                isSameDay="是";
                break;
            }
        }
        table.addCell(GeneratePdfCell("是否存在行风一票否决：", 6, font));

        table.addCell(GeneratePdfCell(isSameDay, 18, font));

        document.add(table);

     //   document.newPage();
        numColumns = 1;
        table = new PdfPTable(numColumns);
        setWids = new int[numColumns];

        //table总Width宽度
        table.setTotalWidth(totalWidth);
        //设置总Width宽度 生效
        table.setLockedWidth(true);

        //列布局
        for (int i = 0; i < numColumns; i++) {
            setWids[i] = 1;
        }
        table.setWidths(setWids);

        //列一
        //填表说明
        cell = new PdfPCell(new Phrase("清风远航  行稳致远", fontCover1));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setFixedHeight(80f);
        cell.setPaddingTop(30);
        cell.setPaddingBottom(10);
        cell.setColspan(numColumns);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("协和医院行风建设办公室 ", font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setFixedHeight(30f);
        cell.setPaddingBottom(10);
        cell.setPaddingRight(10);
        cell.setColspan(numColumns);
        table.addCell(cell);

        String fd= String.format("（报告时间，%1s）",DateUtil.format(new Date(),"yyyy年MM月dd日"));

        cell = new PdfPCell(new Phrase(fd, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setFixedHeight(30f);
        cell.setPaddingBottom(10);
        cell.setPaddingRight(10);
        cell.setColspan(numColumns);
        table.addCell(cell);

        document.add(table);

        out.flush();
        document.close();
        out.close();
    }


    /**
     * 个人行风建设报告
     * @param userAccount
     * @param fileName
     * @param dataUser
     * @throws Exception
     */
    public void writePdf2021_1(String userAccount, String fileName, XfPdf dataUser) throws Exception {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        FileOutputStream out = new FileOutputStream(fileName);
        PdfWriter writer = PdfWriter.getInstance(document, out);

        final String projectPath = System.getProperty("user.dir");
        String fontpath = projectPath + "\\font\\";
        //region 关闭写入
        document.open(); // 文档里写入
        //  BaseFont baseFontChinese = BaseFont.createFont("宋体", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        //  BaseFont baseFontChinese = BaseFont.createFont("D:/font/simsun.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont baseFontChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFontChinese, 11, normal, black);
        Font fontgoal = new Font(baseFontChinese, 9, normal, black);

        int numColumns = 24;
        int totalWidth = 520;
        int[] setWids = new int[numColumns];
        PdfPTable table = null;
        Font font2_bold = new Font(baseFontChinese, 11, bold, black);
        Font fontCover1 = new Font(baseFontChinese, 15, bold, black);
        document.newPage();
        numColumns = 24;
        table = new PdfPTable(numColumns);
        setWids = new int[numColumns];

        //table总Width宽度
        table.setTotalWidth(totalWidth);
        //设置总Width宽度 生效
        table.setLockedWidth(true);

        //列布局
        for (int i = 0; i < numColumns; i++) {
            setWids[i] = 1;
        }
        table.setWidths(setWids);

        //列一
        //填表说明
        SdlBUserMg user= dataUser.getXfBUser();
        PdfPCell cell = new PdfPCell(new Phrase(user.getUserAccountName()+"行风建设个人报告", fontCover1));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setFixedHeight(50f);
        cell.setPaddingBottom(10);
        cell.setColspan(numColumns);
        table.addCell(cell);


log.info(user.getUserAccountName());
        table.addCell(GeneratePdfCell("姓名：", 4, font));
        table.addCell(GeneratePdfCell(user.getUserAccountName(), 8, font));
        table.addCell(GeneratePdfCell("工号：", 4, font));
        table.addCell(GeneratePdfCell(user.getUserAccount(), 8, font));

        table.addCell(GeneratePdfCell("科室：", 4, font));
        table.addCell(GeneratePdfCell(user.getDeptNew(), 8, font));
        table.addCell(GeneratePdfCell("职称：", 4, font));
        table.addCell(GeneratePdfCell(user.getPositionName(), 8, font));





        List<XfBTousu> tousuList = dataUser.getTousuList();

        table.addCell(GeneratePdfCell("行风投诉:", 24, font2_bold));
        table.addCell(GeneratePdfCell("投诉日期", 3, font));
        table.addCell(GeneratePdfCell("投诉内容", 4, font));
        table.addCell(GeneratePdfCell("经办人", 2, font));
        table.addCell(GeneratePdfCell("转办单位", 3, font));
        table.addCell(GeneratePdfCell("接受日期", 2, font));
        table.addCell(GeneratePdfCell("是否有效投诉", 2, font));
        table.addCell(GeneratePdfCell("处理意结果", 4, font));
        table.addCell(GeneratePdfCell("领导小组意见", 4, font));

        for (XfBTousu edu : tousuList
        ) {

            table.addCell(GeneratePdfCell(DateStr(edu.getTousuDate(), "yyyy-MM-dd"), 3, font));
            table.addCell(GeneratePdfCell(edu.getContent(),4,font));
            table.addCell(GeneratePdfCell(edu.getOperationName(), 2, font));
            table.addCell(GeneratePdfCell(edu.getUnit(), 3, font));
            table.addCell(GeneratePdfCell(DateStr(edu.getReceiveDate(), "yyyy-MM-dd"), 2, font));
            table.addCell(GeneratePdfCell(edu.getIsValid(), 2, font));
            table.addCell(GeneratePdfCell(edu.getRedult(), 4, font));
            table.addCell(GeneratePdfCell(edu.getSuggestion(), 4, font));
        }

        log.info("666666666666");

        List<MdlBBadrecord> jobList = dataUser.getBadrecordList();

        table.addCell(GeneratePdfCell("不良执业记分情况:", 24, font2_bold));
        table.addCell(GeneratePdfCell("记录日期", 3, font));
        table.addCell(GeneratePdfCell("记分部门", 3, font));
        table.addCell(GeneratePdfCell("记分指标", 8, font));
        table.addCell(GeneratePdfCell("具体事由", 8, font));
        table.addCell(GeneratePdfCell("记分额度", 2, font));
        if (jobList.size() > 0) {
            for (MdlBBadrecord job : jobList
            ) {
                table.addCell(GeneratePdfCell(DateStr(job.getStartDate(), "yyyy-MM-dd"), 3, font));
                table.addCell(GeneratePdfCell(job.getDeptName(), 3, font));
                table.addCell(GeneratePdfCell(job.getIndict(), 8, font));
                table.addCell(GeneratePdfCell(job.getNote(), 8, font));
                table.addCell(GeneratePdfCell( String.valueOf(job.getScore()), 2, font));
            }
        } else {

            table.addCell(GeneratePdfCell("", 3, font));
            table.addCell(GeneratePdfCell("", 3, font));
            table.addCell(GeneratePdfCell("", 8, font));
            table.addCell(GeneratePdfCell("", 8, font));
            table.addCell(GeneratePdfCell("", 2, font));
        }



        table.addCell(GeneratePdfCell("行风谈话:", 24, font2_bold));
        table.addCell(GeneratePdfCell("谈话日期", 4, font));
        table.addCell(GeneratePdfCell("谈话部门", 4, font));
        table.addCell(GeneratePdfCell("谈话缘由", 8, font));
        table.addCell(GeneratePdfCell("参与人", 4, font));
        table.addCell(GeneratePdfCell("备注", 4, font));

        List<XfBInnertalk> dcaBDocPrizeorpunishList = dataUser.getXfBInnertalkList();
        if (dcaBDocPrizeorpunishList.size() > 0) {
            for (XfBInnertalk pp : dcaBDocPrizeorpunishList
            ) {

                table.addCell(GeneratePdfCell(DateStr(pp.getTalkDate(), "yyyy-MM-dd"), 4, font));
                table.addCell(GeneratePdfCell(pp.getDeptNew(), 4, font));
                table.addCell(GeneratePdfCell(pp.getContent(), 8, font));
                table.addCell(GeneratePdfCell(pp.getOperationName(), 4, font));
                table.addCell(GeneratePdfCell(pp.getRemark(), 4, font));
            }
        } else {
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 8, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
        }


        table.addCell(GeneratePdfCell("定期培训:", 24, font2_bold));
        table.addCell(GeneratePdfCell("首次阅读时间", 4, font));
        table.addCell(GeneratePdfCell("最后一次阅读时间", 4, font));
        table.addCell(GeneratePdfCell("累计阅读时长", 4, font));
        table.addCell(GeneratePdfCell("完成率", 4, font));
        table.addCell(GeneratePdfCell("练习次数", 4, font));
        table.addCell(GeneratePdfCell("已获得学分", 2, font));
        table.addCell(GeneratePdfCell("是否计划内", 2, font));

        List<MdlBKaoshi> kaoshiList = dataUser.getKaoshiList();
        if (kaoshiList.size() > 0) {
            for (MdlBKaoshi pp : kaoshiList
            ) {


                table.addCell(GeneratePdfCell(pp.getFirstyuedu(), 4, font));
                table.addCell(GeneratePdfCell(pp.getLastyuedu(), 4, font));
                table.addCell(GeneratePdfCell(pp.getLeijiyuedu(), 4, font));
                table.addCell(GeneratePdfCell(pp.getWanchenglv(), 4, font));
                table.addCell(GeneratePdfCell(pp.getLxcs(), 4, font));
                table.addCell(GeneratePdfCell(pp.getYihuoxuefen(), 2, font));
                table.addCell(GeneratePdfCell(pp.getIfjihuannei(), 2, font));
            }
        } else {
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 2, font));
            table.addCell(GeneratePdfCell("", 2, font));
        }

        table.addCell(GeneratePdfCell("定期培训后考核:", 24, font2_bold));
        table.addCell(GeneratePdfCell("开始考试时间", 4, font));
        table.addCell(GeneratePdfCell("结束考试时间", 4, font));
        table.addCell(GeneratePdfCell("考试时长", 4, font));
        table.addCell(GeneratePdfCell("考试成绩", 4, font));
        table.addCell(GeneratePdfCell(
                "考试次数", 4, font));
        table.addCell(GeneratePdfCell("是否合格", 4, font));


        List<MdlBPeixun> peixunList = dataUser.getPeixunList();
        if (peixunList.size() > 0) {
            for (MdlBPeixun pp : peixunList
            ) {


                table.addCell(GeneratePdfCell(pp.getStarttime(), 4, font));
                table.addCell(GeneratePdfCell(pp.getEndtime(), 4, font));
                table.addCell(GeneratePdfCell(pp.getKaoshishichang(), 4, font));
                table.addCell(GeneratePdfCell(pp.getScore(), 4, font));
                table.addCell(GeneratePdfCell(pp.getCishu(), 4, font));
                table.addCell(GeneratePdfCell(pp.getIfhege(), 4, font));

            }
        } else {
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));
            table.addCell(GeneratePdfCell("", 4, font));

        }
        table.addCell(GeneratePdfCell("是否存在行风一票否决：", 6, font));
        long isExist= tousuList.stream().filter(p->p.getIsValid().equals("是")).count();
        table.addCell(GeneratePdfCell(isExist>0?"是":"否", 18, font));
        document.add(table);

      //  document.newPage();
        numColumns = 1;
        table = new PdfPTable(numColumns);
        setWids = new int[numColumns];

        //table总Width宽度
        table.setTotalWidth(totalWidth);
        //设置总Width宽度 生效
        table.setLockedWidth(true);

        //列布局
        for (int i = 0; i < numColumns; i++) {
            setWids[i] = 1;
        }
        table.setWidths(setWids);

        //列一
        //填表说明
         cell = new PdfPCell(new Phrase("清风远航  行稳致远", fontCover1));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setFixedHeight(80f);
        cell.setPaddingTop(30);
        cell.setPaddingBottom(10);
        cell.setColspan(numColumns);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("协和医院行风建设办公室 ", font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setFixedHeight(30f);
        cell.setPaddingBottom(10);
        cell.setPaddingRight(10);
        cell.setColspan(numColumns);
        table.addCell(cell);

        String fd= String.format("（报告时间，%1s）",DateUtil.format(new Date(),"yyyy年MM月dd日"));

        cell = new PdfPCell(new Phrase(fd, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setFixedHeight(30f);
        cell.setPaddingBottom(10);
        cell.setPaddingRight(10);
        cell.setColspan(numColumns);
        table.addCell(cell);

        document.add(table);

        out.flush();
        document.close();
        out.close();
    }

    private PdfPCell GeneratePdfCell(String cellValue, int colspan, Font font) {

        PdfPCell cell = new PdfPCell(new Phrase(handleEmptyString(cellValue), font));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //  cell.setFixedHeight(contentHeight30);
        cell.setColspan(colspan);
        cell.setPaddingTop(10f);
        cell.setPaddingBottom(10f);
        return cell;
    }


    private PdfValue generatePdfValue(PdfStyle style, String cellValue, int colspan) {
        PdfValue pdfValue = new PdfValue();
        pdfValue.setCellValue(cellValue);
        pdfValue.setColspan(colspan);
        PdfStyle sty = new PdfStyle();
        sty = ObjectUtil.clone(style);
        pdfValue.setPdfStyle(sty);
        return pdfValue;
    }

    private PdfValue generatePdfValue(PdfStyle style, String cellValue, int colspan, float fixedHeight, int rowspan) {
        PdfValue pdfValue = new PdfValue();
        pdfValue.setCellValue(cellValue);
        pdfValue.setColspan(colspan);
        pdfValue.setRowspan(rowspan);
        PdfStyle sty = new PdfStyle();
        sty = ObjectUtil.clone(style);
        sty.setFixedHeight(fixedHeight);
        pdfValue.setPdfStyle(sty);
        return pdfValue;
    }

    private PdfValue generatePdfValue(PdfStyle style, String cellValue, int colspan, float fixedHeight) {
        PdfValue pdfValue = new PdfValue();
        pdfValue.setCellValue(cellValue);
        pdfValue.setColspan(colspan);
        PdfStyle sty = new PdfStyle();
        sty = ObjectUtil.clone(style);
        sty.setFixedHeight(fixedHeight);
        pdfValue.setPdfStyle(sty);
        return pdfValue;
    }

    private PdfValue generatePdfValue(PdfStyle style, String cellValue, int colspan, float fixedHeight, int horizontalAlignment, int verticalAlignment) {
        PdfValue pdfValue = new PdfValue();
        pdfValue.setCellValue(cellValue);
        pdfValue.setColspan(colspan);
        PdfStyle sty = new PdfStyle();
        sty = ObjectUtil.clone(style);
        sty.setFixedHeight(fixedHeight);
        sty.setHorizontalAlignment(horizontalAlignment);
        if (verticalAlignment > 0) {
            sty.setVerticalAlignment(verticalAlignment);
        }
        pdfValue.setPdfStyle(sty);
        return pdfValue;
    }

    private PdfValue generatePdfValue(PdfStyle style, String cellValue, int colspan, float fixedHeight, Font font) {
        PdfValue pdfValue = new PdfValue();
        pdfValue.setCellValue(cellValue);
        pdfValue.setColspan(colspan);
        PdfStyle sty = new PdfStyle();
        sty = ObjectUtil.clone(style);
        sty.setFixedHeight(fixedHeight);
        sty.setFont(font);
        pdfValue.setPdfStyle(sty);
        return pdfValue;
    }

    private String zuozhe(String zuozhe) {
        String a = zuozhe.replace("[", "").replace("]", "").replace(",", "和").replace("\"", "");
        return a;
    }

    private String DateStr(Date date, String stringFormat) {
        if (date == null) return "";
        return DateUtil.format(date, stringFormat);
    }

    // 查询图片组装image
    private Image loadingPicture(String picUrl) throws BadElementException, IOException {
        File file = new File(picUrl);
        byte[] by = File2byte(file);
        Image image = Image.getInstance(by);
        image.scaleAbsolute(80, 100);// 调整图片大小(宽度 高度)
        return image;
    }

    private byte[] File2byte(File tradeFile) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    private String handleEmptyString(String value) {
        if (value == null) return "";
        if(value.equals("null")) return  "";
        return value;
    }


}
