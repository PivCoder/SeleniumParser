package Parser.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    public WebDriver webDriver;

    public LoginPage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    @FindBy(xpath = "//*[contains(@id, 'loginform-identity')]")
    private WebElement loginField;

    @FindBy(xpath = "//*[contains(@id, 'loginform-password')]")
    private WebElement passwordField;

    @FindBy(xpath = "//*[contains(@class, 'btn btn-primary')]")
    private WebElement loginButton;

    @FindBy(xpath = "//*[@id=\"user-tabs\"]/ul/li[1]/a")
    private WebElement scheduleButton;

    public void inputLogin(String login){
        loginField.sendKeys(login);
    }

    public void inputPassword(String password){
        passwordField.sendKeys(password);
    }

    public void clickLoginButton(){
        loginButton.click();
    }

    public void  clickScheduleButton(){
        scheduleButton.click();
    }
}
