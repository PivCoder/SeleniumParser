package Parser.Starters;

import Parser.Pages.ChooseMySchedulePage;
import Parser.Pages.ChooseRolePage;
import Parser.Util.ConfigurationProperties;

import java.text.ParseException;

public class StarterWithLogin extends Starter {
    public static ChooseRolePage chooseRolePage;
    public static ChooseMySchedulePage chooseMySchedulePage;

    public void setup(){
        super.setup();
        chooseRolePage = new ChooseRolePage(webDriver);
        chooseMySchedulePage = new ChooseMySchedulePage(webDriver);
    }

    public void scheduleParse(){
        try {
            loginPage.inputLogin(ConfigurationProperties.getProperty("login"));
            loginPage.inputPassword(ConfigurationProperties.getProperty("password"));
            loginPage.clickLoginButton();
            chooseRolePage.chooseRole();
            chooseMySchedulePage.chooseMySchedule();
            super.scheduleParse();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
