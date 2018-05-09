package com.ganen.service.impl;

import com.ganen.service.IFileService;
import com.ganen.util.MatchService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

@Service
public class FileService implements IFileService {

    /**
     *
     * @param filePath 上传excel地址
     * @return 读取excel 计算服务公司
     * @throws IOException
     */
//    public Map<String, Object> readExcel(String filePath) throws IOException {
//        InputStream input = new FileInputStream(filePath);
//        //excel文件
//        Workbook workbook = null;
//        if (filePath.toLowerCase().endsWith(".xlsx")) {
//            workbook = new XSSFWorkbook(input);
//        } else {
//            workbook = new HSSFWorkbook(input);
//        }
//        //Sheet总页数
//        int numberOfSheets = workbook.getNumberOfSheets();
//        boolean outIs = true;
//        for (int i = 0; i < numberOfSheets; i++) {
//            //得到excel
//            Sheet sheet = workbook.getSheetAt(i);
//            //now迭代器
//            Iterator<Row> rowAll = sheet.iterator();
//            int rowNum = 0;
//            //循环每一行
//            while (rowAll.hasNext()) {
//                //整行
//                Row row = rowAll.next();
//                Iterator<Cell> cellAll = row.cellIterator();
//                int columnNum = 0;
//
//                while (cellAll.hasNext()) {
//                    System.out.println((columnNum++) + "列");
//                    Cell cell = cellAll.next();
//                    if (columnNum == 7 && !cell.getStringCellValue().trim().equals("")) {
//                        outIs = false;
//                    }
//                    switch (cell.getCellType()) {
//                        case Cell.CELL_TYPE_NUMERIC:
//                            double money = cell.getNumericCellValue();
//                            System.out.println(money);
//                            if (money > 0 && money <= 3500) {
//                                double basic = MatchService.basic(money);
//                                System.out.println("宁波沃山" + basic);
//                            } else if (money > 3500 && money <= 12000) {
//                                money = money / 2;
//                                double basic = MatchService.basic(money);
//                                System.out.println("宁波沃山" + basic);
//                                System.out.println("安徽知恩" + basic);
//                            } else if (money > 12000) {
//                                double optimize = MatchService.optimize(money);
//                                System.out.println("安徽知恩" + optimize);
//                            }
//                            break;
//                        case Cell.CELL_TYPE_STRING:
//                            break;
//                        case Cell.CELL_TYPE_BLANK:
//                            outIs = false;
//                            break;
//                    }
//                }
//                if (!outIs) {
//                    return null;
//                }
//            }
//        }
//        return null;
//    }
}
