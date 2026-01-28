package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageYouthAspirations-related operations in the system.
 * This interface provides methods for CRUD operations on VillageYouthAspirations data,
 * including creation, retrieval, update, and deletion of VillageYouthAspirations records.
 * It serves as the primary entry point for VillageYouthAspirations management functionality.
 */
public interface VillageYouthAspirationsFacade {

    /**
     * Retrieves all VillageYouthAspirationss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageYouthAspirationsDTO
     */
    Set<VillageYouthAspirationsDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageYouthAspirations record in the system.
     *
     * @param VillageYouthAspirationsDTO The VillageYouthAspirations data transfer object containing the information to be saved
     * @return The saved VillageYouthAspirations data with generated identifiers and any system-modified fields
     */
    VillageYouthAspirationsDTO save(VillageYouthAspirationsDTO VillageYouthAspirationsDTO);

    /**
     * Updates an existing VillageYouthAspirations record in the system.
     *
     * @param VillageYouthAspirationsDTO The VillageYouthAspirations data transfer object containing the updated information
     * @return The updated VillageYouthAspirations data as confirmed by the system
     */
    VillageYouthAspirationsDTO update(VillageYouthAspirationsDTO VillageYouthAspirationsDTO);

    /**
     * Retrieves a paginated list of all VillageYouthAspirationss in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageYouthAspirations records
     */
    PageDTO<VillageYouthAspirationsDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageYouthAspirations record from the system.
     *
     * @param id     The unique identifier of the VillageYouthAspirations to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageYouthAspirations by their unique identifier.
     *
     * @param id The unique identifier of the VillageYouthAspirations to retrieve
     * @return The VillageYouthAspirations data transfer object containing the requested information
     */
    VillageYouthAspirationsDTO getById(Long id);



}
