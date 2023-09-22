package Parser.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CathedralSchedulePage {
    public WebDriver webDriver;

    CathedralSchedulePage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    @FindBy(xpath = "//*[@id=\"tree_stage\"]/div/div[2]")
    private List<WebElement> teachers;

    @FindBy(xpath = "//*[@id=\"app-main\"]/div/div[1]/div[2]/label/select")
    private List<WebElement> cathedralSelect;
    
    public List<WebElement> getSchedule(){
        return teachers;
    }

    public List<WebElement> getScheduleDay(){
        return cathedralSelect;
    }
}
