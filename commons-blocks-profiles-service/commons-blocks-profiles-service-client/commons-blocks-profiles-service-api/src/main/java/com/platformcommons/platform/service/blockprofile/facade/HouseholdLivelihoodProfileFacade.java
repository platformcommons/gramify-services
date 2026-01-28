package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HouseholdLivelihoodProfile-related operations in the system.
 * This interface provides methods for CRUD operations on HouseholdLivelihoodProfile data,
 * including creation, retrieval, update, and deletion of HouseholdLivelihoodProfile records.
 * It serves as the primary entry point for HouseholdLivelihoodProfile management functionality.
 */
public interface HouseholdLivelihoodProfileFacade {

    /**
     * Retrieves all HouseholdLivelihoodProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdLivelihoodProfileDTO
     */
    Set<HouseholdLivelihoodProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HouseholdLivelihoodProfile record in the system.
     *
     * @param HouseholdLivelihoodProfileDTO The HouseholdLivelihoodProfile data transfer object containing the information to be saved
     * @return The saved HouseholdLivelihoodProfile data with generated identifiers and any system-modified fields
     */
    HouseholdLivelihoodProfileDTO save(HouseholdLivelihoodProfileDTO HouseholdLivelihoodProfileDTO);

    /**
     * Updates an existing HouseholdLivelihoodProfile record in the system.
     *
     * @param HouseholdLivelihoodProfileDTO The HouseholdLivelihoodProfile data transfer object containing the updated information
     * @return The updated HouseholdLivelihoodProfile data as confirmed by the system
     */
    HouseholdLivelihoodProfileDTO update(HouseholdLivelihoodProfileDTO HouseholdLivelihoodProfileDTO);

    /**
     * Retrieves a paginated list of all HouseholdLivelihoodProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HouseholdLivelihoodProfile records
     */
    PageDTO<HouseholdLivelihoodProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HouseholdLivelihoodProfile record from the system.
     *
     * @param id     The unique identifier of the HouseholdLivelihoodProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HouseholdLivelihoodProfile by their unique identifier.
     *
     * @param id The unique identifier of the HouseholdLivelihoodProfile to retrieve
     * @return The HouseholdLivelihoodProfile data transfer object containing the requested information
     */
    HouseholdLivelihoodProfileDTO getById(Long id);


    /**
     * Adds a list of hHMigrationMonthList to a HouseholdLivelihoodProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdLivelihoodProfile to add hobbies to
     * @param hHMigrationMonthList  to be added
     * @since 1.0.0
     */
    void addHHMigrationMonthToHouseholdLivelihoodProfile(Long id, List<HHMigrationMonthDTO> hHMigrationMonthList);


}
