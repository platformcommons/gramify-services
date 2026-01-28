package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HouseholdAspirationsProfile-related operations.
 * Provides CRUD operations and pagination support for HouseholdAspirationsProfile entities.
 *
 * @since 1.0.0
 */

public interface HouseholdAspirationsProfileService {

    /**
     * Retrieves all HouseholdAspirationsProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdAspirationsProfileDTO in the same order as retrieved
     */
    Set<HouseholdAspirationsProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HouseholdAspirationsProfile entry in the system.
     *
     * @param HouseholdAspirationsProfile The HouseholdAspirationsProfile information to be saved
     * @return The saved HouseholdAspirationsProfile data
     * @since 1.0.0
     */
    HouseholdAspirationsProfileDTO save(HouseholdAspirationsProfileDTO HouseholdAspirationsProfile);

    /**
     * Updates an existing HouseholdAspirationsProfile entry.
     *
     * @param HouseholdAspirationsProfile The HouseholdAspirationsProfile information to be updated
     * @return The updated HouseholdAspirationsProfile data
     * @since 1.0.0
     */
    HouseholdAspirationsProfileDTO update(HouseholdAspirationsProfileDTO HouseholdAspirationsProfile);

    /**
     * Retrieves a paginated list of HouseholdAspirationsProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HouseholdAspirationsProfiles
     * @since 1.0.0
     */
    PageDTO<HouseholdAspirationsProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HouseholdAspirationsProfile by their ID with a specified reason.
     *
     * @param id     The ID of the HouseholdAspirationsProfile to delete
     * @param reason The reason for deletion
     * @return HouseholdAspirationsProfileDTO
     */
    HouseholdAspirationsProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HouseholdAspirationsProfile by their ID.
     *
     * @param id The ID of the HouseholdAspirationsProfile to retrieve
     * @return The HouseholdAspirationsProfile with the specified ID
     * @since 1.0.0
     */
    HouseholdAspirationsProfileDTO getById(Long id);


    /**
     * Adds a list of hHSocialAspirationList to a HouseholdAspirationsProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdAspirationsProfile to add hobbies to
     * @param hHSocialAspirationList  to be added
     * @since 1.0.0
     */
    void addHHSocialAspirationToHouseholdAspirationsProfile(Long id, List<HHSocialAspirationDTO> hHSocialAspirationList);

    /**
     * Adds a list of hHEconomicAspirationList to a HouseholdAspirationsProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdAspirationsProfile to add hobbies to
     * @param hHEconomicAspirationList  to be added
     * @since 1.0.0
     */
    void addHHEconomicAspirationToHouseholdAspirationsProfile(Long id, List<HHEconomicAspirationDTO> hHEconomicAspirationList);

    /**
     * Adds a list of hHGovernanceAspirationList to a HouseholdAspirationsProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdAspirationsProfile to add hobbies to
     * @param hHGovernanceAspirationList  to be added
     * @since 1.0.0
     */
    void addHHGovernanceAspirationToHouseholdAspirationsProfile(Long id, List<HHGovernanceAspirationDTO> hHGovernanceAspirationList);



}
