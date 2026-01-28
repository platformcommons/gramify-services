package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HHEconomicAspiration-related operations in the system.
 * This interface provides methods for CRUD operations on HHEconomicAspiration data,
 * including creation, retrieval, update, and deletion of HHEconomicAspiration records.
 * It serves as the primary entry point for HHEconomicAspiration management functionality.
 */
public interface HHEconomicAspirationFacade {

    /**
     * Retrieves all HHEconomicAspirations by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHEconomicAspirationDTO
     */
    Set<HHEconomicAspirationDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHEconomicAspiration record in the system.
     *
     * @param HHEconomicAspirationDTO The HHEconomicAspiration data transfer object containing the information to be saved
     * @return The saved HHEconomicAspiration data with generated identifiers and any system-modified fields
     */
    HHEconomicAspirationDTO save(HHEconomicAspirationDTO HHEconomicAspirationDTO);

    /**
     * Updates an existing HHEconomicAspiration record in the system.
     *
     * @param HHEconomicAspirationDTO The HHEconomicAspiration data transfer object containing the updated information
     * @return The updated HHEconomicAspiration data as confirmed by the system
     */
    HHEconomicAspirationDTO update(HHEconomicAspirationDTO HHEconomicAspirationDTO);

    /**
     * Retrieves a paginated list of all HHEconomicAspirations in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HHEconomicAspiration records
     */
    PageDTO<HHEconomicAspirationDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HHEconomicAspiration record from the system.
     *
     * @param id     The unique identifier of the HHEconomicAspiration to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HHEconomicAspiration by their unique identifier.
     *
     * @param id The unique identifier of the HHEconomicAspiration to retrieve
     * @return The HHEconomicAspiration data transfer object containing the requested information
     */
    HHEconomicAspirationDTO getById(Long id);



}
