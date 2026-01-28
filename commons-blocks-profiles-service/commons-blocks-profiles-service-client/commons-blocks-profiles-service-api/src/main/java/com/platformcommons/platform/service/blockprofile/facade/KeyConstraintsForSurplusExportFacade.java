package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing KeyConstraintsForSurplusExport-related operations in the system.
 * This interface provides methods for CRUD operations on KeyConstraintsForSurplusExport data,
 * including creation, retrieval, update, and deletion of KeyConstraintsForSurplusExport records.
 * It serves as the primary entry point for KeyConstraintsForSurplusExport management functionality.
 */
public interface KeyConstraintsForSurplusExportFacade {

    /**
     * Retrieves all KeyConstraintsForSurplusExports by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of KeyConstraintsForSurplusExportDTO
     */
    Set<KeyConstraintsForSurplusExportDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new KeyConstraintsForSurplusExport record in the system.
     *
     * @param KeyConstraintsForSurplusExportDTO The KeyConstraintsForSurplusExport data transfer object containing the information to be saved
     * @return The saved KeyConstraintsForSurplusExport data with generated identifiers and any system-modified fields
     */
    KeyConstraintsForSurplusExportDTO save(KeyConstraintsForSurplusExportDTO KeyConstraintsForSurplusExportDTO);

    /**
     * Updates an existing KeyConstraintsForSurplusExport record in the system.
     *
     * @param KeyConstraintsForSurplusExportDTO The KeyConstraintsForSurplusExport data transfer object containing the updated information
     * @return The updated KeyConstraintsForSurplusExport data as confirmed by the system
     */
    KeyConstraintsForSurplusExportDTO update(KeyConstraintsForSurplusExportDTO KeyConstraintsForSurplusExportDTO);

    /**
     * Retrieves a paginated list of all KeyConstraintsForSurplusExports in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested KeyConstraintsForSurplusExport records
     */
    PageDTO<KeyConstraintsForSurplusExportDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a KeyConstraintsForSurplusExport record from the system.
     *
     * @param id     The unique identifier of the KeyConstraintsForSurplusExport to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific KeyConstraintsForSurplusExport by their unique identifier.
     *
     * @param id The unique identifier of the KeyConstraintsForSurplusExport to retrieve
     * @return The KeyConstraintsForSurplusExport data transfer object containing the requested information
     */
    KeyConstraintsForSurplusExportDTO getById(Long id);



}
