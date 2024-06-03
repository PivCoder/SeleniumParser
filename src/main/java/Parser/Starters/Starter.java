package Parser.Starters;

import Parser.Analyser.DayAnalyser;
import Parser.Model.Day;
import Parser.Model.Discipline;
import Parser.Pages.LoginPage;
import Parser.Pages.SchedulePage;
import Parser.Util.ConfigurationProperties;
import Parser.Util.DateFormatter;
import Parser.Util.DisciplineConfigurator;
import Parser.Util.JXLSConvertor;
import Parser.Util.JsonSerializer;
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
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

//TODO После выяснения вопроса с личными данными убрать дублирование кода
public abstract class Starter {
    public static LoginPage loginPage;
    public static SchedulePage schedulePage;
    public static WebDriver webDriver;
    private Date startDate;
    private final GregorianCalendar instance = new GregorianCalendar();
    private Date endDate;
    private Date interimDate;

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

        //TODO удалить
        /*
        deleteScheduleFile();
        deleteFilesScheduleFolder("src/main/resources/Json");
        deleteExcelReport();*/
    }

    public void scheduleWriteInFile() throws ParseException {
        interimDate = findPreviousMondayForDate(startDate);
        changeStartDate(convertDateInString(interimDate));

        //TODO переделать на динамический выбор директории
        while (interimDate.before(endDate)) {
            writeScheduleWithDateInJsonFile();
        }
    }

    public void scheduleWriteInFile(int teacherNumber) throws ParseException {
        interimDate = findPreviousMondayForDate(startDate);
        changeStartDate(convertDateInString(interimDate));

        //TODO переделать на динамический выбор директории
        while (interimDate.before(endDate)) {
            writeScheduleWithDateInJsonFile(teacherNumber);
        }
    }

    private Date findPreviousMondayForDate(Date inputDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }

        return calendar.getTime();
    }

    private void changeStartDate(String startDateInString) {
        schedulePage.clearNewDate();
        schedulePage.inputNewDate(startDateInString);
    }

    private void writeScheduleWithDate() {
        //TODO переделать в создание объекта и сериализаию в JSON
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

    private void writeScheduleWithDateInJsonFile() {
        int weekHours;
        List<WebElement> tables = schedulePage.getSchedule();
        List<WebElement> days = schedulePage.getScheduleDays();
        List<Day> dayListToJson = new ArrayList<>();
        JsonSerializer jsonSerializer = new JsonSerializer();
        DisciplineConfigurator disciplineConfigurator;

        for (int i = 0; i < tables.size(); i++) {
            List<WebElement> rows = tables.get(i).findElements(By.tagName("tr"));
            Day day = new Day();
            List<Discipline> disciplineList = new ArrayList<>();
            day.setDayOfWeak(days.get(i).getText());

            if (!new String(day.getDayOfWeak().getBytes(StandardCharsets.UTF_8)).contains("Воскресенье")) {
                for (WebElement row : rows) {
                    WebElement cell = row.findElement(By.className("table-schedule-discipline"));

                    //TODO выяснить нужно ли выводить header
                    if (!cell.getText().isEmpty() && !cell.getTagName().equals("th")) {
                        Discipline discipline = new Discipline();
                        disciplineConfigurator = new DisciplineConfigurator(discipline);
                        discipline = disciplineConfigurator.configureDiscipline(row.getText());
                        disciplineList.add(discipline);
                    }
                }

                day.setDisciplineList(disciplineList);
                dayListToJson.add(day);
            }
        }

        jsonSerializer.convertDayToJson(dayListToJson, interimDate);

        //TODO вынессти в отдельный метод ?
        DayAnalyser dayAnalyser = new DayAnalyser(dayListToJson);
        weekHours = dayAnalyser.analyseListOfDays();
        JXLSConvertor jxlsConvertor = new JXLSConvertor(dayListToJson, weekHours);
        jxlsConvertor.convert();

        addSevenDaysForInterimDate();
    }

    private void writeScheduleWithDateInJsonFile(int teacherNumber) {
        int weekHours;
        List<WebElement> tables = schedulePage.getSchedule();
        List<WebElement> days = schedulePage.getScheduleDays();
        List<Day> dayListToJson = new ArrayList<>();
        JsonSerializer jsonSerializer = new JsonSerializer();
        DisciplineConfigurator disciplineConfigurator;

        for (int i = 0; i < tables.size(); i++) {
            List<WebElement> rows = tables.get(i).findElements(By.tagName("tr"));
            Day day = new Day();
            List<Discipline> disciplineList = new ArrayList<>();
            day.setDayOfWeak(days.get(i).getText());

            if (!new String(day.getDayOfWeak().getBytes(StandardCharsets.UTF_8)).contains("Воскресенье")) {
                for (WebElement row : rows) {
                    WebElement cell = row.findElement(By.className("table-schedule-discipline"));

                    //TODO выяснить нужно ли выводить header
                    if (!cell.getText().isEmpty() && !cell.getTagName().equals("th")) {
                        Discipline discipline = new Discipline();
                        disciplineConfigurator = new DisciplineConfigurator(discipline);
                        discipline = disciplineConfigurator.configureDisciplineWithTeacherNumber(row.getText(), teacherNumber);
                        disciplineList.add(discipline);
                    }
                }

                day.setDisciplineList(disciplineList);
                dayListToJson.add(day);
            }
        }

        jsonSerializer.convertDayToJson(dayListToJson, interimDate);

        //TODO вынессти в отдельный метод ?
        DayAnalyser dayAnalyser = new DayAnalyser(dayListToJson);
        weekHours = dayAnalyser.analyseListOfDays();
        JXLSConvertor jxlsConvertor = new JXLSConvertor(dayListToJson, weekHours);
        jxlsConvertor.convert();

        addSevenDaysForInterimDate();
    }

    private void addSevenDaysForInterimDate() {
        setInstance(interimDate);
        instance.add(Calendar.DAY_OF_YEAR, 7);
        interimDate = instance.getTime();
        changeStartDate(convertDateInString(interimDate));
    }

    private String convertDateInString(Date dateToString) {
        DateFormatter dateFormatter = new DateFormatter();
        return dateFormatter.formatDateInFormattedString(dateToString);
    }

    private void setInstance(Date dateToInstance) {
        instance.setTime(dateToInstance);
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

    public Date getStartDate() {
        return startDate;
    }

    public GregorianCalendar getInstance() {
        return instance;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void tearDown() {
        schedulePage.logOut();
        webDriver.quit();
    }
}
