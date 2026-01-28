package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing CommonConsumerGoodsPurchased-related operations in the system.
 * This interface provides methods for CRUD operations on CommonConsumerGoodsPurchased data,
 * including creation, retrieval, update, and deletion of CommonConsumerGoodsPurchased records.
 * It serves as the primary entry point for CommonConsumerGoodsPurchased management functionality.
 */
public interface CommonConsumerGoodsPurchasedFacade {

    /**
     * Retrieves all CommonConsumerGoodsPurchaseds by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CommonConsumerGoodsPurchasedDTO
     */
    Set<CommonConsumerGoodsPurchasedDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new CommonConsumerGoodsPurchased record in the system.
     *
     * @param CommonConsumerGoodsPurchasedDTO The CommonConsumerGoodsPurchased data transfer object containing the information to be saved
     * @return The saved CommonConsumerGoodsPurchased data with generated identifiers and any system-modified fields
     */
    CommonConsumerGoodsPurchasedDTO save(CommonConsumerGoodsPurchasedDTO CommonConsumerGoodsPurchasedDTO);

    /**
     * Updates an existing CommonConsumerGoodsPurchased record in the system.
     *
     * @param CommonConsumerGoodsPurchasedDTO The CommonConsumerGoodsPurchased data transfer object containing the updated information
     * @return The updated CommonConsumerGoodsPurchased data as confirmed by the system
     */
    CommonConsumerGoodsPurchasedDTO update(CommonConsumerGoodsPurchasedDTO CommonConsumerGoodsPurchasedDTO);

    /**
     * Retrieves a paginated list of all CommonConsumerGoodsPurchaseds in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested CommonConsumerGoodsPurchased records
     */
    PageDTO<CommonConsumerGoodsPurchasedDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a CommonConsumerGoodsPurchased record from the system.
     *
     * @param id     The unique identifier of the CommonConsumerGoodsPurchased to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific CommonConsumerGoodsPurchased by their unique identifier.
     *
     * @param id The unique identifier of the CommonConsumerGoodsPurchased to retrieve
     * @return The CommonConsumerGoodsPurchased data transfer object containing the requested information
     */
    CommonConsumerGoodsPurchasedDTO getById(Long id);



}
