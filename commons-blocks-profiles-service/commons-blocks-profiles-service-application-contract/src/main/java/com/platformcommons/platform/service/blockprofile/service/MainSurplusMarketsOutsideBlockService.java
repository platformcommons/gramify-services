package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing MainSurplusMarketsOutsideBlock-related operations.
 * Provides CRUD operations and pagination support for MainSurplusMarketsOutsideBlock entities.
 *
 * @since 1.0.0
 */

public interface MainSurplusMarketsOutsideBlockService {

    /**
     * Retrieves all MainSurplusMarketsOutsideBlocks by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MainSurplusMarketsOutsideBlockDTO in the same order as retrieved
     */
    Set<MainSurplusMarketsOutsideBlockDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new MainSurplusMarketsOutsideBlock entry in the system.
     *
     * @param MainSurplusMarketsOutsideBlock The MainSurplusMarketsOutsideBlock information to be saved
     * @return The saved MainSurplusMarketsOutsideBlock data
     * @since 1.0.0
     */
    MainSurplusMarketsOutsideBlockDTO save(MainSurplusMarketsOutsideBlockDTO MainSurplusMarketsOutsideBlock);

    /**
     * Updates an existing MainSurplusMarketsOutsideBlock entry.
     *
     * @param MainSurplusMarketsOutsideBlock The MainSurplusMarketsOutsideBlock information to be updated
     * @return The updated MainSurplusMarketsOutsideBlock data
     * @since 1.0.0
     */
    MainSurplusMarketsOutsideBlockDTO update(MainSurplusMarketsOutsideBlockDTO MainSurplusMarketsOutsideBlock);

    /**
     * Retrieves a paginated list of MainSurplusMarketsOutsideBlocks.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of MainSurplusMarketsOutsideBlocks
     * @since 1.0.0
     */
    PageDTO<MainSurplusMarketsOutsideBlockDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a MainSurplusMarketsOutsideBlock by their ID with a specified reason.
     *
     * @param id     The ID of the MainSurplusMarketsOutsideBlock to delete
     * @param reason The reason for deletion
     * @return MainSurplusMarketsOutsideBlockDTO
     */
    MainSurplusMarketsOutsideBlockDTO deleteById(Long id, String reason);

    /**
     * Retrieves a MainSurplusMarketsOutsideBlock by their ID.
     *
     * @param id The ID of the MainSurplusMarketsOutsideBlock to retrieve
     * @return The MainSurplusMarketsOutsideBlock with the specified ID
     * @since 1.0.0
     */
    MainSurplusMarketsOutsideBlockDTO getById(Long id);




}
