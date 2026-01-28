package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HHInfrastructureAspiration-related operations.
 * Provides CRUD operations and pagination support for HHInfrastructureAspiration entities.
 *
 * @since 1.0.0
 */

public interface HHInfrastructureAspirationService {

    /**
     * Retrieves all HHInfrastructureAspirations by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHInfrastructureAspirationDTO in the same order as retrieved
     */
    Set<HHInfrastructureAspirationDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHInfrastructureAspiration entry in the system.
     *
     * @param HHInfrastructureAspiration The HHInfrastructureAspiration information to be saved
     * @return The saved HHInfrastructureAspiration data
     * @since 1.0.0
     */
    HHInfrastructureAspirationDTO save(HHInfrastructureAspirationDTO HHInfrastructureAspiration);

    /**
     * Updates an existing HHInfrastructureAspiration entry.
     *
     * @param HHInfrastructureAspiration The HHInfrastructureAspiration information to be updated
     * @return The updated HHInfrastructureAspiration data
     * @since 1.0.0
     */
    HHInfrastructureAspirationDTO update(HHInfrastructureAspirationDTO HHInfrastructureAspiration);

    /**
     * Retrieves a paginated list of HHInfrastructureAspirations.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHInfrastructureAspirations
     * @since 1.0.0
     */
    PageDTO<HHInfrastructureAspirationDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HHInfrastructureAspiration by their ID with a specified reason.
     *
     * @param id     The ID of the HHInfrastructureAspiration to delete
     * @param reason The reason for deletion
     * @return HHInfrastructureAspirationDTO
     */
    HHInfrastructureAspirationDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HHInfrastructureAspiration by their ID.
     *
     * @param id The ID of the HHInfrastructureAspiration to retrieve
     * @return The HHInfrastructureAspiration with the specified ID
     * @since 1.0.0
     */
    HHInfrastructureAspirationDTO getById(Long id);


    /**
     * Adds a list of hHInfrastructureAspirationList to a HHInfrastructureAspiration identified by their ID.
     *
     * @param id               The ID of the HHInfrastructureAspiration to add hobbies to
     * @param hHInfrastructureAspirationList  to be added
     * @since 1.0.0
     */
    void addHHInfrastructureAspirationToHHInfrastructureAspiration(Long id, List<HHInfrastructureAspirationDTO> hHInfrastructureAspirationList);



}
