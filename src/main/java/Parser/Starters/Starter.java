package Parser.Starters;

import Parser.Pages.LoginPage;
import Parser.Pages.SchedulePage;
import Parser.Util.ConfigurationProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class Starter {
    public static LoginPage loginPage;
    public static SchedulePage schedulePage;
    public static WebDriver webDriver;

    public void setup(){
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

    public void scheduleParse() throws ParseException {
        GregorianCalendar instance = new GregorianCalendar(2023, Calendar.JANUARY,1);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date startDate = instance.getTime();
        String startDateInString = dateFormat.format(startDate);
        Date endDate = dateFormat.parse("15.01.2024");

        refreshSchedule(startDateInString);

        try(FileWriter writer = new FileWriter("C://Users/Admin/Desktop/Schedule.txt", false))
        {
            while (startDate.before(endDate)) {
                for (int i = 1; i < schedulePage.getScheduleDay().size(); i++) {
                    writer.append(schedulePage.getScheduleDay().get(i).getText());
                    writer.append("\n");
                    writer.append(schedulePage.getSchedule().get(i).getText());
                    writer.append("\n");
                }
                writer.flush();

                instance.add(Calendar.DAY_OF_YEAR, 7);
                startDate = instance.getTime();
                startDateInString = dateFormat.format(startDate);
                refreshSchedule(startDateInString);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void refreshSchedule(String startDate){
        schedulePage.clearNewDate();
        schedulePage.inputNewDate(startDate);
        schedulePage.viewNextWeekScheduleButton();
    }

    public void tearDown() {
        schedulePage.logOut();
        webDriver.quit();
    }
}
