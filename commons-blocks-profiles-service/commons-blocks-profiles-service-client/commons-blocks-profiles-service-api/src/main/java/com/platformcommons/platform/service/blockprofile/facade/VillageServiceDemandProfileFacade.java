package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageServiceDemandProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageServiceDemandProfile data,
 * including creation, retrieval, update, and deletion of VillageServiceDemandProfile records.
 * It serves as the primary entry point for VillageServiceDemandProfile management functionality.
 */
public interface VillageServiceDemandProfileFacade {

    /**
     * Retrieves all VillageServiceDemandProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageServiceDemandProfileDTO
     */
    Set<VillageServiceDemandProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageServiceDemandProfile record in the system.
     *
     * @param VillageServiceDemandProfileDTO The VillageServiceDemandProfile data transfer object containing the information to be saved
     * @return The saved VillageServiceDemandProfile data with generated identifiers and any system-modified fields
     */
    VillageServiceDemandProfileDTO save(VillageServiceDemandProfileDTO VillageServiceDemandProfileDTO);

    /**
     * Updates an existing VillageServiceDemandProfile record in the system.
     *
     * @param VillageServiceDemandProfileDTO The VillageServiceDemandProfile data transfer object containing the updated information
     * @return The updated VillageServiceDemandProfile data as confirmed by the system
     */
    VillageServiceDemandProfileDTO update(VillageServiceDemandProfileDTO VillageServiceDemandProfileDTO);

    /**
     * Retrieves a paginated list of all VillageServiceDemandProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageServiceDemandProfile records
     */
    PageDTO<VillageServiceDemandProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageServiceDemandProfile record from the system.
     *
     * @param id     The unique identifier of the VillageServiceDemandProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageServiceDemandProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageServiceDemandProfile to retrieve
     * @return The VillageServiceDemandProfile data transfer object containing the requested information
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
