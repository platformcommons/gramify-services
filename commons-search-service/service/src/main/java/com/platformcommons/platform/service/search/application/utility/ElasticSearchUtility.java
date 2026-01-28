package com.platformcommons.platform.service.search.application.utility;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ElasticSearchUtility {

    public static boolean computeHasMoreHits(Long totalElements,Integer elementsReturnedInCurrentIndex, Integer page,Integer size) {
        Long elementsFetchedUptoPage = (long) (page * size);
        return totalElements > elementsFetchedUptoPage + elementsReturnedInCurrentIndex;
    }

    public static Map<String,Map<String,Long>> createTermsFacetResult(Aggregations aggregations) {
        Map<String,Map<String,Long>> result = new LinkedHashMap<>();
        if(aggregations != null) {
            aggregations.asMap().forEach((facetField, aggregation) -> {
               if(aggregation instanceof Terms) {
                   Terms term = (Terms) aggregation;
                   Map<String,Long> bucketMap = new LinkedHashMap<>();
                   for(Terms.Bucket bucket : term.getBuckets()) {
                       bucketMap.put(bucket.getKeyAsString(),bucket.getDocCount());
                   }
                   result.put(facetField,bucketMap);
               }
            });
        }
        return result;
    }

    public static void createTermQueryAndFilterInBoolQuery(BoolQueryBuilder boolQueryBuilder, String fieldName, Object fieldValue) {
        boolQueryBuilder.filter(QueryBuilders.termQuery(fieldName, fieldValue));
    }

    public static void createTermQueryAndFilterInBoolQuery(BoolQueryBuilder boolQueryBuilder, String fieldName, Collection<?> fieldValues) {
        boolQueryBuilder.filter(QueryBuilders.termsQuery(fieldName, fieldValues));
    }

}
