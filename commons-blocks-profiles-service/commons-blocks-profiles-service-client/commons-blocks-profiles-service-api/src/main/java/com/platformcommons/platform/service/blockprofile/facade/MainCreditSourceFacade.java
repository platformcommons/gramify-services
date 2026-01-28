package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing MainCreditSource-related operations in the system.
 * This interface provides methods for CRUD operations on MainCreditSource data,
 * including creation, retrieval, update, and deletion of MainCreditSource records.
 * It serves as the primary entry point for MainCreditSource management functionality.
 */
public interface MainCreditSourceFacade {

    /**
     * Retrieves all MainCreditSources by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MainCreditSourceDTO
     */
    Set<MainCreditSourceDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new MainCreditSource record in the system.
     *
     * @param MainCreditSourceDTO The MainCreditSource data transfer object containing the information to be saved
     * @return The saved MainCreditSource data with generated identifiers and any system-modified fields
     */
    MainCreditSourceDTO save(MainCreditSourceDTO MainCreditSourceDTO);

    /**
     * Updates an existing MainCreditSource record in the system.
     *
     * @param MainCreditSourceDTO The MainCreditSource data transfer object containing the updated information
     * @return The updated MainCreditSource data as confirmed by the system
     */
    MainCreditSourceDTO update(MainCreditSourceDTO MainCreditSourceDTO);

    /**
     * Retrieves a paginated list of all MainCreditSources in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested MainCreditSource records
     */
    PageDTO<MainCreditSourceDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a MainCreditSource record from the system.
     *
     * @param id     The unique identifier of the MainCreditSource to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific MainCreditSource by their unique identifier.
     *
     * @param id The unique identifier of the MainCreditSource to retrieve
     * @return The MainCreditSource data transfer object containing the requested information
     */
    MainCreditSourceDTO getById(Long id);



}
