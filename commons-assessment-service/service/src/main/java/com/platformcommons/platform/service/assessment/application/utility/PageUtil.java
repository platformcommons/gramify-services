package com.platformcommons.platform.service.assessment.application.utility;

import com.platformcommons.platform.service.dto.base.BaseDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageUtil {

    public static <T extends BaseDTO> ResponseEntity<PageDTO<T>> getResponseEntity(PageDTO<T> result) {
        return new ResponseEntity<>(result, result.hasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK);
    }


    public static <R extends BaseDTO,T> PageDTO<R> getPage(Page<T> page, Function<T, R> mapper) {
        return new PageDTO<>(page.stream().map(mapper).collect(Collectors.toSet()),page.hasNext(),page.getTotalElements());
    }

//   Page When Zero Indexed
    public static  <R extends BaseDTO,T> PageDTO<R> getPage(Set<T> entity, Long count, Integer page, Integer size, Function<T, R> mapper) {
        return new PageDTO<>(entity.stream().map(mapper).collect(Collectors.toSet()), count>((long) (page + 1) *(size)), count);
    }
}
