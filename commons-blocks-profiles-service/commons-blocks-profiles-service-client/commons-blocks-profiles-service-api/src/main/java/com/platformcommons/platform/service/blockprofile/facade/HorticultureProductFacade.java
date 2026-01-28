package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HorticultureProduct-related operations in the system.
 * This interface provides methods for CRUD operations on HorticultureProduct data,
 * including creation, retrieval, update, and deletion of HorticultureProduct records.
 * It serves as the primary entry point for HorticultureProduct management functionality.
 */
public interface HorticultureProductFacade {

    /**
     * Retrieves all HorticultureProducts by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HorticultureProductDTO
     */
    Set<HorticultureProductDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HorticultureProduct record in the system.
     *
     * @param HorticultureProductDTO The HorticultureProduct data transfer object containing the information to be saved
     * @return The saved HorticultureProduct data with generated identifiers and any system-modified fields
     */
    HorticultureProductDTO save(HorticultureProductDTO HorticultureProductDTO);

    /**
     * Updates an existing HorticultureProduct record in the system.
     *
     * @param HorticultureProductDTO The HorticultureProduct data transfer object containing the updated information
     * @return The updated HorticultureProduct data as confirmed by the system
     */
    HorticultureProductDTO update(HorticultureProductDTO HorticultureProductDTO);

    /**
     * Retrieves a paginated list of all HorticultureProducts in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HorticultureProduct records
     */
    PageDTO<HorticultureProductDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HorticultureProduct record from the system.
     *
     * @param id     The unique identifier of the HorticultureProduct to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HorticultureProduct by their unique identifier.
     *
     * @param id The unique identifier of the HorticultureProduct to retrieve
     * @return The HorticultureProduct data transfer object containing the requested information
     */
    HorticultureProductDTO getById(Long id);



}
