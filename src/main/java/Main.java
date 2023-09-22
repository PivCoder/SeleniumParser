import Parser.Starters.StarterWithLogin;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Scanner;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class Main {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        waitToPushButton();
    }

    private static void waitToPushButton(){
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        break;
                    case KeyEvent.VK_1:
                        StarterWithLogin starterWithLogin = new StarterWithLogin();
                        starterWithLogin.setup();
                        starterWithLogin.scheduleParse();
                        starterWithLogin.tearDown();
                        break;
                    default:
                        waitToPushButton();
                }
            }
        });
    }
}
