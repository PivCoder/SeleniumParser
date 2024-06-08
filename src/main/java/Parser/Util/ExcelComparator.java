package Parser.Util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExcelComparator {
    public static boolean compareExcelFiles(File oldReportFile, File newReportFile) throws IOException {
        try (InputStream is1 = new FileInputStream(oldReportFile);
             InputStream is2 = new FileInputStream(newReportFile);
             Workbook wb1 = new XSSFWorkbook(is1);
             Workbook wb2 = new XSSFWorkbook(is2)) {

            if (wb1.getNumberOfSheets() != wb2.getNumberOfSheets()) {
                return false;
            }

            for (int i = 0; i < wb1.getNumberOfSheets(); i++) {
                Sheet sheetFromOldReportFile = wb1.getSheetAt(i);
                Sheet sheetFromNewReportFile = wb2.getSheetAt(i);

                if (!compareSheets(sheetFromOldReportFile, sheetFromNewReportFile)) {
                    return false;
                }
            }

            try (FileOutputStream fo1 = new FileOutputStream(oldReportFile);
                 FileOutputStream fo2 = new FileOutputStream(oldReportFile)) {
                wb1.write(fo1);
                wb2.write(fo2);
            }
        }
        return true;
    }

    private static boolean compareSheets(Sheet sheetFromOldReportFile, Sheet sheetFromNewReportFile) {
        if (sheetFromOldReportFile.getLastRowNum() != sheetFromNewReportFile.getLastRowNum()) {
            return false;
        }

        for (int i = 0; i <= sheetFromOldReportFile.getLastRowNum(); i++) {
            Row rowFromOldReportFile = sheetFromOldReportFile.getRow(i);
            Row rowFromNewReportFile = sheetFromNewReportFile.getRow(i);

            if (!compareRows(rowFromOldReportFile, rowFromNewReportFile)) {
                return false;
            }
        }
        return true;
    }

    private static boolean compareRows(Row rowFromOldReportFile, Row rowFromNewReportFile) {
        if ((rowFromOldReportFile == null && rowFromNewReportFile != null)
                || (rowFromOldReportFile != null && rowFromNewReportFile == null)) {
            return false;
        }
        if (rowFromOldReportFile != null) {
            if (rowFromOldReportFile.getLastCellNum() != rowFromNewReportFile.getLastCellNum()) {
                return false;
            }

            for (int i = 0; i < rowFromOldReportFile.getLastCellNum(); i++) {
                Cell cellFromOldReportFile = rowFromOldReportFile.getCell(i);
                Cell cellFromNewReportFile = rowFromNewReportFile.getCell(i);

                if (!compareCells(cellFromOldReportFile, cellFromNewReportFile)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean compareCells(Cell cellFromOldReportFile, Cell cellFromNewReportFile) {
        if ((cellFromOldReportFile == null && cellFromNewReportFile != null)
                || (cellFromOldReportFile != null && cellFromNewReportFile == null)) {
            return false;
        }
        if (cellFromOldReportFile != null) {
            if (cellFromOldReportFile.getCellType() != cellFromNewReportFile.getCellType()) {
                return false;
            }

            CellStyle cellStyleForOldReportFile = cellFromOldReportFile.getCellStyle();
            CellStyle cellStyleForNewReportFile = cellFromNewReportFile.getCellStyle();

            if(cellStyleForOldReportFile == null) {
                cellStyleForOldReportFile = cellFromOldReportFile.getSheet().getWorkbook().createCellStyle();
            }

            if(cellStyleForNewReportFile == null) {
                cellStyleForNewReportFile = cellFromNewReportFile.getSheet().getWorkbook().createCellStyle();
            }

            cellStyleForOldReportFile.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            cellStyleForOldReportFile.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyleForNewReportFile.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            cellStyleForNewReportFile.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            switch (cellFromOldReportFile.getCellType()) {
                case STRING:
                    if (!cellFromOldReportFile.getStringCellValue().equals(cellFromNewReportFile.getStringCellValue())) {
                        cellFromNewReportFile.setCellStyle(cellStyleForNewReportFile);
                        cellFromOldReportFile.setCellStyle(cellStyleForOldReportFile);
                        return false;
                    }
                    break;
                case NUMERIC:
                    if (cellFromOldReportFile.getNumericCellValue() != cellFromNewReportFile.getNumericCellValue()) {
                        cellFromNewReportFile.setCellStyle(cellStyleForNewReportFile);
                        cellFromOldReportFile.setCellStyle(cellStyleForOldReportFile);
                        return false;
                    }
                    break;
                case BOOLEAN:
                    if (cellFromOldReportFile.getBooleanCellValue() != cellFromNewReportFile.getBooleanCellValue()) {
                        cellFromNewReportFile.setCellStyle(cellStyleForNewReportFile);
                        cellFromOldReportFile.setCellStyle(cellStyleForOldReportFile);
                        return false;
                    }
                    break;
                case FORMULA:
                    if (!cellFromOldReportFile.getCellFormula().equals(cellFromNewReportFile.getCellFormula())) {
                        cellFromNewReportFile.setCellStyle(cellStyleForNewReportFile);
                        cellFromOldReportFile.setCellStyle(cellStyleForOldReportFile);
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
