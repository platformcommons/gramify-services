package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing CommonConsumerGoodsPurchased-related operations.
 * Provides CRUD operations and pagination support for CommonConsumerGoodsPurchased entities.
 *
 * @since 1.0.0
 */

public interface CommonConsumerGoodsPurchasedService {

    /**
     * Retrieves all CommonConsumerGoodsPurchaseds by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CommonConsumerGoodsPurchasedDTO in the same order as retrieved
     */
    Set<CommonConsumerGoodsPurchasedDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new CommonConsumerGoodsPurchased entry in the system.
     *
     * @param CommonConsumerGoodsPurchased The CommonConsumerGoodsPurchased information to be saved
     * @return The saved CommonConsumerGoodsPurchased data
     * @since 1.0.0
     */
    CommonConsumerGoodsPurchasedDTO save(CommonConsumerGoodsPurchasedDTO CommonConsumerGoodsPurchased);

    /**
     * Updates an existing CommonConsumerGoodsPurchased entry.
     *
     * @param CommonConsumerGoodsPurchased The CommonConsumerGoodsPurchased information to be updated
     * @return The updated CommonConsumerGoodsPurchased data
     * @since 1.0.0
     */
    CommonConsumerGoodsPurchasedDTO update(CommonConsumerGoodsPurchasedDTO CommonConsumerGoodsPurchased);

    /**
     * Retrieves a paginated list of CommonConsumerGoodsPurchaseds.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of CommonConsumerGoodsPurchaseds
     * @since 1.0.0
     */
    PageDTO<CommonConsumerGoodsPurchasedDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a CommonConsumerGoodsPurchased by their ID with a specified reason.
     *
     * @param id     The ID of the CommonConsumerGoodsPurchased to delete
     * @param reason The reason for deletion
     * @return CommonConsumerGoodsPurchasedDTO
     */
    CommonConsumerGoodsPurchasedDTO deleteById(Long id, String reason);

    /**
     * Retrieves a CommonConsumerGoodsPurchased by their ID.
     *
     * @param id The ID of the CommonConsumerGoodsPurchased to retrieve
     * @return The CommonConsumerGoodsPurchased with the specified ID
     * @since 1.0.0
     */
    CommonConsumerGoodsPurchasedDTO getById(Long id);




}
