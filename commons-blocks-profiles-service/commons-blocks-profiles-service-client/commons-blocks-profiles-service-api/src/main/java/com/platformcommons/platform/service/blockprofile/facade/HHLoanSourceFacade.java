package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HHLoanSource-related operations in the system.
 * This interface provides methods for CRUD operations on HHLoanSource data,
 * including creation, retrieval, update, and deletion of HHLoanSource records.
 * It serves as the primary entry point for HHLoanSource management functionality.
 */
public interface HHLoanSourceFacade {

    /**
     * Retrieves all HHLoanSources by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHLoanSourceDTO
     */
    Set<HHLoanSourceDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHLoanSource record in the system.
     *
     * @param HHLoanSourceDTO The HHLoanSource data transfer object containing the information to be saved
     * @return The saved HHLoanSource data with generated identifiers and any system-modified fields
     */
    HHLoanSourceDTO save(HHLoanSourceDTO HHLoanSourceDTO);

    /**
     * Updates an existing HHLoanSource record in the system.
     *
     * @param HHLoanSourceDTO The HHLoanSource data transfer object containing the updated information
     * @return The updated HHLoanSource data as confirmed by the system
     */
    HHLoanSourceDTO update(HHLoanSourceDTO HHLoanSourceDTO);

    /**
     * Retrieves a paginated list of all HHLoanSources in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HHLoanSource records
     */
    PageDTO<HHLoanSourceDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HHLoanSource record from the system.
     *
     * @param id     The unique identifier of the HHLoanSource to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HHLoanSource by their unique identifier.
     *
     * @param id The unique identifier of the HHLoanSource to retrieve
     * @return The HHLoanSource data transfer object containing the requested information
     */
    HHLoanSourceDTO getById(Long id);



}
