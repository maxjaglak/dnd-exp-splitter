package oo.max.dndexperiencesplitter.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DatePrinters {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

    public static String printDateTime(DateTime dateTime) {
        return DATE_TIME_FORMATTER.print(dateTime);
    }
}
