package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HHEnterpriseType-related operations.
 * Provides CRUD operations and pagination support for HHEnterpriseType entities.
 *
 * @since 1.0.0
 */

public interface HHEnterpriseTypeService {

    /**
     * Retrieves all HHEnterpriseTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHEnterpriseTypeDTO in the same order as retrieved
     */
    Set<HHEnterpriseTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHEnterpriseType entry in the system.
     *
     * @param HHEnterpriseType The HHEnterpriseType information to be saved
     * @return The saved HHEnterpriseType data
     * @since 1.0.0
     */
    HHEnterpriseTypeDTO save(HHEnterpriseTypeDTO HHEnterpriseType);

    /**
     * Updates an existing HHEnterpriseType entry.
     *
     * @param HHEnterpriseType The HHEnterpriseType information to be updated
     * @return The updated HHEnterpriseType data
     * @since 1.0.0
     */
    HHEnterpriseTypeDTO update(HHEnterpriseTypeDTO HHEnterpriseType);

    /**
     * Retrieves a paginated list of HHEnterpriseTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHEnterpriseTypes
     * @since 1.0.0
     */
    PageDTO<HHEnterpriseTypeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HHEnterpriseType by their ID with a specified reason.
     *
     * @param id     The ID of the HHEnterpriseType to delete
     * @param reason The reason for deletion
     * @return HHEnterpriseTypeDTO
     */
    HHEnterpriseTypeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HHEnterpriseType by their ID.
     *
     * @param id The ID of the HHEnterpriseType to retrieve
     * @return The HHEnterpriseType with the specified ID
     * @since 1.0.0
     */
    HHEnterpriseTypeDTO getById(Long id);




}
