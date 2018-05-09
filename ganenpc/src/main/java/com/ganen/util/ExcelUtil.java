package com.ganen.util;

import com.ganen.entity.CompanyBilling;
import com.ganen.entity.EmployeeOrder;
import com.ganen.entity.Service;
import com.ganen.entity.ServiceOrder;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;

public class ExcelUtil {

    //导出服务明细
    public static Workbook createWorkService(CompanyBilling billing, List<ServiceOrder> orderList, String[] headText) {
        //创建excel
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
        //创建sheet页
        Sheet sheet = wb.createSheet("员工发放明细");
        //创建第一行
        Row row = sheet.createRow(0);
        //创建两种单元格格式
        CellStyle head = wb.createCellStyle();
        CellStyle body = wb.createCellStyle();

        //创建两个字体
        Font headFont = wb.createFont();
        Font bodyFont = wb.createFont();

        // 创建第一种字体样式（用于列名）
        headFont.setFontHeightInPoints((short) 10);
        headFont.setColor(IndexedColors.BLACK.getIndex());
        headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        bodyFont.setFontHeightInPoints((short) 10);
        bodyFont.setColor(IndexedColors.BLACK.getIndex());

        // 设置第一种单元格的样式（用于列名）
        head.setFont(headFont);
        head.setBorderLeft(CellStyle.BORDER_THIN);
        head.setBorderRight(CellStyle.BORDER_THIN);
        head.setBorderTop(CellStyle.BORDER_THIN);
        head.setBorderBottom(CellStyle.BORDER_THIN);
        head.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        body.setFont(bodyFont);
        body.setBorderLeft(CellStyle.BORDER_THIN);
        body.setBorderRight(CellStyle.BORDER_THIN);
        body.setBorderTop(CellStyle.BORDER_THIN);
        body.setBorderBottom(CellStyle.BORDER_THIN);
        body.setAlignment(CellStyle.ALIGN_CENTER);

        for (int i = 0; i < headText.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headText[i]);
            cell.setCellStyle(head);
        }
//        {"甲方全称","甲方税号","实发总额","个税","服务费","付款总额","发票类目","乙方全称","乙方账户"};

        for (int i = 0; i < orderList.size(); i++) {
            Row row1 = sheet.createRow(i + 1);
            ServiceOrder serviceOrder = orderList.get(i);
            for (int j = 0; j < headText.length; j++) {
                Cell cell=row1.createCell(j);;
                switch (j) {
                    case 0:
                        cell.setCellValue(serviceOrder.getCompanyOrder().getCompany().getCompanyAllName());
                        break;
                    case 1:
                        cell.setCellValue(billing.getCompanyTaxNumber());
                        break;
                    case 2:
                        cell.setCellValue(serviceOrder.getServiceOrderPrice().subtract(serviceOrder.getServiceOrderServicePrice()).toString());
                        break;
                    case 3:
                        cell.setCellValue(serviceOrder.getCompanyOrder().getCompanyOrderTaxCount().toString());
                        break;
                    case 4:
                        cell.setCellValue(serviceOrder.getServiceOrderServicePrice().toString());
                        break;
                    case 5:
                        cell.setCellValue(serviceOrder.getServiceOrderPrice().toString());
                        break;
                    case 6:
                        cell.setCellValue(serviceOrder.getServiceOrderTicketCategory());
                        break;
                    case 7:
                        cell.setCellValue(serviceOrder.getService().getServiceCompanyName());
                        break;
                    case 8:
                        cell.setCellValue(serviceOrder.getService().getServiceOpenNumber());
                        break;
                }
                cell.setCellStyle(body);
            }

        }
        return wb;
    }

    //导出发放明细
    public static Workbook createWordGrant(List<EmployeeOrder> orderList, String[] headText) {
        //创建excel
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
        //创建sheet页
        Sheet sheet = wb.createSheet("员工发放明细");
        //创建第一行
        Row row = sheet.createRow(0);
        //创建两种单元格格式
        CellStyle head = wb.createCellStyle();
        CellStyle body = wb.createCellStyle();

        //创建两个字体
        Font headFont = wb.createFont();
        Font bodyFont = wb.createFont();

        // 创建第一种字体样式（用于列名）
        headFont.setFontHeightInPoints((short) 10);
        headFont.setColor(IndexedColors.BLACK.getIndex());
        headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        bodyFont.setFontHeightInPoints((short) 10);
        bodyFont.setColor(IndexedColors.BLACK.getIndex());

        // 设置第一种单元格的样式（用于列名）
        head.setFont(headFont);
        head.setBorderLeft(CellStyle.BORDER_THIN);
        head.setBorderRight(CellStyle.BORDER_THIN);
        head.setBorderTop(CellStyle.BORDER_THIN);
        head.setBorderBottom(CellStyle.BORDER_THIN);
        head.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        body.setFont(bodyFont);
        body.setBorderLeft(CellStyle.BORDER_THIN);
        body.setBorderRight(CellStyle.BORDER_THIN);
        body.setBorderTop(CellStyle.BORDER_THIN);
        body.setBorderBottom(CellStyle.BORDER_THIN);
        body.setAlignment(CellStyle.ALIGN_CENTER);

        for (int i = 0; i < headText.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headText[i]);
            cell.setCellStyle(head);
        }

        for (int i = 0; i < orderList.size(); i++) {
            Row row1 = sheet.createRow(i + 1);
            EmployeeOrder employeeOrder = orderList.get(i);
            for (int j = 0; j < headText.length; j++) {
                Cell cell=row1.createCell(j);;
                switch (j) {
                    case 0:
                        cell.setCellValue(employeeOrder.getEmployee().getEmployeeName());
                        break;
                    case 1:
                        cell.setCellValue(employeeOrder.getEmployee().getEmployeeCard());
                        break;
                    case 2:
                        cell.setCellValue(employeeOrder.getEmployee().getEmployeeCardCN());
                        break;
                    case 3:
                        cell.setCellValue(employeeOrder.getEmployee().getEmployeeBankNumber());
                        break;
                    case 4:
                        cell.setCellValue(employeeOrder.getEmployee().getEmployeeOpen());
                        break;
                    case 5:
                        cell.setCellValue(employeeOrder.getEmployee().getEmployeePhone());
                        break;
                    case 6:
                        cell.setCellValue(employeeOrder.getEmployee().getEmployeeOpenNumber());
                        break;
                    case 7:
                        cell.setCellValue(employeeOrder.getEmployeeSalary().toString());
                        break;
                    case 8:
                        cell.setCellValue(employeeOrder.getEmployeePrice().toString());
                        break;
                    case 9:
                        cell.setCellValue(employeeOrder.getEmployeeTax().toString());
                        break;
                }
                cell.setCellStyle(body);
            }

        }
        return wb;
    }


}
