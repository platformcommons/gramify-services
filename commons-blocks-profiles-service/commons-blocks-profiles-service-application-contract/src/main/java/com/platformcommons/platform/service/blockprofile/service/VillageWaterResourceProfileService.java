package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageWaterResourceProfile-related operations.
 * Provides CRUD operations and pagination support for VillageWaterResourceProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageWaterResourceProfileService {

    /**
     * Retrieves all VillageWaterResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageWaterResourceProfileDTO in the same order as retrieved
     */
    Set<VillageWaterResourceProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageWaterResourceProfile entry in the system.
     *
     * @param VillageWaterResourceProfile The VillageWaterResourceProfile information to be saved
     * @return The saved VillageWaterResourceProfile data
     * @since 1.0.0
     */
    VillageWaterResourceProfileDTO save(VillageWaterResourceProfileDTO VillageWaterResourceProfile);

    /**
     * Updates an existing VillageWaterResourceProfile entry.
     *
     * @param VillageWaterResourceProfile The VillageWaterResourceProfile information to be updated
     * @return The updated VillageWaterResourceProfile data
     * @since 1.0.0
     */
    VillageWaterResourceProfileDTO update(VillageWaterResourceProfileDTO VillageWaterResourceProfile);

    /**
     * Retrieves a paginated list of VillageWaterResourceProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageWaterResourceProfiles
     * @since 1.0.0
     */
    PageDTO<VillageWaterResourceProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageWaterResourceProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageWaterResourceProfile to delete
     * @param reason The reason for deletion
     * @return VillageWaterResourceProfileDTO
     */
    VillageWaterResourceProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageWaterResourceProfile by their ID.
     *
     * @param id The ID of the VillageWaterResourceProfile to retrieve
     * @return The VillageWaterResourceProfile with the specified ID
     * @since 1.0.0
     */
    VillageWaterResourceProfileDTO getById(Long id);


    /**
     * Adds a list of villageIrrigationSystemTypeList to a VillageWaterResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageWaterResourceProfile to add hobbies to
     * @param villageIrrigationSystemTypeList  to be added
     * @since 1.0.0
     */
    void addVillageIrrigationSystemTypeToVillageWaterResourceProfile(Long id, List<VillageIrrigationSystemTypeDTO> villageIrrigationSystemTypeList);



}
