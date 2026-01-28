package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing PowerCutSeason-related operations.
 * Provides CRUD operations and pagination support for PowerCutSeason entities.
 *
 * @since 1.0.0
 */

public interface PowerCutSeasonService {

    /**
     * Retrieves all PowerCutSeasons by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of PowerCutSeasonDTO in the same order as retrieved
     */
    Set<PowerCutSeasonDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new PowerCutSeason entry in the system.
     *
     * @param PowerCutSeason The PowerCutSeason information to be saved
     * @return The saved PowerCutSeason data
     * @since 1.0.0
     */
    PowerCutSeasonDTO save(PowerCutSeasonDTO PowerCutSeason);

    /**
     * Updates an existing PowerCutSeason entry.
     *
     * @param PowerCutSeason The PowerCutSeason information to be updated
     * @return The updated PowerCutSeason data
     * @since 1.0.0
     */
    PowerCutSeasonDTO update(PowerCutSeasonDTO PowerCutSeason);

    /**
     * Retrieves a paginated list of PowerCutSeasons.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of PowerCutSeasons
     * @since 1.0.0
     */
    PageDTO<PowerCutSeasonDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a PowerCutSeason by their ID with a specified reason.
     *
     * @param id     The ID of the PowerCutSeason to delete
     * @param reason The reason for deletion
     * @return PowerCutSeasonDTO
     */
    PowerCutSeasonDTO deleteById(Long id, String reason);

    /**
     * Retrieves a PowerCutSeason by their ID.
     *
     * @param id The ID of the PowerCutSeason to retrieve
     * @return The PowerCutSeason with the specified ID
     * @since 1.0.0
     */
    PowerCutSeasonDTO getById(Long id);




}
