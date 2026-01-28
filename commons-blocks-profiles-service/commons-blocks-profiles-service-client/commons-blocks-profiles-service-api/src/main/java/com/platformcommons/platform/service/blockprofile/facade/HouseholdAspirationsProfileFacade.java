package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HouseholdAspirationsProfile-related operations in the system.
 * This interface provides methods for CRUD operations on HouseholdAspirationsProfile data,
 * including creation, retrieval, update, and deletion of HouseholdAspirationsProfile records.
 * It serves as the primary entry point for HouseholdAspirationsProfile management functionality.
 */
public interface HouseholdAspirationsProfileFacade {

    /**
     * Retrieves all HouseholdAspirationsProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdAspirationsProfileDTO
     */
    Set<HouseholdAspirationsProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HouseholdAspirationsProfile record in the system.
     *
     * @param HouseholdAspirationsProfileDTO The HouseholdAspirationsProfile data transfer object containing the information to be saved
     * @return The saved HouseholdAspirationsProfile data with generated identifiers and any system-modified fields
     */
    HouseholdAspirationsProfileDTO save(HouseholdAspirationsProfileDTO HouseholdAspirationsProfileDTO);

    /**
     * Updates an existing HouseholdAspirationsProfile record in the system.
     *
     * @param HouseholdAspirationsProfileDTO The HouseholdAspirationsProfile data transfer object containing the updated information
     * @return The updated HouseholdAspirationsProfile data as confirmed by the system
     */
    HouseholdAspirationsProfileDTO update(HouseholdAspirationsProfileDTO HouseholdAspirationsProfileDTO);

    /**
     * Retrieves a paginated list of all HouseholdAspirationsProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HouseholdAspirationsProfile records
     */
    PageDTO<HouseholdAspirationsProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HouseholdAspirationsProfile record from the system.
     *
     * @param id     The unique identifier of the HouseholdAspirationsProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HouseholdAspirationsProfile by their unique identifier.
     *
     * @param id The unique identifier of the HouseholdAspirationsProfile to retrieve
     * @return The HouseholdAspirationsProfile data transfer object containing the requested information
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
