package com.platformcommons.platform.service.iam.application.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtility {

    public static Date convertUTCStringToUTCZoneDate(String dateString)  {
        Date date = null;
        if(dateString != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            try {
                date = simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return date;
    }

    public static String convertDateObjectToStringInUTCZone(Date date) {
        String dateString = null;
        if(date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            dateString = dateFormat.format(date);
        }
        return dateString;
    }
}
