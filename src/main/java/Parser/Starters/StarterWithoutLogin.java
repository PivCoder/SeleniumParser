package Parser.Starters;

import Parser.Model.Teacher;
import Parser.Pages.CathedralSchedulePage;
import Parser.Util.JXLSConvertor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class StarterWithoutLogin extends Starter {
    public static CathedralSchedulePage cathedralSchedulePage;
    private List<Teacher> teacherListForExcel = new ArrayList<>();

    public void setup() {
        super.setup();
        cathedralSchedulePage = new CathedralSchedulePage(webDriver);
    }

    public void cathedralScheduleParse() {
        loginPage.clickScheduleButton();
        Select typeOfSchedule = new Select(cathedralSchedulePage.getTypeOfSchedule());

        //TODO индекс потенциально может измениться. Продумать как отвязаться от такого определения элемента
        typeOfSchedule.selectByIndex(2);

        Select typeOfCathedral = new Select(cathedralSchedulePage.getCathedral());
        List<WebElement> teacherList = cathedralSchedulePage.getTeachers();

        //TODO индекс пустого значения 0 тоже должен входить в цикл, надо обработать ситуации в которых список преподавателей пуст
        //Нужно также сделать возможность выбора кафедры
        /*for (int i = 1; i < 2; i++) { //typeOfCathedral.getOptions().size()
            typeOfCathedral.selectByIndex(i);
            for (int j = 0; j < teacherList.size(); j++){
                teacherList.get(j).click();
                try {
                    super.scheduleParse();
                    loginPage.clickScheduleButton();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }*/

        super.setStartDate();
        super.setEndDate();

        //TODO переделать этот костыль в поиск по значению или по тегу
        typeOfCathedral.selectByIndex(12);

        for (int j = 0; j < teacherList.size(); j++) { //teacherList.size()
            Teacher teacher = new Teacher();

            //TODO переделать в нормальную обработку имени
            String name = "default";
            try {
                byte[] windows1251Bytes = "Преподаватель №".getBytes("Windows-1251");
                name = new String(windows1251Bytes, StandardCharsets.UTF_8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            teacher.setTeacherName(name + j);

            teacherList.get(j).click();

            try {
                super.scheduleWriteInFile(teacher);
                loginPage.clickScheduleButton();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            teacherListForExcel.add(teacher);
        }

        JXLSConvertor jxlsConvertor = new JXLSConvertor(teacherListForExcel, getStartDate(), getEndDate());
        jxlsConvertor.convert();
    }
}
