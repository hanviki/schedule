package cc.mrbird.febs.common.utils;

import cc.mrbird.febs.common.domain.ExportAfferentCustomExcel;
import cc.mrbird.febs.dept.entity.DynamicData;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.*;
import cn.hutool.poi.excel.cell.CellUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.annotation.TableField;

import com.wuwenze.poi.annotation.ExcelField;


import com.wuwenze.poi.factory.ExcelMappingFactory;
import com.wuwenze.poi.pojo.ExcelMapping;
import com.wuwenze.poi.pojo.ExcelProperty;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ExportExcelUtils {

    public static void exportCustomExcel_han(HttpServletResponse response, List<?> list, String customDataJson, String sheelName) throws NoSuchFieldException, IllegalAccessException, IOException {
        List<ExportAfferentCustomExcel> exportList = new ArrayList<>();
        if (customDataJson != null && !customDataJson.equals("")) {
            exportList = JSON.parseObject(customDataJson, new TypeReference<List<ExportAfferentCustomExcel>>() {
            });
        } else {
            Object obj = list.get(0);
            Class objClass = obj.getClass();
            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                TableField tableField = field.getAnnotation(TableField.class);
                if (tableField != null) {
                    ExportAfferentCustomExcel afferentCustomExcel = new ExportAfferentCustomExcel();
                    ExcelField excelField = field.getAnnotation(ExcelField.class);
                    afferentCustomExcel.setTitle(excelField != null ? excelField.value() : field.getName());
                    afferentCustomExcel.setDataIndex(field.getName());
                    exportList.add(afferentCustomExcel);
                }
            }
        }
        Object fieldValue = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        Class objClass2 = list.get(0).getClass();
        boolean isTrue = false;
        ExcelMapping excelMapping = ExcelMappingFactory.get(objClass2);
        List<ExcelProperty> excelPropertyList=excelMapping.getPropertyList();
        for (Object item : list) {
            Class objClass = item.getClass();
            Map<String, Object> row = new LinkedHashMap<>();
            for (ExportAfferentCustomExcel export : exportList) {
                isTrue = true;
                // fieldValue = field.get(item);
                fieldValue=  ReflectUtil.invoke(item, "get"+ StrUtil.sub(export.getDataIndex(),0,1).toUpperCase()+StrUtil.sub(export.getDataIndex(),1,export.getDataIndex().length()));

                List<ExcelProperty> excelProperty=excelPropertyList.stream().filter(p->p.getName().equals(export.getDataIndex())).collect(Collectors.toList());
                if(excelProperty!=null&&excelProperty.size()>0) {
                  fieldValue=  ExportExcelMapping.buildCellValueByExcelProperty(fieldValue, excelProperty.get(0));
                }
                if (fieldValue == null) fieldValue = "";
                row.put(export.getTitle(), fieldValue.toString());
            }
            if (isTrue) rows.add(row);
            isTrue = false;
        }

        if (sheelName.equals("") || sheelName == null) {
            sheelName = "Sheet1";
        }
        // ?????????????????????writer
        ExcelWriter writer = ExcelUtil.getWriterWithSheet(sheelName);
        // ?????????????????????????????????????????????????????????
//                writer.merge(rows.size() - 1, "???????????????");

        int rowCount = 0;
        int sheetColumnCount = exportList.size();
        // ???????????????????????????????????????????????????????????????
        if (list.size() == 0) {
            List<String> rowHead = new ArrayList<>();
            for (ExportAfferentCustomExcel export : exportList) {
                rowHead.add(export.getTitle());
            }
            writer.writeHeadRow(rowHead);
        } else {
            rowCount = rows.size();
            writer.write(rows, true);
        }
        //?????????????????????????????????????????????????????????
        writer.autoSizeColumnAll();

        //??????Row??????
        writer.setRowHeight(0, 25);

        //??????Row??????
        for (int i = 1; i <= rowCount; i++) {
            writer.setRowHeight(i, 20);
        }

        StyleSet style = writer.getStyleSet();
        CellStyle cellStyle = style.getHeadCellStyle();
        Font f1 = writer.createFont();
        f1.setBold(true);
        f1.setFontName("??????");
        short fontHeight = 280;
        f1.setFontHeight(fontHeight);
        cellStyle.setFont(f1);

        List<org.apache.poi.ss.usermodel.Sheet> sheetList = writer.getSheets();
        for (org.apache.poi.ss.usermodel.Sheet sheet : sheetList) {
            for (int i = 0; i <= sheetColumnCount; i++) {
                sheet.autoSizeColumn(i);
            }
        }
        //response???HttpServletResponse??????
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls??????????????????????????????????????????????????????????????????????????????
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
        // ??????writer???????????????
        writer.close();
        //????????????????????????Servlet???
        IoUtil.close(out);
    }


    public static void exportCustomExcel(HttpServletResponse response, List<?> list, String customDataJson, String sheelName) throws NoSuchFieldException, IllegalAccessException, IOException {
        List<ExportAfferentCustomExcel> exportList = new ArrayList<>();
        if (customDataJson != null && !customDataJson.equals("")) {
            exportList = JSON.parseObject(customDataJson, new TypeReference<List<ExportAfferentCustomExcel>>() {
            });
        } else {
            Object obj = list.get(0);
            Class objClass = obj.getClass();
            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                TableField tableField = field.getAnnotation(TableField.class);
                if (tableField != null) {
                    ExportAfferentCustomExcel afferentCustomExcel = new ExportAfferentCustomExcel();
                    ExcelField excelField = field.getAnnotation(ExcelField.class);
                    afferentCustomExcel.setTitle(excelField != null ? excelField.value() : field.getName());
                    afferentCustomExcel.setDataIndex(field.getName());
                    exportList.add(afferentCustomExcel);
                }
            }
        }
        Object fieldValue = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        boolean isTrue = false;
        for (Object item : list) {
            Class objClass = item.getClass();
            Map<String, Object> row = new LinkedHashMap<>();
            for (ExportAfferentCustomExcel export : exportList) {



                isTrue = true;
                Field field = objClass.getDeclaredField(export.getDataIndex());
                field.setAccessible(true);
               // fieldValue = field.get(item);

                fieldValue=  ReflectUtil.invoke(item, "get"+ StrUtil.sub(export.getDataIndex(),0,1).toUpperCase()+StrUtil.sub(export.getDataIndex(),1,export.getDataIndex().length()));
                // ???????????????Boolean ????????????
                /**
                 if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
                 fieldValue = (Boolean) field.get(item) == true ? "???" : "???";
                 }*/

                // ???????????????boolean ??????????????????????????? ???????????????????????????????????? isXXX??? ???????????????isXXX???
                // ???????????????getter????????????
                if (field.getGenericType().toString().contains("Boolean") && fieldValue!= null) {
                    fieldValue = (boolean) fieldValue == true ? "???" : "???";
                }
                // ???????????????Date
                if (field.getGenericType().toString().contains("Date")) {
                   // fieldValue = (Date) field.get(item);
                    if (fieldValue != null) {
                        fieldValue = formatter.format(fieldValue);
                    }
                }
                if (fieldValue == null) fieldValue = "";
                row.put(export.getTitle(), fieldValue.toString());
            }
            if (isTrue) rows.add(row);
            isTrue = false;
        }

        if (sheelName.equals("") || sheelName == null) {
            sheelName = "Sheet1";
        }
        // ?????????????????????writer
        ExcelWriter writer = ExcelUtil.getWriterWithSheet(sheelName);
        // ?????????????????????????????????????????????????????????
