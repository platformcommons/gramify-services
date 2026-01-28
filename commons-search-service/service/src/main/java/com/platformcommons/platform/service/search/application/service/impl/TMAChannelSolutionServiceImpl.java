package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.service.market.dto.TMAChannelSolutionDTO;
import com.platformcommons.platform.service.product.dto.SolutionTagDTO;
import com.platformcommons.platform.service.search.application.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.service.GenericProductVarietyService;
import com.platformcommons.platform.service.search.application.service.TMAChannelSolutionService;
import com.platformcommons.platform.service.search.application.utility.JPAUtility;
import com.platformcommons.platform.service.search.application.utility.SearchTermParser;
import com.platformcommons.platform.service.search.application.utility.SolrUtil;
import com.platformcommons.platform.service.search.domain.*;
import com.platformcommons.platform.service.search.domain.repo.GenericSolutionRepository;
import com.platformcommons.platform.service.search.domain.repo.TMAChannelSolutionRepository;
import com.platformcommons.platform.service.search.facade.client.ClassificationClientV2;
import com.platformcommons.platform.service.search.messaging.mapper.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TMAChannelSolutionServiceImpl  implements TMAChannelSolutionService {

    @Autowired
    private TMAChannelSolutionRepository tmaChannelSolutionRepository;

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private GenericSolutionRepository genericSolutionRepository;

    @Autowired
    private GenericProductVarietyService genericProductVarietyService;

    @Autowired
    private ClassificationClientV2 classificationClientV2;

    @Value("${commons.platform.service.market-client.api-key:YXBwS2V5OmRlYzAxODgzLTZjNDItNDMxOC1hYjk1LWRmZjJkMzEwNDFiMSxhcHBDb2RlOkNPTU1PTlMuU0VSQUNIX0NMSUVOVA==}")
    private String appKey;


    @Override
    public TMAChannelSolution addOrUpdate(TMAChannelSolution tmaChannelSolution) {
        Optional<TMAChannelSolution> optionalTMAChannelSolution = tmaChannelSolutionRepository.findById(tmaChannelSolution.getId());
        TMAChannelSolution tmaChannelSolutionToBeSavedOrUpdate ;
        if(optionalTMAChannelSolution.isPresent()){
            TMAChannelSolution fetchedTMAChannelSolution  = optionalTMAChannelSolution.get();
            fetchedTMAChannelSolution.update(tmaChannelSolution);
            tmaChannelSolutionToBeSavedOrUpdate = fetchedTMAChannelSolution;
        }
        else {
            tmaChannelSolutionToBeSavedOrUpdate = tmaChannelSolution;
            if(tmaChannelSolution.getBaseSolutionId() != null){
                Optional<GenericSolution> optionalGenericSolution = genericSolutionRepository.findById(String.valueOf(tmaChannelSolution.getBaseSolutionId()));
                optionalGenericSolution.ifPresent(fetchedGenericSolution -> {
                  Long currentNoOfChildSolutions = fetchedGenericSolution.getNoOfChildSolutions();
                  fetchedGenericSolution.setNoOfChildSolutions((currentNoOfChildSolutions != null) ? currentNoOfChildSolutions + 1L : 1L);
                  genericSolutionRepository.save(fetchedGenericSolution);
               });
            }
        }
        return tmaChannelSolutionRepository.save(tmaChannelSolutionToBeSavedOrUpdate);
    }

    @Override
    public Page<TMAChannelSolution> readTMAChannelSolutionByTitles(String marketCode, String channelCode, String searchTerm,Long baseSolutionId, Set<String> categoryCodes,
                                                                   Set<String> subCategoryCodes, Long traderId, String solutionType, String solutionClass, String tagType, Set<String> tagCodes,
                                                                   Integer page, Integer size, String sortBy, String direction) {
        Query query = new SimpleQuery(new SimpleStringCriteria(buildQueryForTMAChannelSolutionSearch(searchTerm)));

        Criteria marketAndChannelCriteria = Criteria.where("marketCode").is(marketCode)
                .and("channelCode").is(channelCode);
        query.addCriteria(marketAndChannelCriteria);

        if(baseSolutionId!=null ){
            Criteria criteria = new Criteria("baseSolutionId").is(baseSolutionId);
            query.addCriteria(criteria);
        }
        if(categoryCodes!=null && !categoryCodes.isEmpty()){
            Criteria criteria = new Criteria("categoryCodes").in(categoryCodes);
            query.addCriteria(criteria);
        }
        if(subCategoryCodes!=null && !subCategoryCodes.isEmpty()){
            Criteria criteria = new Criteria("subCategoryCodes").in(subCategoryCodes);
            query.addCriteria(criteria);

        }
        if(solutionType!=null ){
            Criteria criteria = new Criteria("solutionType").is(solutionType);
            query.addCriteria(criteria);
        }
        if(solutionClass!=null ){
            Criteria criteria = new Criteria("solutionClass").is(solutionClass);
            query.addCriteria(criteria);
        }
        Criteria criteria;
        if(traderId!=null){
            criteria = new SimpleStringCriteria("(tmaChannelSolutionStatus:TMA_CHANNEL_SOLUTION_PUBLISH_STATUS.PUBLISHED OR (-tmaChannelSolutionStatus:TMA_CHANNEL_SOLUTION_PUBLISH_STATUS.PUBLISHED AND traderId:"+traderId+"))");

        }else {
            criteria = new Criteria("tmaChannelSolutionStatus").is("TMA_CHANNEL_SOLUTION_PUBLISH_STATUS.PUBLISHED");
        }
        query.addCriteria(criteria);

        FilterQuery fq = searchSolutionsByTagCode(tagType,tagCodes);
        if(fq != null){
            query.addFilterQuery(fq);
        }

        query.setPageRequest(PageRequest.of(page, size, JPAUtility.convertToSort(sortBy,direction)));
        return solrTemplate.query("tma_channel_solution",query, TMAChannelSolution.class);
    }

    @Override
    public Page<TMAChannelSolution> getTMAChannelSolutions(String marketCode, String channelCode, Long traderId, String solutionType, String solutionClass, Integer page, Integer size, String sortBy, String direction) {
        Query query = new SimpleQuery();

        Criteria marketAndChannelCriteria = Criteria.where("marketCode").is(marketCode)
                .and("channelCode").is(channelCode);
        query.addCriteria(marketAndChannelCriteria);

        if(solutionType!=null ){
            Criteria criteria = new Criteria("solutionType").is(solutionType);
            query.addCriteria(criteria);
        }
        if(solutionClass!=null ){
            Criteria criteria = new Criteria("solutionClass").is(solutionClass);
            query.addCriteria(criteria);
        }
        if(traderId!=null){
            Criteria criteria = new Criteria("traderId").is(traderId);
            query.addCriteria(criteria);
        }

        Criteria criteria = new SimpleStringCriteria("(isActive:true OR (*:* -isActive:[* TO *]))");
        query.addCriteria(criteria);

        Criteria publishCriteria = new SimpleStringCriteria("(tmaChannelSolutionStatus:TMA_CHANNEL_SOLUTION_PUBLISH_STATUS.PUBLISHED OR (*:* -tmaChannelSolutionStatus:[* TO *]))");
        query.addCriteria(publishCriteria);

        query.setPageRequest(PageRequest.of(page, size, JPAUtility.convertToSort(sortBy,direction)));
        return solrTemplate.query("tma_channel_solution",query, TMAChannelSolution.class);
    }

    @Override
    public void updateTraderDetails(Long traderId, String traderDisplayName, String iconPic) {
        Criteria criteria = new Criteria("traderId").is(traderId);
        Query countQuery = new SimpleQuery(criteria);
        long totalCount = solrTemplate.count("tma_channel_solution", countQuery);

        int pageSize = 50;
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        for (int page = 0; page < totalPages; page++) {
            Query pagedQuery = new SimpleQuery(criteria);
            pagedQuery.setPageRequest(PageRequest.of(page, pageSize));

            List<TMAChannelSolution> solutions = solrTemplate
                    .query("tma_channel_solution", pagedQuery, TMAChannelSolution.class)
                    .getContent();

            for (TMAChannelSolution solution : solutions) {
                solution.setLastModifiedTimestamp();
                solution.setTraderName(traderDisplayName != null ? traderDisplayName:solution.getTraderName());
                solution.setTraderIconURL(iconPic != null ? iconPic:solution.getTraderIconURL());
            }

            solrTemplate.saveBeans("tma_channel_solution", solutions);
            solrTemplate.commit("tma_channel_solution");
        }
    }

    @Override
    public void deleteTmaChannelSolution(Long solutionId, Long traderId, Long channelId, Long marketId) {
        Query query = new SimpleQuery();

        Criteria tmaChannelSolutionCriteria = Criteria.where("solutionId").is(solutionId)
                .and("traderId").is(traderId).and("channelId").is(channelId).and("marketId").is(marketId);
        query.addCriteria(tmaChannelSolutionCriteria);
        TMAChannelSolution fetchedTmaChannelSolution = solrTemplate.queryForObject("tma_channel_solution",query, TMAChannelSolution.class)
                .orElseThrow(() -> new RuntimeException("TmaChannelSolution Not Found for solution,trader,channel,market "));
        fetchedTmaChannelSolution.setIsActive(false);
        solrTemplate.saveBean("tma_channel_solution", fetchedTmaChannelSolution);
        solrTemplate.commit("tma_channel_solution");

        GenericProductVariety fetchedGenericProductVariety =  genericProductVarietyService.getByGenericProductVarietyId(fetchedTmaChannelSolution.getGenericProductVarietyId(),
                fetchedTmaChannelSolution.getChannelCode(), fetchedTmaChannelSolution.getMarketCode());

        if (fetchedGenericProductVariety != null && fetchedGenericProductVariety.getSolutionIds() != null) {
            boolean removed = fetchedGenericProductVariety.getSolutionIds()
                    .removeIf(id -> id.equals(solutionId));
            if (removed) {
                solrTemplate.saveBean("generic_product_variety", fetchedGenericProductVariety);
            }
        }
        solrTemplate.commit("generic_product_variety");
    }

    @Override
    public TMAChannelSolution save(TMAChannelSolution tmaChannelSolution){
        tmaChannelSolution.init();
        return tmaChannelSolutionRepository.save(tmaChannelSolution);
    }

    @Override
    public void updateGenericProductDetails(GenericProduct newGenericProduct) {
        Criteria criteria = new Criteria("genericProductId").is(newGenericProduct.getGenericProductId()).and("marketId").is(newGenericProduct.marketId);
        Query countQuery = new SimpleQuery(criteria);
        long totalCount = solrTemplate.count("tma_channel_solution", countQuery);

        int pageSize = 50;
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        for (int page = 0; page < totalPages; page++) {
            Query pagedQuery = new SimpleQuery(criteria);
            pagedQuery.setPageRequest(PageRequest.of(page, pageSize));

            List<TMAChannelSolution> tmaSolutions = solrTemplate
                    .query("tma_channel_solution", pagedQuery, TMAChannelSolution.class)
                    .getContent();
            TMAChannelSolution newTmaChannelSolution = convertGpBasicDetailsToTmaSolution(newGenericProduct);
            for (TMAChannelSolution tmaSolution : tmaSolutions) {
                tmaSolution.update(newTmaChannelSolution);
            }

            solrTemplate.saveBeans("tma_channel_solution", tmaSolutions);
            solrTemplate.commit("tma_channel_solution");
        }
    }

    private TMAChannelSolution convertGpBasicDetailsToTmaSolution(GenericProduct newGenericProduct) {
        return TMAChannelSolution.builder()
                .genericProductId(newGenericProduct.getGenericProductId())
                .genericProductName(newGenericProduct.getGenericProductName())
                .genericProductCode(newGenericProduct.getGenericProductCode())
                .categoryCodes(newGenericProduct.getCategoryCodes())
                .categoryNames(newGenericProduct.getCategoryNames())
                .subCategoryCodes(newGenericProduct.getSubCategoryCodes())
                .subCategoryNames(newGenericProduct.getSubCategoryNames())
                .build();
    }

    @Override
    public void updateGenericProductVarietyDetails(GenericProductVariety newGenericProductVariety) {
        Long gpvId = newGenericProductVariety.getGenericProductVarietyId();
        String gpvCode = newGenericProductVariety.getGenericProductVarietyCode();
        Long marketId = newGenericProductVariety.getMarketId();

        // Build criteria to find solutions with this GPV as primary or as tag
        String orPart = "genericProductVarietyId:" + gpvId
                + " OR tagCodes:TAG." + gpvCode
                + " OR TAG_CODE_BY_TYPE_TAG_TYPE_GENERIC_PRODUCT_VARIETY:TAG." + gpvCode;

        Criteria criteria = new Criteria("marketId").is(marketId)
                .and(new Criteria().expression("(" + orPart + ")"));


        Query countQuery = new SimpleQuery(criteria);
        long totalCount = solrTemplate.count("tma_channel_solution", countQuery);
        int pageSize = 20;
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        for (int page = 0; page < totalPages; page++) {
            Query pagedQuery = new SimpleQuery(criteria);
            pagedQuery.setPageRequest(PageRequest.of(page, pageSize));

            List<TMAChannelSolution> tmaSolutions = solrTemplate
                    .query("tma_channel_solution", pagedQuery, TMAChannelSolution.class)
                    .getContent();

            if (!tmaSolutions.isEmpty()) {
                updateSolutionsBatchWithFeign(tmaSolutions, newGenericProductVariety);
            }
        }
    }

    private void updateSolutionsBatchWithFeign(List<TMAChannelSolution> solutions, GenericProductVariety newGenericProductVariety) {
        // Extract solution IDs for feign call
        Set<Long> solutionIds = solutions.stream()
                .map(TMAChannelSolution::getSolutionId).filter(Objects::nonNull).collect(Collectors.toSet());

        if (!solutionIds.isEmpty()) {
            // Get fresh solution tag data from Product service
            ResponseEntity<Map<Long, Set<SolutionTagDTO>>> response = classificationClientV2
                    .getSolutionsWithSolutionTagsWithAppKey("Appkey " + appKey, solutionIds);
            Map<Long, Set<SolutionTagDTO>> freshSolutionTagData = response.getBody();

            if (freshSolutionTagData != null) {
                for (TMAChannelSolution solution : solutions) {
                    // Case 1: Solution has this GPV as primary GPV - update basic details
                    if (Objects.equals(solution.getGenericProductVarietyId(), newGenericProductVariety.getGenericProductVarietyId())) {
                        TMAChannelSolution newSolution = convertGpvBasicDetailsToTmaSolution(newGenericProductVariety);
                        solution.update(newSolution);
                    }

                    // Case 2: Update all tag fields from fresh data
                    Long solutionId = solution.getSolutionId();
                    if (solutionId != null && freshSolutionTagData.containsKey(solutionId)) {
                        TMAChannelSolution newSolution = buildTmaChannelSolutionWithTag(freshSolutionTagData.get(solutionId));
                        if(newSolution != null){
                            solution.updateTagDetails(newSolution);
                        }
                    }
                }
            }
        }

        solrTemplate.saveBeans("tma_channel_solution", solutions);
        solrTemplate.commit("tma_channel_solution");
    }

    private TMAChannelSolution buildTmaChannelSolutionWithTag( Set<SolutionTagDTO> freshTags) {
        if (freshTags != null && !freshTags.isEmpty()) {
            return TMAChannelSolution.builder()
                    .tagCodes(MapperUtil.getTagCodes(freshTags))
                    .tagLabels(MapperUtil.getTagLabels(freshTags))
                    .tagCodesByType(MapperUtil.getTagCodesByTypeForSolutionTag(freshTags))
                    .tagLabelsByType(MapperUtil.getTagLabelsByTypeForSolutionTag(freshTags))
                    .tagLabelsByLang(MapperUtil.getTagLabelsByLangForSolutionTag(freshTags))
                    .build();
        }
        return null;
    }

    private TMAChannelSolution convertGpvBasicDetailsToTmaSolution(GenericProductVariety newGenericProductVariety) {
        return TMAChannelSolution.builder()
                .genericProductId(newGenericProductVariety.getGenericProductId())
                .genericProductName(newGenericProductVariety.getGenericProductName())
                .genericProductCode(newGenericProductVariety.getGenericProductCode())
                .genericProductVarietyId(newGenericProductVariety.getGenericProductVarietyId())
                .genericProductVarietyName(newGenericProductVariety.getGenericProductVarietyName())
                .genericProductVarietyCode(newGenericProductVariety.getGenericProductVarietyCode())
                .categoryCodes(newGenericProductVariety.getCategoryCodes())
                .categoryNames(newGenericProductVariety.getCategoryNames())
                .subCategoryCodes(newGenericProductVariety.getSubCategoryCodes())
                .subCategoryNames(newGenericProductVariety.getSubCategoryNames())
                .build();
    }

    private String buildQueryForTMAChannelSolutionSearch(String searchTerm){
        if(searchTerm!=null) {
            searchTerm = SearchTermParser.parseSearchTerm(searchTerm);
            return "titles:" + searchTerm ;
        }
        else {
            return "*:*";
        }
    }

    private FilterQuery searchSolutionsByTagCode(String tagType, Set<String> tagCodes) {
        if (tagType != null && !tagType.isEmpty() && tagCodes != null && !tagCodes.isEmpty()) {
            // Case 1: TagType + TagCodes → filter only in tagCodesByType
            String safeType = SolrUtil.solrSafeKey(tagType);

            Criteria criteria = new Criteria("TAG_CODE_BY_TYPE_" + safeType)
                    .in(tagCodes.stream()
                            .map(SolrUtil::escapeQueryChars)
                            .collect(Collectors.toList()));

            return new SimpleFilterQuery(criteria);

        } else if (tagCodes != null && !tagCodes.isEmpty()) {
            // Case 2: Only TagCodes → search in both tagCodes and tagCodesByType
            Criteria tagCodesCriteria = new Criteria("tagCodes")
                    .in(tagCodes.stream()
                            .map(SolrUtil::escapeQueryChars)
                            .collect(Collectors.toList()));

            return new SimpleFilterQuery(tagCodesCriteria);
        }
        return null;
    }
    @Override
    public void reindex() {
        int page = 0;
        int pageSize = 200;
        Page<TMAChannelSolution> resultPage;
        do{
            Pageable pageable = PageRequest.of(page, pageSize);
            resultPage = tmaChannelSolutionRepository.findAll((pageable));

            List<TMAChannelSolution> dataBatch = resultPage.getContent();

            solrTemplate.saveBeans(ServiceConstant.TMA_CHANNEL_SOLUTION_CORE, dataBatch);
            solrTemplate.commit(ServiceConstant.TMA_CHANNEL_SOLUTION_CORE);
            page++;
        }while (!resultPage.isLast());
    }

}
