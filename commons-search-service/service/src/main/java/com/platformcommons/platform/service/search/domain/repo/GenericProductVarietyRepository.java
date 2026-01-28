package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.GenericProductVariety;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface GenericProductVarietyRepository extends SolrCrudRepository<GenericProductVariety,String> {
    List<GenericProductVariety> findByGenericProductVarietyIdAndChannelCodeAndMarketCode(Long genericProductVarietyId, String channelCode, String marketCode);
}