//                writer.merge(rows.size() - 1, "???????????????");

        int rowCount = 0;
        int sheetColumnCount = exportList.size();
        // ???????????????????????????????????????????????????????????????
        if (list.size() == 0) {
            List<String> rowHead = new ArrayList<>();
            for (ExportAfferentCustomExcel export : exportList) {
                rowHead.add(export.getTitle());
            }
            writer.writeHeadRow(rowHead);
        } else {
            rowCount = rows.size();
            writer.write(rows, true);
        }
        //?????????????????????????????????????????????????????????
        writer.autoSizeColumnAll();

        //??????Row??????
        writer.setRowHeight(0, 25);

        //??????Row??????
        for (int i = 1; i <= rowCount; i++) {
            writer.setRowHeight(i, 20);
        }

        StyleSet style = writer.getStyleSet();
        CellStyle cellStyle = style.getHeadCellStyle();
        Font f1 = writer.createFont();
        f1.setBold(true);
        f1.setFontName("??????");
        short fontHeight = 280;
        f1.setFontHeight(fontHeight);
        cellStyle.setFont(f1);

        List<org.apache.poi.ss.usermodel.Sheet> sheetList = writer.getSheets();
        for (org.apache.poi.ss.usermodel.Sheet sheet : sheetList) {
            for (int i = 0; i <= sheetColumnCount; i++) {
                sheet.autoSizeColumn(i);
            }
        }
        //response???HttpServletResponse??????
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls??????????????????????????????????????????????????????????????????????????????
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
        // ??????writer???????????????
        writer.close();
        //????????????????????????Servlet???
        IoUtil.close(out);
    }

//    public static void exportCustomExcelCutome(HttpServletResponse response, List<?> list, String customDataJson, List<DcaBAuditdynamic> dynamicData, String sheelName) throws NoSuchFieldException, IllegalAccessException, IOException {
//        ServletOutputStream out = response.getOutputStream();
//        List<ExportAfferentCustomExcel> exportList = new ArrayList<>();
//        if (customDataJson != null && !customDataJson.equals("")) {
//            exportList = JSON.parseObject(customDataJson, new TypeReference<List<ExportAfferentCustomExcel>>() {
//            });
//            List<String> filedTypeStr= exportList.stream().map(p->p.getDataIndex()).collect(Collectors.toList());
//            dynamicData =dynamicData.stream().filter(p->filedTypeStr.contains(p.getAuditTitletype())).collect(Collectors.toList());
//        } else {
//            Object obj = list.get(0);
//            Class objClass = obj.getClass();
//            Field[] fields = objClass.getDeclaredFields();
//            for (Field field : fields) {
//                TableField tableField = field.getAnnotation(TableField.class);
//                if (tableField != null) {
//                    ExportAfferentCustomExcel afferentCustomExcel = new ExportAfferentCustomExcel();
//                    ExcelField excelField = field.getAnnotation(ExcelField.class);
//                    afferentCustomExcel.setTitle(excelField != null ? excelField.value() : field.getName());
//                    afferentCustomExcel.setDataIndex(field.getName());
//                    exportList.add(afferentCustomExcel);
//                }
//            }
//        }
//        Object fieldValue = null;
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        ArrayList<Map<String, Object>> rows = new ArrayList<>();
//        boolean isTrue = false;
//        for (Object item : list) {
//
//            String userAcoount = "";
//            List<String> userList = new ArrayList<>();
//
//            Class objClass = item.getClass();
//            Map<String, Object> row = new LinkedHashMap<>();
//            for (ExportAfferentCustomExcel export : exportList) {
//                isTrue = true;
//
//                if (export.getIsDynamic() == 0) {
//
//                    if (export.getDataIndex().equals("xl")) {
//                        fieldValue = ((DcaBUser) item).getXl();
//                    } else if (export.getDataIndex().equals("birthdaystr")) {
//                        fieldValue = ((DcaBUser) item).getBirthdaystr();
//                    } else if (export.getDataIndex().equals("positionName")) {
//                        fieldValue = ((DcaBUser) item).getPositionName();
//                    } else if (export.getDataIndex().equals("gwdj")) {
//                        fieldValue = ((DcaBUser) item).getGwdj();
//                    } else if (export.getDataIndex().equals("zygwDate")) {
//                        fieldValue = ((DcaBUser) item).getZygwDate();
//                    } else {
//                        Field field = objClass.getDeclaredField(export.getDataIndex());
//                        field.setAccessible(true);
//                        fieldValue = field.get(item);
//
//
//                        // ???????????????Boolean ????????????
//                        /**
//                         if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
//                         fieldValue = (Boolean) field.get(item) == true ? "???" : "???";
//                         }*/
//
//                        // ???????????????boolean ??????????????????????????? ???????????????????????????????????? isXXX??? ???????????????isXXX???
//                        // ???????????????getter????????????
//                        if (field.getGenericType().toString().equals("boolean")) {
//                            fieldValue = (boolean) field.get(item) == true ? "???" : "???";
//                        }
//                        // ???????????????Date
//                        if (field.getGenericType().toString().equals("class java.util.Date")) {
//                            fieldValue = (Date) field.get(item);
//                            if (fieldValue != null) {
//                                fieldValue = formatter.format(fieldValue);
//                            }
//                        }
//                    }
//                    if (export.getDataIndex().equals("userAccount")) {
//                        userAcoount = fieldValue == null ? "" : fieldValue.toString();
//                        userList.add(userAcoount);
//                    }
//                } else {
//                    List<DcaBAuditdynamic> listUser = dynamicData.stream().filter(p -> userList.contains(p.getUserAccount()) && p.getAuditTitletype().equals(export.getDataIndex())).collect(Collectors.toList());
//                    if (listUser.size() > 0) {
//                        fieldValue = listUser.get(0).getAuditResult();
//                    } else {
//                        fieldValue = null;
//                    }
//                }
//                if (fieldValue == null) fieldValue = "";
//                row.put(export.getTitle(), fieldValue.toString());
//            }
//
//            if (isTrue) rows.add(row);
//            isTrue = false;
//        }
//
//        if (sheelName.equals("") || sheelName == null) {
//            sheelName = "Sheet1";
//        }
//        // ?????????????????????writer
//        ExcelWriter writer = ExcelUtil.getWriterWithSheet(sheelName);
//        // ?????????????????????????????????????????????????????????
////                writer.merge(rows.size() - 1, "???????????????");
//
//        int rowCount = 0;
//        int sheetColumnCount = exportList.size();
//        // ???????????????????????????????????????????????????????????????
//        if (list.size() == 0) {
//            List<String> rowHead = new ArrayList<>();
//            for (ExportAfferentCustomExcel export : exportList) {
//                rowHead.add(export.getTitle());
//            }
//            writer.writeHeadRow(rowHead);
//        } else {
//            rowCount = rows.size();
//            writer.write(rows, true);
//        }
////        SXSSFSheet sheet = (SXSSFSheet)writer.getSheet();
////        //??????????????????SXSSFSheet  ????????????trackAllColumnsForAutoSizing??????
////        sheet.trackAllColumnsForAutoSizing();
////        //?????????????????????????????????????????????????????????
////        writer.autoSizeColumnAll();
//        writer.autoSizeColumnAll();
//        //??????Row??????
//        writer.setRowHeight(0, 25);
//
//        //??????Row??????
//        for (int i = 1; i <= rowCount; i++) {
//            writer.setRowHeight(i, 20);
//        }
//
//        StyleSet style = writer.getStyleSet();
//        CellStyle cellStyle = style.getHeadCellStyle();
//        Font f1 = writer.createFont();
//        f1.setBold(true);
//        f1.setFontName("??????");
//        short fontHeight = 280;
//        f1.setFontHeight(fontHeight);
//        cellStyle.setFont(f1);
//
//        List<org.apache.poi.ss.usermodel.Sheet> sheetList = writer.getSheets();
//        for (org.apache.poi.ss.usermodel.Sheet sheet : sheetList) {
//            for (int i = 0; i <= sheetColumnCount; i++) {
//                sheet.autoSizeColumn(i);
//            }
//        }
//        //response???HttpServletResponse??????
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
//        //test.xls??????????????????????????????????????????????????????????????????????????????
//        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
//
//
//        writer.flush(out, true);
//        // ??????writer???????????????
//        writer.close();
//        //????????????????????????Servlet???
//        IoUtil.close(out);
//    }


