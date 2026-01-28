package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing LocalFestival-related operations.
 * Provides CRUD operations and pagination support for LocalFestival entities.
 *
 * @since 1.0.0
 */

public interface LocalFestivalService {

    /**
     * Retrieves all LocalFestivals by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of LocalFestivalDTO in the same order as retrieved
     */
    Set<LocalFestivalDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new LocalFestival entry in the system.
     *
     * @param LocalFestival The LocalFestival information to be saved
     * @return The saved LocalFestival data
     * @since 1.0.0
     */
    LocalFestivalDTO save(LocalFestivalDTO LocalFestival);

    /**
     * Updates an existing LocalFestival entry.
     *
     * @param LocalFestival The LocalFestival information to be updated
     * @return The updated LocalFestival data
     * @since 1.0.0
     */
    LocalFestivalDTO update(LocalFestivalDTO LocalFestival);

    /**
     * Retrieves a paginated list of LocalFestivals.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of LocalFestivals
     * @since 1.0.0
     */
    PageDTO<LocalFestivalDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a LocalFestival by their ID with a specified reason.
     *
     * @param id     The ID of the LocalFestival to delete
     * @param reason The reason for deletion
     * @return LocalFestivalDTO
     */
    LocalFestivalDTO deleteById(Long id, String reason);

    /**
     * Retrieves a LocalFestival by their ID.
     *
     * @param id The ID of the LocalFestival to retrieve
     * @return The LocalFestival with the specified ID
     * @since 1.0.0
     */
    LocalFestivalDTO getById(Long id);




}
