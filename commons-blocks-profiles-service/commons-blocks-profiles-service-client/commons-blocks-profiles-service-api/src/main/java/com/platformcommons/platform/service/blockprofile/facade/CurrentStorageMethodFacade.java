package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing CurrentStorageMethod-related operations in the system.
 * This interface provides methods for CRUD operations on CurrentStorageMethod data,
 * including creation, retrieval, update, and deletion of CurrentStorageMethod records.
 * It serves as the primary entry point for CurrentStorageMethod management functionality.
 */
public interface CurrentStorageMethodFacade {

    /**
     * Retrieves all CurrentStorageMethods by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CurrentStorageMethodDTO
     */
    Set<CurrentStorageMethodDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new CurrentStorageMethod record in the system.
     *
     * @param CurrentStorageMethodDTO The CurrentStorageMethod data transfer object containing the information to be saved
     * @return The saved CurrentStorageMethod data with generated identifiers and any system-modified fields
     */
    CurrentStorageMethodDTO save(CurrentStorageMethodDTO CurrentStorageMethodDTO);

    /**
     * Updates an existing CurrentStorageMethod record in the system.
     *
     * @param CurrentStorageMethodDTO The CurrentStorageMethod data transfer object containing the updated information
     * @return The updated CurrentStorageMethod data as confirmed by the system
     */
    CurrentStorageMethodDTO update(CurrentStorageMethodDTO CurrentStorageMethodDTO);

    /**
     * Retrieves a paginated list of all CurrentStorageMethods in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested CurrentStorageMethod records
     */
    PageDTO<CurrentStorageMethodDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a CurrentStorageMethod record from the system.
     *
     * @param id     The unique identifier of the CurrentStorageMethod to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific CurrentStorageMethod by their unique identifier.
     *
     * @param id The unique identifier of the CurrentStorageMethod to retrieve
     * @return The CurrentStorageMethod data transfer object containing the requested information
     */
    CurrentStorageMethodDTO getById(Long id);



}
