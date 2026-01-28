package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageHumanCapitalProfile-related operations.
 * Provides CRUD operations and pagination support for VillageHumanCapitalProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageHumanCapitalProfileService {

    /**
     * Retrieves all VillageHumanCapitalProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageHumanCapitalProfileDTO in the same order as retrieved
     */
    Set<VillageHumanCapitalProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageHumanCapitalProfile entry in the system.
     *
     * @param VillageHumanCapitalProfile The VillageHumanCapitalProfile information to be saved
     * @return The saved VillageHumanCapitalProfile data
     * @since 1.0.0
     */
    VillageHumanCapitalProfileDTO save(VillageHumanCapitalProfileDTO VillageHumanCapitalProfile);

    /**
     * Updates an existing VillageHumanCapitalProfile entry.
     *
     * @param VillageHumanCapitalProfile The VillageHumanCapitalProfile information to be updated
     * @return The updated VillageHumanCapitalProfile data
     * @since 1.0.0
     */
    VillageHumanCapitalProfileDTO update(VillageHumanCapitalProfileDTO VillageHumanCapitalProfile);

    /**
     * Retrieves a paginated list of VillageHumanCapitalProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageHumanCapitalProfiles
     * @since 1.0.0
     */
    PageDTO<VillageHumanCapitalProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageHumanCapitalProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageHumanCapitalProfile to delete
     * @param reason The reason for deletion
     * @return VillageHumanCapitalProfileDTO
     */
    VillageHumanCapitalProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageHumanCapitalProfile by their ID.
     *
     * @param id The ID of the VillageHumanCapitalProfile to retrieve
     * @return The VillageHumanCapitalProfile with the specified ID
     * @since 1.0.0
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
