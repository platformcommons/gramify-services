package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageCommonFlora-related operations in the system.
 * This interface provides methods for CRUD operations on VillageCommonFlora data,
 * including creation, retrieval, update, and deletion of VillageCommonFlora records.
 * It serves as the primary entry point for VillageCommonFlora management functionality.
 */
public interface VillageCommonFloraFacade {

    /**
     * Retrieves all VillageCommonFloras by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCommonFloraDTO
     */
    Set<VillageCommonFloraDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageCommonFlora record in the system.
     *
     * @param VillageCommonFloraDTO The VillageCommonFlora data transfer object containing the information to be saved
     * @return The saved VillageCommonFlora data with generated identifiers and any system-modified fields
     */
    VillageCommonFloraDTO save(VillageCommonFloraDTO VillageCommonFloraDTO);

    /**
     * Updates an existing VillageCommonFlora record in the system.
     *
     * @param VillageCommonFloraDTO The VillageCommonFlora data transfer object containing the updated information
     * @return The updated VillageCommonFlora data as confirmed by the system
     */
    VillageCommonFloraDTO update(VillageCommonFloraDTO VillageCommonFloraDTO);

    /**
     * Retrieves a paginated list of all VillageCommonFloras in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageCommonFlora records
     */
    PageDTO<VillageCommonFloraDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageCommonFlora record from the system.
     *
     * @param id     The unique identifier of the VillageCommonFlora to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageCommonFlora by their unique identifier.
     *
     * @param id The unique identifier of the VillageCommonFlora to retrieve
     * @return The VillageCommonFlora data transfer object containing the requested information
     */
    VillageCommonFloraDTO getById(Long id);



}
