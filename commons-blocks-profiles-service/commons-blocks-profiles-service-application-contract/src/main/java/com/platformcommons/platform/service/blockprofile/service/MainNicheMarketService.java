package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing MainNicheMarket-related operations.
 * Provides CRUD operations and pagination support for MainNicheMarket entities.
 *
 * @since 1.0.0
 */

public interface MainNicheMarketService {

    /**
     * Retrieves all MainNicheMarkets by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MainNicheMarketDTO in the same order as retrieved
     */
    Set<MainNicheMarketDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new MainNicheMarket entry in the system.
     *
     * @param MainNicheMarket The MainNicheMarket information to be saved
     * @return The saved MainNicheMarket data
     * @since 1.0.0
     */
    MainNicheMarketDTO save(MainNicheMarketDTO MainNicheMarket);

    /**
     * Updates an existing MainNicheMarket entry.
     *
     * @param MainNicheMarket The MainNicheMarket information to be updated
     * @return The updated MainNicheMarket data
     * @since 1.0.0
     */
    MainNicheMarketDTO update(MainNicheMarketDTO MainNicheMarket);

    /**
     * Retrieves a paginated list of MainNicheMarkets.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of MainNicheMarkets
     * @since 1.0.0
     */
    PageDTO<MainNicheMarketDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a MainNicheMarket by their ID with a specified reason.
     *
     * @param id     The ID of the MainNicheMarket to delete
     * @param reason The reason for deletion
     * @return MainNicheMarketDTO
     */
    MainNicheMarketDTO deleteById(Long id, String reason);

    /**
     * Retrieves a MainNicheMarket by their ID.
     *
     * @param id The ID of the MainNicheMarket to retrieve
     * @return The MainNicheMarket with the specified ID
     * @since 1.0.0
     */
    MainNicheMarketDTO getById(Long id);




}
