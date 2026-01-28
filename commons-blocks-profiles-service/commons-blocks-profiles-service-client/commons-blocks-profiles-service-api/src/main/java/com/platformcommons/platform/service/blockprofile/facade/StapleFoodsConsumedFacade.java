package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing StapleFoodsConsumed-related operations in the system.
 * This interface provides methods for CRUD operations on StapleFoodsConsumed data,
 * including creation, retrieval, update, and deletion of StapleFoodsConsumed records.
 * It serves as the primary entry point for StapleFoodsConsumed management functionality.
 */
public interface StapleFoodsConsumedFacade {

    /**
     * Retrieves all StapleFoodsConsumeds by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of StapleFoodsConsumedDTO
     */
    Set<StapleFoodsConsumedDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new StapleFoodsConsumed record in the system.
     *
     * @param StapleFoodsConsumedDTO The StapleFoodsConsumed data transfer object containing the information to be saved
     * @return The saved StapleFoodsConsumed data with generated identifiers and any system-modified fields
     */
    StapleFoodsConsumedDTO save(StapleFoodsConsumedDTO StapleFoodsConsumedDTO);

    /**
     * Updates an existing StapleFoodsConsumed record in the system.
     *
     * @param StapleFoodsConsumedDTO The StapleFoodsConsumed data transfer object containing the updated information
     * @return The updated StapleFoodsConsumed data as confirmed by the system
     */
    StapleFoodsConsumedDTO update(StapleFoodsConsumedDTO StapleFoodsConsumedDTO);

    /**
     * Retrieves a paginated list of all StapleFoodsConsumeds in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested StapleFoodsConsumed records
     */
    PageDTO<StapleFoodsConsumedDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a StapleFoodsConsumed record from the system.
     *
     * @param id     The unique identifier of the StapleFoodsConsumed to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific StapleFoodsConsumed by their unique identifier.
     *
     * @param id The unique identifier of the StapleFoodsConsumed to retrieve
     * @return The StapleFoodsConsumed data transfer object containing the requested information
     */
    StapleFoodsConsumedDTO getById(Long id);



}
