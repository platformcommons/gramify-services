package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing SeasonalityOfSurplus-related operations in the system.
 * This interface provides methods for CRUD operations on SeasonalityOfSurplus data,
 * including creation, retrieval, update, and deletion of SeasonalityOfSurplus records.
 * It serves as the primary entry point for SeasonalityOfSurplus management functionality.
 */
public interface SeasonalityOfSurplusFacade {

    /**
     * Retrieves all SeasonalityOfSurpluss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SeasonalityOfSurplusDTO
     */
    Set<SeasonalityOfSurplusDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new SeasonalityOfSurplus record in the system.
     *
     * @param SeasonalityOfSurplusDTO The SeasonalityOfSurplus data transfer object containing the information to be saved
     * @return The saved SeasonalityOfSurplus data with generated identifiers and any system-modified fields
     */
    SeasonalityOfSurplusDTO save(SeasonalityOfSurplusDTO SeasonalityOfSurplusDTO);

    /**
     * Updates an existing SeasonalityOfSurplus record in the system.
     *
     * @param SeasonalityOfSurplusDTO The SeasonalityOfSurplus data transfer object containing the updated information
     * @return The updated SeasonalityOfSurplus data as confirmed by the system
     */
    SeasonalityOfSurplusDTO update(SeasonalityOfSurplusDTO SeasonalityOfSurplusDTO);

    /**
     * Retrieves a paginated list of all SeasonalityOfSurpluss in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested SeasonalityOfSurplus records
     */
    PageDTO<SeasonalityOfSurplusDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a SeasonalityOfSurplus record from the system.
     *
     * @param id     The unique identifier of the SeasonalityOfSurplus to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific SeasonalityOfSurplus by their unique identifier.
     *
     * @param id The unique identifier of the SeasonalityOfSurplus to retrieve
     * @return The SeasonalityOfSurplus data transfer object containing the requested information
     */
    SeasonalityOfSurplusDTO getById(Long id);



}
