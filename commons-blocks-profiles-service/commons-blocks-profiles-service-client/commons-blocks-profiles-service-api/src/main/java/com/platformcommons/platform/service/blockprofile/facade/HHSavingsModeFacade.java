package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HHSavingsMode-related operations in the system.
 * This interface provides methods for CRUD operations on HHSavingsMode data,
 * including creation, retrieval, update, and deletion of HHSavingsMode records.
 * It serves as the primary entry point for HHSavingsMode management functionality.
 */
public interface HHSavingsModeFacade {

    /**
     * Retrieves all HHSavingsModes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHSavingsModeDTO
     */
    Set<HHSavingsModeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHSavingsMode record in the system.
     *
     * @param HHSavingsModeDTO The HHSavingsMode data transfer object containing the information to be saved
     * @return The saved HHSavingsMode data with generated identifiers and any system-modified fields
     */
    HHSavingsModeDTO save(HHSavingsModeDTO HHSavingsModeDTO);

    /**
     * Updates an existing HHSavingsMode record in the system.
     *
     * @param HHSavingsModeDTO The HHSavingsMode data transfer object containing the updated information
     * @return The updated HHSavingsMode data as confirmed by the system
     */
    HHSavingsModeDTO update(HHSavingsModeDTO HHSavingsModeDTO);

    /**
     * Retrieves a paginated list of all HHSavingsModes in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HHSavingsMode records
     */
    PageDTO<HHSavingsModeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HHSavingsMode record from the system.
     *
     * @param id     The unique identifier of the HHSavingsMode to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HHSavingsMode by their unique identifier.
     *
     * @param id The unique identifier of the HHSavingsMode to retrieve
     * @return The HHSavingsMode data transfer object containing the requested information
     */
    HHSavingsModeDTO getById(Long id);



}
