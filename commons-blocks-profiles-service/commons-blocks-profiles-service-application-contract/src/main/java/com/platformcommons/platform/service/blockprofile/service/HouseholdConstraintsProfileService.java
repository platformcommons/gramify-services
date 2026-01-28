package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HouseholdConstraintsProfile-related operations.
 * Provides CRUD operations and pagination support for HouseholdConstraintsProfile entities.
 *
 * @since 1.0.0
 */

public interface HouseholdConstraintsProfileService {

    /**
     * Retrieves all HouseholdConstraintsProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdConstraintsProfileDTO in the same order as retrieved
     */
    Set<HouseholdConstraintsProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HouseholdConstraintsProfile entry in the system.
     *
     * @param HouseholdConstraintsProfile The HouseholdConstraintsProfile information to be saved
     * @return The saved HouseholdConstraintsProfile data
     * @since 1.0.0
     */
    HouseholdConstraintsProfileDTO save(HouseholdConstraintsProfileDTO HouseholdConstraintsProfile);

    /**
     * Updates an existing HouseholdConstraintsProfile entry.
     *
     * @param HouseholdConstraintsProfile The HouseholdConstraintsProfile information to be updated
     * @return The updated HouseholdConstraintsProfile data
     * @since 1.0.0
     */
    HouseholdConstraintsProfileDTO update(HouseholdConstraintsProfileDTO HouseholdConstraintsProfile);

    /**
     * Retrieves a paginated list of HouseholdConstraintsProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HouseholdConstraintsProfiles
     * @since 1.0.0
     */
    PageDTO<HouseholdConstraintsProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HouseholdConstraintsProfile by their ID with a specified reason.
     *
     * @param id     The ID of the HouseholdConstraintsProfile to delete
     * @param reason The reason for deletion
     * @return HouseholdConstraintsProfileDTO
     */
    HouseholdConstraintsProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HouseholdConstraintsProfile by their ID.
     *
     * @param id The ID of the HouseholdConstraintsProfile to retrieve
     * @return The HouseholdConstraintsProfile with the specified ID
     * @since 1.0.0
     */
    HouseholdConstraintsProfileDTO getById(Long id);


    /**
     * Adds a list of hHSocialConstraintsList to a HouseholdConstraintsProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdConstraintsProfile to add hobbies to
     * @param hHSocialConstraintsList  to be added
     * @since 1.0.0
     */
    void addHHSocialConstraintsToHouseholdConstraintsProfile(Long id, List<HHSocialConstraintsDTO> hHSocialConstraintsList);

    /**
     * Adds a list of hHEconomicConstraintsList to a HouseholdConstraintsProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdConstraintsProfile to add hobbies to
     * @param hHEconomicConstraintsList  to be added
     * @since 1.0.0
     */
    void addHHEconomicConstraintsToHouseholdConstraintsProfile(Long id, List<HHEconomicConstraintsDTO> hHEconomicConstraintsList);



}
