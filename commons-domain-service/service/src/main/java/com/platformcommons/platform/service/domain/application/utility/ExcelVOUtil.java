package com.platformcommons.platform.service.domain.application.utility;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExcelVOUtil {

    public static Set<String> getSets(String sets, String separator ){
        return Arrays.stream( sets.split(separator) ).collect(Collectors.toSet());
    }
    public static <T> Set<T> getSets( Collection<T> collection ){
        return collection.stream().collect(Collectors.toSet());
    }

    public static Set<Long> getSetOfIDs(String useCaseIDs) {
        return Arrays.stream(useCaseIDs.split(",")).map(Long::parseLong).collect(Collectors.toSet());
    }
}
