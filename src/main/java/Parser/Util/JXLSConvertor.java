package Parser.Util;

import Parser.Model.Teacher;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class JXLSConvertor {
    private List<Teacher> teacherList;
    private LocalDate startReportDate;
    private LocalDate endReportDate;
    private String reportPath;

    public JXLSConvertor() {
    }

    public JXLSConvertor(List<Teacher> teacherList, LocalDate startReportDate, LocalDate endReportDate) {
        this.teacherList = teacherList;
        this.startReportDate = startReportDate;
        this.endReportDate = endReportDate;
    }

    public void convert() {
        String templatePath = "src/main/resources/Excel/template.xlsx";
        reportPath = "src/main/resources/Excel/report" + startReportDate + "_" + endReportDate +".xlsx".replaceAll("\\s", "_");
        reportPath = reportPath.replaceAll("\\s", "_");
        reportPath = reportPath.replaceAll(":", "_");

        try (InputStream templateStream = new FileInputStream(templatePath)) {
            boolean reportExists = Files.exists(Paths.get(reportPath));

            if (!reportExists) {
                createExcelFile(templateStream);
            } else {
                //TODO тут будет ветка с сравнением отчётов
                createExcelFile(templateStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createExcelFile(InputStream templateStream) {
        try (OutputStream os = new FileOutputStream(reportPath)) {
            Context context = new Context();
            context.putVar("teachers", teacherList);
            JxlsHelper.getInstance().processTemplate(templateStream, os, context);
            System.out.println("Initial report created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
