package Parser.Starters;

import Parser.Pages.LoginPage;
import Parser.Pages.SchedulePage;
import Parser.Util.ConfigurationProperties;
import Parser.Util.DateFormatter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public abstract class Starter {
    public static LoginPage loginPage;
    public static SchedulePage schedulePage;
    public static WebDriver webDriver;
    private Date startDate;
    private final GregorianCalendar instance = new GregorianCalendar();
    private Date endDate;

    public void setup() {
        System.setProperty("webdriver.chrome.driver", ConfigurationProperties.getProperty("chromeDriver"));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        webDriver = new ChromeDriver(options);
        loginPage = new LoginPage(webDriver);
        schedulePage = new SchedulePage(webDriver);
        webDriver.manage().window().minimize();   //Выводит или скрывает окно исполнения
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.get(ConfigurationProperties.getProperty("loginPage"));
    }

    public void scheduleWriteInFile() throws ParseException {
        Date interimDate = startDate;
        changeStartDate(convertDateInString(interimDate));

        //TODO переделать на динамический выбор директории, перенести из этого файла класса ?
        //TODO Подумать как обыграть дату старта и промежуточную дату
        while (interimDate.before(endDate)) {
            try (FileWriter writer = new FileWriter("C://Users/Admin/Desktop/Schedule.txt", true)) {
                for (int i = 0; i < schedulePage.getScheduleDay().size(); i++) {
                    writer.write(schedulePage.getScheduleDay().get(i).getText());
                    writer.write("\n");
                    writer.write(schedulePage.getSchedule().get(i).getText());
                    writer.write("\n");
                }

                writer.close();
                instance.add(Calendar.DAY_OF_YEAR, 7);
                interimDate = instance.getTime();
                changeStartDate(convertDateInString(interimDate));

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void changeStartDate(String startDateInString){
        schedulePage.clearNewDate();
        schedulePage.inputNewDate(startDateInString);
    }

    private String convertDateInString(Date dateToString) {
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

    public void setInstance(Date dateToInstance) {
        instance.setTime(dateToInstance);
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
