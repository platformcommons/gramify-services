package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.TMAChannelProduct;
import com.platformcommons.platform.service.search.domain.TMAChannelProductSKU;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.Set;

public interface TMAChannelProductRepository extends SolrCrudRepository<TMAChannelProduct,String> {

    @Query("traderId:?0")
    Set<TMAChannelProduct> findAllByTraderId(Long traderId);
    @Query("productId:?0")
    Set<TMAChannelProduct> findByProductId(Long productId);

    @Query("defaultSKUId:?0")
    Set<TMAChannelProduct> findByDefaultSkuId(Long productSkuId);

    Set<TMAChannelProduct> findByGenericProductVarietyIdAndMarketIdAndChannelId(Long varietyId, Long marketId, Long channelId);
}
