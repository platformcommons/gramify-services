package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HHArtisanType-related operations.
 * Provides CRUD operations and pagination support for HHArtisanType entities.
 *
 * @since 1.0.0
 */

public interface HHArtisanTypeService {

    /**
     * Retrieves all HHArtisanTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHArtisanTypeDTO in the same order as retrieved
     */
    Set<HHArtisanTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHArtisanType entry in the system.
     *
     * @param HHArtisanType The HHArtisanType information to be saved
     * @return The saved HHArtisanType data
     * @since 1.0.0
     */
    HHArtisanTypeDTO save(HHArtisanTypeDTO HHArtisanType);

    /**
     * Updates an existing HHArtisanType entry.
     *
     * @param HHArtisanType The HHArtisanType information to be updated
     * @return The updated HHArtisanType data
     * @since 1.0.0
     */
    HHArtisanTypeDTO update(HHArtisanTypeDTO HHArtisanType);

    /**
     * Retrieves a paginated list of HHArtisanTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHArtisanTypes
     * @since 1.0.0
     */
    PageDTO<HHArtisanTypeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HHArtisanType by their ID with a specified reason.
     *
     * @param id     The ID of the HHArtisanType to delete
     * @param reason The reason for deletion
     * @return HHArtisanTypeDTO
     */
    HHArtisanTypeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HHArtisanType by their ID.
     *
     * @param id The ID of the HHArtisanType to retrieve
     * @return The HHArtisanType with the specified ID
     * @since 1.0.0
     */
    HHArtisanTypeDTO getById(Long id);




}
