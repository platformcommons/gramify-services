package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing Village-related operations.
 * Provides CRUD operations and pagination support for Village entities.
 *
 * @since 1.0.0
 */

public interface VillageService {

    /**
     * Retrieves all Villages by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageDTO in the same order as retrieved
     */
    Set<VillageDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new Village entry in the system.
     *
     * @param Village The Village information to be saved
     * @return The saved Village data
     * @since 1.0.0
     */
    VillageDTO save(VillageDTO Village);

    /**
     * Updates an existing Village entry.
     *
     * @param Village The Village information to be updated
     * @return The updated Village data
     * @since 1.0.0
     */
    VillageDTO update(VillageDTO Village);

    /**
     * Retrieves a paginated list of Villages.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of Villages
     * @since 1.0.0
     */
    PageDTO<VillageDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a Village by their ID with a specified reason.
     *
     * @param id     The ID of the Village to delete
     * @param reason The reason for deletion
     * @return VillageDTO
     */
    VillageDTO deleteById(Long id, String reason);

    /**
     * Retrieves a Village by their ID.
     *
     * @param id The ID of the Village to retrieve
     * @return The Village with the specified ID
     * @since 1.0.0
     */
    VillageDTO getById(Long id);




}
