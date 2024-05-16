package Parser.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CathedralSchedulePage {
    public WebDriver webDriver;

    public CathedralSchedulePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    @FindBy(xpath = "//*[@id=\"app-main\"]/div/div[1]/div[1]/label/select")
    private WebElement typeOfSchedule;

    @FindBy(xpath = "//*[@id=\"app-main\"]/div/div[1]/div[2]/label/select")
    private WebElement typeOfCathedral;

    @FindBy(xpath = "//*[@id=\"tree_stage\"]/div/div[2]/a")
    private List<WebElement> teachersList;

    public WebElement getTypeOfSchedule() {
        return typeOfSchedule;
    }

    public WebElement getCathedral() {
        return typeOfCathedral;
    }

    public List<WebElement> getTeachers() {
        return teachersList;
    }
}
