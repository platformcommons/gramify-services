package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing PowerCutSeason-related operations in the system.
 * This interface provides methods for CRUD operations on PowerCutSeason data,
 * including creation, retrieval, update, and deletion of PowerCutSeason records.
 * It serves as the primary entry point for PowerCutSeason management functionality.
 */
public interface PowerCutSeasonFacade {

    /**
     * Retrieves all PowerCutSeasons by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of PowerCutSeasonDTO
     */
    Set<PowerCutSeasonDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new PowerCutSeason record in the system.
     *
     * @param PowerCutSeasonDTO The PowerCutSeason data transfer object containing the information to be saved
     * @return The saved PowerCutSeason data with generated identifiers and any system-modified fields
     */
    PowerCutSeasonDTO save(PowerCutSeasonDTO PowerCutSeasonDTO);

    /**
     * Updates an existing PowerCutSeason record in the system.
     *
     * @param PowerCutSeasonDTO The PowerCutSeason data transfer object containing the updated information
     * @return The updated PowerCutSeason data as confirmed by the system
     */
    PowerCutSeasonDTO update(PowerCutSeasonDTO PowerCutSeasonDTO);

    /**
     * Retrieves a paginated list of all PowerCutSeasons in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested PowerCutSeason records
     */
    PageDTO<PowerCutSeasonDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a PowerCutSeason record from the system.
     *
     * @param id     The unique identifier of the PowerCutSeason to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific PowerCutSeason by their unique identifier.
     *
     * @param id The unique identifier of the PowerCutSeason to retrieve
     * @return The PowerCutSeason data transfer object containing the requested information
     */
    PowerCutSeasonDTO getById(Long id);



}
