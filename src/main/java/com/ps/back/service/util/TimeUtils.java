package com.ps.back.service.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    public static String getDateFormated(LocalDateTime date, String format) {
        format = format == null ? "dd/MM/yyyy" : format;
        return getHourFormated(date, format);
    }

    public static String getHourFormated(LocalDateTime date, String format) {
        format = format == null ? "HH:mm:ss" : format;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(format);
        return date.format(timeFormatter);
    }

    public static LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
