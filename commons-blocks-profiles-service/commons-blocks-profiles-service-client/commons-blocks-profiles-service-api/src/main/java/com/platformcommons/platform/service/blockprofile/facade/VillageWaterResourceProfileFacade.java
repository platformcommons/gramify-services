package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageWaterResourceProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageWaterResourceProfile data,
 * including creation, retrieval, update, and deletion of VillageWaterResourceProfile records.
 * It serves as the primary entry point for VillageWaterResourceProfile management functionality.
 */
public interface VillageWaterResourceProfileFacade {

    /**
     * Retrieves all VillageWaterResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageWaterResourceProfileDTO
     */
    Set<VillageWaterResourceProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageWaterResourceProfile record in the system.
     *
     * @param VillageWaterResourceProfileDTO The VillageWaterResourceProfile data transfer object containing the information to be saved
     * @return The saved VillageWaterResourceProfile data with generated identifiers and any system-modified fields
     */
    VillageWaterResourceProfileDTO save(VillageWaterResourceProfileDTO VillageWaterResourceProfileDTO);

    /**
     * Updates an existing VillageWaterResourceProfile record in the system.
     *
     * @param VillageWaterResourceProfileDTO The VillageWaterResourceProfile data transfer object containing the updated information
     * @return The updated VillageWaterResourceProfile data as confirmed by the system
     */
    VillageWaterResourceProfileDTO update(VillageWaterResourceProfileDTO VillageWaterResourceProfileDTO);

    /**
     * Retrieves a paginated list of all VillageWaterResourceProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageWaterResourceProfile records
     */
    PageDTO<VillageWaterResourceProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageWaterResourceProfile record from the system.
     *
     * @param id     The unique identifier of the VillageWaterResourceProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageWaterResourceProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageWaterResourceProfile to retrieve
     * @return The VillageWaterResourceProfile data transfer object containing the requested information
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
