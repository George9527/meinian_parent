package com.atguigu.meinian.test;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;

public class ExcelTest {

//    // 往Excel文件写内容
//    @Test
//    public void test02() throws Exception{
//        // 创建excel对象
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        // 创建工作表
//        XSSFSheet sheet = workbook.createSheet("abc");
//        // 创建行对象
//        XSSFRow row = sheet.createRow(0);
//        // 创建列对象
//        row.createCell(0).setCellValue("编号");
//        row.createCell(1).setCellValue("姓名");
//        row.createCell(2).setCellValue("年龄");
//        // 设置第二行对象
//        XSSFRow row1 = sheet.createRow(1);
//        row1.createCell(0).setCellValue("1");
//        row1.createCell(1).setCellValue("小明");
//        row1.createCell(2).setCellValue("18");
//        // 设置第三行对象
//        XSSFRow row2 = sheet.createRow(2);
//        row2.createCell(0).setCellValue("2");
//        row2.createCell(1).setCellValue("小红");
//        row2.createCell(2).setCellValue("20");
//        // 使用IO流进行读写
//        FileOutputStream fileOutputStream = new FileOutputStream("D:\\Test02.xlsx");
//        workbook.write(fileOutputStream);
//        fileOutputStream.flush();
//        fileOutputStream.close();
//        workbook.close();
//    }


    // 读取Excel文件内容
//    @Test
//    public void test01() throws Exception{
//        // 创建excel对象
//        XSSFWorkbook workbook = new XSSFWorkbook("D:\\Test02.xlsx");
//        // 获取标签
//        XSSFSheet sheetAt = workbook.getSheetAt(0);
//        // 循环读取行的数据
//        for (Row row : sheetAt) {
//            for (Cell cell : row) {
//                // 获取列数据
//                String stringCellValue = cell.getStringCellValue();
//                System.out.println( stringCellValue);
//            }
//        }
//        workbook.close();
//    }
}
