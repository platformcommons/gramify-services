package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing Village-related operations in the system.
 * This interface provides methods for CRUD operations on Village data,
 * including creation, retrieval, update, and deletion of Village records.
 * It serves as the primary entry point for Village management functionality.
 */
public interface VillageFacade {

    /**
     * Retrieves all Villages by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageDTO
     */
    Set<VillageDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new Village record in the system.
     *
     * @param VillageDTO The Village data transfer object containing the information to be saved
     * @return The saved Village data with generated identifiers and any system-modified fields
     */
    VillageDTO save(VillageDTO VillageDTO);

    /**
     * Updates an existing Village record in the system.
     *
     * @param VillageDTO The Village data transfer object containing the updated information
     * @return The updated Village data as confirmed by the system
     */
    VillageDTO update(VillageDTO VillageDTO);

    /**
     * Retrieves a paginated list of all Villages in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested Village records
     */
    PageDTO<VillageDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a Village record from the system.
     *
     * @param id     The unique identifier of the Village to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific Village by their unique identifier.
     *
     * @param id The unique identifier of the Village to retrieve
     * @return The Village data transfer object containing the requested information
     */
    VillageDTO getById(Long id);



}
