package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HouseholdConstraintsProfile-related operations in the system.
 * This interface provides methods for CRUD operations on HouseholdConstraintsProfile data,
 * including creation, retrieval, update, and deletion of HouseholdConstraintsProfile records.
 * It serves as the primary entry point for HouseholdConstraintsProfile management functionality.
 */
public interface HouseholdConstraintsProfileFacade {

    /**
     * Retrieves all HouseholdConstraintsProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdConstraintsProfileDTO
     */
    Set<HouseholdConstraintsProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HouseholdConstraintsProfile record in the system.
     *
     * @param HouseholdConstraintsProfileDTO The HouseholdConstraintsProfile data transfer object containing the information to be saved
     * @return The saved HouseholdConstraintsProfile data with generated identifiers and any system-modified fields
     */
    HouseholdConstraintsProfileDTO save(HouseholdConstraintsProfileDTO HouseholdConstraintsProfileDTO);

    /**
     * Updates an existing HouseholdConstraintsProfile record in the system.
     *
     * @param HouseholdConstraintsProfileDTO The HouseholdConstraintsProfile data transfer object containing the updated information
     * @return The updated HouseholdConstraintsProfile data as confirmed by the system
     */
    HouseholdConstraintsProfileDTO update(HouseholdConstraintsProfileDTO HouseholdConstraintsProfileDTO);

    /**
     * Retrieves a paginated list of all HouseholdConstraintsProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HouseholdConstraintsProfile records
     */
    PageDTO<HouseholdConstraintsProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HouseholdConstraintsProfile record from the system.
     *
     * @param id     The unique identifier of the HouseholdConstraintsProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HouseholdConstraintsProfile by their unique identifier.
     *
     * @param id The unique identifier of the HouseholdConstraintsProfile to retrieve
     * @return The HouseholdConstraintsProfile data transfer object containing the requested information
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