//    public static void exportCustomExcelCutome_check(HttpServletResponse response,List<CheckDTitle> checkDTitleList,String userAccount, List<?> list, String customDataJson, List<CheckBAuditresult> dynamicData, String sheelName) throws NoSuchFieldException, IllegalAccessException, IOException {
//        List<ExportAfferentCustomExcel> exportList = new ArrayList<>();
//        if (customDataJson != null && !customDataJson.equals("")) {
//            exportList = JSON.parseObject(customDataJson, new TypeReference<List<ExportAfferentCustomExcel>>() {
//            });
//        } else {
//            Object obj = list.get(0);
//            Class objClass = obj.getClass();
//            Field[] fields = objClass.getDeclaredFields();
//            for (Field field : fields) {
//                TableField tableField = field.getAnnotation(TableField.class);
//                if (tableField != null) {
//                    ExportAfferentCustomExcel afferentCustomExcel = new ExportAfferentCustomExcel();
//                    ExcelField excelField = field.getAnnotation(ExcelField.class);
//                    afferentCustomExcel.setTitle(excelField != null ? excelField.value() : field.getName());
//                    afferentCustomExcel.setDataIndex(field.getName());
//                    exportList.add(afferentCustomExcel);
//                }
//            }
//        }
//        Object fieldValue = null;
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        ArrayList<Map<String, Object>> rows = new ArrayList<>();
//        boolean isTrue = false;
//        for (Object item : list) {
//
//            String userAcoount = "";
//            List<String> userList = new ArrayList<>();
//
//            Class objClass = item.getClass();
//            Map<String, Object> row = new LinkedHashMap<>();
//            for (ExportAfferentCustomExcel export : exportList) {
//                isTrue = true;
//
//                if(export.getIsDynamic()>0 && !isDiable(export,checkDTitleList,(CheckBUser)item,userAccount)){
//                    fieldValue ="??????????????????";
//                    row.put(export.getTitle(), fieldValue.toString());
//                    continue;
//                }
//                if (export.getIsDynamic() == 0) {
//
//
//                        Field field = objClass.getDeclaredField(export.getDataIndex());
//                        field.setAccessible(true);
//                        fieldValue = field.get(item);
//
//
//                        // ???????????????Boolean ????????????
//                        /**
//                         if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
//                         fieldValue = (Boolean) field.get(item) == true ? "???" : "???";
//                         }*/
//
//                        // ???????????????boolean ??????????????????????????? ???????????????????????????????????? isXXX??? ???????????????isXXX???
//                        // ???????????????getter????????????
//                        if (field.getGenericType().toString().equals("boolean")) {
//                            fieldValue = (boolean) field.get(item) == true ? "???" : "???";
//                        }
//                        // ???????????????Date
//                        if (field.getGenericType().toString().equals("class java.util.Date")) {
//                            fieldValue = (Date) field.get(item);
//                            if (fieldValue != null) {
//                                fieldValue = formatter.format(fieldValue);
//                            }
//                        }
//
//                    if (export.getDataIndex().equals("userAccount")) {
//                        userAcoount = fieldValue == null ? "" : fieldValue.toString();
//                        userList.add(userAcoount);
//                    }
//                } else {
//                    List<CheckBAuditresult> listUser = dynamicData.stream().filter(p -> userList.contains(p.getUserAccount()) && p.getAuditTitletype().equals(export.getDataIndex())&&p.getDcaYear().equals(((CheckBUser)item).getDcaYear())).collect(Collectors.toList());
//                    if (listUser.size() > 0) {
//                        fieldValue = listUser.get(0).getAuditResult();
//                    } else {
//                        fieldValue = null;
//                    }
//                }
//                if (fieldValue == null) fieldValue = "";
//                row.put(export.getTitle(), fieldValue.toString());
//            }
//
//            if (isTrue) rows.add(row);
//            isTrue = false;
//        }
//
//        if (sheelName.equals("") || sheelName == null) {
//            sheelName = "Sheet1";
//        }
//        // ?????????????????????writer
//        ExcelWriter writer = ExcelUtil.getWriterWithSheet(sheelName);
//        // ?????????????????????????????????????????????????????????
////                writer.merge(rows.size() - 1, "???????????????");
//
//        int rowCount = 0;
//        int sheetColumnCount = exportList.size();
//        // ???????????????????????????????????????????????????????????????
//        if (list.size() == 0) {
//            List<String> rowHead = new ArrayList<>();
//            for (ExportAfferentCustomExcel export : exportList) {
//                rowHead.add(export.getTitle());
//            }
//            writer.writeHeadRow(rowHead);
//        } else {
//            rowCount = rows.size();
//            writer.write(rows, true);
//        }
//        //?????????????????????????????????????????????????????????
//        writer.autoSizeColumnAll();
//
//        //??????Row??????
//        writer.setRowHeight(0, 25);
//
//        //??????Row??????
//        for (int i = 1; i <= rowCount; i++) {
//            writer.setRowHeight(i, 20);
//        }
//
//        StyleSet style = writer.getStyleSet();
//        CellStyle cellStyle = style.getHeadCellStyle();
//        Font f1 = writer.createFont();
//        f1.setBold(true);
//        f1.setFontName("??????");
//        short fontHeight = 280;
//        f1.setFontHeight(fontHeight);
//        cellStyle.setFont(f1);
//
//        List<org.apache.poi.ss.usermodel.Sheet> sheetList = writer.getSheets();
//        for (org.apache.poi.ss.usermodel.Sheet sheet : sheetList) {
//            for (int i = 0; i <= sheetColumnCount; i++) {
//                sheet.autoSizeColumn(i);
//            }
//        }
//        //response???HttpServletResponse??????
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
//        //test.xls??????????????????????????????????????????????????????????????????????????????
//        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
//        ServletOutputStream out = response.getOutputStream();
//
//        writer.flush(out, true);
//        // ??????writer???????????????
//        writer.close();
//        //????????????????????????Servlet???
//        IoUtil.close(out);
//    }
//
//    public static void exportCustomExcelCutome2(HttpServletResponse response, List<?> list, String customDataJson, List<DcaBAuditdynamic> dynamicData, String sheelName) throws NoSuchFieldException, IllegalAccessException, IOException {
//        List<ExportAfferentCustomExcel> exportList = new ArrayList<>();
//        if (customDataJson != null && !customDataJson.equals("")) {
//            exportList = JSON.parseObject(customDataJson, new TypeReference<List<ExportAfferentCustomExcel>>() {
//            });
//        } else {
//            Object obj = list.get(0);
//            Class objClass = obj.getClass();
//            Field[] fields = objClass.getDeclaredFields();
//            for (Field field : fields) {
//                TableField tableField = field.getAnnotation(TableField.class);
//                if (tableField != null) {
//                    ExportAfferentCustomExcel afferentCustomExcel = new ExportAfferentCustomExcel();
//                    ExcelField excelField = field.getAnnotation(ExcelField.class);
//                    afferentCustomExcel.setTitle(excelField != null ? excelField.value() : field.getName());
//                    afferentCustomExcel.setDataIndex(field.getName());
//                    exportList.add(afferentCustomExcel);
//                }
//            }
//        }
//        Object fieldValue = null;
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        ArrayList<Map<String, Object>> rows = new ArrayList<>();
//        boolean isTrue = false;
//        for (Object item : list) {
//
//            String userAcoount = "";
//            List<String> userList = new ArrayList<>();
//
//            Class objClass = item.getClass();
//            Map<String, Object> row = new LinkedHashMap<>();
//            for (ExportAfferentCustomExcel export : exportList) {
//                isTrue = true;
//
//                if (export.getIsDynamic() == 0) {
//
//                    if (export.getDataIndex().equals("xl")) {
//                        fieldValue = ((DcaBUserapply) item).getXl();
//                    }  else {
//                        Field field = objClass.getDeclaredField(export.getDataIndex());
//                        field.setAccessible(true);
//                        fieldValue = field.get(item);
//
//
//                        // ???????????????Boolean ????????????
//                        /**
//                         if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
//                         fieldValue = (Boolean) field.get(item) == true ? "???" : "???";
//                         }*/
//
//                        // ???????????????boolean ??????????????????????????? ???????????????????????????????????? isXXX??? ???????????????isXXX???
//                        // ???????????????getter????????????
//                        if (field.getGenericType().toString().equals("boolean")) {
//                            fieldValue = (boolean) field.get(item) == true ? "???" : "???";
//                        }
//                        // ???????????????Date
//                        if (field.getGenericType().toString().equals("class java.util.Date")) {
//                            fieldValue = (Date) field.get(item);
//                            if (fieldValue != null) {
//                                fieldValue = formatter.format(fieldValue);
//                            }
//                        }
//                    }
//                    if (export.getDataIndex().equals("userAccount")) {
//                        userAcoount = fieldValue == null ? "" : fieldValue.toString();
//                        userList.add(userAcoount);
//                    }
//                } else {
//                    List<DcaBAuditdynamic> listUser = dynamicData.stream().filter(p -> userList.contains(p.getUserAccount()) && p.getAuditTitletype().equals(export.getDataIndex())).collect(Collectors.toList());
//                    if (listUser.size() > 0) {
//                        fieldValue = listUser.get(0).getAuditResult();
//                    } else {
//                        fieldValue = null;
//                    }
//                }
//                if (fieldValue == null) fieldValue = "?????????";
//                row.put(export.getTitle(), fieldValue.toString());
//            }
//
//            if (isTrue) rows.add(row);
//            isTrue = false;
//        }
//
//        if (sheelName.equals("") || sheelName == null) {
//            sheelName = "Sheet1";
//        }
//        // ?????????????????????writer
//        ExcelWriter writer = ExcelUtil.getWriterWithSheet(sheelName);
//        // ?????????????????????????????????????????????????????????
////                writer.merge(rows.size() - 1, "???????????????");
//
//        int rowCount = 0;
//        int sheetColumnCount = exportList.size();
//        // ???????????????????????????????????????????????????????????????
//        if (list.size() == 0) {
//            List<String> rowHead = new ArrayList<>();
//            for (ExportAfferentCustomExcel export : exportList) {
//                rowHead.add(export.getTitle());
//            }
//            writer.writeHeadRow(rowHead);
//        } else {
//            rowCount = rows.size();
//            writer.write(rows, true);
//        }
//        //?????????????????????????????????????????????????????????
//        writer.autoSizeColumnAll();
//
//        //??????Row??????
//        writer.setRowHeight(0, 25);
//
//        //??????Row??????
//        for (int i = 1; i <= rowCount; i++) {
//            writer.setRowHeight(i, 20);
//        }
//
//        StyleSet style = writer.getStyleSet();
//        CellStyle cellStyle = style.getHeadCellStyle();
//        Font f1 = writer.createFont();
//        f1.setBold(true);
//        f1.setFontName("??????");
//        short fontHeight = 280;
//        f1.setFontHeight(fontHeight);
//        cellStyle.setFont(f1);
//
//        List<org.apache.poi.ss.usermodel.Sheet> sheetList = writer.getSheets();
//        for (org.apache.poi.ss.usermodel.Sheet sheet : sheetList) {
//            for (int i = 0; i <= sheetColumnCount; i++) {
//                sheet.autoSizeColumn(i);
//            }
//        }
//        //response???HttpServletResponse??????
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
//        //test.xls??????????????????????????????????????????????????????????????????????????????
//        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
//        ServletOutputStream out = response.getOutputStream();
//
//        writer.flush(out, true);
//        // ??????writer???????????????
//        writer.close();
//        //????????????????????????Servlet???
//        IoUtil.close(out);
//    }
//
//
//    public static void exportCustomExcelCutome3(HttpServletResponse response, List<?> list, String customDataJson, String tempUrl,int startRowCount) throws NoSuchFieldException, IllegalAccessException, IOException {
//        List<ExportAfferentCustomExcel> exportList = new ArrayList<>();
//        String dcayear="";
//
//        if (customDataJson != null && !customDataJson.equals("")) {
//            exportList = JSON.parseObject(customDataJson, new TypeReference<List<ExportAfferentCustomExcel>>() {
//            });
//        } else {
//            Object obj = list.get(0);
//            Class objClass = obj.getClass();
//            Field[] fields = objClass.getDeclaredFields();
//            for (Field field : fields) {
//                TableField tableField = field.getAnnotation(TableField.class);
//                if (tableField != null) {
//                    ExportAfferentCustomExcel afferentCustomExcel = new ExportAfferentCustomExcel();
//                    ExcelField excelField = field.getAnnotation(ExcelField.class);
//                    afferentCustomExcel.setTitle(excelField != null ? excelField.value() : field.getName());
//                    afferentCustomExcel.setDataIndex(field.getName());
//                    exportList.add(afferentCustomExcel);
//                }
//            }
//        }
//        Object fieldValue = null;
//
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        ArrayList<Map<String, Object>> rows = new ArrayList<>();
//        boolean isTrue = false;
//        int index=0;
//        for (Object item : list) {
//            index=index+1;
//            String userAcoount = "";
//            List<String> userList = new ArrayList<>();
//
//            Class objClass = item.getClass();
//
//                if(dcayear.equals("")){
//                    Field field = objClass.getDeclaredField("year");
//                    if(field!=null) {
//                        field.setAccessible(true);
//                        fieldValue = field.get(item);
//                        dcayear = fieldValue == null ? "" : fieldValue.toString();
//                    }
//                }
//
//            Map<String, Object> row = new LinkedHashMap<>();
//            for (ExportAfferentCustomExcel export : exportList) {
//                isTrue = true;
//
//                if (export.getIsDynamic() == 0) {
//
//                    if (export.getDataIndex().equals("indexHao")) {//??????????????????
//                        fieldValue = String.valueOf(index);
//                    }  else {
//                        Field field = objClass.getDeclaredField(export.getDataIndex());
//                        field.setAccessible(true);
//                        fieldValue = field.get(item);
//
//
//                        // ???????????????Boolean ????????????
//                        /**
//                         if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
//                         fieldValue = (Boolean) field.get(item) == true ? "???" : "???";
//                         }*/
//
//                        // ???????????????boolean ??????????????????????????? ???????????????????????????????????? isXXX??? ???????????????isXXX???
//                        // ???????????????getter????????????
//                        if (field.getGenericType().toString().equals("boolean")) {
//                            fieldValue = (boolean) field.get(item) == true ? "???" : "???";
//                        }
//                        // ???????????????Date
//                        if (field.getGenericType().toString().equals("class java.util.Date")) {
//                            fieldValue = (Date) field.get(item);
//                            if (fieldValue != null) {
//                                fieldValue = formatter.format(fieldValue);
//                            }
//                        }
//                    }
//                    if (export.getDataIndex().equals("userAccount")) {
//                        userAcoount = fieldValue == null ? "" : fieldValue.toString();
//                        userList.add(userAcoount);
//                    }
//
//                } else {
//                    List<DcaBAuditdynamic> dynamicData =((DcaBReport) item).getDcaBAuditdynamicList();
//                    List<DcaBAuditdynamic> listUser = dynamicData.stream().filter(p -> p.getAuditTitletype().equals(export.getDataIndex())).collect(Collectors.toList());
//                    if (listUser.size() > 0) {
//                        fieldValue = listUser.get(0).getAuditResult();
//                    } else {
//                        fieldValue = null;
//                    }
//                }
//                if (fieldValue == null) fieldValue = "";
//                row.put(export.getTitle(), fieldValue.toString().replace("#","\r\n"));
//            }
//
//            if (isTrue) rows.add(row);
//            isTrue = false;
//        }
//
//        // ?????????????????????writer
//        InputStream inputStream = new FileInputStream(tempUrl);
//        ExcelReader reader = ExcelUtil.getReader(inputStream,0);
//
//        List<List<Object>> readAll = reader.read();
//        if(readAll.size()>0) { //???????????? ??????????????? 20210531
//            if(!dcayear.equals("")) {
//                String titleA = readAll.get(0).get(0).toString();
//                titleA = titleA.replace("2019", dcayear);
//                reader.getCell(0, 0).setCellValue(titleA);
//            }
//        }
//
//        ExcelWriter writer = reader.getWriter();
//
//        // ?????????????????????????????????????????????????????????
////                writer.merge(rows.size() - 1, "???????????????");
//
//        for (int i = 0; i < startRowCount; i++) {
//            writer.passCurrentRow();
//        }
//        int rowCount = 0;
//        int sheetColumnCount = exportList.size();
//        // ???????????????????????????????????????????????????????????????
//        if (list.size()> 0) {
//            rowCount = rows.size();
//            writer.write(rows, false);
//        }
//        //?????????????????????????????????????????????????????????
//      //  writer.autoSizeColumnAll();
//
//        //??????Row??????
//       // writer.setRowHeight(0, 25);
//
//        //??????Row??????
//        for (int i = startRowCount; i <= rowCount; i++) {
//            writer.setRowHeight(i, 80);
//        }
//
//        StyleSet style = writer.getStyleSet();
//        /**
//        CellStyle cellStyle = style.getHeadCellStyle();
//        Font f1 = writer.createFont();
//        f1.setBold(true);
//        f1.setFontName("??????");
//        short fontHeight = 280;
//        f1.setFontHeight(fontHeight);
//        cellStyle.setFont(f1);*/
//
//        CellStyle cellStyle2 = style.getCellStyle();
//        cellStyle2.setWrapText(true);
//       // cellStyle.setWrapText(true);
//
//        /**
//        List<org.apache.poi.ss.usermodel.Sheet> sheetList = writer.getSheets();
//        for (org.apache.poi.ss.usermodel.Sheet sheet : sheetList) {
//            for (int i = 0; i <= sheetColumnCount; i++) {
//                sheet.autoSizeColumn(i);
//            }
//        }*/
//        //response???HttpServletResponse??????
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
//        //test.xls??????????????????????????????????????????????????????????????????????????????
//        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
//        ServletOutputStream out = response.getOutputStream();
//
//        writer.flush(out, true);
//        // ??????writer???????????????
//        writer.close();
//        //????????????????????????Servlet???
//        IoUtil.close(out);
//    }
//    public static boolean isDiable(ExportAfferentCustomExcel export, List<CheckDTitle> list,CheckBUser checkBUser,String user_account){
//
//        List<CheckDTitle> selectList=list.stream().filter(p->p.getFiledName().equals(export.getDataIndex())).collect(Collectors.toList());
//        if(selectList.size()>0) {
//            CheckDTitle checkDTitle = selectList.get(0);
//            if (checkDTitle.getCheckPerson().equals(1) && !checkDTitle.getLb().contains(checkBUser.getZjlb())) {
//                return false;
//            }
//            if (checkDTitle.getCheckPerson().equals(2)) {
//                if (!checkBUser.getKsLeaderPernr().equals(user_account)) {
//                    return false;
//                }
//            }
//            if (checkDTitle.getCheckPerson().equals(3)) {
//                if (!checkBUser.getZgLeaderPernr().equals(user_account)) {
//                    return false;
//                }
//            }
//        }
//        return  true;
//    }
//
//    public static void exportCustomExcel_checkUser(HttpServletResponse response, List<?> list, String customDataJson, String sheelName) throws NoSuchFieldException, IllegalAccessException, IOException {
//        List<ExportAfferentCustomExcel> exportList = new ArrayList<>();
//        if (customDataJson != null && !customDataJson.equals("")) {
//            exportList = JSON.parseObject(customDataJson, new TypeReference<List<ExportAfferentCustomExcel>>() {
//            });
//        } else {
//            Object obj = list.get(0);
//            Class objClass = obj.getClass();
//            Field[] fields = objClass.getDeclaredFields();
//            for (Field field : fields) {
//                TableField tableField = field.getAnnotation(TableField.class);
//                if (tableField != null) {
//                    ExportAfferentCustomExcel afferentCustomExcel = new ExportAfferentCustomExcel();
//                    ExcelField excelField = field.getAnnotation(ExcelField.class);
//                    afferentCustomExcel.setTitle(excelField != null ? excelField.value() : field.getName());
//                    afferentCustomExcel.setDataIndex(field.getName());
//                    exportList.add(afferentCustomExcel);
//                }
//            }
//        }
//        Object fieldValue = null;
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        ArrayList<Map<String, Object>> rows = new ArrayList<>();
//        Class objClass2 = list.get(0).getClass();
//        boolean isTrue = false;
//        ExcelMapping excelMapping = ExcelMappingFactory.get(objClass2);
//        List<ExcelProperty> excelPropertyList=excelMapping.getPropertyList();
//        for (Object item : list) {
//            Class objClass = item.getClass();
//            Map<String, Object> row = new LinkedHashMap<>();
//            for (ExportAfferentCustomExcel export : exportList) {
//                isTrue = true;
//                if (export.getIsDynamic() == 0) {
//                    // fieldValue = field.get(item);
//                    fieldValue = ReflectUtil.invoke(item, "get" + StrUtil.sub(export.getDataIndex(), 0, 1).toUpperCase() + StrUtil.sub(export.getDataIndex(), 1, export.getDataIndex().length()));
//
//                    List<ExcelProperty> excelProperty = excelPropertyList.stream().filter(p -> p.getName().equals(export.getDataIndex())).collect(Collectors.toList());
//                    if (excelProperty != null && excelProperty.size() > 0) {
//                        fieldValue = ExportExcelMapping.buildCellValueByExcelProperty(fieldValue, excelProperty.get(0));
//                    }
//                }
//                else {
//                    List<CheckBAuditresult> dynamicData =((CheckBUser) item).getCheckBAuditresultList();
//                    List<CheckBAuditresult> listUser = dynamicData.stream().filter(p -> p.getAuditTitletype().equals(export.getDataIndex())).collect(Collectors.toList());
//                    if (listUser.size() > 0) {
//                        fieldValue = listUser.get(0).getAuditResult();
//                    } else {
//                        fieldValue = null;
//                    }
//                }
//                if (fieldValue == null) fieldValue = "";
//                row.put(export.getTitle(), fieldValue.toString());
//            }
//            if (isTrue) rows.add(row);
//            isTrue = false;
//        }
//
//        if (sheelName.equals("") || sheelName == null) {
//            sheelName = "Sheet1";
//        }
//        // ?????????????????????writer
//        ExcelWriter writer = ExcelUtil.getWriterWithSheet(sheelName);
//        // ?????????????????????????????????????????????????????????
////                writer.merge(rows.size() - 1, "???????????????");
//
//        int rowCount = 0;
//        int sheetColumnCount = exportList.size();
//        // ???????????????????????????????????????????????????????????????
//        if (list.size() == 0) {
//            List<String> rowHead = new ArrayList<>();
//            for (ExportAfferentCustomExcel export : exportList) {
//                rowHead.add(export.getTitle());
//            }
//            writer.writeHeadRow(rowHead);
//        } else {
//            rowCount = rows.size();
//            writer.write(rows, true);
//        }
//        //?????????????????????????????????????????????????????????
//        writer.autoSizeColumnAll();
//
//        //??????Row??????
//        writer.setRowHeight(0, 25);
//
//        //??????Row??????
//        for (int i = 1; i <= rowCount; i++) {
//            writer.setRowHeight(i, 20);
//        }
//
//        StyleSet style = writer.getStyleSet();
//        CellStyle cellStyle = style.getHeadCellStyle();
//        Font f1 = writer.createFont();
//        f1.setBold(true);
//        f1.setFontName("??????");
//        short fontHeight = 280;
//        f1.setFontHeight(fontHeight);
//        cellStyle.setFont(f1);
//
//        List<org.apache.poi.ss.usermodel.Sheet> sheetList = writer.getSheets();
//        for (org.apache.poi.ss.usermodel.Sheet sheet : sheetList) {
//            for (int i = 0; i <= sheetColumnCount; i++) {
//                sheet.autoSizeColumn(i);
//            }
//        }
//        //response???HttpServletResponse??????
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
//        //test.xls??????????????????????????????????????????????????????????????????????????????
//        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
//        ServletOutputStream out = response.getOutputStream();
//
//        writer.flush(out, true);
//        // ??????writer???????????????
//        writer.close();
//        //????????????????????????Servlet???
//        IoUtil.close(out);
//    }
//    public static void exportCustomExcel_person(HttpServletResponse response, List<?> list, String customDataJson, String sheelName,String dcaYear,String userAccountName,String zyjsgw,String ks) throws NoSuchFieldException, IllegalAccessException, IOException {
//        List<ExportAfferentCustomExcel> exportList = new ArrayList<>();
//        if (customDataJson != null && !customDataJson.equals("")) {
//            exportList = JSON.parseObject(customDataJson, new TypeReference<List<ExportAfferentCustomExcel>>() {
//            });
//        } else {
//            Object obj = list.get(0);
//            Class objClass = obj.getClass();
//            Field[] fields = objClass.getDeclaredFields();
//            for (Field field : fields) {
//                TableField tableField = field.getAnnotation(TableField.class);
//                if (tableField != null) {
//                    ExportAfferentCustomExcel afferentCustomExcel = new ExportAfferentCustomExcel();
//                    ExcelField excelField = field.getAnnotation(ExcelField.class);
//                    afferentCustomExcel.setTitle(excelField != null ? excelField.value() : field.getName());
//                    afferentCustomExcel.setDataIndex(field.getName());
//                    exportList.add(afferentCustomExcel);
//                }
//            }
//        }
//        Object fieldValue = null;
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        ArrayList<Map<String, Object>> rows = new ArrayList<>();
//        Class objClass2 = list.get(0).getClass();
//        boolean isTrue = false;
//        ExcelMapping excelMapping = ExcelMappingFactory.get(objClass2);
//        List<ExcelProperty> excelPropertyList=excelMapping.getPropertyList();
//        for (Object item : list) {
//            Class objClass = item.getClass();
//            Map<String, Object> row = new LinkedHashMap<>();
//            for (ExportAfferentCustomExcel export : exportList) {
//                isTrue = true;
//                // fieldValue = field.get(item);
//                fieldValue=  ReflectUtil.invoke(item, "get"+ StrUtil.sub(export.getDataIndex(),0,1).toUpperCase()+StrUtil.sub(export.getDataIndex(),1,export.getDataIndex().length()));
//
//                List<ExcelProperty> excelProperty=excelPropertyList.stream().filter(p->p.getName().equals(export.getDataIndex())).collect(Collectors.toList());
//                if(excelProperty!=null&&excelProperty.size()>0) {
//                    fieldValue=  ExportExcelMapping.buildCellValueByExcelProperty(fieldValue, excelProperty.get(0));
//                }
//                if (fieldValue == null) fieldValue = "";
//                row.put(export.getTitle(), fieldValue.toString());
//            }
//            if (isTrue) rows.add(row);
//            isTrue = false;
//        }
//
//
//
//
//        if (sheelName.equals("") || sheelName == null) {
//            sheelName = "Sheet1";
//        }
//        // ?????????????????????writer
//        ExcelWriter writer = ExcelUtil.getWriterWithSheet(sheelName);
//        // ?????????????????????????????????????????????????????????
////                writer.merge(rows.size() - 1, "???????????????");
//
//        int rowCount = 0;
//        int sheetColumnCount = exportList.size();
//        // ???????????????????????????????????????????????????????????????
//        if (list.size() == 0) {
//            List<String> rowHead = new ArrayList<>();
//            for (ExportAfferentCustomExcel export : exportList) {
//                rowHead.add(export.getTitle());
//            }
//            writer.writeHeadRow(rowHead);
//        } else {
//            rowCount = rows.size();
//            writer.write(rows, true);
//        }
//        ArrayList<Map<String, Object>> rows2 = new ArrayList<>();
//        //?????????????????????????????????????????????????????????
//
//      //  writer.passCurrentRow();
//
//       writer.merge(2, StrUtil.format("????????????{} ???????????????{} ?????????????????????{} ???????????????{}",userAccountName,ks,zyjsgw,dcaYear),false);
//      // writer.merge(2, StrUtil.format("????????????{} ???????????????{} ?????????????????????{} ???????????????{}",userAccountName,ks,zyjsgw,dcaYear),false);
//      //  CellUtil.setCellValue
//        writer.merge(2, "???????????????????????????????????????????????????5????????????2?????????10????????????3??????????????????????????????????????????????????????",false);
//
//      //  CellUtil.setCellValue(writer.getCell(0,rowCount+2),StrUtil.format("????????????{} ???????????????{} ?????????????????????{} ???????????????{}",userAccountName,ks,zyjsgw,dcaYear),styCu);
//      //  CellUtil.setCellValue(writer.getCell(0,rowCount+3),StrUtil.format("????????????{} ???????????????{} ?????????????????????{} ???????????????{}",userAccountName,ks,zyjsgw,dcaYear),styCu);
//        //??????Row??????
//       // writer.setRowHeight(0, 25);
//
//
//        //??????Row??????
//        for (int i = 1; i <= rowCount; i++) {
//            writer.setRowHeight(i, 20);
//        }
//        for (int i = 1; i <= 2; i++) {
//            writer.setRowHeight(rowCount+i, 40);
//        }
//
//        writer.setColumnWidth(0,60);
//        writer.setColumnWidth(1,8);
//        writer.setColumnWidth(2,12);
//
//        StyleSet style = writer.getStyleSet();
//        CellStyle cellStyle = style.getHeadCellStyle();
//        Font f1 = writer.createFont();
//        f1.setBold(true);
//        f1.setFontName("??????");
//        short fontHeight = 280;
//        f1.setFontHeight(fontHeight);
//        cellStyle.setFont(f1);
//
//        CellStyle cellRowStyle = style.getCellStyle();
//        cellRowStyle.setAlignment(HorizontalAlignment.LEFT);
//        cellRowStyle.setWrapText(true);
//
//
//
//
//
//
//        //response???HttpServletResponse??????
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
//        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
//        //test.xls??????????????????????????????????????????????????????????????????????????????
//        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
//        ServletOutputStream out = response.getOutputStream();
//
//        writer.flush(out, true);
//        // ??????writer???????????????
//        writer.close();
//        //????????????????????????Servlet???
//        IoUtil.close(out);
//    }

    public static void exportRows(HttpServletResponse response,List<Map<String,Object>> rows,String sheelName,String tempUrl,int startRowCount) throws NoSuchFieldException, IllegalAccessException, IOException{
        // ?????????????????????writer
        InputStream inputStream = new FileInputStream(tempUrl);
        ExcelReader reader = ExcelUtil.getReader(inputStream,0);


        ExcelWriter writer = reader.getWriter();

        // ?????????????????????????????????????????????????????????
//                writer.merge(rows.size() - 1, "???????????????");

        for (int i = 0; i < startRowCount; i++) {
            writer.passCurrentRow();
        }
        int rowCount = 0;
      //  int sheetColumnCount = exportList.size();
        // ???????????????????????????????????????????????????????????????

            rowCount = rows.size();
            writer.write(rows, false);

        //?????????????????????????????????????????????????????????
        //  writer.autoSizeColumnAll();

        //??????Row??????
        // writer.setRowHeight(0, 25);

        //??????Row??????


        StyleSet style = writer.getStyleSet();
        /**
         CellStyle cellStyle = style.getHeadCellStyle();
         Font f1 = writer.createFont();
         f1.setBold(true);
         f1.setFontName("??????");
         short fontHeight = 280;
         f1.setFontHeight(fontHeight);
         cellStyle.setFont(f1);*/

        CellStyle cellStyle2 = style.getCellStyle();
        cellStyle2.setWrapText(true);
        // cellStyle.setWrapText(true);

        /**
         List<org.apache.poi.ss.usermodel.Sheet> sheetList = writer.getSheets();
         for (org.apache.poi.ss.usermodel.Sheet sheet : sheetList) {
         for (int i = 0; i <= sheetColumnCount; i++) {
         sheet.autoSizeColumn(i);
         }
         }*/
        //response???HttpServletResponse??????
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls??????????????????????????????????????????????????????????????????????????????
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
        // ??????writer???????????????
        writer.close();
        //????????????????????????Servlet???
        IoUtil.close(out);
    }


    public static void exportCustomExcelCutome(HttpServletResponse response, List<?> list, String customDataJson, List<DynamicData> dynamicData, String sheelName) throws NoSuchFieldException, IllegalAccessException, IOException {
        ServletOutputStream out = response.getOutputStream();
        List<ExportAfferentCustomExcel> exportList = new ArrayList<>();
        if (customDataJson != null && !customDataJson.equals("")) {
            exportList = JSON.parseObject(customDataJson, new TypeReference<List<ExportAfferentCustomExcel>>() {
            });
//            List<String> filedTypeStr= exportList.stream().map(p->p.getDataIndex()).collect(Collectors.toList());
//            dynamicData =dynamicData.stream().filter(p->filedTypeStr.contains(p.getAuditTitletype())).collect(Collectors.toList());
        } else {
            Object obj = list.get(0);
            Class objClass = obj.getClass();
            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                TableField tableField = field.getAnnotation(TableField.class);
                if (tableField != null) {
                    ExportAfferentCustomExcel afferentCustomExcel = new ExportAfferentCustomExcel();
                    ExcelField excelField = field.getAnnotation(ExcelField.class);
                    afferentCustomExcel.setTitle(excelField != null ? excelField.value() : field.getName());
                    afferentCustomExcel.setDataIndex(field.getName());
                    exportList.add(afferentCustomExcel);
                }
            }
        }
        Object fieldValue = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        boolean isTrue = false;
        for (Object item : list) {

            String userAcoount = "";
            List<String> userList = new ArrayList<>();

            Class objClass = item.getClass();
            Map<String, Object> row = new LinkedHashMap<>();
            for (ExportAfferentCustomExcel export : exportList) {
                isTrue = true;

                if (export.getIsDynamic() == 0) {

                    if (export.getDataIndex().equals("xl")) {
                        fieldValue = "";
                    } else {
                        Field field = objClass.getDeclaredField(export.getDataIndex());
                        field.setAccessible(true);
                        fieldValue = field.get(item);


                        // ???????????????Boolean ????????????
                        /**
                         if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
                         fieldValue = (Boolean) field.get(item) == true ? "???" : "???";
                         }*/

                        // ???????????????boolean ??????????????????????????? ???????????????????????????????????? isXXX??? ???????????????isXXX???
                        // ???????????????getter????????????
                        if (field.getGenericType().toString().equals("boolean")) {
                            fieldValue = (boolean) field.get(item) == true ? "???" : "???";
                        }
                        // ???????????????Date
                        if (field.getGenericType().toString().equals("class java.util.Date")) {
                            fieldValue = (Date) field.get(item);
                            if (fieldValue != null) {
                                fieldValue = formatter.format(fieldValue);
                            }
                        }
                    }
                    if (export.getDataIndex().equals("userAccount")) {
                        userAcoount = fieldValue == null ? "" : fieldValue.toString();
                        userList.add(userAcoount);
                    }
                } else {
                    List<DynamicData> listUser = dynamicData.stream().filter(p -> userList.contains(p.getUserAccount()) && p.getAuditTitletype().equals(export.getDataIndex())).collect(Collectors.toList());
                    if (listUser.size() > 0) {
                        fieldValue = listUser.get(0).getAuditResult();
                    } else {
                        fieldValue = null;
                    }
                }
                if (fieldValue == null) fieldValue = "";
                row.put(export.getTitle(), fieldValue.toString());
            }

            if (isTrue) rows.add(row);
            isTrue = false;
        }

        if (sheelName.equals("") || sheelName == null) {
            sheelName = "Sheet1";
        }
        // ?????????????????????writer
        ExcelWriter writer = ExcelUtil.getWriterWithSheet(sheelName);
        // ?????????????????????????????????????????????????????????
