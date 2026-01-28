package com.platformcommons.platform.service.assessment.reporting.application.utility;

import java.util.HashMap;
import java.util.stream.Collectors;

public class DatasetUtil {
    public static String createParams(HashMap<String, String> params) {
        return params.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining("--"));
    }
}
