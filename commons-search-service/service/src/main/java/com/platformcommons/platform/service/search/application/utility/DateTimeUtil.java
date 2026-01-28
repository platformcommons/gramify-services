package com.platformcommons.platform.service.search.application.utility;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtil {

    public static String convertDateToStringInGivenZoneAndPattern(Date date,String pattern,String timeZone) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern(pattern)
                .withZone(ZoneId.of(timeZone));
        return formatter.format(instant);
    }

    public static Date createDateFromAge(Integer age, String timeZone) {
        LocalDate birthDate = LocalDate.now().minusYears(age);
        ZonedDateTime zonedDateTime = birthDate.atStartOfDay(ZoneId.of(timeZone));
        return Date.from(zonedDateTime.toInstant());
    }
}