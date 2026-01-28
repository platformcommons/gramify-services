package com.platformcommons.platform.service.search.application.utility;

public class SolrUtil {

    public static String escapeQueryChars(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder sb = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            // Special Solr/Lucene characters that need escaping
            if (c == '\\' || c == '+' || c == '-' || c == '!' ||
                    c == '(' || c == ')' || c == ':' || c == '^' ||
                    c == '[' || c == ']' || c == '\"' || c == '{' ||
                    c == '}' || c == '~' || c == '*' || c == '?' ||
                    c == '|' || c == '&' || c == ';' ||
                    Character.isWhitespace(c)) {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static String solrSafeKey(String key) {
        // Ensure only letters, numbers, underscore — no spaces or special chars
        return key.trim().replaceAll("[^A-Za-z0-9_]", "_").toUpperCase();
    }
}

