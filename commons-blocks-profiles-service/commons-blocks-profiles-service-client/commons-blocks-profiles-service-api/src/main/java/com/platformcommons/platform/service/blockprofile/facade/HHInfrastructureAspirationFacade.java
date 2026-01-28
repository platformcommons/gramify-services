package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HHInfrastructureAspiration-related operations in the system.
 * This interface provides methods for CRUD operations on HHInfrastructureAspiration data,
 * including creation, retrieval, update, and deletion of HHInfrastructureAspiration records.
 * It serves as the primary entry point for HHInfrastructureAspiration management functionality.
 */
public interface HHInfrastructureAspirationFacade {

    /**
     * Retrieves all HHInfrastructureAspirations by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHInfrastructureAspirationDTO
     */
    Set<HHInfrastructureAspirationDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHInfrastructureAspiration record in the system.
     *
     * @param HHInfrastructureAspirationDTO The HHInfrastructureAspiration data transfer object containing the information to be saved
     * @return The saved HHInfrastructureAspiration data with generated identifiers and any system-modified fields
     */
    HHInfrastructureAspirationDTO save(HHInfrastructureAspirationDTO HHInfrastructureAspirationDTO);

    /**
     * Updates an existing HHInfrastructureAspiration record in the system.
     *
     * @param HHInfrastructureAspirationDTO The HHInfrastructureAspiration data transfer object containing the updated information
     * @return The updated HHInfrastructureAspiration data as confirmed by the system
     */
    HHInfrastructureAspirationDTO update(HHInfrastructureAspirationDTO HHInfrastructureAspirationDTO);

    /**
     * Retrieves a paginated list of all HHInfrastructureAspirations in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HHInfrastructureAspiration records
     */
    PageDTO<HHInfrastructureAspirationDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HHInfrastructureAspiration record from the system.
     *
     * @param id     The unique identifier of the HHInfrastructureAspiration to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HHInfrastructureAspiration by their unique identifier.
     *
     * @param id The unique identifier of the HHInfrastructureAspiration to retrieve
     * @return The HHInfrastructureAspiration data transfer object containing the requested information
     */
    HHInfrastructureAspirationDTO getById(Long id);


    /**
     * Adds a list of hHInfrastructureAspirationList to a HHInfrastructureAspiration identified by their ID.
     *
     * @param id               The ID of the HHInfrastructureAspiration to add hobbies to
     * @param hHInfrastructureAspirationList  to be added
     * @since 1.0.0
     */
    void addHHInfrastructureAspirationToHHInfrastructureAspiration(Long id, List<HHInfrastructureAspirationDTO> hHInfrastructureAspirationList);


}
