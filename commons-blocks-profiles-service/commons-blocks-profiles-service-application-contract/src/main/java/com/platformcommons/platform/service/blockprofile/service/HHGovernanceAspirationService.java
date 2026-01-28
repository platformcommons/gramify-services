package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HHGovernanceAspiration-related operations.
 * Provides CRUD operations and pagination support for HHGovernanceAspiration entities.
 *
 * @since 1.0.0
 */

public interface HHGovernanceAspirationService {

    /**
     * Retrieves all HHGovernanceAspirations by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHGovernanceAspirationDTO in the same order as retrieved
     */
    Set<HHGovernanceAspirationDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHGovernanceAspiration entry in the system.
     *
     * @param HHGovernanceAspiration The HHGovernanceAspiration information to be saved
     * @return The saved HHGovernanceAspiration data
     * @since 1.0.0
     */
    HHGovernanceAspirationDTO save(HHGovernanceAspirationDTO HHGovernanceAspiration);

    /**
     * Updates an existing HHGovernanceAspiration entry.
     *
     * @param HHGovernanceAspiration The HHGovernanceAspiration information to be updated
     * @return The updated HHGovernanceAspiration data
     * @since 1.0.0
     */
    HHGovernanceAspirationDTO update(HHGovernanceAspirationDTO HHGovernanceAspiration);

    /**
     * Retrieves a paginated list of HHGovernanceAspirations.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHGovernanceAspirations
     * @since 1.0.0
     */
    PageDTO<HHGovernanceAspirationDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HHGovernanceAspiration by their ID with a specified reason.
     *
     * @param id     The ID of the HHGovernanceAspiration to delete
     * @param reason The reason for deletion
     * @return HHGovernanceAspirationDTO
     */
    HHGovernanceAspirationDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HHGovernanceAspiration by their ID.
     *
     * @param id The ID of the HHGovernanceAspiration to retrieve
     * @return The HHGovernanceAspiration with the specified ID
     * @since 1.0.0
     */
    HHGovernanceAspirationDTO getById(Long id);




}
