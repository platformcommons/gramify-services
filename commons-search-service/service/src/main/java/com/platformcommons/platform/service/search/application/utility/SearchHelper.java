package com.platformcommons.platform.service.search.application.utility;

import com.platformcommons.platform.service.search.dto.OpportunityApplicantFilterDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;

public class SearchHelper {

    public static String buildQueryForIndividualField(String fieldName, List<String> searchTermsArray) {
        StringBuilder queryBuilder =  new StringBuilder();
        queryBuilder.append("( ");
        Iterator<String> listIterator = searchTermsArray.listIterator();
        while(listIterator.hasNext()) {
            queryBuilder.append(fieldName)
                    .append(":")
                    .append(listIterator.next());
            if(listIterator.hasNext()) {
                queryBuilder.append(" AND ");
            }
        }
        queryBuilder.append(" )");
        return queryBuilder.toString();
    }

    public static Pageable getPageable(String searchTerm,String sortFieldName,String sortFieldOrder,Integer page,Integer size ) {
        Sort sort = null;
        if(StringUtils.isBlank(searchTerm)) {
            sort = JPAUtility.convertToSort(sortFieldName, sortFieldOrder);
        }
        else {
            sort = Sort.unsorted();
        }
        return PageRequest.of(page,size,sort);
    }

}
