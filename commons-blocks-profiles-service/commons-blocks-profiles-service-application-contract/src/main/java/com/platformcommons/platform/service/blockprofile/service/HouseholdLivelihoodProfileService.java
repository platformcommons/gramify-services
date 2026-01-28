package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HouseholdLivelihoodProfile-related operations.
 * Provides CRUD operations and pagination support for HouseholdLivelihoodProfile entities.
 *
 * @since 1.0.0
 */

public interface HouseholdLivelihoodProfileService {

    /**
     * Retrieves all HouseholdLivelihoodProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdLivelihoodProfileDTO in the same order as retrieved
     */
    Set<HouseholdLivelihoodProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HouseholdLivelihoodProfile entry in the system.
     *
     * @param HouseholdLivelihoodProfile The HouseholdLivelihoodProfile information to be saved
     * @return The saved HouseholdLivelihoodProfile data
     * @since 1.0.0
     */
    HouseholdLivelihoodProfileDTO save(HouseholdLivelihoodProfileDTO HouseholdLivelihoodProfile);

    /**
     * Updates an existing HouseholdLivelihoodProfile entry.
     *
     * @param HouseholdLivelihoodProfile The HouseholdLivelihoodProfile information to be updated
     * @return The updated HouseholdLivelihoodProfile data
     * @since 1.0.0
     */
    HouseholdLivelihoodProfileDTO update(HouseholdLivelihoodProfileDTO HouseholdLivelihoodProfile);

    /**
     * Retrieves a paginated list of HouseholdLivelihoodProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HouseholdLivelihoodProfiles
     * @since 1.0.0
     */
    PageDTO<HouseholdLivelihoodProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HouseholdLivelihoodProfile by their ID with a specified reason.
     *
     * @param id     The ID of the HouseholdLivelihoodProfile to delete
     * @param reason The reason for deletion
     * @return HouseholdLivelihoodProfileDTO
     */
    HouseholdLivelihoodProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HouseholdLivelihoodProfile by their ID.
     *
     * @param id The ID of the HouseholdLivelihoodProfile to retrieve
     * @return The HouseholdLivelihoodProfile with the specified ID
     * @since 1.0.0
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
