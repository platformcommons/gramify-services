package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.application.service.AppService;
import com.platformcommons.platform.service.search.domain.App;
import com.platformcommons.platform.service.search.dto.AppDTO;
import com.platformcommons.platform.service.search.facade.AppFacade;
import com.platformcommons.platform.service.search.facade.assembler.AppDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.*;
import java.util.stream.Collectors;

import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class AppFacadeImpl implements AppFacade {

    @Autowired
    private AppService appService;

    @Autowired
    private AppDTOAssembler appDTOAssembler;

    @Override
    public PageDTO<AppDTO> readAppByName(String name, Integer page, Integer size) {
        Page<App> apps = appService.readAppByName(name, page, size);
        return new PageDTO<>(apps.stream().map(appDTOAssembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)),apps.hasNext(),apps.getTotalElements());

    }

    @Override
    public FacetPageDTO<App> getResultWithFacet(Set<String> fields, Integer page, Integer size){
        FacetPage<App> results= appService.getApp(fields,page,size);
        Map<String, Map<String,Long>> facetResult = new HashMap<>();
        fields.forEach(it-> {
            Map<String,Long> map = new HashMap<>();
            results.getFacetResultPage(it).getContent().forEach(it2->map.put(it2.getValue(),it2.getValueCount()));
            facetResult.put(it,map);
        });
        return new FacetPageDTO<App>(results.get().collect(Collectors.toCollection(LinkedHashSet::new)), results.hasNext(),facetResult);

    }

    @Override
    public FacetPageDTO<AppDTO> getResultWithFacetAndFilter(String searchTerm,Set<String> fields, List<String> domainCodes,
                                                         List<String> subDomainCodes, List<Long> useCseIds,
                                                         Integer page, Integer size,String sortBy,String direction){
        FacetPage<App> results= appService.getAppWithFilter(searchTerm,fields,page,size,domainCodes,subDomainCodes,useCseIds,sortBy,direction);
        Map<String, Map<String,Long>> facetResult = new HashMap<>();
        fields.forEach(it-> {
            Map<String,Long> map = new HashMap<>();
            results.getFacetResultPage(it).getContent().forEach(it2->map.put(it2.getValue(),it2.getValueCount()));
            facetResult.put(it,map);
        });
        return new FacetPageDTO<AppDTO>(results.stream().map(appDTOAssembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)), results.hasNext(),facetResult, results.getTotalElements());

    }

    @Override
    public Set<AppDTO> getByIds(Set<Long> ids) {
        Set<App> apps = appService.getByIds(ids);
        return apps.stream().map(appDTOAssembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));

    }


    @Override
    public AppDTO updateAppSlug(Long appId, String slug){
        return  appDTOAssembler.toDTO(appService.updateAppSlug(appId,slug));
    }
}
