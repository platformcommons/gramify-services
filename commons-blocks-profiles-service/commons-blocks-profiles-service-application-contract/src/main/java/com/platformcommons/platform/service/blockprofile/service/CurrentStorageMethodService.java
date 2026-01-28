package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing CurrentStorageMethod-related operations.
 * Provides CRUD operations and pagination support for CurrentStorageMethod entities.
 *
 * @since 1.0.0
 */

public interface CurrentStorageMethodService {

    /**
     * Retrieves all CurrentStorageMethods by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CurrentStorageMethodDTO in the same order as retrieved
     */
    Set<CurrentStorageMethodDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new CurrentStorageMethod entry in the system.
     *
     * @param CurrentStorageMethod The CurrentStorageMethod information to be saved
     * @return The saved CurrentStorageMethod data
     * @since 1.0.0
     */
    CurrentStorageMethodDTO save(CurrentStorageMethodDTO CurrentStorageMethod);

    /**
     * Updates an existing CurrentStorageMethod entry.
     *
     * @param CurrentStorageMethod The CurrentStorageMethod information to be updated
     * @return The updated CurrentStorageMethod data
     * @since 1.0.0
     */
    CurrentStorageMethodDTO update(CurrentStorageMethodDTO CurrentStorageMethod);

    /**
     * Retrieves a paginated list of CurrentStorageMethods.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of CurrentStorageMethods
     * @since 1.0.0
     */
    PageDTO<CurrentStorageMethodDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a CurrentStorageMethod by their ID with a specified reason.
     *
     * @param id     The ID of the CurrentStorageMethod to delete
     * @param reason The reason for deletion
     * @return CurrentStorageMethodDTO
     */
    CurrentStorageMethodDTO deleteById(Long id, String reason);

    /**
     * Retrieves a CurrentStorageMethod by their ID.
     *
     * @param id The ID of the CurrentStorageMethod to retrieve
     * @return The CurrentStorageMethod with the specified ID
     * @since 1.0.0
     */
    CurrentStorageMethodDTO getById(Long id);




}
