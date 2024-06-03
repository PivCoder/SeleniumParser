package Parser.Util;

import Parser.Analyser.DayAnalyser;
import Parser.Model.Day;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JXLSConvertor {
    List<Day> dayList;
    private int weekHours;

    public JXLSConvertor() {
    }

    public JXLSConvertor(List<Day> dayList, int weekHours) {
        this.dayList = dayList;
        this.weekHours = weekHours;
    }

    public void convert() {
        String templatePath = "src/main/resources/Excel/template.xlsx";
        String reportPath = "src/main/resources/Excel/grouping_output.xlsx";

        try (InputStream templateStream = new FileInputStream(templatePath)) {
            // Проверка наличия файла отчета
            boolean reportExists = Files.exists(Paths.get(reportPath));

            // Если файл отчета не существует, создаем его с нуля
            if (!reportExists) {
                try (OutputStream os = new FileOutputStream(reportPath)) {
                    Context context = new Context();
                    context.putVar("dayList", dayList);
                    context.putVar("weekHours", weekHours);
                    JxlsHelper.getInstance().processTemplate(templateStream, os, context);
                    System.out.println("Initial report created successfully!");
                }
            } else {
                // Открываем существующий файл отчета
                try (FileInputStream reportInputStream = new FileInputStream(reportPath);
                     Workbook workbook = new XSSFWorkbook(reportInputStream)) {

                    // Находим последний заполненный ряд
                    Sheet sheet = workbook.getSheetAt(0);
                    int lastRowNum = sheet.getLastRowNum();

                    // Создаем временный файл для хранения новых данных
                    File tempFile = File.createTempFile("temp", ".xlsx");

                    try (OutputStream tempOutStream = new FileOutputStream(tempFile)) {
                        // Создаем контекст JXLS и указываем с какой строки начинать запись
                        Context context = new Context();
                        context.putVar("dayList", dayList);
                        context.putVar("startRow", lastRowNum + 1);
                        context.putVar("weekHours", weekHours);
                        JxlsHelper.getInstance().processTemplate(templateStream, tempOutStream, context);

                        // Закрываем временный файл, чтобы затем объединить его с существующим
                        tempOutStream.close();

                        // Читаем временный файл и добавляем его содержимое к существующему файлу
                        try (FileInputStream tempInputStream = new FileInputStream(tempFile)) {
                            Workbook tempWorkbook = new XSSFWorkbook(tempInputStream);
                            Sheet tempSheet = tempWorkbook.getSheetAt(0);

                            for (int i = tempSheet.getFirstRowNum(); i <= tempSheet.getLastRowNum(); i++) {
                                Row tempRow = tempSheet.getRow(i);
                                Row newRow = sheet.createRow(lastRowNum + 1 + i);
                                for (int j = tempRow.getFirstCellNum(); j < tempRow.getLastCellNum(); j++) {
                                    Cell tempCell = tempRow.getCell(j);
                                    Cell newCell = newRow.createCell(j);
                                    newCell.setCellValue(tempCell.toString());
                                }
                            }
                            tempWorkbook.close();
                        }
                    }

                    // Сохраняем обновленный файл
                    try (FileOutputStream reportOutStream = new FileOutputStream(reportPath)) {
                        workbook.write(reportOutStream);
                    }

                    System.out.println("Report updated successfully!");

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
