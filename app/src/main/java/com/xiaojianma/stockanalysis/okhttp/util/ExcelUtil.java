package com.xiaojianma.stockanalysis.okhttp.util;

import android.util.Log;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public final class ExcelUtil {

    private static final String TAG = "ExcelUtil";

    private static final String NUMS = "0123456789.";

    private ExcelUtil() {

    }

    public static void readExcel(File file) {
        try {
            Workbook book = Workbook.getWorkbook(file);
            book.getNumberOfSheets();
            // 获得第一个工作表对象
            Sheet sheet = book.getSheet(1);
            int Rows = sheet.getRows();
            int Cols = sheet.getColumns();
            System.out.println("当前工作表的名字:" + sheet.getName());
            System.out.println("总行数:" + Rows);
            System.out.println("总列数:" + Cols);
            for (int i = 0; i < Cols; ++i) {
                for (int j = 0; j < Rows; ++j) {
                    // getCell(Col,Row)获得单元格的值
                    System.out.print((sheet.getCell(i, j)).getContents() + "\t");
                }
                System.out.print("\n");
            }
            // 得到第一列第一行的单元格
            Cell cell1 = sheet.getCell(0, 0);
            String result = cell1.getContents();
            System.out.println(result);
            book.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createExcel() {
        try {
            // 创建或打开Excel文件
            WritableWorkbook book = Workbook.createWorkbook(new File(
                    "mnt/sdcard/test.xls"));

            // 生成名为“第一页”的工作表,参数0表示这是第一页
            WritableSheet sheet1 = book.createSheet("第一页", 0);
            WritableSheet sheet2 = book.createSheet("第三页", 2);

            // 在Label对象的构造函数中,元格位置是第一列第一行(0,0)以及单元格内容为test
            Label label = new Label(0, 0, "test");

            // 将定义好的单元格添加到工作表中
            sheet1.addCell(label);

            /*
             * 生成一个保存数字的单元格.必须使用Number的完整包路径,否则有语法歧义
             */
            jxl.write.Number number = new jxl.write.Number(1, 0, 555.12541);
            sheet2.addCell(number);

            // 写入数据并关闭文件
            book.write();
            book.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * jxl暂时不提供修改已经存在的数据表,这里通过一个小办法来达到这个目的,不适合大型数据更新! 这里是通过覆盖原文件来更新的.
     *
     * @param file        需要更新excel表格文件
     * @param debtFile    资产负债表excel表格文件
     * @param benefitFile 利润表excel表格文件
     * @param cashFile    现金流量表excel表格文件
     */
    public static void updateExcel(File file, File debtFile, File benefitFile, File cashFile) {
        Workbook rwb = null;
        WritableWorkbook wwb = null;
        File dstFile = new File(file.getParentFile(), "newFile.xls");
        try {
            rwb = Workbook.getWorkbook(file);
            wwb = Workbook.createWorkbook(dstFile, rwb);// copy
            WritableSheet srcSheet = wwb.getSheet(1);
            int srcRows = 1;
            srcRows = updateDebtData(debtFile, srcSheet, srcRows);
            srcRows = updateDebtData(benefitFile, srcSheet, srcRows);
            updateDebtData(cashFile, srcSheet, srcRows);
            wwb.write();
        } catch (Exception e) {
            Log.e(TAG, "yejian updateExcel excel exception: " + e.toString());
        } finally {
            try {
                if (wwb != null) {
                    wwb.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "yejian updateExcel excel wwb.close() exception: " + e.toString());
            }
            try {
                if (rwb != null) {
                    rwb.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "yejian updateExcel excel rwb.close() exception: " + e.toString());
            }
            dstFile.renameTo(file);
        }
    }

    /**
     * jxl暂时不提供修改已经存在的数据表,这里通过一个小办法来达到这个目的,不适合大型数据更新! 这里是通过覆盖原文件来更新的.
     *
     * @param file        需要更新excel表格文件
     * @param debtFile    资产负债表excel表格文件
     * @param benefitFile 利润表excel表格文件
     * @param cashFile    现金流量表excel表格文件
     */
    public static void updateExcelByPOI(File file, File debtFile, File benefitFile, File cashFile) {
        try {
            //创建工作簿
            FileInputStream fileInputStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            fileInputStream.close();
            //读取第一个工作表(这里的下标与list一样的，从0开始取，之后的也是如此)
            XSSFSheet sheet = workbook.getSheetAt(1);
            int rowNum = 1;
            //获取第一行的数据
            XSSFRow row = sheet.getRow(0);
            rowNum = fillDebtDate(debtFile, sheet, rowNum);
            rowNum = fillDebtDate(benefitFile, sheet, rowNum);
            fillDebtDate(cashFile, sheet, rowNum);
//            refreshFormula(workbook);
            refreshFormula(workbook, 2);
            refreshFormula(workbook, 3);
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
                outputStream.flush();
            }
        } catch (IOException e) {
            Log.e(TAG, "yejian updateExcelByPOI exception: " + e.toString());
        }
    }

    /**
     * 刷新公式
     */
    private static void refreshFormula(XSSFWorkbook workbook, int sheetIndex) {
        try {
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 0; i <= lastRowNum; i++) {
                XSSFRow row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                for (int j = 0; j <= row.getLastCellNum(); j++) {
                    XSSFCell cell = row.getCell(j);
                    if (cell != null) {
                        cell.setCellFormula(cell.getCellFormula());
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "yejian refreshFormula exception: " + e.toString());
        }
    }

    private static int fillDebtDate(File updateFile, XSSFSheet updateSheet, int rowNum) throws IOException {
        Workbook debtBook = null;
        try {
            debtBook = Workbook.getWorkbook(updateFile);
        } catch (Exception e) {
            Log.e(TAG, "yejian updateDebtData exception: " + e.toString() + ", file is: " + updateFile.getAbsolutePath());
        }
        Sheet sheet = debtBook.getSheet(0);
        for (int row = 0; row < sheet.getRows(); row++) {
            for (int col = 0; col < sheet.getColumns(); col++) {
                Object contents = sheet.getCell(col, row).getContents().trim();
                XSSFRow curRow = updateSheet.getRow(rowNum);
                if (curRow == null) {
                    curRow = updateSheet.createRow(rowNum);
                }
                XSSFCell curRowCell = curRow.getCell(col);
                if (curRowCell == null) {
                    curRowCell = curRow.createCell(col);
                }
                fillCellValue(contents, curRowCell);
            }
            rowNum++;
        }
        return rowNum;
    }

    private static void fillCellValue(Object contents, XSSFCell curRowCell) {
//        if (contents != null) {
//            String str = contents.toString().trim();
//            if (str.isEmpty()) {
//                return;
//            }
//            curRowCell.setCellValue(str);
//        }
        try {
            if (contents != null) {
                String str = contents.toString().trim();
                if (str.isEmpty()) {
                    return;
                }
                try {
//                    if (!str.contains(".")) {
//                        curRowCell.setCellValue(Double.valueOf(str + ".00"));
//                    } else {
//                        curRowCell.setCellValue(Double.valueOf(str).doubleValue());
//                    }
                    curRowCell.setCellValue(Double.valueOf(str).doubleValue());
                } catch (Exception e) {
                    Log.e(TAG, "yejian fillCellValue parseDouble error: " + e.toString());
                    curRowCell.setCellValue(str);
                }

//                if (!str.startsWith("--") && str.startsWith("-")) {
//                    try {
//                        if (str.contains(".")) {
//                            curRowCell.setCellValue(Double.parseDouble(str));
////                            curRowCell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC);
//                        } else {
//                            curRowCell.setCellValue(Long.parseLong(str));
////                            curRowCell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC);
//                        }
//                        Log.e(TAG, "yejian fillCellValue getCellType: " + curRowCell.getCellType());
//                        return;
//                    } catch (Exception e) {
//                        Log.e(TAG, "yejian fillCellValue error 1: " + e.toString());
//                    }
//                }
//                for (char ch : str.toCharArray()) {
//                    if (!NUMS.contains(ch + "")) {
//                        curRowCell.setCellValue(str);
//                        Log.e(TAG, "yejian fillCellValue getCellType: " + curRowCell.getCellType());
//                        return;
//                    }
//                }
//                if (str.contains(".")) {
//                    curRowCell.setCellValue(Double.parseDouble(str));
////                    curRowCell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC);
//                } else {
//                    curRowCell.setCellValue(Long.parseLong(str));
////                    curRowCell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC);
//                }
                Log.e(TAG, "yejian fillCellValue getCellType: " + curRowCell.getCellType());
            }
        } catch (Exception e) {
            Log.e(TAG, "yejian fillCellValue error 2: " + e.toString());
        }
    }

    private static int updateDebtData(File updateFile, WritableSheet srcSheet, int srcRows) {
        WritableCell cell;
        Workbook debtBook = null;
        try {
            debtBook = Workbook.getWorkbook(updateFile);
        } catch (Exception e) {
            Log.e(TAG, "yejian updateDebtData exception: " + e.toString() + ", file is: " + updateFile.getAbsolutePath());
        }
        Sheet sheet = debtBook.getSheet(0);
        int rows = srcRows;
        for (int col = 0; col < sheet.getColumns() - 1; col++) {
            rows = srcRows;
            for (int row = 2; row < sheet.getRows() - 1; row++) {
                String contents = sheet.getCell(col, row).getContents().trim();
//                if (row == srcRows && (contents == null || "".equals(contents))) {
//                    // 如果该行内容为空，直接跳过改行
//                    continue;
//                }
                cell = srcSheet.getWritableCell(col, rows++);
                if (cell == null) {
                    continue;
                }
                CellFormat format = cell.getCellFormat();
                Label label = new Label(col, rows - 1, contents);
                if (format != null) {
                    label.setCellFormat(format);
                }
                if (label != null) {
                    try {
                        srcSheet.addCell(label);
                    } catch (WriteException e) {
                        Log.e(TAG, "yejian updateDebtData addCell exception: " + e.toString() + ", file is: " + updateFile.getAbsolutePath());
                    }
                }
            }
        }
        if (debtBook != null) {
            debtBook.close();
        }
        return rows;
    }

    public static void writeExcel(String filePath) {
        try {
            // 创建工作薄
            WritableWorkbook wwb = Workbook.createWorkbook(new File(filePath));
            // 创建工作表
            WritableSheet ws = wwb.createSheet("Sheet1", 0);
            // 添加标签文本
            // Random rnd = new Random((new Date()).getTime());
            // int forNumber = rnd.nextInt(100);
            // Label label = new Label(0, 0, "test");
            // for (int i = 0; i < 3; i++) {
            // ws.addCell(label);
            // ws.addCell(new jxl.write.Number(rnd.nextInt(50), rnd
            // .nextInt(50), rnd.nextInt(1000)));
            // }
            // 添加图片(注意此处jxl暂时只支持png格式的图片)
            // 0,1分别代表x,y 2,5代表宽和高占的单元格数
            ws.addImage(new WritableImage(5, 5, 2, 5, new File(
                    "mnt/sdcard/nb.png")));
            wwb.write();
            wwb.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
