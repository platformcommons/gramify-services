package com.platformcommons.platform.service.assessment.reporting.application.constant;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class LinkedSystem {

    public static final String COMMONS = "COMMONS";
    public static final String LTLD = "LTLD";
    public static final String IGX = "IGX";

    public static final Set<String> SYSTEMS=Arrays.stream(new String[] {COMMONS, LTLD, IGX}).collect(Collectors.toSet());

}
