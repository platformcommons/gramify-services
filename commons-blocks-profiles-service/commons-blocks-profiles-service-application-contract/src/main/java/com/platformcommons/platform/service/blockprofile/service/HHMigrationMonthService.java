package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HHMigrationMonth-related operations.
 * Provides CRUD operations and pagination support for HHMigrationMonth entities.
 *
 * @since 1.0.0
 */

public interface HHMigrationMonthService {

    /**
     * Retrieves all HHMigrationMonths by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHMigrationMonthDTO in the same order as retrieved
     */
    Set<HHMigrationMonthDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHMigrationMonth entry in the system.
     *
     * @param HHMigrationMonth The HHMigrationMonth information to be saved
     * @return The saved HHMigrationMonth data
     * @since 1.0.0
     */
    HHMigrationMonthDTO save(HHMigrationMonthDTO HHMigrationMonth);

    /**
     * Updates an existing HHMigrationMonth entry.
     *
     * @param HHMigrationMonth The HHMigrationMonth information to be updated
     * @return The updated HHMigrationMonth data
     * @since 1.0.0
     */
    HHMigrationMonthDTO update(HHMigrationMonthDTO HHMigrationMonth);

    /**
     * Retrieves a paginated list of HHMigrationMonths.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHMigrationMonths
     * @since 1.0.0
     */
    PageDTO<HHMigrationMonthDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HHMigrationMonth by their ID with a specified reason.
     *
     * @param id     The ID of the HHMigrationMonth to delete
     * @param reason The reason for deletion
     * @return HHMigrationMonthDTO
     */
    HHMigrationMonthDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HHMigrationMonth by their ID.
     *
     * @param id The ID of the HHMigrationMonth to retrieve
     * @return The HHMigrationMonth with the specified ID
     * @since 1.0.0
     */
    HHMigrationMonthDTO getById(Long id);




}
