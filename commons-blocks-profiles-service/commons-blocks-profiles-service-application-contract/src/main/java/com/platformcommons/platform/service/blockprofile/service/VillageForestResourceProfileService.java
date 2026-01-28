package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageForestResourceProfile-related operations.
 * Provides CRUD operations and pagination support for VillageForestResourceProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageForestResourceProfileService {

    /**
     * Retrieves all VillageForestResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageForestResourceProfileDTO in the same order as retrieved
     */
    Set<VillageForestResourceProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageForestResourceProfile entry in the system.
     *
     * @param VillageForestResourceProfile The VillageForestResourceProfile information to be saved
     * @return The saved VillageForestResourceProfile data
     * @since 1.0.0
     */
    VillageForestResourceProfileDTO save(VillageForestResourceProfileDTO VillageForestResourceProfile);

    /**
     * Updates an existing VillageForestResourceProfile entry.
     *
     * @param VillageForestResourceProfile The VillageForestResourceProfile information to be updated
     * @return The updated VillageForestResourceProfile data
     * @since 1.0.0
     */
    VillageForestResourceProfileDTO update(VillageForestResourceProfileDTO VillageForestResourceProfile);

    /**
     * Retrieves a paginated list of VillageForestResourceProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageForestResourceProfiles
     * @since 1.0.0
     */
    PageDTO<VillageForestResourceProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageForestResourceProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageForestResourceProfile to delete
     * @param reason The reason for deletion
     * @return VillageForestResourceProfileDTO
     */
    VillageForestResourceProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageForestResourceProfile by their ID.
     *
     * @param id The ID of the VillageForestResourceProfile to retrieve
     * @return The VillageForestResourceProfile with the specified ID
     * @since 1.0.0
     */
    VillageForestResourceProfileDTO getById(Long id);


    /**
     * Adds a list of villageForestProduceTypeList to a VillageForestResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageForestResourceProfile to add hobbies to
     * @param villageForestProduceTypeList  to be added
     * @since 1.0.0
     */
    void addVillageForestProduceTypeToVillageForestResourceProfile(Long id, List<VillageForestProduceTypeDTO> villageForestProduceTypeList);

    /**
     * Adds a list of villageCommonWildlifeList to a VillageForestResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageForestResourceProfile to add hobbies to
     * @param villageCommonWildlifeList  to be added
     * @since 1.0.0
     */
    void addVillageCommonWildlifeToVillageForestResourceProfile(Long id, List<VillageCommonWildlifeDTO> villageCommonWildlifeList);



}
