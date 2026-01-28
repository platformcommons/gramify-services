package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing ArtisanType-related operations.
 * Provides CRUD operations and pagination support for ArtisanType entities.
 *
 * @since 1.0.0
 */

public interface ArtisanTypeService {

    /**
     * Retrieves all ArtisanTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ArtisanTypeDTO in the same order as retrieved
     */
    Set<ArtisanTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new ArtisanType entry in the system.
     *
     * @param ArtisanType The ArtisanType information to be saved
     * @return The saved ArtisanType data
     * @since 1.0.0
     */
    ArtisanTypeDTO save(ArtisanTypeDTO ArtisanType);

    /**
     * Updates an existing ArtisanType entry.
     *
     * @param ArtisanType The ArtisanType information to be updated
     * @return The updated ArtisanType data
     * @since 1.0.0
     */
    ArtisanTypeDTO update(ArtisanTypeDTO ArtisanType);

    /**
     * Retrieves a paginated list of ArtisanTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of ArtisanTypes
     * @since 1.0.0
     */
    PageDTO<ArtisanTypeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a ArtisanType by their ID with a specified reason.
     *
     * @param id     The ID of the ArtisanType to delete
     * @param reason The reason for deletion
     * @return ArtisanTypeDTO
     */
    ArtisanTypeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a ArtisanType by their ID.
     *
     * @param id The ID of the ArtisanType to retrieve
     * @return The ArtisanType with the specified ID
     * @since 1.0.0
     */
    ArtisanTypeDTO getById(Long id);




}
