package com.platformcommons.platform.service.assessment.reporting.domain.enums;

import java.time.format.DateTimeFormatter;


public enum TimelineType {
    DAILY("yyyy-MM-dd"),
    WEEKLY("yyyy-'W'ww"), // ISO 8601 week date format (e.g., 2025-W42)
    MONTHLY("yyyy-MM"),
    YEARLY("yyyy");

    private final DateTimeFormatter formatter;

    TimelineType(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }
}