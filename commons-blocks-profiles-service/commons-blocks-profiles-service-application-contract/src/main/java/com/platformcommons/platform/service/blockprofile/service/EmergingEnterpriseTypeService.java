package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing EmergingEnterpriseType-related operations.
 * Provides CRUD operations and pagination support for EmergingEnterpriseType entities.
 *
 * @since 1.0.0
 */

public interface EmergingEnterpriseTypeService {

    /**
     * Retrieves all EmergingEnterpriseTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of EmergingEnterpriseTypeDTO in the same order as retrieved
     */
    Set<EmergingEnterpriseTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new EmergingEnterpriseType entry in the system.
     *
     * @param EmergingEnterpriseType The EmergingEnterpriseType information to be saved
     * @return The saved EmergingEnterpriseType data
     * @since 1.0.0
     */
    EmergingEnterpriseTypeDTO save(EmergingEnterpriseTypeDTO EmergingEnterpriseType);

    /**
     * Updates an existing EmergingEnterpriseType entry.
     *
     * @param EmergingEnterpriseType The EmergingEnterpriseType information to be updated
     * @return The updated EmergingEnterpriseType data
     * @since 1.0.0
     */
    EmergingEnterpriseTypeDTO update(EmergingEnterpriseTypeDTO EmergingEnterpriseType);

    /**
     * Retrieves a paginated list of EmergingEnterpriseTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of EmergingEnterpriseTypes
     * @since 1.0.0
     */
    PageDTO<EmergingEnterpriseTypeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a EmergingEnterpriseType by their ID with a specified reason.
     *
     * @param id     The ID of the EmergingEnterpriseType to delete
     * @param reason The reason for deletion
     * @return EmergingEnterpriseTypeDTO
     */
    EmergingEnterpriseTypeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a EmergingEnterpriseType by their ID.
     *
     * @param id The ID of the EmergingEnterpriseType to retrieve
     * @return The EmergingEnterpriseType with the specified ID
     * @since 1.0.0
     */
    EmergingEnterpriseTypeDTO getById(Long id);




}
