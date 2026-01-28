package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing SurplusMovedThrough-related operations.
 * Provides CRUD operations and pagination support for SurplusMovedThrough entities.
 *
 * @since 1.0.0
 */

public interface SurplusMovedThroughService {

    /**
     * Retrieves all SurplusMovedThroughs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SurplusMovedThroughDTO in the same order as retrieved
     */
    Set<SurplusMovedThroughDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new SurplusMovedThrough entry in the system.
     *
     * @param SurplusMovedThrough The SurplusMovedThrough information to be saved
     * @return The saved SurplusMovedThrough data
     * @since 1.0.0
     */
    SurplusMovedThroughDTO save(SurplusMovedThroughDTO SurplusMovedThrough);

    /**
     * Updates an existing SurplusMovedThrough entry.
     *
     * @param SurplusMovedThrough The SurplusMovedThrough information to be updated
     * @return The updated SurplusMovedThrough data
     * @since 1.0.0
     */
    SurplusMovedThroughDTO update(SurplusMovedThroughDTO SurplusMovedThrough);

    /**
     * Retrieves a paginated list of SurplusMovedThroughs.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of SurplusMovedThroughs
     * @since 1.0.0
     */
    PageDTO<SurplusMovedThroughDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a SurplusMovedThrough by their ID with a specified reason.
     *
     * @param id     The ID of the SurplusMovedThrough to delete
     * @param reason The reason for deletion
     * @return SurplusMovedThroughDTO
     */
    SurplusMovedThroughDTO deleteById(Long id, String reason);

    /**
     * Retrieves a SurplusMovedThrough by their ID.
     *
     * @param id The ID of the SurplusMovedThrough to retrieve
     * @return The SurplusMovedThrough with the specified ID
     * @since 1.0.0
     */
    SurplusMovedThroughDTO getById(Long id);




}
