package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing SurplusMovedThrough-related operations in the system.
 * This interface provides methods for CRUD operations on SurplusMovedThrough data,
 * including creation, retrieval, update, and deletion of SurplusMovedThrough records.
 * It serves as the primary entry point for SurplusMovedThrough management functionality.
 */
public interface SurplusMovedThroughFacade {

    /**
     * Retrieves all SurplusMovedThroughs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SurplusMovedThroughDTO
     */
    Set<SurplusMovedThroughDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new SurplusMovedThrough record in the system.
     *
     * @param SurplusMovedThroughDTO The SurplusMovedThrough data transfer object containing the information to be saved
     * @return The saved SurplusMovedThrough data with generated identifiers and any system-modified fields
     */
    SurplusMovedThroughDTO save(SurplusMovedThroughDTO SurplusMovedThroughDTO);

    /**
     * Updates an existing SurplusMovedThrough record in the system.
     *
     * @param SurplusMovedThroughDTO The SurplusMovedThrough data transfer object containing the updated information
     * @return The updated SurplusMovedThrough data as confirmed by the system
     */
    SurplusMovedThroughDTO update(SurplusMovedThroughDTO SurplusMovedThroughDTO);

    /**
     * Retrieves a paginated list of all SurplusMovedThroughs in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested SurplusMovedThrough records
     */
    PageDTO<SurplusMovedThroughDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a SurplusMovedThrough record from the system.
     *
     * @param id     The unique identifier of the SurplusMovedThrough to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific SurplusMovedThrough by their unique identifier.
     *
     * @param id The unique identifier of the SurplusMovedThrough to retrieve
     * @return The SurplusMovedThrough data transfer object containing the requested information
     */
    SurplusMovedThroughDTO getById(Long id);



}
