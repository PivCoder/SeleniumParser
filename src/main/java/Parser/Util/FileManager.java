package Parser.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    public void deleteScheduleFile() {
        File file = new File("src/main/resources/Schedule.txt");

        if (file.delete()) {
            System.out.println("File " + "src/main/resources/Schedule.txt" + " deleted");
        } else {
            System.out.println("Fail " + "src/main/resources/Schedule.txt" + " deletion failed !");
        }
    }

    public void deleteFilesScheduleFolder(String directoryPath) {
        Path dirPath = Paths.get(directoryPath);

        if (!Files.exists(dirPath) || !Files.isDirectory(dirPath)) {
            throw new IllegalArgumentException("The specified path is not a directory or does not exist: " + directoryPath);
        }

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dirPath)) {
            for (Path path : directoryStream) {
                if (Files.isRegularFile(path)) {
                    Files.delete(path);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteExcelReport(File file) {
        //TODO переименовать пути поиска
        if (file.delete()) {
            System.out.println("File " + file.getPath() + " deleted");
        } else {
            System.out.println("Fail " + file.getPath() + " deletion failed !");
        }
    }
}
