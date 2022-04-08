package com.itheima.test;

import org.apache.jute.compiler.JFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author 侯孟珂
 * @date 2022/4/3-16:31
 */
public class POITest {
    @Test
    public void test1() throws Exception{
        //加载指定文件，创建一个Excel对象
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("E:\\poi.xlsx")));
        //读取exce中的sheet标签
        XSSFSheet sheet = excel.getSheetAt(0);
        //遍历sheet标签页，获取每一行数据
        for (Row row :sheet){
            for(Cell cell :row){
                //遍历行，获取单元格数据
                System.out.println(cell.getStringCellValue());
            }
        }
        //关闭资源
        excel.close();


    }
    //通过poi向excel写入数据
    @Test
    public void test2() throws Exception{
        //在内存中创建一个Excel文件
        XSSFWorkbook workbook = new XSSFWorkbook();
//创建工作表，指定工作表名称
        XSSFSheet sheet = workbook.createSheet("POI测试");

//创建行，0表示第一行
        XSSFRow row = sheet.createRow(0);
//创建单元格，0表示第一个单元格
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("名称");
        row.createCell(2).setCellValue("年龄");

        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("1");
        row1.createCell(1).setCellValue("小明");
        row1.createCell(2).setCellValue("10");

        XSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("2");
        row2.createCell(1).setCellValue("小王");
        row2.createCell(2).setCellValue("20");

//通过输出流将workbook对象下载到磁盘
        FileOutputStream out = new FileOutputStream("E:\\itcast.xlsx");
        workbook.write(out);
        out.flush();
        out.close();
        workbook.close();



    }
}
