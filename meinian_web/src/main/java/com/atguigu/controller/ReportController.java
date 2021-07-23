package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.service.MemberService;
import com.atguigu.service.ReportService;
import com.atguigu.service.SetmealService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.atguigu.constant.MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private ReportService reportService;

    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;

    // 导出Excel数据表
    //'/report/exportBusinessReport.do'
    @RequestMapping("exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request,HttpServletResponse response) {
        try {
            // 数据存放在 map 集合中 看运营数据统计 中的map
            // 从中获取数据写入excel中

            // 获取 运营数据 的结果 map
            Map<String, Object> result = reportService.getBusinessReportData();
            //取出返回结果数据，准备将报表数据写入到Excel文件中
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            //获得Excel模板文件绝对路径
            //file.separator这个代表系统目录中的间隔符，说白了就是斜线，不过有时候需要双线，有时候是单线，你用这个静态变量就解决兼容问题了。
            // getSession() --- 会话域 getServletContext() --- 上写文 getRealPath --- 真实路径
            String temlateRealPath = request.getSession().getServletContext().getRealPath("template") +
                    File.separator + "report_template.xlsx";

            //读取模板文件创建Excel表格对象
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(temlateRealPath)));
            XSSFSheet sheet = workbook.getSheetAt(0);

            // 获取 需要写入数据的表格坐标 再 写入数据
            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);//日期

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日出游数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周出游数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月出游数

            int rowNum = 12;
            for(Map map : hotSetmeal){//热门套餐
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                Double price = (Double) map.get("price");
                row = sheet.getRow(rowNum ++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(price); // 价格
                row.getCell(7).setCellValue(proportion.doubleValue());//占比

            }

            //通过输出流进行文件下载
            ServletOutputStream out = response.getOutputStream();
            // 下载的数据类型（excel类型）
            response.setContentType("application/vnd.ms-excel");
            // 设置下载形式(通过附件的形式下载)
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(out);

            out.flush();
            out.close();
            workbook.close();

            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL,null);
        }
    }

    // ======================================================================================================


    // 运营数据统计
    // axios.get("/report/getBusinessReportData.do")
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        // 返回的数据使用 map封装
        try {
            Map<String, Object> map = reportService.getBusinessReportData();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_SUCCESS);
        }
    }

    // ======================================================================================================


    // 套餐预约占比
    // axios.get("/report/getSetmealReport.do")
    @RequestMapping("getSetmealReport")
    public Result getSetmealReport() {
        // 查询套餐的数量 返回值：List 旅游项目 map
        List<Map<String, Object>> lists = setmealService.findSetmealCount();
        // setmealNames 就是 list集合中的 name 遍历取出
        // 取出后，再存进进的集合中
        List<String> setmealNames = new ArrayList<>();
        for (Map<String, Object> list : lists) {
            String name = (String)list.get("name");
            setmealNames.add(name);
        }
        // 将获取的 setmealNames setmealCount(lists) 存进 map 中 数据结构如下
        Map map = new HashMap<>();
        map.put("setmealNames",setmealNames);
        map.put("setmealCount",lists);
        // 返回前端
        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
    }

    /**
     * res.data.data.setmealNames: ['澳门威尼斯人商圈酒店双飞3-5日自由行套餐', '香港九龙尖沙咀商圈双飞3-8日自由行套餐']
     * res.data.data.setmealCount:[
     {value: 335, name: '澳门威尼斯人商圈酒店双飞3-5日自由行套餐'},
     {value: 310, name: '香港九龙尖沙咀商圈双飞3-8日自由行套餐'},
     ]
     */


  // ======================================================================================================

    // 注册会员的月份统计
    /**
     *返回Result(flag,message,data):data=Map<String,Object>
     *     map：key             value
     *          months          List<String>   (["2018-12","2019-01","2019-02","2019-03","2019-04","2019-05"])
     *          memberCount     List<Integer>  ([5, 20, 36, 40, 50, 60])
     */

    // axios.get("/report/getMemberReport.do")
    @RequestMapping("getMemberReport")
    public Result getMemberReport() {
        // 获取过去12个月的数据，每个月用户注册的数量
        // 获取日历对象 Calender
        Calendar calendar = Calendar.getInstance();
        // 第一个参数：表示需要的月份(过去的月份 -12)
        calendar.add(Calendar.MONTH, -12);
        // 使用集合 迭代 接收 数据
        List<String> dataLists = new ArrayList<>();
            // 迭代添加
        for (int i = 0; i < 12; i++) {
            // 日历格式化 simpleDataFormat 后再封装
            calendar.add(Calendar.MONTH, 1);
            // 格式化当前获取的日期
            dataLists.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
        }

        // 新建一个HashMap，第一个 key 为 months 类型为 data=Map<String,Object>
        Map<String,Object> map = new HashMap<>();
        map.put("months",dataLists);

        // 统计每月注册人数
        List<Integer> lists = memberService.findMemberCount(dataLists);

        map.put("memberCount",lists);

        // 返回值Result(flag,message,data):data=Map<String,Object>
        return new Result(true, GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }


}
