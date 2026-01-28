package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing ProductionSeason-related operations.
 * Provides CRUD operations and pagination support for ProductionSeason entities.
 *
 * @since 1.0.0
 */

public interface ProductionSeasonService {

    /**
     * Retrieves all ProductionSeasons by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ProductionSeasonDTO in the same order as retrieved
     */
    Set<ProductionSeasonDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new ProductionSeason entry in the system.
     *
     * @param ProductionSeason The ProductionSeason information to be saved
     * @return The saved ProductionSeason data
     * @since 1.0.0
     */
    ProductionSeasonDTO save(ProductionSeasonDTO ProductionSeason);

    /**
     * Updates an existing ProductionSeason entry.
     *
     * @param ProductionSeason The ProductionSeason information to be updated
     * @return The updated ProductionSeason data
     * @since 1.0.0
     */
    ProductionSeasonDTO update(ProductionSeasonDTO ProductionSeason);

    /**
     * Retrieves a paginated list of ProductionSeasons.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of ProductionSeasons
     * @since 1.0.0
     */
    PageDTO<ProductionSeasonDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a ProductionSeason by their ID with a specified reason.
     *
     * @param id     The ID of the ProductionSeason to delete
     * @param reason The reason for deletion
     * @return ProductionSeasonDTO
     */
    ProductionSeasonDTO deleteById(Long id, String reason);

    /**
     * Retrieves a ProductionSeason by their ID.
     *
     * @param id The ID of the ProductionSeason to retrieve
     * @return The ProductionSeason with the specified ID
     * @since 1.0.0
     */
    ProductionSeasonDTO getById(Long id);




}
