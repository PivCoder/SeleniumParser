package Parser.Util;

import Parser.Model.Day;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

public class JsonSerializer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void convertDayToJson(List<Day> dayList, Date fileDate) {
        String path = "src/main/resources/Json/Schedule_" + fileDate + ".json";
        path = path.replaceAll("\\s", "_");
        path = path.replaceAll(":", "_");
        File jsonFile = new File(path);

        try {
            if (jsonFile.createNewFile()) {
                System.out.println("The file was successfully created: " + jsonFile.getAbsolutePath());
            } else {
                System.out.println("The file already exists: " + jsonFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Error during file creation: " + e.getMessage());
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(jsonFile, false);
             OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, Charset.forName("Windows-1251"))) {
            ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
            objectWriter.writeValue(writer, dayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
