package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing CommonRepairNeed-related operations.
 * Provides CRUD operations and pagination support for CommonRepairNeed entities.
 *
 * @since 1.0.0
 */

public interface CommonRepairNeedService {

    /**
     * Retrieves all CommonRepairNeeds by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CommonRepairNeedDTO in the same order as retrieved
     */
    Set<CommonRepairNeedDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new CommonRepairNeed entry in the system.
     *
     * @param CommonRepairNeed The CommonRepairNeed information to be saved
     * @return The saved CommonRepairNeed data
     * @since 1.0.0
     */
    CommonRepairNeedDTO save(CommonRepairNeedDTO CommonRepairNeed);

    /**
     * Updates an existing CommonRepairNeed entry.
     *
     * @param CommonRepairNeed The CommonRepairNeed information to be updated
     * @return The updated CommonRepairNeed data
     * @since 1.0.0
     */
    CommonRepairNeedDTO update(CommonRepairNeedDTO CommonRepairNeed);

    /**
     * Retrieves a paginated list of CommonRepairNeeds.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of CommonRepairNeeds
     * @since 1.0.0
     */
    PageDTO<CommonRepairNeedDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a CommonRepairNeed by their ID with a specified reason.
     *
     * @param id     The ID of the CommonRepairNeed to delete
     * @param reason The reason for deletion
     * @return CommonRepairNeedDTO
     */
    CommonRepairNeedDTO deleteById(Long id, String reason);

    /**
     * Retrieves a CommonRepairNeed by their ID.
     *
     * @param id The ID of the CommonRepairNeed to retrieve
     * @return The CommonRepairNeed with the specified ID
     * @since 1.0.0
     */
    CommonRepairNeedDTO getById(Long id);




}
