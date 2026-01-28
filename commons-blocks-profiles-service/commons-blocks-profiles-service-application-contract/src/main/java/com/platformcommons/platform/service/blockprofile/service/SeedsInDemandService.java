package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing SeedsInDemand-related operations.
 * Provides CRUD operations and pagination support for SeedsInDemand entities.
 *
 * @since 1.0.0
 */

public interface SeedsInDemandService {

    /**
     * Retrieves all SeedsInDemands by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SeedsInDemandDTO in the same order as retrieved
     */
    Set<SeedsInDemandDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new SeedsInDemand entry in the system.
     *
     * @param SeedsInDemand The SeedsInDemand information to be saved
     * @return The saved SeedsInDemand data
     * @since 1.0.0
     */
    SeedsInDemandDTO save(SeedsInDemandDTO SeedsInDemand);

    /**
     * Updates an existing SeedsInDemand entry.
     *
     * @param SeedsInDemand The SeedsInDemand information to be updated
     * @return The updated SeedsInDemand data
     * @since 1.0.0
     */
    SeedsInDemandDTO update(SeedsInDemandDTO SeedsInDemand);

    /**
     * Retrieves a paginated list of SeedsInDemands.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of SeedsInDemands
     * @since 1.0.0
     */
    PageDTO<SeedsInDemandDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a SeedsInDemand by their ID with a specified reason.
     *
     * @param id     The ID of the SeedsInDemand to delete
     * @param reason The reason for deletion
     * @return SeedsInDemandDTO
     */
    SeedsInDemandDTO deleteById(Long id, String reason);

    /**
     * Retrieves a SeedsInDemand by their ID.
     *
     * @param id The ID of the SeedsInDemand to retrieve
     * @return The SeedsInDemand with the specified ID
     * @since 1.0.0
     */
    SeedsInDemandDTO getById(Long id);




}
