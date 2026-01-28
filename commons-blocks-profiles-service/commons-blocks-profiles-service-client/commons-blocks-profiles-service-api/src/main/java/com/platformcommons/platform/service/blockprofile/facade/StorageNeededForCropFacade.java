package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing StorageNeededForCrop-related operations in the system.
 * This interface provides methods for CRUD operations on StorageNeededForCrop data,
 * including creation, retrieval, update, and deletion of StorageNeededForCrop records.
 * It serves as the primary entry point for StorageNeededForCrop management functionality.
 */
public interface StorageNeededForCropFacade {

    /**
     * Retrieves all StorageNeededForCrops by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of StorageNeededForCropDTO
     */
    Set<StorageNeededForCropDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new StorageNeededForCrop record in the system.
     *
     * @param StorageNeededForCropDTO The StorageNeededForCrop data transfer object containing the information to be saved
     * @return The saved StorageNeededForCrop data with generated identifiers and any system-modified fields
     */
    StorageNeededForCropDTO save(StorageNeededForCropDTO StorageNeededForCropDTO);

    /**
     * Updates an existing StorageNeededForCrop record in the system.
     *
     * @param StorageNeededForCropDTO The StorageNeededForCrop data transfer object containing the updated information
     * @return The updated StorageNeededForCrop data as confirmed by the system
     */
    StorageNeededForCropDTO update(StorageNeededForCropDTO StorageNeededForCropDTO);

    /**
     * Retrieves a paginated list of all StorageNeededForCrops in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested StorageNeededForCrop records
     */
    PageDTO<StorageNeededForCropDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a StorageNeededForCrop record from the system.
     *
     * @param id     The unique identifier of the StorageNeededForCrop to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific StorageNeededForCrop by their unique identifier.
     *
     * @param id The unique identifier of the StorageNeededForCrop to retrieve
     * @return The StorageNeededForCrop data transfer object containing the requested information
     */
    StorageNeededForCropDTO getById(Long id);



}
