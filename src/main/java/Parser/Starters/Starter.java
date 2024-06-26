package Parser.Starters;

import Parser.Analyser.DayAnalyser;
import Parser.Analyser.TeacherDisciplinesAnalyser;
import Parser.Model.Day;
import Parser.Model.Discipline;
import Parser.Model.Teacher;
import Parser.Pages.LoginPage;
import Parser.Pages.SchedulePage;
import Parser.Util.ConfigurationProperties;
import Parser.Util.DateFormatter;
import Parser.Util.DisciplineConfigurator;
import Parser.Util.DisciplineTypeExtractor;
import Parser.Util.JsonSerializer;
import Parser.Util.StringIntoWindows1251Convertor;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//TODO После выяснения вопроса с личными данными убрать дублирование кода
public abstract class Starter {
    public static LoginPage loginPage;
    public static SchedulePage schedulePage;
    public static WebDriver webDriver;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate interimDate;

    public void setup() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        WebDriverManager.chromedriver().setup();

        webDriver = new ChromeDriver(options);
        loginPage = new LoginPage(webDriver);
        schedulePage = new SchedulePage(webDriver);
        webDriver.manage().window().minimize();   //Выводит или скрывает окно исполнения
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.get(ConfigurationProperties.getProperty("loginPage"));
    }

    public void scheduleWriteInFile(Teacher teacher) throws ParseException {
        interimDate = findPreviousMondayForDate(startDate);
        changeStartDate(convertDateInString(interimDate));

        //TODO переделать на динамический выбор директории
        while (interimDate.isBefore(endDate)) {
            writeScheduleWithDateInJsonFile(teacher);
        }
    }

    public void scheduleWriteInFile() throws ParseException {
        interimDate = findPreviousMondayForDate(startDate);
        changeStartDate(convertDateInString(interimDate));

        //TODO переделать на динамический выбор директории
        while (interimDate.isBefore(endDate)) {
            writeScheduleWithDateInJsonFile();
        }
    }

    private LocalDate findPreviousMondayForDate(LocalDate inputDate) {
        return inputDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    private void changeStartDate(String startDateInString) {
        schedulePage.clearNewDate();
        schedulePage.inputNewDate(startDateInString);
    }

    private void writeScheduleWithDate() {
        try (FileWriter writer = new FileWriter("src/main/resources/Schedule.txt", true)) {
            List<WebElement> tables = schedulePage.getSchedule();
            List<WebElement> days = schedulePage.getScheduleDays();

            for (int i = 0; i < tables.size(); i++) {
                List<WebElement> rows = tables.get(i).findElements(By.tagName("tr"));

                writer.write("\n");
                writer.write(days.get(i).getText());
                writer.write("\n");

                for (WebElement row : rows) {
                    WebElement cell = row.findElement(By.className("table-schedule-discipline"));

                    //TODO выяснить нужно ли выводить header
                    if (!cell.getText().isEmpty() && !cell.getTagName().equals("th")) {
                        writer.write(row.getText());
                        writer.write("\n");
                    }
                }
            }

            writer.close();
            addSevenDaysForInterimDate();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void writeScheduleWithDateInJsonFile(Teacher teacher) {
        int weekHours;
        List<WebElement> tables = schedulePage.getSchedule();
        List<WebElement> days = schedulePage.getScheduleDays();
        List<Day> dayList = new ArrayList<>();
        JsonSerializer jsonSerializer = new JsonSerializer();
        DisciplineConfigurator disciplineConfigurator;

        for (int i = 0; i < tables.size(); i++) {
            List<WebElement> rows = tables.get(i).findElements(By.tagName("tr"));
            Day day = new Day();
            day.setDayOfWeak(days.get(i).getText());
            List<Discipline> disciplineList = new ArrayList<>();
            Map<String, Integer> disciplineCounter;

            if (!new String(day.getDayOfWeak().getBytes(StandardCharsets.UTF_8)).contains("Воскресенье")) {
                for (WebElement row : rows) {
                    WebElement cell = row.findElement(By.className("table-schedule-discipline"));

                    if (!cell.getText().isEmpty() && !cell.getTagName().equals("th")) {
                        Discipline discipline = new Discipline();
                        disciplineConfigurator = new DisciplineConfigurator(discipline);
                        discipline = disciplineConfigurator.configureDiscipline(row.getText());
                        disciplineList.add(discipline);
                    }
                }

                //TODO пока что все часы считаются по станадртной формуле "пара * 2 академических часа"
                disciplineCounter = DisciplineTypeExtractor.countDisciplines(disciplineList);
                String lecture = StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Лекционные"),
                        practice = StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Практические"),
                        labs = StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Лабораторные"),
                        exam = StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Экзамен"),
                        test = StringIntoWindows1251Convertor.convertIntoWindows1251InUTF8("Зачет");
                teacher.setLectureHours(disciplineCounter.getOrDefault(lecture, 0) * 2);
                teacher.setPractiseHours(disciplineCounter.getOrDefault(practice, 0) * 2);
                teacher.setLabHours(disciplineCounter.getOrDefault(labs, 0) * 2);
                teacher.setExamHours(disciplineCounter.getOrDefault(exam, 0) * 2);
                teacher.setTestHours(disciplineCounter.getOrDefault(test, 0) * 2);

                day.setDisciplineList(disciplineList);
                dayList.add(day);
            }
        }

        //TODO в будущем переделать под Teacher
        //jsonSerializer.convertDayToJson(dayList, interimDate);

        //TODO вынессти в отдельный метод ?
        DayAnalyser dayAnalyser = new DayAnalyser(dayList);
        weekHours = dayAnalyser.analyseListOfDays();
        teacher.getDayList().addAll(dayList);
        teacher.setWeekHours(weekHours);

        TeacherDisciplinesAnalyser teacherDisciplinesAnalyser = new TeacherDisciplinesAnalyser(teacher);
        teacher.setDisciplinesHours(teacherDisciplinesAnalyser.countDisciplinesHours());

        addSevenDaysForInterimDate();
    }

    //TODO доделать вариант для пользователя без кафедр и преподавателя
    private void writeScheduleWithDateInJsonFile() {

    }

    private void addSevenDaysForInterimDate() {
        interimDate = interimDate.plusDays(7);
        changeStartDate(convertDateInString(interimDate));
    }

    private String convertDateInString(LocalDate dateToString) {
        DateFormatter dateFormatter = new DateFormatter();
        return dateFormatter.formatDateInFormattedString(dateToString);
    }

    public void setStartDate() {
        Scanner scanner = new Scanner(System.in);
        DateFormatter dateFormatter = new DateFormatter();
        System.out.println("Введите начальную дату в формате dd.MM.YYYY");
        String startDateInString = scanner.nextLine();
        startDate = dateFormatter.formatDate(startDateInString);
    }

    public void setEndDate() {
        Scanner scanner = new Scanner(System.in);
        DateFormatter dateFormatter = new DateFormatter();
        System.out.println("Введите конечную дату в формате dd.MM.YYYY");
        String endDateInString = scanner.nextLine();
        endDate = dateFormatter.formatDate(endDateInString);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void tearDown() {
        schedulePage.logOut();
        webDriver.quit();
    }
}
