package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HouseholdDemographics-related operations.
 * Provides CRUD operations and pagination support for HouseholdDemographics entities.
 *
 * @since 1.0.0
 */

public interface HouseholdDemographicsService {

    /**
     * Retrieves all HouseholdDemographicss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdDemographicsDTO in the same order as retrieved
     */
    Set<HouseholdDemographicsDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HouseholdDemographics entry in the system.
     *
     * @param HouseholdDemographics The HouseholdDemographics information to be saved
     * @return The saved HouseholdDemographics data
     * @since 1.0.0
     */
    HouseholdDemographicsDTO save(HouseholdDemographicsDTO HouseholdDemographics);

    /**
     * Updates an existing HouseholdDemographics entry.
     *
     * @param HouseholdDemographics The HouseholdDemographics information to be updated
     * @return The updated HouseholdDemographics data
     * @since 1.0.0
     */
    HouseholdDemographicsDTO update(HouseholdDemographicsDTO HouseholdDemographics);

    /**
     * Retrieves a paginated list of HouseholdDemographicss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HouseholdDemographicss
     * @since 1.0.0
     */
    PageDTO<HouseholdDemographicsDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HouseholdDemographics by their ID with a specified reason.
     *
     * @param id     The ID of the HouseholdDemographics to delete
     * @param reason The reason for deletion
     * @return HouseholdDemographicsDTO
     */
    HouseholdDemographicsDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HouseholdDemographics by their ID.
     *
     * @param id The ID of the HouseholdDemographics to retrieve
     * @return The HouseholdDemographics with the specified ID
     * @since 1.0.0
     */
    HouseholdDemographicsDTO getById(Long id);




}
