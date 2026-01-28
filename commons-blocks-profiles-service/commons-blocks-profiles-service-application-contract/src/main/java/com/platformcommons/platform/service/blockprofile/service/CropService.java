package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing Crop-related operations.
 * Provides CRUD operations and pagination support for Crop entities.
 *
 * @since 1.0.0
 */

public interface CropService {

    /**
     * Retrieves all Crops by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CropDTO in the same order as retrieved
     */
    Set<CropDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new Crop entry in the system.
     *
     * @param Crop The Crop information to be saved
     * @return The saved Crop data
     * @since 1.0.0
     */
    CropDTO save(CropDTO Crop);

    /**
     * Updates an existing Crop entry.
     *
     * @param Crop The Crop information to be updated
     * @return The updated Crop data
     * @since 1.0.0
     */
    CropDTO update(CropDTO Crop);

    /**
     * Retrieves a paginated list of Crops.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of Crops
     * @since 1.0.0
     */
    PageDTO<CropDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a Crop by their ID with a specified reason.
     *
     * @param id     The ID of the Crop to delete
     * @param reason The reason for deletion
     * @return CropDTO
     */
    CropDTO deleteById(Long id, String reason);

    /**
     * Retrieves a Crop by their ID.
     *
     * @param id The ID of the Crop to retrieve
     * @return The Crop with the specified ID
     * @since 1.0.0
     */
    CropDTO getById(Long id);




}
