package com.platformcommons.platform.service.search.application.utility;

import com.platformcommons.platform.service.search.domain.TenantUser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<TenantUser> excelToTenantUser(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<TenantUser> users = new ArrayList<TenantUser>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                TenantUser user = new TenantUser();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            user.setId((long) currentCell.getNumericCellValue());
                            break;
                        case 1:
                            user.setUser_login(currentCell.getStringCellValue());
                            break;
                        case 2:
                            user.setCompleteName(currentCell.getStringCellValue());
                            break;
                        case 3:
                            user.setMobile((long) currentCell.getNumericCellValue());
                            break;
                        case 4:
                            user.setEmail(currentCell.getStringCellValue());
                            break;
                        case 5:
                            user.setGender(currentCell.getStringCellValue());
                            break;
                        case 6:
                            user.setCity(currentCell.getStringCellValue());
                            break;
                        case 7:
                            user.setState(currentCell.getStringCellValue());
                            break;
                        case 8:
                            user.setPincode((long) currentCell.getNumericCellValue());
                            break;
                        case 9:
                            user.setTenant_id((long) currentCell.getNumericCellValue());
                            break;
                        case 10:
                            user.setTenant_login(currentCell.getStringCellValue());
                            break;
                        case 11:
                            user.setRoles_codes(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                users.add(user);
            }
            workbook.close();
            return users;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}

