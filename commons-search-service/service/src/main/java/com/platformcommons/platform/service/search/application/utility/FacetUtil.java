package com.platformcommons.platform.service.search.application.utility;


import org.springframework.data.solr.core.query.result.FacetPage;
import java.util.HashMap;
import java.util.Map;

public class FacetUtil {

    public static <T> Map<String, Map<String, Long>> createFacetResult(FacetPage<T> facetPage) {
        Map<String, Map<String,Long>> facetResultMap = new HashMap<>();
        if(facetPage != null) {
            facetPage.getFacetFields().forEach(facetField-> {
                Map<String,Long> map = new HashMap<>();
                facetPage.getFacetResultPage(facetField).getContent()
                        .forEach(facetFieldCount->map.put(facetFieldCount.getValue(),facetFieldCount.getValueCount()));
                facetResultMap.put(facetField.getName(),map);
            });
        }
        return facetResultMap;
    }

    public static <T> Map<String, Long> getFacetFieldResultByFieldName(FacetPage<T> facetPage,String facetField) {
        Map<String,Long> facetFieldResultMap = new HashMap<>();
        if(facetPage != null) {
                facetPage.getFacetResultPage(facetField).getContent()
                        .forEach(facetFieldCount->facetFieldResultMap.put(facetFieldCount.getValue(),facetFieldCount.getValueCount()));
        }
        return facetFieldResultMap;
    }

}
