package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing WherePeopleGoForCommonIllness-related operations in the system.
 * This interface provides methods for CRUD operations on WherePeopleGoForCommonIllness data,
 * including creation, retrieval, update, and deletion of WherePeopleGoForCommonIllness records.
 * It serves as the primary entry point for WherePeopleGoForCommonIllness management functionality.
 */
public interface WherePeopleGoForCommonIllnessFacade {

    /**
     * Retrieves all WherePeopleGoForCommonIllnesss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of WherePeopleGoForCommonIllnessDTO
     */
    Set<WherePeopleGoForCommonIllnessDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new WherePeopleGoForCommonIllness record in the system.
     *
     * @param WherePeopleGoForCommonIllnessDTO The WherePeopleGoForCommonIllness data transfer object containing the information to be saved
     * @return The saved WherePeopleGoForCommonIllness data with generated identifiers and any system-modified fields
     */
    WherePeopleGoForCommonIllnessDTO save(WherePeopleGoForCommonIllnessDTO WherePeopleGoForCommonIllnessDTO);

    /**
     * Updates an existing WherePeopleGoForCommonIllness record in the system.
     *
     * @param WherePeopleGoForCommonIllnessDTO The WherePeopleGoForCommonIllness data transfer object containing the updated information
     * @return The updated WherePeopleGoForCommonIllness data as confirmed by the system
     */
    WherePeopleGoForCommonIllnessDTO update(WherePeopleGoForCommonIllnessDTO WherePeopleGoForCommonIllnessDTO);

    /**
     * Retrieves a paginated list of all WherePeopleGoForCommonIllnesss in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested WherePeopleGoForCommonIllness records
     */
    PageDTO<WherePeopleGoForCommonIllnessDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a WherePeopleGoForCommonIllness record from the system.
     *
     * @param id     The unique identifier of the WherePeopleGoForCommonIllness to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific WherePeopleGoForCommonIllness by their unique identifier.
     *
     * @param id The unique identifier of the WherePeopleGoForCommonIllness to retrieve
     * @return The WherePeopleGoForCommonIllness data transfer object containing the requested information
     */
    WherePeopleGoForCommonIllnessDTO getById(Long id);



}
