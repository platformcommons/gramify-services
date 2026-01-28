package com.platformcommons.platform.service.search.application.utility;

import java.util.regex.Pattern;

public class SearchTermParser {


    private static final Pattern pattern = Pattern.compile("[\\+\\-\\&\\|\\!\\(\\)\\{\\}\\[\\]\\^\\\"\\~\\*\\?\\:\\/]");

    public static String parseSearchTerm(String input) {
        return pattern.matcher(input)
                .replaceAll("\\\\$0")
                .replace("AND", "and")
                .replace("OR", "or")
                .trim();
    }

}
