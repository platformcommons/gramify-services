package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HouseholdHumanResourceProfile-related operations in the system.
 * This interface provides methods for CRUD operations on HouseholdHumanResourceProfile data,
 * including creation, retrieval, update, and deletion of HouseholdHumanResourceProfile records.
 * It serves as the primary entry point for HouseholdHumanResourceProfile management functionality.
 */
public interface HouseholdHumanResourceProfileFacade {

    /**
     * Retrieves all HouseholdHumanResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdHumanResourceProfileDTO
     */
    Set<HouseholdHumanResourceProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HouseholdHumanResourceProfile record in the system.
     *
     * @param HouseholdHumanResourceProfileDTO The HouseholdHumanResourceProfile data transfer object containing the information to be saved
     * @return The saved HouseholdHumanResourceProfile data with generated identifiers and any system-modified fields
     */
    HouseholdHumanResourceProfileDTO save(HouseholdHumanResourceProfileDTO HouseholdHumanResourceProfileDTO);

    /**
     * Updates an existing HouseholdHumanResourceProfile record in the system.
     *
     * @param HouseholdHumanResourceProfileDTO The HouseholdHumanResourceProfile data transfer object containing the updated information
     * @return The updated HouseholdHumanResourceProfile data as confirmed by the system
     */
    HouseholdHumanResourceProfileDTO update(HouseholdHumanResourceProfileDTO HouseholdHumanResourceProfileDTO);

    /**
     * Retrieves a paginated list of all HouseholdHumanResourceProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HouseholdHumanResourceProfile records
     */
    PageDTO<HouseholdHumanResourceProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HouseholdHumanResourceProfile record from the system.
     *
     * @param id     The unique identifier of the HouseholdHumanResourceProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HouseholdHumanResourceProfile by their unique identifier.
     *
     * @param id The unique identifier of the HouseholdHumanResourceProfile to retrieve
     * @return The HouseholdHumanResourceProfile data transfer object containing the requested information
     */
    HouseholdHumanResourceProfileDTO getById(Long id);


    /**
     * Adds a list of hHEmploymentTypeList to a HouseholdHumanResourceProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdHumanResourceProfile to add hobbies to
     * @param hHEmploymentTypeList  to be added
     * @since 1.0.0
     */
    void addHHEmploymentTypeToHouseholdHumanResourceProfile(Long id, List<HHEmploymentTypeDTO> hHEmploymentTypeList);

    /**
     * Adds a list of hHEnterpriseTypeList to a HouseholdHumanResourceProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdHumanResourceProfile to add hobbies to
     * @param hHEnterpriseTypeList  to be added
     * @since 1.0.0
     */
    void addHHEnterpriseTypeToHouseholdHumanResourceProfile(Long id, List<HHEnterpriseTypeDTO> hHEnterpriseTypeList);

    /**
     * Adds a list of hHArtisanTypeList to a HouseholdHumanResourceProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdHumanResourceProfile to add hobbies to
     * @param hHArtisanTypeList  to be added
     * @since 1.0.0
     */
    void addHHArtisanTypeToHouseholdHumanResourceProfile(Long id, List<HHArtisanTypeDTO> hHArtisanTypeList);


}
