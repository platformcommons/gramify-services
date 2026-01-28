package com.platformcommons.platform.service.search.application.utility;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatterUtility {
    private static final SimpleDateFormat solrDateFormat;
    static {
        solrDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        solrDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static String getFormattedDate(Date date){
        return solrDateFormat.format(date);
    }

}
