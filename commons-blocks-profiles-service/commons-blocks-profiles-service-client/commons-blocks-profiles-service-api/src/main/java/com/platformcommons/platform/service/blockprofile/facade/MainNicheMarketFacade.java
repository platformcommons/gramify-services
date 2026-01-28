package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing MainNicheMarket-related operations in the system.
 * This interface provides methods for CRUD operations on MainNicheMarket data,
 * including creation, retrieval, update, and deletion of MainNicheMarket records.
 * It serves as the primary entry point for MainNicheMarket management functionality.
 */
public interface MainNicheMarketFacade {

    /**
     * Retrieves all MainNicheMarkets by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MainNicheMarketDTO
     */
    Set<MainNicheMarketDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new MainNicheMarket record in the system.
     *
     * @param MainNicheMarketDTO The MainNicheMarket data transfer object containing the information to be saved
     * @return The saved MainNicheMarket data with generated identifiers and any system-modified fields
     */
    MainNicheMarketDTO save(MainNicheMarketDTO MainNicheMarketDTO);

    /**
     * Updates an existing MainNicheMarket record in the system.
     *
     * @param MainNicheMarketDTO The MainNicheMarket data transfer object containing the updated information
     * @return The updated MainNicheMarket data as confirmed by the system
     */
    MainNicheMarketDTO update(MainNicheMarketDTO MainNicheMarketDTO);

    /**
     * Retrieves a paginated list of all MainNicheMarkets in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested MainNicheMarket records
     */
    PageDTO<MainNicheMarketDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a MainNicheMarket record from the system.
     *
     * @param id     The unique identifier of the MainNicheMarket to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific MainNicheMarket by their unique identifier.
     *
     * @param id The unique identifier of the MainNicheMarket to retrieve
     * @return The MainNicheMarket data transfer object containing the requested information
     */
    MainNicheMarketDTO getById(Long id);



}
