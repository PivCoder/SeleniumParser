package Parser.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChooseRolePage {
    public WebDriver webDriver;

    public ChooseRolePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    @FindBy(xpath = "//*[contains(@class, 'fa fa-user')]")
    private WebElement roleField;

    public void chooseRole() {
        roleField.click();
    }
}
