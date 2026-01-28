package com.platformcommons.platform.service.search.application.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platformcommons.platform.security.token.PlatformAppToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.product.dto.ClassificationDTO;
import com.platformcommons.platform.service.search.facade.client.ClassificationClientV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TraderTypeCategoryUtil {

    @Autowired
    private ClassificationClientV2 classificationClientV2;


    @Value("${commons.platform.service.search-client.api-key:YXBwS2V5OmRlYzAxODgzLTZjNDItNDMxOC1hYjk1LWRmZjJkMzEwNDFiMSxhcHBDb2RlOkNPTU1PTlMuU0VSQUNIX0NMSUVOVA==}")
    private String appKey;

    public Set<String> getClassificationCodesForTraderInterest(String marketCode, String parentClassificationCode, String sourceTraderType, String transactionType) {
        boolean isAppToken = SecurityContextHolder.getContext().getAuthentication() instanceof PlatformAppToken;
        Set<ClassificationDTO> allClassifications = new HashSet<>();
        int page = 0;
        int size = 50;
        while (true) {
            ResponseEntity<Map<String, Object>> responseEntity = isAppToken ? classificationClientV2.getChildClassificationByParentCodeAndTraderTypeInterestWithAppKey(
                    parentClassificationCode, page, size, null, null, null, sourceTraderType, null, transactionType, "Appkey " + appKey) :
                    classificationClientV2.getChildClassificationByParentCodeAndTraderTypeInterest(
                            parentClassificationCode, page, size, null, null, null, sourceTraderType, null, transactionType,
                            PlatformSecurityUtil.getToken());

            if (responseEntity.getStatusCode() == HttpStatus.PARTIAL_CONTENT) {
                Set<ClassificationDTO> classificationPage = ((Collection<Object>) Objects.requireNonNull(responseEntity.getBody()).get("elements")).stream().map(o -> {
                    return new ObjectMapper().convertValue(o, ClassificationDTO.class);
                }).collect(Collectors.toSet());
                allClassifications.addAll(classificationPage);
                page++;
            } else if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Set<ClassificationDTO> classificationPage = ((Collection<Object>) Objects.requireNonNull(responseEntity.getBody()).get("elements")).stream().map(o -> {
                    return new ObjectMapper().convertValue(o, ClassificationDTO.class);
                }).collect(Collectors.toSet());
                allClassifications.addAll(classificationPage);
                break;
            }
        }
        return allClassifications.stream().map(ClassificationDTO::getCode).collect(Collectors.toSet());
    }
}
