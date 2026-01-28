package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageCommonFauna-related operations in the system.
 * This interface provides methods for CRUD operations on VillageCommonFauna data,
 * including creation, retrieval, update, and deletion of VillageCommonFauna records.
 * It serves as the primary entry point for VillageCommonFauna management functionality.
 */
public interface VillageCommonFaunaFacade {

    /**
     * Retrieves all VillageCommonFaunas by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCommonFaunaDTO
     */
    Set<VillageCommonFaunaDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageCommonFauna record in the system.
     *
     * @param VillageCommonFaunaDTO The VillageCommonFauna data transfer object containing the information to be saved
     * @return The saved VillageCommonFauna data with generated identifiers and any system-modified fields
     */
    VillageCommonFaunaDTO save(VillageCommonFaunaDTO VillageCommonFaunaDTO);

    /**
     * Updates an existing VillageCommonFauna record in the system.
     *
     * @param VillageCommonFaunaDTO The VillageCommonFauna data transfer object containing the updated information
     * @return The updated VillageCommonFauna data as confirmed by the system
     */
    VillageCommonFaunaDTO update(VillageCommonFaunaDTO VillageCommonFaunaDTO);

    /**
     * Retrieves a paginated list of all VillageCommonFaunas in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageCommonFauna records
     */
    PageDTO<VillageCommonFaunaDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageCommonFauna record from the system.
     *
     * @param id     The unique identifier of the VillageCommonFauna to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageCommonFauna by their unique identifier.
     *
     * @param id The unique identifier of the VillageCommonFauna to retrieve
     * @return The VillageCommonFauna data transfer object containing the requested information
     */
    VillageCommonFaunaDTO getById(Long id);



}
