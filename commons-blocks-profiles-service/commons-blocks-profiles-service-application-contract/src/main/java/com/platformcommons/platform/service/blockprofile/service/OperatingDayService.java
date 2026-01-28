package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing OperatingDay-related operations.
 * Provides CRUD operations and pagination support for OperatingDay entities.
 *
 * @since 1.0.0
 */

public interface OperatingDayService {

    /**
     * Retrieves all OperatingDays by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of OperatingDayDTO in the same order as retrieved
     */
    Set<OperatingDayDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new OperatingDay entry in the system.
     *
     * @param OperatingDay The OperatingDay information to be saved
     * @return The saved OperatingDay data
     * @since 1.0.0
     */
    OperatingDayDTO save(OperatingDayDTO OperatingDay);

    /**
     * Updates an existing OperatingDay entry.
     *
     * @param OperatingDay The OperatingDay information to be updated
     * @return The updated OperatingDay data
     * @since 1.0.0
     */
    OperatingDayDTO update(OperatingDayDTO OperatingDay);

    /**
     * Retrieves a paginated list of OperatingDays.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of OperatingDays
     * @since 1.0.0
     */
    PageDTO<OperatingDayDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a OperatingDay by their ID with a specified reason.
     *
     * @param id     The ID of the OperatingDay to delete
     * @param reason The reason for deletion
     * @return OperatingDayDTO
     */
    OperatingDayDTO deleteById(Long id, String reason);

    /**
     * Retrieves a OperatingDay by their ID.
     *
     * @param id The ID of the OperatingDay to retrieve
     * @return The OperatingDay with the specified ID
     * @since 1.0.0
     */
    OperatingDayDTO getById(Long id);




}
