package Parser.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    private Date FormattedDate;
    private final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public Date formatDate(String dateInString) {
        try {
            FormattedDate = dateFormat.parse(dateInString);
        } catch (ParseException exception) {
            System.err.println("Ошибка разбора даты. Пожалуйста, введите дату в правильном формате (dd.mm.yyyy).");
        }

        return FormattedDate;
    }

    public String formatDateInFormattedString(Date date) {
        return dateFormat.format(date);
    }
}
