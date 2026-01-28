package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HHSkilledWorkerType-related operations in the system.
 * This interface provides methods for CRUD operations on HHSkilledWorkerType data,
 * including creation, retrieval, update, and deletion of HHSkilledWorkerType records.
 * It serves as the primary entry point for HHSkilledWorkerType management functionality.
 */
public interface HHSkilledWorkerTypeFacade {

    /**
     * Retrieves all HHSkilledWorkerTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHSkilledWorkerTypeDTO
     */
    Set<HHSkilledWorkerTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHSkilledWorkerType record in the system.
     *
     * @param HHSkilledWorkerTypeDTO The HHSkilledWorkerType data transfer object containing the information to be saved
     * @return The saved HHSkilledWorkerType data with generated identifiers and any system-modified fields
     */
    HHSkilledWorkerTypeDTO save(HHSkilledWorkerTypeDTO HHSkilledWorkerTypeDTO);

    /**
     * Updates an existing HHSkilledWorkerType record in the system.
     *
     * @param HHSkilledWorkerTypeDTO The HHSkilledWorkerType data transfer object containing the updated information
     * @return The updated HHSkilledWorkerType data as confirmed by the system
     */
    HHSkilledWorkerTypeDTO update(HHSkilledWorkerTypeDTO HHSkilledWorkerTypeDTO);

    /**
     * Retrieves a paginated list of all HHSkilledWorkerTypes in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HHSkilledWorkerType records
     */
    PageDTO<HHSkilledWorkerTypeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HHSkilledWorkerType record from the system.
     *
     * @param id     The unique identifier of the HHSkilledWorkerType to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HHSkilledWorkerType by their unique identifier.
     *
     * @param id The unique identifier of the HHSkilledWorkerType to retrieve
     * @return The HHSkilledWorkerType data transfer object containing the requested information
     */
    HHSkilledWorkerTypeDTO getById(Long id);



}
