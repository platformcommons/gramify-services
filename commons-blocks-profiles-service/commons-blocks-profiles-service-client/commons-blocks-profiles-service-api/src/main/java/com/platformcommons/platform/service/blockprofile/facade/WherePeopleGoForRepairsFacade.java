package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing WherePeopleGoForRepairs-related operations in the system.
 * This interface provides methods for CRUD operations on WherePeopleGoForRepairs data,
 * including creation, retrieval, update, and deletion of WherePeopleGoForRepairs records.
 * It serves as the primary entry point for WherePeopleGoForRepairs management functionality.
 */
public interface WherePeopleGoForRepairsFacade {

    /**
     * Retrieves all WherePeopleGoForRepairss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of WherePeopleGoForRepairsDTO
     */
    Set<WherePeopleGoForRepairsDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new WherePeopleGoForRepairs record in the system.
     *
     * @param WherePeopleGoForRepairsDTO The WherePeopleGoForRepairs data transfer object containing the information to be saved
     * @return The saved WherePeopleGoForRepairs data with generated identifiers and any system-modified fields
     */
    WherePeopleGoForRepairsDTO save(WherePeopleGoForRepairsDTO WherePeopleGoForRepairsDTO);

    /**
     * Updates an existing WherePeopleGoForRepairs record in the system.
     *
     * @param WherePeopleGoForRepairsDTO The WherePeopleGoForRepairs data transfer object containing the updated information
     * @return The updated WherePeopleGoForRepairs data as confirmed by the system
     */
    WherePeopleGoForRepairsDTO update(WherePeopleGoForRepairsDTO WherePeopleGoForRepairsDTO);

    /**
     * Retrieves a paginated list of all WherePeopleGoForRepairss in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested WherePeopleGoForRepairs records
     */
    PageDTO<WherePeopleGoForRepairsDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a WherePeopleGoForRepairs record from the system.
     *
     * @param id     The unique identifier of the WherePeopleGoForRepairs to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific WherePeopleGoForRepairs by their unique identifier.
     *
     * @param id The unique identifier of the WherePeopleGoForRepairs to retrieve
     * @return The WherePeopleGoForRepairs data transfer object containing the requested information
     */
    WherePeopleGoForRepairsDTO getById(Long id);



}
