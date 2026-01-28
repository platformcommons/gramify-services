package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.PostDTO;

import java.util.Set;


public interface PostFacade {

    PageDTO<PostDTO> getPostTimeline(String searchTerm,String tenantLogin, String  appContext, String domainContext,
                                     String languageContext, String locationContext, String postType, Set<String> postSubTypes,
                                     Integer page, Integer size, String sortBy, String direction);
}
