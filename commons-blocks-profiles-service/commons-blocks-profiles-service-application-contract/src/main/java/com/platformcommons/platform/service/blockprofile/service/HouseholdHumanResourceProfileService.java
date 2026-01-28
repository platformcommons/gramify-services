package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HouseholdHumanResourceProfile-related operations.
 * Provides CRUD operations and pagination support for HouseholdHumanResourceProfile entities.
 *
 * @since 1.0.0
 */

public interface HouseholdHumanResourceProfileService {

    /**
     * Retrieves all HouseholdHumanResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdHumanResourceProfileDTO in the same order as retrieved
     */
    Set<HouseholdHumanResourceProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HouseholdHumanResourceProfile entry in the system.
     *
     * @param HouseholdHumanResourceProfile The HouseholdHumanResourceProfile information to be saved
     * @return The saved HouseholdHumanResourceProfile data
     * @since 1.0.0
     */
    HouseholdHumanResourceProfileDTO save(HouseholdHumanResourceProfileDTO HouseholdHumanResourceProfile);

    /**
     * Updates an existing HouseholdHumanResourceProfile entry.
     *
     * @param HouseholdHumanResourceProfile The HouseholdHumanResourceProfile information to be updated
     * @return The updated HouseholdHumanResourceProfile data
     * @since 1.0.0
     */
    HouseholdHumanResourceProfileDTO update(HouseholdHumanResourceProfileDTO HouseholdHumanResourceProfile);

    /**
     * Retrieves a paginated list of HouseholdHumanResourceProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HouseholdHumanResourceProfiles
     * @since 1.0.0
     */
    PageDTO<HouseholdHumanResourceProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HouseholdHumanResourceProfile by their ID with a specified reason.
     *
     * @param id     The ID of the HouseholdHumanResourceProfile to delete
     * @param reason The reason for deletion
     * @return HouseholdHumanResourceProfileDTO
     */
    HouseholdHumanResourceProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HouseholdHumanResourceProfile by their ID.
     *
     * @param id The ID of the HouseholdHumanResourceProfile to retrieve
     * @return The HouseholdHumanResourceProfile with the specified ID
     * @since 1.0.0
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
