package Parser.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatter {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public LocalDate formatDate(String dateInString) {
        try {
            return LocalDate.parse(dateInString, dateFormat);
        } catch (DateTimeParseException e) {
            System.err.println("Ошибка формата даты: " + e.getMessage());
            return null;
        }
    }

    public String formatDateInFormattedString(LocalDate date) {
        return date.format(dateFormat);
    }
}
