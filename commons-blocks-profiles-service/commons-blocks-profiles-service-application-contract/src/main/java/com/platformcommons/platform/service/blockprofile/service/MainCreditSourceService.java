package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing MainCreditSource-related operations.
 * Provides CRUD operations and pagination support for MainCreditSource entities.
 *
 * @since 1.0.0
 */

public interface MainCreditSourceService {

    /**
     * Retrieves all MainCreditSources by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MainCreditSourceDTO in the same order as retrieved
     */
    Set<MainCreditSourceDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new MainCreditSource entry in the system.
     *
     * @param MainCreditSource The MainCreditSource information to be saved
     * @return The saved MainCreditSource data
     * @since 1.0.0
     */
    MainCreditSourceDTO save(MainCreditSourceDTO MainCreditSource);

    /**
     * Updates an existing MainCreditSource entry.
     *
     * @param MainCreditSource The MainCreditSource information to be updated
     * @return The updated MainCreditSource data
     * @since 1.0.0
     */
    MainCreditSourceDTO update(MainCreditSourceDTO MainCreditSource);

    /**
     * Retrieves a paginated list of MainCreditSources.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of MainCreditSources
     * @since 1.0.0
     */
    PageDTO<MainCreditSourceDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a MainCreditSource by their ID with a specified reason.
     *
     * @param id     The ID of the MainCreditSource to delete
     * @param reason The reason for deletion
     * @return MainCreditSourceDTO
     */
    MainCreditSourceDTO deleteById(Long id, String reason);

    /**
     * Retrieves a MainCreditSource by their ID.
     *
     * @param id The ID of the MainCreditSource to retrieve
     * @return The MainCreditSource with the specified ID
     * @since 1.0.0
     */
    MainCreditSourceDTO getById(Long id);




}
