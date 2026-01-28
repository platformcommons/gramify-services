package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing WherePeopleGoForRepairs-related operations.
 * Provides CRUD operations and pagination support for WherePeopleGoForRepairs entities.
 *
 * @since 1.0.0
 */

public interface WherePeopleGoForRepairsService {

    /**
     * Retrieves all WherePeopleGoForRepairss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of WherePeopleGoForRepairsDTO in the same order as retrieved
     */
    Set<WherePeopleGoForRepairsDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new WherePeopleGoForRepairs entry in the system.
     *
     * @param WherePeopleGoForRepairs The WherePeopleGoForRepairs information to be saved
     * @return The saved WherePeopleGoForRepairs data
     * @since 1.0.0
     */
    WherePeopleGoForRepairsDTO save(WherePeopleGoForRepairsDTO WherePeopleGoForRepairs);

    /**
     * Updates an existing WherePeopleGoForRepairs entry.
     *
     * @param WherePeopleGoForRepairs The WherePeopleGoForRepairs information to be updated
     * @return The updated WherePeopleGoForRepairs data
     * @since 1.0.0
     */
    WherePeopleGoForRepairsDTO update(WherePeopleGoForRepairsDTO WherePeopleGoForRepairs);

    /**
     * Retrieves a paginated list of WherePeopleGoForRepairss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of WherePeopleGoForRepairss
     * @since 1.0.0
     */
    PageDTO<WherePeopleGoForRepairsDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a WherePeopleGoForRepairs by their ID with a specified reason.
     *
     * @param id     The ID of the WherePeopleGoForRepairs to delete
     * @param reason The reason for deletion
     * @return WherePeopleGoForRepairsDTO
     */
    WherePeopleGoForRepairsDTO deleteById(Long id, String reason);

    /**
     * Retrieves a WherePeopleGoForRepairs by their ID.
     *
     * @param id The ID of the WherePeopleGoForRepairs to retrieve
     * @return The WherePeopleGoForRepairs with the specified ID
     * @since 1.0.0
     */
    WherePeopleGoForRepairsDTO getById(Long id);




}
