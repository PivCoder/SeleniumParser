package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;

public class LoginTest {
    public static LoginPage loginPage;
    public static ChooseRolePage chooseRolePage;
    public static ChooseMySchedulePage chooseMySchedulePage;
    public static SchedulePage schedulePage;
    public static WebDriver webDriver;

    @BeforeAll
    public static void setup(){
        System.setProperty("webdriver.chrome.driver", ConfigurationProperties.getProperty("chromeDriver"));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
        loginPage = new LoginPage(webDriver);
        chooseRolePage = new ChooseRolePage(webDriver);
        chooseMySchedulePage = new ChooseMySchedulePage(webDriver);
        schedulePage = new SchedulePage(webDriver);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.get(ConfigurationProperties.getProperty("loginPage"));
    }

    @Test
    public void scheduleParseTest(){
        loginPage.inputLogin(ConfigurationProperties.getProperty("login"));
        loginPage.inputPassword(ConfigurationProperties.getProperty("password"));
        loginPage.clickButton();
        chooseRolePage.chooseRole();
        chooseMySchedulePage.chooseMySchedule();
        DateFormat df = new SimpleDateFormat("dd MM yyyy");

        try(FileWriter writer = new FileWriter("C://Users/Admin/Desktop/Schedule.txt", false))
        {
            for (int i = 0; i < schedulePage.getScheduleDay().size(); i++) {
                writer.append(schedulePage.getScheduleDay().get(i).getText());
                writer.append("\n");
                writer.append(schedulePage.getSchedule().get(i).getText());
                writer.append("\n");
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    @AfterAll
    public static void tearDown() {
        schedulePage.logOut();
        webDriver.quit();
    }
}
