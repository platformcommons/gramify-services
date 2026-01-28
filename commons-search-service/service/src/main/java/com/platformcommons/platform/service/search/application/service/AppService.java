package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.domain.App;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.FacetPage;

import java.util.List;
import java.util.Set;

public interface AppService {

    App createApp(App app);

    App updateApp(App app);

    App updateAppSlug(Long appId, String slug);

    Page<App> readAppByName(String name, Integer page, Integer size);

    FacetPage<App> getApp(Set<String> fields, Integer page, Integer size);

    FacetPage<App> getAppWithFilter(String searchTerm,Set<String> fields, Integer page, Integer size, List<String> domainCodes, List<String> subDomainCodes, List<Long> userCaseIds, String sortBy,String direction);

    Set<App> getByIds(Set<Long> ids);
}
