package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing Consumption-related operations.
 * Provides CRUD operations and pagination support for Consumption entities.
 *
 * @since 1.0.0
 */

public interface ConsumptionService {

    /**
     * Retrieves all Consumptions by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ConsumptionDTO in the same order as retrieved
     */
    Set<ConsumptionDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new Consumption entry in the system.
     *
     * @param Consumption The Consumption information to be saved
     * @return The saved Consumption data
     * @since 1.0.0
     */
    ConsumptionDTO save(ConsumptionDTO Consumption);

    /**
     * Updates an existing Consumption entry.
     *
     * @param Consumption The Consumption information to be updated
     * @return The updated Consumption data
     * @since 1.0.0
     */
    ConsumptionDTO update(ConsumptionDTO Consumption);

    /**
     * Retrieves a paginated list of Consumptions.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of Consumptions
     * @since 1.0.0
     */
    PageDTO<ConsumptionDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a Consumption by their ID with a specified reason.
     *
     * @param id     The ID of the Consumption to delete
     * @param reason The reason for deletion
     * @return ConsumptionDTO
     */
    ConsumptionDTO deleteById(Long id, String reason);

    /**
     * Retrieves a Consumption by their ID.
     *
     * @param id The ID of the Consumption to retrieve
     * @return The Consumption with the specified ID
     * @since 1.0.0
     */
    ConsumptionDTO getById(Long id);




}
