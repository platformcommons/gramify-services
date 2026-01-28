package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing Consumption-related operations in the system.
 * This interface provides methods for CRUD operations on Consumption data,
 * including creation, retrieval, update, and deletion of Consumption records.
 * It serves as the primary entry point for Consumption management functionality.
 */
public interface ConsumptionFacade {

    /**
     * Retrieves all Consumptions by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ConsumptionDTO
     */
    Set<ConsumptionDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new Consumption record in the system.
     *
     * @param ConsumptionDTO The Consumption data transfer object containing the information to be saved
     * @return The saved Consumption data with generated identifiers and any system-modified fields
     */
    ConsumptionDTO save(ConsumptionDTO ConsumptionDTO);

    /**
     * Updates an existing Consumption record in the system.
     *
     * @param ConsumptionDTO The Consumption data transfer object containing the updated information
     * @return The updated Consumption data as confirmed by the system
     */
    ConsumptionDTO update(ConsumptionDTO ConsumptionDTO);

    /**
     * Retrieves a paginated list of all Consumptions in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested Consumption records
     */
    PageDTO<ConsumptionDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a Consumption record from the system.
     *
     * @param id     The unique identifier of the Consumption to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific Consumption by their unique identifier.
     *
     * @param id The unique identifier of the Consumption to retrieve
     * @return The Consumption data transfer object containing the requested information
     */
    ConsumptionDTO getById(Long id);



}
