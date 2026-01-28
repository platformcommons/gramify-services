package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageIrrigationSystemType-related operations.
 * Provides CRUD operations and pagination support for VillageIrrigationSystemType entities.
 *
 * @since 1.0.0
 */

public interface VillageIrrigationSystemTypeService {

    /**
     * Retrieves all VillageIrrigationSystemTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageIrrigationSystemTypeDTO in the same order as retrieved
     */
    Set<VillageIrrigationSystemTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageIrrigationSystemType entry in the system.
     *
     * @param VillageIrrigationSystemType The VillageIrrigationSystemType information to be saved
     * @return The saved VillageIrrigationSystemType data
     * @since 1.0.0
     */
    VillageIrrigationSystemTypeDTO save(VillageIrrigationSystemTypeDTO VillageIrrigationSystemType);

    /**
     * Updates an existing VillageIrrigationSystemType entry.
     *
     * @param VillageIrrigationSystemType The VillageIrrigationSystemType information to be updated
     * @return The updated VillageIrrigationSystemType data
     * @since 1.0.0
     */
    VillageIrrigationSystemTypeDTO update(VillageIrrigationSystemTypeDTO VillageIrrigationSystemType);

    /**
     * Retrieves a paginated list of VillageIrrigationSystemTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageIrrigationSystemTypes
     * @since 1.0.0
     */
    PageDTO<VillageIrrigationSystemTypeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageIrrigationSystemType by their ID with a specified reason.
     *
     * @param id     The ID of the VillageIrrigationSystemType to delete
     * @param reason The reason for deletion
     * @return VillageIrrigationSystemTypeDTO
     */
    VillageIrrigationSystemTypeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageIrrigationSystemType by their ID.
     *
     * @param id The ID of the VillageIrrigationSystemType to retrieve
     * @return The VillageIrrigationSystemType with the specified ID
     * @since 1.0.0
     */
    VillageIrrigationSystemTypeDTO getById(Long id);




}
