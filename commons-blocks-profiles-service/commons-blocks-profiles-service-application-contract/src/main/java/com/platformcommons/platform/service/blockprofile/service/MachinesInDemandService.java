package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing MachinesInDemand-related operations.
 * Provides CRUD operations and pagination support for MachinesInDemand entities.
 *
 * @since 1.0.0
 */

public interface MachinesInDemandService {

    /**
     * Retrieves all MachinesInDemands by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MachinesInDemandDTO in the same order as retrieved
     */
    Set<MachinesInDemandDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new MachinesInDemand entry in the system.
     *
     * @param MachinesInDemand The MachinesInDemand information to be saved
     * @return The saved MachinesInDemand data
     * @since 1.0.0
     */
    MachinesInDemandDTO save(MachinesInDemandDTO MachinesInDemand);

    /**
     * Updates an existing MachinesInDemand entry.
     *
     * @param MachinesInDemand The MachinesInDemand information to be updated
     * @return The updated MachinesInDemand data
     * @since 1.0.0
     */
    MachinesInDemandDTO update(MachinesInDemandDTO MachinesInDemand);

    /**
     * Retrieves a paginated list of MachinesInDemands.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of MachinesInDemands
     * @since 1.0.0
     */
    PageDTO<MachinesInDemandDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a MachinesInDemand by their ID with a specified reason.
     *
     * @param id     The ID of the MachinesInDemand to delete
     * @param reason The reason for deletion
     * @return MachinesInDemandDTO
     */
    MachinesInDemandDTO deleteById(Long id, String reason);

    /**
     * Retrieves a MachinesInDemand by their ID.
     *
     * @param id The ID of the MachinesInDemand to retrieve
     * @return The MachinesInDemand with the specified ID
     * @since 1.0.0
     */
    MachinesInDemandDTO getById(Long id);




}
