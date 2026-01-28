package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing SourceOfRawMaterial-related operations in the system.
 * This interface provides methods for CRUD operations on SourceOfRawMaterial data,
 * including creation, retrieval, update, and deletion of SourceOfRawMaterial records.
 * It serves as the primary entry point for SourceOfRawMaterial management functionality.
 */
public interface SourceOfRawMaterialFacade {

    /**
     * Retrieves all SourceOfRawMaterials by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SourceOfRawMaterialDTO
     */
    Set<SourceOfRawMaterialDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new SourceOfRawMaterial record in the system.
     *
     * @param SourceOfRawMaterialDTO The SourceOfRawMaterial data transfer object containing the information to be saved
     * @return The saved SourceOfRawMaterial data with generated identifiers and any system-modified fields
     */
    SourceOfRawMaterialDTO save(SourceOfRawMaterialDTO SourceOfRawMaterialDTO);

    /**
     * Updates an existing SourceOfRawMaterial record in the system.
     *
     * @param SourceOfRawMaterialDTO The SourceOfRawMaterial data transfer object containing the updated information
     * @return The updated SourceOfRawMaterial data as confirmed by the system
     */
    SourceOfRawMaterialDTO update(SourceOfRawMaterialDTO SourceOfRawMaterialDTO);

    /**
     * Retrieves a paginated list of all SourceOfRawMaterials in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested SourceOfRawMaterial records
     */
    PageDTO<SourceOfRawMaterialDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a SourceOfRawMaterial record from the system.
     *
     * @param id     The unique identifier of the SourceOfRawMaterial to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific SourceOfRawMaterial by their unique identifier.
     *
     * @param id The unique identifier of the SourceOfRawMaterial to retrieve
     * @return The SourceOfRawMaterial data transfer object containing the requested information
     */
    SourceOfRawMaterialDTO getById(Long id);



}
