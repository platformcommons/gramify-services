package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing FertilizersInDemand-related operations.
 * Provides CRUD operations and pagination support for FertilizersInDemand entities.
 *
 * @since 1.0.0
 */

public interface FertilizersInDemandService {

    /**
     * Retrieves all FertilizersInDemands by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of FertilizersInDemandDTO in the same order as retrieved
     */
    Set<FertilizersInDemandDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new FertilizersInDemand entry in the system.
     *
     * @param FertilizersInDemand The FertilizersInDemand information to be saved
     * @return The saved FertilizersInDemand data
     * @since 1.0.0
     */
    FertilizersInDemandDTO save(FertilizersInDemandDTO FertilizersInDemand);

    /**
     * Updates an existing FertilizersInDemand entry.
     *
     * @param FertilizersInDemand The FertilizersInDemand information to be updated
     * @return The updated FertilizersInDemand data
     * @since 1.0.0
     */
    FertilizersInDemandDTO update(FertilizersInDemandDTO FertilizersInDemand);

    /**
     * Retrieves a paginated list of FertilizersInDemands.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of FertilizersInDemands
     * @since 1.0.0
     */
    PageDTO<FertilizersInDemandDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a FertilizersInDemand by their ID with a specified reason.
     *
     * @param id     The ID of the FertilizersInDemand to delete
     * @param reason The reason for deletion
     * @return FertilizersInDemandDTO
     */
    FertilizersInDemandDTO deleteById(Long id, String reason);

    /**
     * Retrieves a FertilizersInDemand by their ID.
     *
     * @param id The ID of the FertilizersInDemand to retrieve
     * @return The FertilizersInDemand with the specified ID
     * @since 1.0.0
     */
    FertilizersInDemandDTO getById(Long id);




}
