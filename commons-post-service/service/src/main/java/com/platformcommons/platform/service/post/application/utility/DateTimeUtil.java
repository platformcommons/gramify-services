package com.platformcommons.platform.service.post.application.utility;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateTimeUtil {

    public String convertLocalDateToGivenStringFormat(LocalDate localDate,String format) {
        if(localDate == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDate.format(formatter);
    }
}
