package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing FertilizersInDemand-related operations in the system.
 * This interface provides methods for CRUD operations on FertilizersInDemand data,
 * including creation, retrieval, update, and deletion of FertilizersInDemand records.
 * It serves as the primary entry point for FertilizersInDemand management functionality.
 */
public interface FertilizersInDemandFacade {

    /**
     * Retrieves all FertilizersInDemands by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of FertilizersInDemandDTO
     */
    Set<FertilizersInDemandDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new FertilizersInDemand record in the system.
     *
     * @param FertilizersInDemandDTO The FertilizersInDemand data transfer object containing the information to be saved
     * @return The saved FertilizersInDemand data with generated identifiers and any system-modified fields
     */
    FertilizersInDemandDTO save(FertilizersInDemandDTO FertilizersInDemandDTO);

    /**
     * Updates an existing FertilizersInDemand record in the system.
     *
     * @param FertilizersInDemandDTO The FertilizersInDemand data transfer object containing the updated information
     * @return The updated FertilizersInDemand data as confirmed by the system
     */
    FertilizersInDemandDTO update(FertilizersInDemandDTO FertilizersInDemandDTO);

    /**
     * Retrieves a paginated list of all FertilizersInDemands in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested FertilizersInDemand records
     */
    PageDTO<FertilizersInDemandDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a FertilizersInDemand record from the system.
     *
     * @param id     The unique identifier of the FertilizersInDemand to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific FertilizersInDemand by their unique identifier.
     *
     * @param id The unique identifier of the FertilizersInDemand to retrieve
     * @return The FertilizersInDemand data transfer object containing the requested information
     */
    FertilizersInDemandDTO getById(Long id);



}
