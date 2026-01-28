package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageServiceDemandProfile-related operations.
 * Provides CRUD operations and pagination support for VillageServiceDemandProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageServiceDemandProfileService {

    /**
     * Retrieves all VillageServiceDemandProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageServiceDemandProfileDTO in the same order as retrieved
     */
    Set<VillageServiceDemandProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageServiceDemandProfile entry in the system.
     *
     * @param VillageServiceDemandProfile The VillageServiceDemandProfile information to be saved
     * @return The saved VillageServiceDemandProfile data
     * @since 1.0.0
     */
    VillageServiceDemandProfileDTO save(VillageServiceDemandProfileDTO VillageServiceDemandProfile);

    /**
     * Updates an existing VillageServiceDemandProfile entry.
     *
     * @param VillageServiceDemandProfile The VillageServiceDemandProfile information to be updated
     * @return The updated VillageServiceDemandProfile data
     * @since 1.0.0
     */
    VillageServiceDemandProfileDTO update(VillageServiceDemandProfileDTO VillageServiceDemandProfile);

    /**
     * Retrieves a paginated list of VillageServiceDemandProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageServiceDemandProfiles
     * @since 1.0.0
     */
    PageDTO<VillageServiceDemandProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageServiceDemandProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageServiceDemandProfile to delete
     * @param reason The reason for deletion
     * @return VillageServiceDemandProfileDTO
     */
    VillageServiceDemandProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageServiceDemandProfile by their ID.
     *
     * @param id The ID of the VillageServiceDemandProfile to retrieve
     * @return The VillageServiceDemandProfile with the specified ID
     * @since 1.0.0
     */
    VillageServiceDemandProfileDTO getById(Long id);


    /**
     * Adds a list of wherePeopleGoForRepairsList to a VillageServiceDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageServiceDemandProfile to add hobbies to
     * @param wherePeopleGoForRepairsList  to be added
     * @since 1.0.0
     */
    void addWherePeopleGoForRepairsToVillageServiceDemandProfile(Long id, List<WherePeopleGoForRepairsDTO> wherePeopleGoForRepairsList);

    /**
     * Adds a list of wherePeopleGoForCommonIllnessList to a VillageServiceDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageServiceDemandProfile to add hobbies to
     * @param wherePeopleGoForCommonIllnessList  to be added
     * @since 1.0.0
     */
    void addWherePeopleGoForCommonIllnessToVillageServiceDemandProfile(Long id, List<WherePeopleGoForCommonIllnessDTO> wherePeopleGoForCommonIllnessList);

    /**
     * Adds a list of commonRepairNeedList to a VillageServiceDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageServiceDemandProfile to add hobbies to
     * @param commonRepairNeedList  to be added
     * @since 1.0.0
     */
    void addCommonRepairNeedToVillageServiceDemandProfile(Long id, List<CommonRepairNeedDTO> commonRepairNeedList);



}
