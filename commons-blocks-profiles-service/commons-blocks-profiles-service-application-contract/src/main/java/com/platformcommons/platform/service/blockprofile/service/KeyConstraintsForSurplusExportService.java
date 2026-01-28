package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing KeyConstraintsForSurplusExport-related operations.
 * Provides CRUD operations and pagination support for KeyConstraintsForSurplusExport entities.
 *
 * @since 1.0.0
 */

public interface KeyConstraintsForSurplusExportService {

    /**
     * Retrieves all KeyConstraintsForSurplusExports by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of KeyConstraintsForSurplusExportDTO in the same order as retrieved
     */
    Set<KeyConstraintsForSurplusExportDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new KeyConstraintsForSurplusExport entry in the system.
     *
     * @param KeyConstraintsForSurplusExport The KeyConstraintsForSurplusExport information to be saved
     * @return The saved KeyConstraintsForSurplusExport data
     * @since 1.0.0
     */
    KeyConstraintsForSurplusExportDTO save(KeyConstraintsForSurplusExportDTO KeyConstraintsForSurplusExport);

    /**
     * Updates an existing KeyConstraintsForSurplusExport entry.
     *
     * @param KeyConstraintsForSurplusExport The KeyConstraintsForSurplusExport information to be updated
     * @return The updated KeyConstraintsForSurplusExport data
     * @since 1.0.0
     */
    KeyConstraintsForSurplusExportDTO update(KeyConstraintsForSurplusExportDTO KeyConstraintsForSurplusExport);

    /**
     * Retrieves a paginated list of KeyConstraintsForSurplusExports.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of KeyConstraintsForSurplusExports
     * @since 1.0.0
     */
    PageDTO<KeyConstraintsForSurplusExportDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a KeyConstraintsForSurplusExport by their ID with a specified reason.
     *
     * @param id     The ID of the KeyConstraintsForSurplusExport to delete
     * @param reason The reason for deletion
     * @return KeyConstraintsForSurplusExportDTO
     */
    KeyConstraintsForSurplusExportDTO deleteById(Long id, String reason);

    /**
     * Retrieves a KeyConstraintsForSurplusExport by their ID.
     *
     * @param id The ID of the KeyConstraintsForSurplusExport to retrieve
     * @return The KeyConstraintsForSurplusExport with the specified ID
     * @since 1.0.0
     */
    KeyConstraintsForSurplusExportDTO getById(Long id);




}
