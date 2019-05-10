package com.ls.stockforecast.utils;


import com.ls.stockforecast.utils.constant.CellVo;
import com.ls.stockforecast.utils.constant.ExcelVo;
import com.ls.stockforecast.utils.constant.SheetVo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel文件处理工具类: 包括填充数据到普通excel、模板excel文件,单元格格式处理
 * @author
 *
 */
public class ExcelUtil {

    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
    public static final String EMPTY = "";
    public static final String POINT = ".";
    public static SimpleDateFormat sdf =   new SimpleDateFormat("yyyy/MM/dd");
    public static int totalRows; //sheet中总行数
    public static int totalCells; //每一行总单元格数
    /**
     * read the Excel .xlsx,.xls
     * @param file jsp中的上传文件
     * @return
     * @throws IOException
     */
    public static List<ArrayList<String>> readExcel(MultipartFile file) throws IOException {
        if(file==null||ExcelUtil.EMPTY.equals(file.getOriginalFilename().trim())) {
            return null;
        } else {
            String postfix = ExcelUtil.getPostfix(file.getOriginalFilename());
            if(!ExcelUtil.EMPTY.equals(postfix)) {
                if(ExcelUtil.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                    return readXls(file);
                } else if(ExcelUtil.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    return readXlsx(file);
                } else{
                    return null;
                }
            }
        }
        return null;
//        return readXlsx(file);
    }
    /**
     * read the Excel 2010 .xlsx
     * @param file
     * @return
     * @throws IOException
     */
    @SuppressWarnings("deprecation")
    public static List<ArrayList<String>> readXlsx(MultipartFile file){
        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        // IO流读取文件
        InputStream input = null;
        XSSFWorkbook wb = null;
        ArrayList<String> rowList = null;
        try {
            input = file.getInputStream();
            // 创建文档
            wb = new XSSFWorkbook(input);
            //读取sheet(页)
            for(int numSheet=0;numSheet<wb.getNumberOfSheets();numSheet++){
                XSSFSheet xssfSheet = wb.getSheetAt(numSheet);
                if(xssfSheet == null){
                    continue;
                }
                totalRows = xssfSheet.getLastRowNum();
                //读取Row,从第二行开始
                for(int rowNum = 0;rowNum <= totalRows;rowNum++){
                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                    if(xssfRow!=null){
                        rowList = new ArrayList<String>();
                        totalCells = xssfRow.getLastCellNum();
                        //读取列，从第一列开始
                        for(int c=0;c<=totalCells+1;c++){
                            XSSFCell cell = xssfRow.getCell(c);
                            if(cell==null){
                                rowList.add(ExcelUtil.EMPTY);
                                continue;
                            }
                            rowList.add(ExcelUtil.getXValue(cell).trim());
                        }
                        list.add(rowList);
                    }
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
    /**
     * read the Excel 2003-2007 .xls
     * @param file
     * @return
     * @throws IOException
     */
    public static List<ArrayList<String>> readXls(MultipartFile file){
        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        // IO流读取文件
        InputStream input = null;
        HSSFWorkbook wb = null;
        ArrayList<String> rowList = null;
        try {
            input = file.getInputStream();
            // 创建文档
            wb = new HSSFWorkbook(input);
            //读取sheet(页)
            for(int numSheet=0;numSheet<wb.getNumberOfSheets();numSheet++){
                HSSFSheet hssfSheet = wb.getSheetAt(numSheet);
                if(hssfSheet == null){
                    continue;
                }
                totalRows = hssfSheet.getLastRowNum();
                //读取Row,从第二行开始
                for(int rowNum = 1;rowNum <= totalRows;rowNum++){
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if(hssfRow!=null){
                        rowList = new ArrayList<String>();
                        totalCells = hssfRow.getLastCellNum();
                        //读取列，从第一列开始
                        for(short c=0;c<=totalCells+1;c++){
                            HSSFCell cell = hssfRow.getCell(c);
                            if(cell==null){
                                rowList.add(ExcelUtil.EMPTY);
                                continue;
                            }
                            rowList.add(ExcelUtil.getHValue(cell).trim());
                        }
                        list.add(rowList);
                    }
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 获得path的后缀名
     * @param path
     * @return
     */
    public static String getPostfix(String path){
        if(path==null || EMPTY.equals(path.trim())){
            return EMPTY;
        }
        if(path.contains(POINT)){
            return path.substring(path.lastIndexOf(POINT)+1,path.length());
        }
        return EMPTY;
    }
    /**
     * 单元格格式
     * @param hssfCell
     * @return
     */
    @SuppressWarnings({ "static-access", "deprecation" })
    public static String getHValue(HSSFCell hssfCell){
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            String cellValue = "";
            if(HSSFDateUtil.isCellDateFormatted(hssfCell)){
                Date date = HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue());
                cellValue = sdf.format(date);
            }else{
                DecimalFormat df = new DecimalFormat("#.##");
                cellValue = df.format(hssfCell.getNumericCellValue());
                String strArr = cellValue.substring(cellValue.lastIndexOf(POINT)+1,cellValue.length());
                if(strArr.equals("00")){
                    cellValue = cellValue.substring(0, cellValue.lastIndexOf(POINT));
                }
            }
            return cellValue;
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
    /**
     * 单元格格式
     * @param xssfCell
     * @return
     */
    public static String getXValue(XSSFCell xssfCell){
        if (xssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else if (xssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            String cellValue = "";
            if(XSSFDateUtil.isCellDateFormatted(xssfCell)){
                Date date = XSSFDateUtil.getJavaDate(xssfCell.getNumericCellValue());
                cellValue = sdf.format(date);
            }else{
                DecimalFormat df = new DecimalFormat("#.##");
                cellValue = df.format(xssfCell.getNumericCellValue());
                String strArr = cellValue.substring(cellValue.lastIndexOf(POINT)+1,cellValue.length());
                if(strArr.equals("00")){
                    cellValue = cellValue.substring(0, cellValue.lastIndexOf(POINT));
                }
            }
            return cellValue;
        } else {
            return String.valueOf(xssfCell.getStringCellValue());
        }
    }
    /**
     * 自定义xssf日期工具类
     * @author lp
     *
     */
    static class XSSFDateUtil extends DateUtil{
        protected static int absoluteDay(Calendar cal, boolean use1904windowing) {
            return DateUtil.absoluteDay(cal, use1904windowing);
        }
    }

    public static void createExcel(ExcelVo excelVo,
                                   HttpServletResponse response, String character) throws IOException {
        // 保证不乱码
        response.setContentType("application/x-msdownload;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        if ("utf-8".equals(character)) {
            response.setHeader("Content-Disposition", "attachment;"
                    + " filename="
                    + URLEncoder
                    .encode(excelVo.getFileName() + ".xls", "utf-8"));
        } else {
            response.setHeader("Content-Disposition", "attachment;"
                    + " filename="
                    + new String(excelVo.getFileName().getBytes("GB2312"),
                    "ISO-8859-1") + ".xls");
        }
        // 创建输出流
        OutputStream os = response.getOutputStream();
        // 创建工作表
        HSSFWorkbook workBook = new HSSFWorkbook();
        SheetVo sheet = excelVo.getSheet();
        while (sheet != null) {
            HSSFSheet workSheet = workBook.createSheet(sheet.getName());
            HSSFRow row = null;
            CellVo cell = sheet.getCell();
            int a = -1;
            while (cell != null) {
                if (a != cell.getRowNo()){
                    a = cell.getRowNo();
                    row = workSheet.createRow(cell.getRowNo());
                }

                HSSFCell workCell = row.createCell(cell.getColNo());
                workCell = setCellValue(workCell,cell.getText());
                cell = sheet.getCell();
            }
            sheet = excelVo.getSheet();
        }
        workBook.write(os);
        workBook.close();
        os.close();
    }

    /**
     *
     * @param serviceResult
     * @param response
     * excel_title(String[])  excel标题 / result_list(List<List>) excel内容
     * @throws IOException
     */
    public static void exportExcel(Map<String, Object> serviceResult, HttpServletResponse response) throws IOException {
        String character = "UTF-8";
        // 导出excel
        createExcel(getExcelVo(serviceResult), response, character);
    }

    public static  ExcelVo getExcelVo(Map<String, Object> serviceResult){
        // 新建excel对象
        ExcelVo excelVo = new ExcelVo();
        // 设置excel文件名
        excelVo.setFileName(serviceResult.get("file_name") == null? "错误日志":serviceResult.get("file_name") + "");
        // 创建sheet
        SheetVo sheetVo = new SheetVo();
        sheetVo.setSheetNo(1);
        sheetVo.setName("明细");
        excelVo.addSheet(sheetVo);
        // 创建单元格
        CellVo cellVoFirst = new CellVo();
        cellVoFirst.setText(serviceResult.get("text") == null?"明细":serviceResult.get("text") + "");
        cellVoFirst.setColNo(0);
        cellVoFirst.setRowNo(0);
        sheetVo.addCell(cellVoFirst);
        // 设置excel标题
        Object excelTitle = serviceResult.get("excel_title");
        String[] titles = {"内容","原因"};
        if (excelTitle != null){
            titles = (String[]) serviceResult.get("excel_title");
        }

        for (int i = 0; i < titles.length; i++) {
            cellVoFirst = new CellVo();
            cellVoFirst.setColNo(i);
            cellVoFirst.setRowNo(1);
            sheetVo.addCell(cellVoFirst);
            cellVoFirst.setText(titles[i]);
        }

        Object result = serviceResult.get("result_list");
        if (result != null){
            List<List> resultList = (List<List>) result;
            for (int i = 0; i < resultList.size(); i++) {
                List list = resultList.get(i);
                // 内容数组
                for (int j = 0; j < list.size(); j++) {
                    cellVoFirst = new CellVo();
                    cellVoFirst.setText(list.get(j) + "");
                    cellVoFirst.setColNo(j);
                    cellVoFirst.setRowNo(i + 2);
                    sheetVo.addCell(cellVoFirst);
                }
            }
        }
        return excelVo;
    }
    /**
     *
     * @param serviceResult
     * excel_title(String[])  excel标题 / result_list(List<List>) excel内容
     * @throws IOException
     */
    public static String exportExcelBase64(Map<String, Object> serviceResult, HttpServletRequest request) throws IOException {
        ExcelVo excelVo = getExcelVo(serviceResult);
        // 获取excel Base64
        return createExcelBase64(excelVo,request);
    }
    public static String createExcelBase64(ExcelVo excelVo, HttpServletRequest request) throws IOException {
        String realPath = request.getServletContext().getRealPath("/");
        File file = new File(realPath + "/export_" +new Date().getTime()+ ".xls");
        file.createNewFile();
        // 创建工作表
        HSSFWorkbook workBook = new HSSFWorkbook();
        SheetVo sheet = excelVo.getSheet();
        while (sheet != null) {
            HSSFSheet workSheet = workBook.createSheet(sheet.getName());
            HSSFRow row = null;
            CellVo cell = sheet.getCell();
            int a = -1;
            while (cell != null) {
                if (a != cell.getRowNo()){
                    a = cell.getRowNo();
                    row = workSheet.createRow(cell.getRowNo());
                }

                HSSFCell workCell = row.createCell(cell.getColNo());
                workCell = setCellValue(workCell,cell.getText());
                cell = sheet.getCell();
            }
            sheet = excelVo.getSheet();
        }
        workBook.write(file);
        workBook.close();
        return file.getName();
    }

    private static HSSFCell setCellValue( HSSFCell workCell,Object value){
        if (value instanceof Double){
            workCell.setCellValue((Double) value);
        }else if (value instanceof Integer){
            workCell.setCellValue((Integer) value);
        }else if (value instanceof Float){
            workCell.setCellValue((Integer) value);
        }else {
            workCell.setCellValue((String) value);
        }

        return workCell;
    }


    /**
     *
     * @param serviceResult
     * @param response
     * excel_title(String[])  excel标题 / result_list(List<List>) excel内容
     * @throws IOException
     */
    public static void exportExcelNew(Map<String, Object> serviceResult, HttpServletResponse response) throws IOException {
        String character = "UTF-8";
        // 导出excel
        createExcel(getExcelVoNew(serviceResult), response, character);
    }

    public static  ExcelVo getExcelVoNew(Map<String, Object> serviceResult){
        // 新建excel对象
        ExcelVo excelVo = new ExcelVo();
        // 设置excel文件名
        excelVo.setFileName(serviceResult.get("file_name") == null? "错误日志":serviceResult.get("file_name") + "");
        // 创建sheet
        SheetVo sheetVo = new SheetVo();
        sheetVo.setSheetNo(1);
        sheetVo.setName("明细");
        excelVo.addSheet(sheetVo);
        // 创建单元格
        CellVo cellVoFirst = new CellVo();
        // 设置excel标题
        Object excelTitle = serviceResult.get("excel_title");
        String[] titles = {"内容","原因"};
        if (excelTitle != null){
            titles = (String[]) serviceResult.get("excel_title");
        }

        for (int i = 0; i < titles.length; i++) {
            cellVoFirst = new CellVo();
            cellVoFirst.setColNo(i);
            cellVoFirst.setRowNo(0);
            sheetVo.addCell(cellVoFirst);
            cellVoFirst.setText(titles[i]);
        }

        Object result = serviceResult.get("result_list");
        if (result != null){
            List<List> resultList = (List<List>) result;
            for (int i = 0; i < resultList.size(); i++) {
                List list = resultList.get(i);
                // 内容数组
                for (int j = 0; j < list.size(); j++) {
                    cellVoFirst = new CellVo();
                    cellVoFirst.setText(list.get(j));
                    cellVoFirst.setColNo(j);
                    cellVoFirst.setRowNo(i + 1);
                    sheetVo.addCell(cellVoFirst);
                }
            }
        }
        return excelVo;
    }
}
