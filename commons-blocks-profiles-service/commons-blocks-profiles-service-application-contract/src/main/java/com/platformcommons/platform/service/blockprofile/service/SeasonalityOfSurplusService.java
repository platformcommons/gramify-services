package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing SeasonalityOfSurplus-related operations.
 * Provides CRUD operations and pagination support for SeasonalityOfSurplus entities.
 *
 * @since 1.0.0
 */

public interface SeasonalityOfSurplusService {

    /**
     * Retrieves all SeasonalityOfSurpluss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SeasonalityOfSurplusDTO in the same order as retrieved
     */
    Set<SeasonalityOfSurplusDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new SeasonalityOfSurplus entry in the system.
     *
     * @param SeasonalityOfSurplus The SeasonalityOfSurplus information to be saved
     * @return The saved SeasonalityOfSurplus data
     * @since 1.0.0
     */
    SeasonalityOfSurplusDTO save(SeasonalityOfSurplusDTO SeasonalityOfSurplus);

    /**
     * Updates an existing SeasonalityOfSurplus entry.
     *
     * @param SeasonalityOfSurplus The SeasonalityOfSurplus information to be updated
     * @return The updated SeasonalityOfSurplus data
     * @since 1.0.0
     */
    SeasonalityOfSurplusDTO update(SeasonalityOfSurplusDTO SeasonalityOfSurplus);

    /**
     * Retrieves a paginated list of SeasonalityOfSurpluss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of SeasonalityOfSurpluss
     * @since 1.0.0
     */
    PageDTO<SeasonalityOfSurplusDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a SeasonalityOfSurplus by their ID with a specified reason.
     *
     * @param id     The ID of the SeasonalityOfSurplus to delete
     * @param reason The reason for deletion
     * @return SeasonalityOfSurplusDTO
     */
    SeasonalityOfSurplusDTO deleteById(Long id, String reason);

    /**
     * Retrieves a SeasonalityOfSurplus by their ID.
     *
     * @param id The ID of the SeasonalityOfSurplus to retrieve
     * @return The SeasonalityOfSurplus with the specified ID
     * @since 1.0.0
     */
    SeasonalityOfSurplusDTO getById(Long id);




}
