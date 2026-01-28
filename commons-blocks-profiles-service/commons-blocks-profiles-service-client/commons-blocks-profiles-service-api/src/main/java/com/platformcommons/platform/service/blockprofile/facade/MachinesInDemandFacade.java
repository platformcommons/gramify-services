package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing MachinesInDemand-related operations in the system.
 * This interface provides methods for CRUD operations on MachinesInDemand data,
 * including creation, retrieval, update, and deletion of MachinesInDemand records.
 * It serves as the primary entry point for MachinesInDemand management functionality.
 */
public interface MachinesInDemandFacade {

    /**
     * Retrieves all MachinesInDemands by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MachinesInDemandDTO
     */
    Set<MachinesInDemandDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new MachinesInDemand record in the system.
     *
     * @param MachinesInDemandDTO The MachinesInDemand data transfer object containing the information to be saved
     * @return The saved MachinesInDemand data with generated identifiers and any system-modified fields
     */
    MachinesInDemandDTO save(MachinesInDemandDTO MachinesInDemandDTO);

    /**
     * Updates an existing MachinesInDemand record in the system.
     *
     * @param MachinesInDemandDTO The MachinesInDemand data transfer object containing the updated information
     * @return The updated MachinesInDemand data as confirmed by the system
     */
    MachinesInDemandDTO update(MachinesInDemandDTO MachinesInDemandDTO);

    /**
     * Retrieves a paginated list of all MachinesInDemands in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested MachinesInDemand records
     */
    PageDTO<MachinesInDemandDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a MachinesInDemand record from the system.
     *
     * @param id     The unique identifier of the MachinesInDemand to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific MachinesInDemand by their unique identifier.
     *
     * @param id The unique identifier of the MachinesInDemand to retrieve
     * @return The MachinesInDemand data transfer object containing the requested information
     */
    MachinesInDemandDTO getById(Long id);



}
