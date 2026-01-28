package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing MainSkilledTradesPresent-related operations in the system.
 * This interface provides methods for CRUD operations on MainSkilledTradesPresent data,
 * including creation, retrieval, update, and deletion of MainSkilledTradesPresent records.
 * It serves as the primary entry point for MainSkilledTradesPresent management functionality.
 */
public interface MainSkilledTradesPresentFacade {

    /**
     * Retrieves all MainSkilledTradesPresents by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MainSkilledTradesPresentDTO
     */
    Set<MainSkilledTradesPresentDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new MainSkilledTradesPresent record in the system.
     *
     * @param MainSkilledTradesPresentDTO The MainSkilledTradesPresent data transfer object containing the information to be saved
     * @return The saved MainSkilledTradesPresent data with generated identifiers and any system-modified fields
     */
    MainSkilledTradesPresentDTO save(MainSkilledTradesPresentDTO MainSkilledTradesPresentDTO);

    /**
     * Updates an existing MainSkilledTradesPresent record in the system.
     *
     * @param MainSkilledTradesPresentDTO The MainSkilledTradesPresent data transfer object containing the updated information
     * @return The updated MainSkilledTradesPresent data as confirmed by the system
     */
    MainSkilledTradesPresentDTO update(MainSkilledTradesPresentDTO MainSkilledTradesPresentDTO);

    /**
     * Retrieves a paginated list of all MainSkilledTradesPresents in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested MainSkilledTradesPresent records
     */
    PageDTO<MainSkilledTradesPresentDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a MainSkilledTradesPresent record from the system.
     *
     * @param id     The unique identifier of the MainSkilledTradesPresent to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific MainSkilledTradesPresent by their unique identifier.
     *
     * @param id The unique identifier of the MainSkilledTradesPresent to retrieve
     * @return The MainSkilledTradesPresent data transfer object containing the requested information
     */
    MainSkilledTradesPresentDTO getById(Long id);



}
