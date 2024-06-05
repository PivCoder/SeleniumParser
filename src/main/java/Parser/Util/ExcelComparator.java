package Parser.Util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExcelComparator {
    public static boolean compareExcelFiles(File file1, File file2) throws IOException {
        try (InputStream is1 = new FileInputStream(file1);
             InputStream is2 = new FileInputStream(file2);
             Workbook wb1 = new XSSFWorkbook(is1);
             Workbook wb2 = new XSSFWorkbook(is2)) {

            if (wb1.getNumberOfSheets() != wb2.getNumberOfSheets()) {
                return false;
            }

            for (int i = 0; i < wb1.getNumberOfSheets(); i++) {
                Sheet sheet1 = wb1.getSheetAt(i);
                Sheet sheet2 = wb2.getSheetAt(i);

                if (!compareSheets(sheet1, sheet2)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean compareSheets(Sheet sheet1, Sheet sheet2) {
        if (sheet1.getLastRowNum() != sheet2.getLastRowNum()) {
            return false;
        }

        for (int i = 0; i <= sheet1.getLastRowNum(); i++) {
            Row row1 = sheet1.getRow(i);
            Row row2 = sheet2.getRow(i);

            if (!compareRows(row1, row2)) {
                return false;
            }
        }
        return true;
    }

    private static boolean compareRows(Row row1, Row row2) {
        if ((row1 == null && row2 != null) || (row1 != null && row2 == null)) {
            return false;
        }
        if (row1 != null) {
            if (row1.getLastCellNum() != row2.getLastCellNum()) {
                return false;
            }

            for (int i = 0; i < row1.getLastCellNum(); i++) {
                Cell cell1 = row1.getCell(i);
                Cell cell2 = row2.getCell(i);

                if (!compareCells(cell1, cell2)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean compareCells(Cell cell1, Cell cell2) {
        if ((cell1 == null && cell2 != null) || (cell1 != null && cell2 == null)) {
            return false;
        }
        if (cell1 != null) {
            if (cell1.getCellType() != cell2.getCellType()) {
                return false;
            }

            switch (cell1.getCellType()) {
                case STRING:
                    if (!cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
                        return false;
                    }
                    break;
                case NUMERIC:
                    if (cell1.getNumericCellValue() != cell2.getNumericCellValue()) {
                        return false;
                    }
                    break;
                case BOOLEAN:
                    if (cell1.getBooleanCellValue() != cell2.getBooleanCellValue()) {
                        return false;
                    }
                    break;
                case FORMULA:
                    if (!cell1.getCellFormula().equals(cell2.getCellFormula())) {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }
        return true;
    }
}
