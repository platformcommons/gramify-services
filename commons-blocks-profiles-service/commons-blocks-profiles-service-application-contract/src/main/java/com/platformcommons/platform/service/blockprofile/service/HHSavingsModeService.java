package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HHSavingsMode-related operations.
 * Provides CRUD operations and pagination support for HHSavingsMode entities.
 *
 * @since 1.0.0
 */

public interface HHSavingsModeService {

    /**
     * Retrieves all HHSavingsModes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHSavingsModeDTO in the same order as retrieved
     */
    Set<HHSavingsModeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHSavingsMode entry in the system.
     *
     * @param HHSavingsMode The HHSavingsMode information to be saved
     * @return The saved HHSavingsMode data
     * @since 1.0.0
     */
    HHSavingsModeDTO save(HHSavingsModeDTO HHSavingsMode);

    /**
     * Updates an existing HHSavingsMode entry.
     *
     * @param HHSavingsMode The HHSavingsMode information to be updated
     * @return The updated HHSavingsMode data
     * @since 1.0.0
     */
    HHSavingsModeDTO update(HHSavingsModeDTO HHSavingsMode);

    /**
     * Retrieves a paginated list of HHSavingsModes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHSavingsModes
     * @since 1.0.0
     */
    PageDTO<HHSavingsModeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HHSavingsMode by their ID with a specified reason.
     *
     * @param id     The ID of the HHSavingsMode to delete
     * @param reason The reason for deletion
     * @return HHSavingsModeDTO
     */
    HHSavingsModeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HHSavingsMode by their ID.
     *
     * @param id The ID of the HHSavingsMode to retrieve
     * @return The HHSavingsMode with the specified ID
     * @since 1.0.0
     */
    HHSavingsModeDTO getById(Long id);




}
