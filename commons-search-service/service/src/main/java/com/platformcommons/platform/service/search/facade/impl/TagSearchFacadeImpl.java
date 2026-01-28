package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.application.service.TagSearchService;
import com.platformcommons.platform.service.search.domain.TagSearch;
import com.platformcommons.platform.service.search.dto.TagSearchDTO;
import com.platformcommons.platform.service.search.facade.TagSearchFacade;
import com.platformcommons.platform.service.search.facade.assembler.TagSearchDTOAssembler;
import com.platformcommons.platform.service.search.facade.client.TagClientV2;
import com.platformcommons.platform.service.search.messaging.mapper.TagDTOEventMapper;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
@Slf4j
public class TagSearchFacadeImpl implements TagSearchFacade {

    @Autowired
    private TagSearchService tagSearchService;

    @Autowired
    private TagSearchDTOAssembler tagSearchDTOAssembler;

    @Autowired
    private TagClientV2 tagClient;


    @Autowired
    private TagDTOEventMapper tagDTOEventMapper;

    @Override
    public PageDTO<TagSearchDTO> readTagSearch(String keyword, String context,Set<String> excludeTypes, Integer page, Integer size) {
        Page<TagSearch> tagSearches = tagSearchService.readTagSearch(keyword, context,excludeTypes, page, size);
        return new PageDTO<>(tagSearches.stream().map(tagSearchDTOAssembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)),tagSearches.hasNext(),tagSearches.getTotalElements());

    }

    @Async
    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void syncTag(Integer startPage, Integer defaultSize, String type) {
        startPage = startPage==null ? 0 : startPage;
        int statusCode =0;
        int retryCount = 0;
        do{
            try {
                ResponseEntity<PageDTO<TagDTO>> tagResponse = tagClient.getTagsV2(
                       startPage,defaultSize,null,null,type, PlatformSecurityUtil.getToken());
                assert tagResponse.getBody() != null;
                Set<TagDTO> tagDTOS = Objects.requireNonNull(tagResponse.getBody().getElements());
                Set<TagSearch> tagSearches = tagDTOS.stream().map(tagDTOEventMapper::fromDTO)
                        .collect(Collectors.toCollection(LinkedHashSet::new));
                tagSearchService.addOrUpdateAll(tagSearches);
                statusCode = tagResponse.getStatusCode().value();
                startPage = startPage + 1;
                retryCount = 0;
            }
            catch (Exception e){
                if(e instanceof FeignException){
                    FeignException feignException = (FeignException) e;
                    if(feignException.status()!=200 || feignException.status()!=206){
                        statusCode = feignException.status();
                    }
                }
                log.error(ExceptionUtils.getRootCauseMessage(e));
                retryCount = retryCount+1;
            }
        } while (statusCode==206 && retryCount < 5 );

    }

    @Override
    public void reindex() {
        tagSearchService.reindex();
    }


}
