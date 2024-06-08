package Parser.Util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class StringIntoWindows1251Convertor {
    public static String convertIntoWindows1251InUTF8(String convertingString){
        String stringInUTF8 = "default string";

        try {
            byte[] windows1251Bytes = convertingString.getBytes("Windows-1251");
            stringInUTF8 = new String(windows1251Bytes, StandardCharsets.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return stringInUTF8;
    }
}
