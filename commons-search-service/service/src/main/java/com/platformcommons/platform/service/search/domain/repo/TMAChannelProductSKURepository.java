package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.TMAChannelProduct;
import com.platformcommons.platform.service.search.domain.TMAChannelProductSKU;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.Set;

public interface TMAChannelProductSKURepository extends SolrCrudRepository<TMAChannelProductSKU,Long> {
    @Query("traderId:?0")
    Set<TMAChannelProductSKU> findAllByTraderId(Long traderId);
    @Query("productId:?0")
    Set<TMAChannelProductSKU> findAllByProductId(Long productId);

    Set<TMAChannelProductSKU> findAllByProductIdAndMarketIdAndChannelId(Long productId, Long marketId, Long channelId);
}
