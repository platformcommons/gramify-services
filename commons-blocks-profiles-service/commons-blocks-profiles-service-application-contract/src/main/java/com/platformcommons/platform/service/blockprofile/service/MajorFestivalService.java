package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing MajorFestival-related operations.
 * Provides CRUD operations and pagination support for MajorFestival entities.
 *
 * @since 1.0.0
 */

public interface MajorFestivalService {

    /**
     * Retrieves all MajorFestivals by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MajorFestivalDTO in the same order as retrieved
     */
    Set<MajorFestivalDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new MajorFestival entry in the system.
     *
     * @param MajorFestival The MajorFestival information to be saved
     * @return The saved MajorFestival data
     * @since 1.0.0
     */
    MajorFestivalDTO save(MajorFestivalDTO MajorFestival);

    /**
     * Updates an existing MajorFestival entry.
     *
     * @param MajorFestival The MajorFestival information to be updated
     * @return The updated MajorFestival data
     * @since 1.0.0
     */
    MajorFestivalDTO update(MajorFestivalDTO MajorFestival);

    /**
     * Retrieves a paginated list of MajorFestivals.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of MajorFestivals
     * @since 1.0.0
     */
    PageDTO<MajorFestivalDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a MajorFestival by their ID with a specified reason.
     *
     * @param id     The ID of the MajorFestival to delete
     * @param reason The reason for deletion
     * @return MajorFestivalDTO
     */
    MajorFestivalDTO deleteById(Long id, String reason);

    /**
     * Retrieves a MajorFestival by their ID.
     *
     * @param id The ID of the MajorFestival to retrieve
     * @return The MajorFestival with the specified ID
     * @since 1.0.0
     */
    MajorFestivalDTO getById(Long id);




}
