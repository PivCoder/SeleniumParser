package Parser.Pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SchedulePage {
    public WebDriver webDriver;

    public SchedulePage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    @FindBy(tagName = "table")
    private List<WebElement> mySchedule;

    @FindBy(xpath = "//*[contains(@class, 'day-name')]")
    private List<WebElement> scheduleDays;

    @FindBy(xpath = "//*[@id=\"user-tabs\"]/ul/li[5]/a")
    private WebElement logoutButton;

    @FindBy(xpath = "//*[contains(@id, 'datetimepicker1')]")
    private WebElement startDate;

    @FindBy(xpath = "//*[contains(@class, 'fa fa-arrow-right')]")
    private WebElement viewNextWeekSchedule;

    public List<WebElement> getSchedule(){
        return mySchedule;
    }

    public List<WebElement> getScheduleDay(){
        return scheduleDays;
    }

    public WebElement getStartDate(){
        return  startDate;
    }

    public void clearNewDate(){
        startDate.clear();
    }

    public void inputNewDate(String freshStartDate){
        startDate.sendKeys(freshStartDate);
        startDate.sendKeys(Keys.ENTER);
    }

    public void viewNextWeekScheduleButton(){
        viewNextWeekSchedule.click();
    }

    public void logOut(){
        logoutButton.click();
    }
}
