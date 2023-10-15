import Parser.Starters.StarterWithLogin;
import Parser.Starters.StarterWithoutLogin;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        waitToPushButton();
    }

    private static void waitToPushButton(){
        Scanner scanner = new Scanner(System.in);
        String choose = scanner.next();
        switch (choose) {
            case "0":
                return;
            case "1":
                StarterWithLogin starterWithLogin = new StarterWithLogin();
                starterWithLogin.setup();
                starterWithLogin.scheduleWriteInFile();
                starterWithLogin.tearDown();
                break;
            case "2":
                StarterWithoutLogin starterWithoutLogin = new StarterWithoutLogin();
                starterWithoutLogin.setup();
                starterWithoutLogin.cathedralScheduleParse();
                starterWithoutLogin.tearDown();
                break;
            default:
                waitToPushButton();
        }
    }
}
