package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HHSkilledWorkerType-related operations.
 * Provides CRUD operations and pagination support for HHSkilledWorkerType entities.
 *
 * @since 1.0.0
 */

public interface HHSkilledWorkerTypeService {

    /**
     * Retrieves all HHSkilledWorkerTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHSkilledWorkerTypeDTO in the same order as retrieved
     */
    Set<HHSkilledWorkerTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHSkilledWorkerType entry in the system.
     *
     * @param HHSkilledWorkerType The HHSkilledWorkerType information to be saved
     * @return The saved HHSkilledWorkerType data
     * @since 1.0.0
     */
    HHSkilledWorkerTypeDTO save(HHSkilledWorkerTypeDTO HHSkilledWorkerType);

    /**
     * Updates an existing HHSkilledWorkerType entry.
     *
     * @param HHSkilledWorkerType The HHSkilledWorkerType information to be updated
     * @return The updated HHSkilledWorkerType data
     * @since 1.0.0
     */
    HHSkilledWorkerTypeDTO update(HHSkilledWorkerTypeDTO HHSkilledWorkerType);

    /**
     * Retrieves a paginated list of HHSkilledWorkerTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHSkilledWorkerTypes
     * @since 1.0.0
     */
    PageDTO<HHSkilledWorkerTypeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HHSkilledWorkerType by their ID with a specified reason.
     *
     * @param id     The ID of the HHSkilledWorkerType to delete
     * @param reason The reason for deletion
     * @return HHSkilledWorkerTypeDTO
     */
    HHSkilledWorkerTypeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HHSkilledWorkerType by their ID.
     *
     * @param id The ID of the HHSkilledWorkerType to retrieve
     * @return The HHSkilledWorkerType with the specified ID
     * @since 1.0.0
     */
    HHSkilledWorkerTypeDTO getById(Long id);




}
