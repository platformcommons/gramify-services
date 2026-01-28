package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.application.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.service.PostService;
import com.platformcommons.platform.service.search.application.utility.JPAUtility;
import com.platformcommons.platform.service.search.application.utility.PlatformUtil;
import com.platformcommons.platform.service.search.domain.Post;
import com.platformcommons.platform.service.search.dto.PostDTO;
import com.platformcommons.platform.service.search.facade.PostFacade;
import com.platformcommons.platform.service.search.facade.assembler.PostDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Component
public class PostFacadeImpl implements PostFacade {

    @Autowired
    private PostService service;

    @Autowired
    private PostDTOAssembler assembler;

    @Override
    public PageDTO<PostDTO> getPostTimeline(String searchTerm,String tenantLogin,String  appContext, String domainContext, String languageContext,
                                            String locationContext, String postType,Set<String> postSubTypes,
                                            Integer page, Integer size, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(page, size, JPAUtility.convertToSort(sortBy,direction));
        String status = ServiceConstant.POST_STATUS_APPROVED;
        if(PlatformUtil.isSession()) {
            tenantLogin = PlatformSecurityUtil.getCurrentTenantLogin();
        }
        Page<Post> result = service.getPostTimeline(searchTerm,tenantLogin, appContext, domainContext, languageContext, locationContext,
                postType, postSubTypes,status,pageable);
        Set<PostDTO> postDTOS = result.stream()
                .map(assembler::toCustomDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return new PageDTO<>(postDTOS, result.hasNext(), result.getTotalElements());
    }


}
