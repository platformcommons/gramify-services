package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.search.application.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.service.TagSearchService;
import com.platformcommons.platform.service.search.application.utility.SearchTermParser;
import com.platformcommons.platform.service.search.domain.TMAChannelSolution;
import com.platformcommons.platform.service.search.domain.TagSearch;
import com.platformcommons.platform.service.search.domain.repo.TagSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class TagSearchServiceImpl implements TagSearchService {

    @Autowired
    private TagSearchRepository tagSearchRepository;

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public TagSearch createTagSearch(TagSearch tagSearch) {
        return tagSearchRepository.save(tagSearch);
    }


    @Override
    public TagSearch updateTagSearch(TagSearch tagSearch) {
        TagSearch fetchedTagSearch = tagSearchRepository.findById(tagSearch.getId()).orElseThrow(()-> new NotFoundException(String.format("TagSearch with  %d  not found",tagSearch.getId())));
        fetchedTagSearch.update(tagSearch);
        return tagSearchRepository.save(fetchedTagSearch);
    }

    @Override
    public Page<TagSearch> readTagSearch(String keyword, String context, Set<String> excludeTypes,Integer page, Integer size) {
        Query query = new SimpleQuery(new SimpleStringCriteria(buildQueryForTagSearch(keyword)));
        if (excludeTypes != null && !excludeTypes.isEmpty()) {
            Criteria excludeTypesCriteria = new Criteria("type").not().in(excludeTypes);
            query.addCriteria(excludeTypesCriteria);
        }
        if(context !=null ){
            Criteria criteria = new Criteria("context").is(context);
            query.addCriteria(criteria);
        }
        query.setPageRequest(PageRequest.of(page, size));
        return solrTemplate.query("tag_search",query, TagSearch.class);
    }

    @Override
    public void addOrUpdateAll(Set<TagSearch> tagSearches) {
        tagSearchRepository.saveAll(tagSearches);
    }

    private String buildQueryForTagSearch(String searchTerm){
        if(searchTerm!=null) {
            searchTerm = SearchTermParser.parseSearchTerm(searchTerm);
            return "labels:" + searchTerm +
                    " OR name:" + searchTerm;
        }
        else {
            return "*:*";
        }
    }
    @Override
    public void reindex() {
        int page = 0;
        int pageSize = 200;
        Page<TagSearch> resultPage;
        do{
            Pageable pageable = PageRequest.of(page, pageSize);
            resultPage = tagSearchRepository.findAll((pageable));

            List<TagSearch> dataBatch = resultPage.getContent();

            solrTemplate.saveBeans(ServiceConstant.TAG_SEARCH_CORE, dataBatch);
            solrTemplate.commit(ServiceConstant.TAG_SEARCH_CORE);
            page++;
        }while (!resultPage.isLast());
    }

}
