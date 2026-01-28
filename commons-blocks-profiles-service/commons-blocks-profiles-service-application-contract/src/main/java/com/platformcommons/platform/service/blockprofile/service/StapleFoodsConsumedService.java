package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing StapleFoodsConsumed-related operations.
 * Provides CRUD operations and pagination support for StapleFoodsConsumed entities.
 *
 * @since 1.0.0
 */

public interface StapleFoodsConsumedService {

    /**
     * Retrieves all StapleFoodsConsumeds by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of StapleFoodsConsumedDTO in the same order as retrieved
     */
    Set<StapleFoodsConsumedDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new StapleFoodsConsumed entry in the system.
     *
     * @param StapleFoodsConsumed The StapleFoodsConsumed information to be saved
     * @return The saved StapleFoodsConsumed data
     * @since 1.0.0
     */
    StapleFoodsConsumedDTO save(StapleFoodsConsumedDTO StapleFoodsConsumed);

    /**
     * Updates an existing StapleFoodsConsumed entry.
     *
     * @param StapleFoodsConsumed The StapleFoodsConsumed information to be updated
     * @return The updated StapleFoodsConsumed data
     * @since 1.0.0
     */
    StapleFoodsConsumedDTO update(StapleFoodsConsumedDTO StapleFoodsConsumed);

    /**
     * Retrieves a paginated list of StapleFoodsConsumeds.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of StapleFoodsConsumeds
     * @since 1.0.0
     */
    PageDTO<StapleFoodsConsumedDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a StapleFoodsConsumed by their ID with a specified reason.
     *
     * @param id     The ID of the StapleFoodsConsumed to delete
     * @param reason The reason for deletion
     * @return StapleFoodsConsumedDTO
     */
    StapleFoodsConsumedDTO deleteById(Long id, String reason);

    /**
     * Retrieves a StapleFoodsConsumed by their ID.
     *
     * @param id The ID of the StapleFoodsConsumed to retrieve
     * @return The StapleFoodsConsumed with the specified ID
     * @since 1.0.0
     */
    StapleFoodsConsumedDTO getById(Long id);




}
