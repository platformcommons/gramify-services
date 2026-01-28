package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HouseholdDemographics-related operations in the system.
 * This interface provides methods for CRUD operations on HouseholdDemographics data,
 * including creation, retrieval, update, and deletion of HouseholdDemographics records.
 * It serves as the primary entry point for HouseholdDemographics management functionality.
 */
public interface HouseholdDemographicsFacade {

    /**
     * Retrieves all HouseholdDemographicss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdDemographicsDTO
     */
    Set<HouseholdDemographicsDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HouseholdDemographics record in the system.
     *
     * @param HouseholdDemographicsDTO The HouseholdDemographics data transfer object containing the information to be saved
     * @return The saved HouseholdDemographics data with generated identifiers and any system-modified fields
     */
    HouseholdDemographicsDTO save(HouseholdDemographicsDTO HouseholdDemographicsDTO);

    /**
     * Updates an existing HouseholdDemographics record in the system.
     *
     * @param HouseholdDemographicsDTO The HouseholdDemographics data transfer object containing the updated information
     * @return The updated HouseholdDemographics data as confirmed by the system
     */
    HouseholdDemographicsDTO update(HouseholdDemographicsDTO HouseholdDemographicsDTO);

    /**
     * Retrieves a paginated list of all HouseholdDemographicss in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HouseholdDemographics records
     */
    PageDTO<HouseholdDemographicsDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HouseholdDemographics record from the system.
     *
     * @param id     The unique identifier of the HouseholdDemographics to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HouseholdDemographics by their unique identifier.
     *
     * @param id The unique identifier of the HouseholdDemographics to retrieve
     * @return The HouseholdDemographics data transfer object containing the requested information
     */
    HouseholdDemographicsDTO getById(Long id);



}
