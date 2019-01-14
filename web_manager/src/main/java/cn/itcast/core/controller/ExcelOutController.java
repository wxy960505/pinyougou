package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.service.ExcelOutService;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/derived")
public class ExcelOutController {

    @Reference
    private ExcelOutService excelOutService;

    /*品牌*/
    @RequestMapping("/brandGet")
public Result brandTest() {
        try {

            List<String> list = getDataFromExcel("C:"+ File.separator +"Users"+ File.separator +"g5767"+ File.separator +"Desktop"+ File.separator  + "品牌表.xls");
            excelOutService.getBrandExcel(list);
            return new Result(true, "导入");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "导入失败");
        }
    }
    /*规格*/
    @RequestMapping("/specGet")
    public Result specTest() {
        try {
            List<String> specList = getDataFromExcel("C:"+ File.separator +"Users"+ File.separator +"g5767"+ File.separator +"Desktop"+ File.separator  + "规格表.xls");
            excelOutService.getSpecificationExcel(specList);
            return new Result(true, "导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "导入失败");
        }
    }
    /*模板导入*/
    @RequestMapping("/templateGet")
    public Result templateTest() {
        try {
            List<String> tempList = getDataFromExcel("C:"+ File.separator +"Users"+ File.separator +"g5767"+ File.separator +"Desktop"+ File.separator  + "模板表.xls");
            excelOutService.getTemplateExcel(tempList);
            return new Result(true, "导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "导入失败");
        }
    }

    /*分类导入*/
    @RequestMapping("/categoryGet")
    public Result categoryTest() {
        try {
            List<String> categoryList = getDataFromExcel("C:"+ File.separator +"Users"+ File.separator +"g5767"+ File.separator +"Desktop"+ File.separator  + "分类表.xls");
            excelOutService.getCategoryExcel(categoryList);
            return new Result(true, "导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "导入失败");
        }
    }
    /*读取数据方法*/
    public static List<String> getDataFromExcel(String filePath) {
        List<Map<String, String>> listMap = new ArrayList<>();
        //filePath = "D:\\data\\品牌.xls";
        //判断是否为excel类型文件
        if(!filePath.endsWith(".xls")&&!filePath.endsWith(".xlsx"))
        {
            System.out.println("文件不是excel类型");
        }
        FileInputStream fis =null;
        Workbook wookbook = null;
        try
        {
            //获取一个绝对地址的流
            fis = new FileInputStream(filePath);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            //2003版本的excel，用.xls结尾
            wookbook = new HSSFWorkbook(fis);//得到工作簿
        }
        catch (Exception ex)
        {
            //ex.printStackTrace();
            try
            {
                //2007版本的excel，用.xlsx结尾
                wookbook = new XSSFWorkbook(fis);//得到工作簿
            } catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //得到一个工作表
        Sheet sheet = wookbook.getSheetAt(0);
        //获得表头
        Row rowHead = sheet.getRow(0);
        //获得数据的总行数
        int totalRowNum = sheet.getLastRowNum();
        Map<String,String> map=new HashMap<>();
        List<String> list=new ArrayList<>();
        //获得所有数据
        for(int i = 1 ; i <= totalRowNum ; i++) {
            //获得第i行对象
            Row row = sheet.getRow(i);
            //获取总列数
            int cells = row.getPhysicalNumberOfCells();
            for (int h = 0; h < cells; h++) {
                //获得获得第i行第h列的 String类型对象
                Cell cell = row.getCell((short)h);//row=null,空指针
                String s = cell.getStringCellValue().toString();
                list.add(s);
            }
        }
        return list;
    }
}
