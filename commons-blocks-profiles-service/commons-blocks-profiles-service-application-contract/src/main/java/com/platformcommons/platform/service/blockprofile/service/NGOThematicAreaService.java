package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing NGOThematicArea-related operations.
 * Provides CRUD operations and pagination support for NGOThematicArea entities.
 *
 * @since 1.0.0
 */

public interface NGOThematicAreaService {

    /**
     * Retrieves all NGOThematicAreas by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of NGOThematicAreaDTO in the same order as retrieved
     */
    Set<NGOThematicAreaDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new NGOThematicArea entry in the system.
     *
     * @param NGOThematicArea The NGOThematicArea information to be saved
     * @return The saved NGOThematicArea data
     * @since 1.0.0
     */
    NGOThematicAreaDTO save(NGOThematicAreaDTO NGOThematicArea);

    /**
     * Updates an existing NGOThematicArea entry.
     *
     * @param NGOThematicArea The NGOThematicArea information to be updated
     * @return The updated NGOThematicArea data
     * @since 1.0.0
     */
    NGOThematicAreaDTO update(NGOThematicAreaDTO NGOThematicArea);

    /**
     * Retrieves a paginated list of NGOThematicAreas.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of NGOThematicAreas
     * @since 1.0.0
     */
    PageDTO<NGOThematicAreaDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a NGOThematicArea by their ID with a specified reason.
     *
     * @param id     The ID of the NGOThematicArea to delete
     * @param reason The reason for deletion
     * @return NGOThematicAreaDTO
     */
    NGOThematicAreaDTO deleteById(Long id, String reason);

    /**
     * Retrieves a NGOThematicArea by their ID.
     *
     * @param id The ID of the NGOThematicArea to retrieve
     * @return The NGOThematicArea with the specified ID
     * @since 1.0.0
     */
    NGOThematicAreaDTO getById(Long id);




}
