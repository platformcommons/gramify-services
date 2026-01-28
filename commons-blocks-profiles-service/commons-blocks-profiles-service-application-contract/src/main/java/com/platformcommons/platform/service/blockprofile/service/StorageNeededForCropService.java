package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing StorageNeededForCrop-related operations.
 * Provides CRUD operations and pagination support for StorageNeededForCrop entities.
 *
 * @since 1.0.0
 */

public interface StorageNeededForCropService {

    /**
     * Retrieves all StorageNeededForCrops by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of StorageNeededForCropDTO in the same order as retrieved
     */
    Set<StorageNeededForCropDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new StorageNeededForCrop entry in the system.
     *
     * @param StorageNeededForCrop The StorageNeededForCrop information to be saved
     * @return The saved StorageNeededForCrop data
     * @since 1.0.0
     */
    StorageNeededForCropDTO save(StorageNeededForCropDTO StorageNeededForCrop);

    /**
     * Updates an existing StorageNeededForCrop entry.
     *
     * @param StorageNeededForCrop The StorageNeededForCrop information to be updated
     * @return The updated StorageNeededForCrop data
     * @since 1.0.0
     */
    StorageNeededForCropDTO update(StorageNeededForCropDTO StorageNeededForCrop);

    /**
     * Retrieves a paginated list of StorageNeededForCrops.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of StorageNeededForCrops
     * @since 1.0.0
     */
    PageDTO<StorageNeededForCropDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a StorageNeededForCrop by their ID with a specified reason.
     *
     * @param id     The ID of the StorageNeededForCrop to delete
     * @param reason The reason for deletion
     * @return StorageNeededForCropDTO
     */
    StorageNeededForCropDTO deleteById(Long id, String reason);

    /**
     * Retrieves a StorageNeededForCrop by their ID.
     *
     * @param id The ID of the StorageNeededForCrop to retrieve
     * @return The StorageNeededForCrop with the specified ID
     * @since 1.0.0
     */
    StorageNeededForCropDTO getById(Long id);




}
