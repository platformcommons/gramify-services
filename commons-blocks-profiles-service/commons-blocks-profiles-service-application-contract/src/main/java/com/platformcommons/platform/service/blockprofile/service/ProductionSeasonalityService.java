package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing ProductionSeasonality-related operations.
 * Provides CRUD operations and pagination support for ProductionSeasonality entities.
 *
 * @since 1.0.0
 */

public interface ProductionSeasonalityService {

    /**
     * Retrieves all ProductionSeasonalitys by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ProductionSeasonalityDTO in the same order as retrieved
     */
    Set<ProductionSeasonalityDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new ProductionSeasonality entry in the system.
     *
     * @param ProductionSeasonality The ProductionSeasonality information to be saved
     * @return The saved ProductionSeasonality data
     * @since 1.0.0
     */
    ProductionSeasonalityDTO save(ProductionSeasonalityDTO ProductionSeasonality);

    /**
     * Updates an existing ProductionSeasonality entry.
     *
     * @param ProductionSeasonality The ProductionSeasonality information to be updated
     * @return The updated ProductionSeasonality data
     * @since 1.0.0
     */
    ProductionSeasonalityDTO update(ProductionSeasonalityDTO ProductionSeasonality);

    /**
     * Retrieves a paginated list of ProductionSeasonalitys.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of ProductionSeasonalitys
     * @since 1.0.0
     */
    PageDTO<ProductionSeasonalityDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a ProductionSeasonality by their ID with a specified reason.
     *
     * @param id     The ID of the ProductionSeasonality to delete
     * @param reason The reason for deletion
     * @return ProductionSeasonalityDTO
     */
    ProductionSeasonalityDTO deleteById(Long id, String reason);

    /**
     * Retrieves a ProductionSeasonality by their ID.
     *
     * @param id The ID of the ProductionSeasonality to retrieve
     * @return The ProductionSeasonality with the specified ID
     * @since 1.0.0
     */
    ProductionSeasonalityDTO getById(Long id);




}
