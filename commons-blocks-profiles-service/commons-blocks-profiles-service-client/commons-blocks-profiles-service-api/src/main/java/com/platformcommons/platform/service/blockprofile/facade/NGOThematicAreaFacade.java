package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing NGOThematicArea-related operations in the system.
 * This interface provides methods for CRUD operations on NGOThematicArea data,
 * including creation, retrieval, update, and deletion of NGOThematicArea records.
 * It serves as the primary entry point for NGOThematicArea management functionality.
 */
public interface NGOThematicAreaFacade {

    /**
     * Retrieves all NGOThematicAreas by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of NGOThematicAreaDTO
     */
    Set<NGOThematicAreaDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new NGOThematicArea record in the system.
     *
     * @param NGOThematicAreaDTO The NGOThematicArea data transfer object containing the information to be saved
     * @return The saved NGOThematicArea data with generated identifiers and any system-modified fields
     */
    NGOThematicAreaDTO save(NGOThematicAreaDTO NGOThematicAreaDTO);

    /**
     * Updates an existing NGOThematicArea record in the system.
     *
     * @param NGOThematicAreaDTO The NGOThematicArea data transfer object containing the updated information
     * @return The updated NGOThematicArea data as confirmed by the system
     */
    NGOThematicAreaDTO update(NGOThematicAreaDTO NGOThematicAreaDTO);

    /**
     * Retrieves a paginated list of all NGOThematicAreas in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested NGOThematicArea records
     */
    PageDTO<NGOThematicAreaDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a NGOThematicArea record from the system.
     *
     * @param id     The unique identifier of the NGOThematicArea to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific NGOThematicArea by their unique identifier.
     *
     * @param id The unique identifier of the NGOThematicArea to retrieve
     * @return The NGOThematicArea data transfer object containing the requested information
     */
    NGOThematicAreaDTO getById(Long id);



}
