package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HHEconomicAspiration-related operations.
 * Provides CRUD operations and pagination support for HHEconomicAspiration entities.
 *
 * @since 1.0.0
 */

public interface HHEconomicAspirationService {

    /**
     * Retrieves all HHEconomicAspirations by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHEconomicAspirationDTO in the same order as retrieved
     */
    Set<HHEconomicAspirationDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHEconomicAspiration entry in the system.
     *
     * @param HHEconomicAspiration The HHEconomicAspiration information to be saved
     * @return The saved HHEconomicAspiration data
     * @since 1.0.0
     */
    HHEconomicAspirationDTO save(HHEconomicAspirationDTO HHEconomicAspiration);

    /**
     * Updates an existing HHEconomicAspiration entry.
     *
     * @param HHEconomicAspiration The HHEconomicAspiration information to be updated
     * @return The updated HHEconomicAspiration data
     * @since 1.0.0
     */
    HHEconomicAspirationDTO update(HHEconomicAspirationDTO HHEconomicAspiration);

    /**
     * Retrieves a paginated list of HHEconomicAspirations.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHEconomicAspirations
     * @since 1.0.0
     */
    PageDTO<HHEconomicAspirationDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HHEconomicAspiration by their ID with a specified reason.
     *
     * @param id     The ID of the HHEconomicAspiration to delete
     * @param reason The reason for deletion
     * @return HHEconomicAspirationDTO
     */
    HHEconomicAspirationDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HHEconomicAspiration by their ID.
     *
     * @param id The ID of the HHEconomicAspiration to retrieve
     * @return The HHEconomicAspiration with the specified ID
     * @since 1.0.0
     */
    HHEconomicAspirationDTO getById(Long id);




}
