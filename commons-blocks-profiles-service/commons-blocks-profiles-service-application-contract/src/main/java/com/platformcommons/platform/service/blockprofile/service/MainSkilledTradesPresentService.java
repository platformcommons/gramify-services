package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing MainSkilledTradesPresent-related operations.
 * Provides CRUD operations and pagination support for MainSkilledTradesPresent entities.
 *
 * @since 1.0.0
 */

public interface MainSkilledTradesPresentService {

    /**
     * Retrieves all MainSkilledTradesPresents by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MainSkilledTradesPresentDTO in the same order as retrieved
     */
    Set<MainSkilledTradesPresentDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new MainSkilledTradesPresent entry in the system.
     *
     * @param MainSkilledTradesPresent The MainSkilledTradesPresent information to be saved
     * @return The saved MainSkilledTradesPresent data
     * @since 1.0.0
     */
    MainSkilledTradesPresentDTO save(MainSkilledTradesPresentDTO MainSkilledTradesPresent);

    /**
     * Updates an existing MainSkilledTradesPresent entry.
     *
     * @param MainSkilledTradesPresent The MainSkilledTradesPresent information to be updated
     * @return The updated MainSkilledTradesPresent data
     * @since 1.0.0
     */
    MainSkilledTradesPresentDTO update(MainSkilledTradesPresentDTO MainSkilledTradesPresent);

    /**
     * Retrieves a paginated list of MainSkilledTradesPresents.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of MainSkilledTradesPresents
     * @since 1.0.0
     */
    PageDTO<MainSkilledTradesPresentDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a MainSkilledTradesPresent by their ID with a specified reason.
     *
     * @param id     The ID of the MainSkilledTradesPresent to delete
     * @param reason The reason for deletion
     * @return MainSkilledTradesPresentDTO
     */
    MainSkilledTradesPresentDTO deleteById(Long id, String reason);

    /**
     * Retrieves a MainSkilledTradesPresent by their ID.
     *
     * @param id The ID of the MainSkilledTradesPresent to retrieve
     * @return The MainSkilledTradesPresent with the specified ID
     * @since 1.0.0
     */
    MainSkilledTradesPresentDTO getById(Long id);




}
