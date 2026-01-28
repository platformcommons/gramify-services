package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing MainSurplusMarketsOutsideBlock-related operations in the system.
 * This interface provides methods for CRUD operations on MainSurplusMarketsOutsideBlock data,
 * including creation, retrieval, update, and deletion of MainSurplusMarketsOutsideBlock records.
 * It serves as the primary entry point for MainSurplusMarketsOutsideBlock management functionality.
 */
public interface MainSurplusMarketsOutsideBlockFacade {

    /**
     * Retrieves all MainSurplusMarketsOutsideBlocks by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MainSurplusMarketsOutsideBlockDTO
     */
    Set<MainSurplusMarketsOutsideBlockDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new MainSurplusMarketsOutsideBlock record in the system.
     *
     * @param MainSurplusMarketsOutsideBlockDTO The MainSurplusMarketsOutsideBlock data transfer object containing the information to be saved
     * @return The saved MainSurplusMarketsOutsideBlock data with generated identifiers and any system-modified fields
     */
    MainSurplusMarketsOutsideBlockDTO save(MainSurplusMarketsOutsideBlockDTO MainSurplusMarketsOutsideBlockDTO);

    /**
     * Updates an existing MainSurplusMarketsOutsideBlock record in the system.
     *
     * @param MainSurplusMarketsOutsideBlockDTO The MainSurplusMarketsOutsideBlock data transfer object containing the updated information
     * @return The updated MainSurplusMarketsOutsideBlock data as confirmed by the system
     */
    MainSurplusMarketsOutsideBlockDTO update(MainSurplusMarketsOutsideBlockDTO MainSurplusMarketsOutsideBlockDTO);

    /**
     * Retrieves a paginated list of all MainSurplusMarketsOutsideBlocks in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested MainSurplusMarketsOutsideBlock records
     */
    PageDTO<MainSurplusMarketsOutsideBlockDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a MainSurplusMarketsOutsideBlock record from the system.
     *
     * @param id     The unique identifier of the MainSurplusMarketsOutsideBlock to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific MainSurplusMarketsOutsideBlock by their unique identifier.
     *
     * @param id The unique identifier of the MainSurplusMarketsOutsideBlock to retrieve
     * @return The MainSurplusMarketsOutsideBlock data transfer object containing the requested information
     */
    MainSurplusMarketsOutsideBlockDTO getById(Long id);



}
