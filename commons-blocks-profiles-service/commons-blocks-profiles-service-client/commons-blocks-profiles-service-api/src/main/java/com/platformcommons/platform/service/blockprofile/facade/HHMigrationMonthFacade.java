package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HHMigrationMonth-related operations in the system.
 * This interface provides methods for CRUD operations on HHMigrationMonth data,
 * including creation, retrieval, update, and deletion of HHMigrationMonth records.
 * It serves as the primary entry point for HHMigrationMonth management functionality.
 */
public interface HHMigrationMonthFacade {

    /**
     * Retrieves all HHMigrationMonths by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHMigrationMonthDTO
     */
    Set<HHMigrationMonthDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHMigrationMonth record in the system.
     *
     * @param HHMigrationMonthDTO The HHMigrationMonth data transfer object containing the information to be saved
     * @return The saved HHMigrationMonth data with generated identifiers and any system-modified fields
     */
    HHMigrationMonthDTO save(HHMigrationMonthDTO HHMigrationMonthDTO);

    /**
     * Updates an existing HHMigrationMonth record in the system.
     *
     * @param HHMigrationMonthDTO The HHMigrationMonth data transfer object containing the updated information
     * @return The updated HHMigrationMonth data as confirmed by the system
     */
    HHMigrationMonthDTO update(HHMigrationMonthDTO HHMigrationMonthDTO);

    /**
     * Retrieves a paginated list of all HHMigrationMonths in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HHMigrationMonth records
     */
    PageDTO<HHMigrationMonthDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HHMigrationMonth record from the system.
     *
     * @param id     The unique identifier of the HHMigrationMonth to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HHMigrationMonth by their unique identifier.
     *
     * @param id The unique identifier of the HHMigrationMonth to retrieve
     * @return The HHMigrationMonth data transfer object containing the requested information
     */
    HHMigrationMonthDTO getById(Long id);



}
