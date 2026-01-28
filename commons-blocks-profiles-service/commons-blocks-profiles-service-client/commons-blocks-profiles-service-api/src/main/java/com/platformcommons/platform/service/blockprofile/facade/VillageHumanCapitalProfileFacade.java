package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageHumanCapitalProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageHumanCapitalProfile data,
 * including creation, retrieval, update, and deletion of VillageHumanCapitalProfile records.
 * It serves as the primary entry point for VillageHumanCapitalProfile management functionality.
 */
public interface VillageHumanCapitalProfileFacade {

    /**
     * Retrieves all VillageHumanCapitalProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageHumanCapitalProfileDTO
     */
    Set<VillageHumanCapitalProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageHumanCapitalProfile record in the system.
     *
     * @param VillageHumanCapitalProfileDTO The VillageHumanCapitalProfile data transfer object containing the information to be saved
     * @return The saved VillageHumanCapitalProfile data with generated identifiers and any system-modified fields
     */
    VillageHumanCapitalProfileDTO save(VillageHumanCapitalProfileDTO VillageHumanCapitalProfileDTO);

    /**
     * Updates an existing VillageHumanCapitalProfile record in the system.
     *
     * @param VillageHumanCapitalProfileDTO The VillageHumanCapitalProfile data transfer object containing the updated information
     * @return The updated VillageHumanCapitalProfile data as confirmed by the system
     */
    VillageHumanCapitalProfileDTO update(VillageHumanCapitalProfileDTO VillageHumanCapitalProfileDTO);

    /**
     * Retrieves a paginated list of all VillageHumanCapitalProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageHumanCapitalProfile records
     */
    PageDTO<VillageHumanCapitalProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageHumanCapitalProfile record from the system.
     *
     * @param id     The unique identifier of the VillageHumanCapitalProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageHumanCapitalProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageHumanCapitalProfile to retrieve
     * @return The VillageHumanCapitalProfile data transfer object containing the requested information
     */
    VillageHumanCapitalProfileDTO getById(Long id);


    /**
     * Adds a list of villageYouthAspirationsList to a VillageHumanCapitalProfile identified by their ID.
     *
     * @param id               The ID of the VillageHumanCapitalProfile to add hobbies to
     * @param villageYouthAspirationsList  to be added
     * @since 1.0.0
     */
    void addVillageYouthAspirationsToVillageHumanCapitalProfile(Long id, List<VillageYouthAspirationsDTO> villageYouthAspirationsList);

    /**
     * Adds a list of villageSkillShortageTypeList to a VillageHumanCapitalProfile identified by their ID.
     *
     * @param id               The ID of the VillageHumanCapitalProfile to add hobbies to
     * @param villageSkillShortageTypeList  to be added
     * @since 1.0.0
     */
    void addVillageSkillShortageTypeToVillageHumanCapitalProfile(Long id, List<VillageSkillShortageTypeDTO> villageSkillShortageTypeList);


}
