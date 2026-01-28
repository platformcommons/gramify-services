package com.platformcommons.platform.service.search.application.utility;


import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import static org.springframework.data.domain.Sort.Direction;


public class JPAUtility {
    private JPAUtility() {
    }

    public static Sort convertToSort(String sortBy, String direction) {
        return null != sortBy && !sortBy.isEmpty() ?
                Sort.by(direction == null ? Direction.ASC : Direction.fromString(direction), StringUtils.split(sortBy, ",")) : Sort.unsorted();
    }
}