//                writer.merge(rows.size() - 1, "???????????????");

        int rowCount = 0;
        int sheetColumnCount = exportList.size();
        // ???????????????????????????????????????????????????????????????
        if (list.size() == 0) {
            List<String> rowHead = new ArrayList<>();
            for (ExportAfferentCustomExcel export : exportList) {
                rowHead.add(export.getTitle());
            }
            writer.writeHeadRow(rowHead);
        } else {
            rowCount = rows.size();
            writer.write(rows, true);
        }
//        SXSSFSheet sheet = (SXSSFSheet)writer.getSheet();
//        //??????????????????SXSSFSheet  ????????????trackAllColumnsForAutoSizing??????
//        sheet.trackAllColumnsForAutoSizing();
//        //?????????????????????????????????????????????????????????
//        writer.autoSizeColumnAll();
        writer.autoSizeColumnAll();
        //??????Row??????
        writer.setRowHeight(0, 25);

        //??????Row??????
        for (int i = 1; i <= rowCount; i++) {
            writer.setRowHeight(i, 20);
        }

        StyleSet style = writer.getStyleSet();
        CellStyle cellStyle = style.getHeadCellStyle();
        Font f1 = writer.createFont();
        f1.setBold(true);
        f1.setFontName("??????");
        short fontHeight = 280;
        f1.setFontHeight(fontHeight);
        cellStyle.setFont(f1);

        List<org.apache.poi.ss.usermodel.Sheet> sheetList = writer.getSheets();
        for (org.apache.poi.ss.usermodel.Sheet sheet : sheetList) {
            for (int i = 0; i <= sheetColumnCount; i++) {
                sheet.autoSizeColumn(i);
            }
        }
        //response???HttpServletResponse??????
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls??????????????????????????????????????????????????????????????????????????????
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");


        writer.flush(out, true);
        // ??????writer???????????????
        writer.close();
        //????????????????????????Servlet???
        IoUtil.close(out);
    }

}
