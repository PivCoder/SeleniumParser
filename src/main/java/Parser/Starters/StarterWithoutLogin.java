package Parser.Starters;

import Parser.Pages.CathedralSchedulePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.text.ParseException;
import java.util.List;

public class StarterWithoutLogin extends Starter {
    public static CathedralSchedulePage cathedralSchedulePage;

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
            teacherList.get(j).click();

            try {
                super.scheduleWriteInFile(j);
                loginPage.clickScheduleButton();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}
