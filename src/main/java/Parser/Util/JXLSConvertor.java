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
import java.util.List;

public class JXLSConvertor {
    List<Teacher> teacherList;

    public JXLSConvertor() {
    }

    public JXLSConvertor(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    public void convert() {
        String templatePath = "src/main/resources/Excel/template.xlsx";
        String reportPath = "src/main/resources/Excel/grouping_output.xlsx";

        try (InputStream templateStream = new FileInputStream(templatePath)) {
            boolean reportExists = Files.exists(Paths.get(reportPath));

            if (!reportExists) {
                try (OutputStream os = new FileOutputStream(reportPath)) {
                    Context context = new Context();
                    context.putVar("teachers", teacherList);
                    JxlsHelper.getInstance().processTemplate(templateStream, os, context);
                    System.out.println("Initial report created successfully!");
                }
            }
            //TODO тут будет ветка с сравнением отчётов
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
