package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing WherePeopleGoForCommonIllness-related operations.
 * Provides CRUD operations and pagination support for WherePeopleGoForCommonIllness entities.
 *
 * @since 1.0.0
 */

public interface WherePeopleGoForCommonIllnessService {

    /**
     * Retrieves all WherePeopleGoForCommonIllnesss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of WherePeopleGoForCommonIllnessDTO in the same order as retrieved
     */
    Set<WherePeopleGoForCommonIllnessDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new WherePeopleGoForCommonIllness entry in the system.
     *
     * @param WherePeopleGoForCommonIllness The WherePeopleGoForCommonIllness information to be saved
     * @return The saved WherePeopleGoForCommonIllness data
     * @since 1.0.0
     */
    WherePeopleGoForCommonIllnessDTO save(WherePeopleGoForCommonIllnessDTO WherePeopleGoForCommonIllness);

    /**
     * Updates an existing WherePeopleGoForCommonIllness entry.
     *
     * @param WherePeopleGoForCommonIllness The WherePeopleGoForCommonIllness information to be updated
     * @return The updated WherePeopleGoForCommonIllness data
     * @since 1.0.0
     */
    WherePeopleGoForCommonIllnessDTO update(WherePeopleGoForCommonIllnessDTO WherePeopleGoForCommonIllness);

    /**
     * Retrieves a paginated list of WherePeopleGoForCommonIllnesss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of WherePeopleGoForCommonIllnesss
     * @since 1.0.0
     */
    PageDTO<WherePeopleGoForCommonIllnessDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a WherePeopleGoForCommonIllness by their ID with a specified reason.
     *
     * @param id     The ID of the WherePeopleGoForCommonIllness to delete
     * @param reason The reason for deletion
     * @return WherePeopleGoForCommonIllnessDTO
     */
    WherePeopleGoForCommonIllnessDTO deleteById(Long id, String reason);

    /**
     * Retrieves a WherePeopleGoForCommonIllness by their ID.
     *
     * @param id The ID of the WherePeopleGoForCommonIllness to retrieve
     * @return The WherePeopleGoForCommonIllness with the specified ID
     * @since 1.0.0
     */
    WherePeopleGoForCommonIllnessDTO getById(Long id);




}
