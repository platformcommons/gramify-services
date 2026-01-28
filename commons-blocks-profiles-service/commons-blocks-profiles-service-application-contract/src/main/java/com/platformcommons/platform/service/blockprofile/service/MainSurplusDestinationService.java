package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing MainSurplusDestination-related operations.
 * Provides CRUD operations and pagination support for MainSurplusDestination entities.
 *
 * @since 1.0.0
 */

public interface MainSurplusDestinationService {

    /**
     * Retrieves all MainSurplusDestinations by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MainSurplusDestinationDTO in the same order as retrieved
     */
    Set<MainSurplusDestinationDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new MainSurplusDestination entry in the system.
     *
     * @param MainSurplusDestination The MainSurplusDestination information to be saved
     * @return The saved MainSurplusDestination data
     * @since 1.0.0
     */
    MainSurplusDestinationDTO save(MainSurplusDestinationDTO MainSurplusDestination);

    /**
     * Updates an existing MainSurplusDestination entry.
     *
     * @param MainSurplusDestination The MainSurplusDestination information to be updated
     * @return The updated MainSurplusDestination data
     * @since 1.0.0
     */
    MainSurplusDestinationDTO update(MainSurplusDestinationDTO MainSurplusDestination);

    /**
     * Retrieves a paginated list of MainSurplusDestinations.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of MainSurplusDestinations
     * @since 1.0.0
     */
    PageDTO<MainSurplusDestinationDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a MainSurplusDestination by their ID with a specified reason.
     *
     * @param id     The ID of the MainSurplusDestination to delete
     * @param reason The reason for deletion
     * @return MainSurplusDestinationDTO
     */
    MainSurplusDestinationDTO deleteById(Long id, String reason);

    /**
     * Retrieves a MainSurplusDestination by their ID.
     *
     * @param id The ID of the MainSurplusDestination to retrieve
     * @return The MainSurplusDestination with the specified ID
     * @since 1.0.0
     */
    MainSurplusDestinationDTO getById(Long id);




}
