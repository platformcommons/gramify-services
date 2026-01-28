package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing SourceOfRawMaterial-related operations.
 * Provides CRUD operations and pagination support for SourceOfRawMaterial entities.
 *
 * @since 1.0.0
 */

public interface SourceOfRawMaterialService {

    /**
     * Retrieves all SourceOfRawMaterials by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SourceOfRawMaterialDTO in the same order as retrieved
     */
    Set<SourceOfRawMaterialDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new SourceOfRawMaterial entry in the system.
     *
     * @param SourceOfRawMaterial The SourceOfRawMaterial information to be saved
     * @return The saved SourceOfRawMaterial data
     * @since 1.0.0
     */
    SourceOfRawMaterialDTO save(SourceOfRawMaterialDTO SourceOfRawMaterial);

    /**
     * Updates an existing SourceOfRawMaterial entry.
     *
     * @param SourceOfRawMaterial The SourceOfRawMaterial information to be updated
     * @return The updated SourceOfRawMaterial data
     * @since 1.0.0
     */
    SourceOfRawMaterialDTO update(SourceOfRawMaterialDTO SourceOfRawMaterial);

    /**
     * Retrieves a paginated list of SourceOfRawMaterials.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of SourceOfRawMaterials
     * @since 1.0.0
     */
    PageDTO<SourceOfRawMaterialDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a SourceOfRawMaterial by their ID with a specified reason.
     *
     * @param id     The ID of the SourceOfRawMaterial to delete
     * @param reason The reason for deletion
     * @return SourceOfRawMaterialDTO
     */
    SourceOfRawMaterialDTO deleteById(Long id, String reason);

    /**
     * Retrieves a SourceOfRawMaterial by their ID.
     *
     * @param id The ID of the SourceOfRawMaterial to retrieve
     * @return The SourceOfRawMaterial with the specified ID
     * @since 1.0.0
     */
    SourceOfRawMaterialDTO getById(Long id);




}
