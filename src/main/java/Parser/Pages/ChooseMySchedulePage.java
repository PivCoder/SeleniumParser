package Parser.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChooseMySchedulePage {
    public WebDriver webDriver;

    public ChooseMySchedulePage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    @FindBy(xpath = "//*[contains(@class, 'fa fa-thumb-tack')]")
    private WebElement myScheduleField;

    public void chooseMySchedule(){
        myScheduleField.click();
    }
}
