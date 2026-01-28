package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing Crop-related operations in the system.
 * This interface provides methods for CRUD operations on Crop data,
 * including creation, retrieval, update, and deletion of Crop records.
 * It serves as the primary entry point for Crop management functionality.
 */
public interface CropFacade {

    /**
     * Retrieves all Crops by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CropDTO
     */
    Set<CropDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new Crop record in the system.
     *
     * @param CropDTO The Crop data transfer object containing the information to be saved
     * @return The saved Crop data with generated identifiers and any system-modified fields
     */
    CropDTO save(CropDTO CropDTO);

    /**
     * Updates an existing Crop record in the system.
     *
     * @param CropDTO The Crop data transfer object containing the updated information
     * @return The updated Crop data as confirmed by the system
     */
    CropDTO update(CropDTO CropDTO);

    /**
     * Retrieves a paginated list of all Crops in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested Crop records
     */
    PageDTO<CropDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a Crop record from the system.
     *
     * @param id     The unique identifier of the Crop to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific Crop by their unique identifier.
     *
     * @param id The unique identifier of the Crop to retrieve
     * @return The Crop data transfer object containing the requested information
     */
    CropDTO getById(Long id);



}
