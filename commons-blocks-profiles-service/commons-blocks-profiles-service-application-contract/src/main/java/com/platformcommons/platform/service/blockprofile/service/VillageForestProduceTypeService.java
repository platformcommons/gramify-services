package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageForestProduceType-related operations.
 * Provides CRUD operations and pagination support for VillageForestProduceType entities.
 *
 * @since 1.0.0
 */

public interface VillageForestProduceTypeService {

    /**
     * Retrieves all VillageForestProduceTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageForestProduceTypeDTO in the same order as retrieved
     */
    Set<VillageForestProduceTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageForestProduceType entry in the system.
     *
     * @param VillageForestProduceType The VillageForestProduceType information to be saved
     * @return The saved VillageForestProduceType data
     * @since 1.0.0
     */
    VillageForestProduceTypeDTO save(VillageForestProduceTypeDTO VillageForestProduceType);

    /**
     * Updates an existing VillageForestProduceType entry.
     *
     * @param VillageForestProduceType The VillageForestProduceType information to be updated
     * @return The updated VillageForestProduceType data
     * @since 1.0.0
     */
    VillageForestProduceTypeDTO update(VillageForestProduceTypeDTO VillageForestProduceType);

    /**
     * Retrieves a paginated list of VillageForestProduceTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageForestProduceTypes
     * @since 1.0.0
     */
    PageDTO<VillageForestProduceTypeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageForestProduceType by their ID with a specified reason.
     *
     * @param id     The ID of the VillageForestProduceType to delete
     * @param reason The reason for deletion
     * @return VillageForestProduceTypeDTO
     */
    VillageForestProduceTypeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageForestProduceType by their ID.
     *
     * @param id The ID of the VillageForestProduceType to retrieve
     * @return The VillageForestProduceType with the specified ID
     * @since 1.0.0
     */
    VillageForestProduceTypeDTO getById(Long id);




}
