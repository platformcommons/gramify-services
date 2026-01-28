package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HHEmploymentType-related operations.
 * Provides CRUD operations and pagination support for HHEmploymentType entities.
 *
 * @since 1.0.0
 */

public interface HHEmploymentTypeService {

    /**
     * Retrieves all HHEmploymentTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHEmploymentTypeDTO in the same order as retrieved
     */
    Set<HHEmploymentTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHEmploymentType entry in the system.
     *
     * @param HHEmploymentType The HHEmploymentType information to be saved
     * @return The saved HHEmploymentType data
     * @since 1.0.0
     */
    HHEmploymentTypeDTO save(HHEmploymentTypeDTO HHEmploymentType);

    /**
     * Updates an existing HHEmploymentType entry.
     *
     * @param HHEmploymentType The HHEmploymentType information to be updated
     * @return The updated HHEmploymentType data
     * @since 1.0.0
     */
    HHEmploymentTypeDTO update(HHEmploymentTypeDTO HHEmploymentType);

    /**
     * Retrieves a paginated list of HHEmploymentTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHEmploymentTypes
     * @since 1.0.0
     */
    PageDTO<HHEmploymentTypeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HHEmploymentType by their ID with a specified reason.
     *
     * @param id     The ID of the HHEmploymentType to delete
     * @param reason The reason for deletion
     * @return HHEmploymentTypeDTO
     */
    HHEmploymentTypeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HHEmploymentType by their ID.
     *
     * @param id The ID of the HHEmploymentType to retrieve
     * @return The HHEmploymentType with the specified ID
     * @since 1.0.0
     */
    HHEmploymentTypeDTO getById(Long id);




}
