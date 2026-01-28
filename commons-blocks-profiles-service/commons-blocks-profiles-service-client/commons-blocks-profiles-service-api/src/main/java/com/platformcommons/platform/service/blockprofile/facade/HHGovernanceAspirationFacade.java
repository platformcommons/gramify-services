package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HHGovernanceAspiration-related operations in the system.
 * This interface provides methods for CRUD operations on HHGovernanceAspiration data,
 * including creation, retrieval, update, and deletion of HHGovernanceAspiration records.
 * It serves as the primary entry point for HHGovernanceAspiration management functionality.
 */
public interface HHGovernanceAspirationFacade {

    /**
     * Retrieves all HHGovernanceAspirations by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHGovernanceAspirationDTO
     */
    Set<HHGovernanceAspirationDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHGovernanceAspiration record in the system.
     *
     * @param HHGovernanceAspirationDTO The HHGovernanceAspiration data transfer object containing the information to be saved
     * @return The saved HHGovernanceAspiration data with generated identifiers and any system-modified fields
     */
    HHGovernanceAspirationDTO save(HHGovernanceAspirationDTO HHGovernanceAspirationDTO);

    /**
     * Updates an existing HHGovernanceAspiration record in the system.
     *
     * @param HHGovernanceAspirationDTO The HHGovernanceAspiration data transfer object containing the updated information
     * @return The updated HHGovernanceAspiration data as confirmed by the system
     */
    HHGovernanceAspirationDTO update(HHGovernanceAspirationDTO HHGovernanceAspirationDTO);

    /**
     * Retrieves a paginated list of all HHGovernanceAspirations in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HHGovernanceAspiration records
     */
    PageDTO<HHGovernanceAspirationDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HHGovernanceAspiration record from the system.
     *
     * @param id     The unique identifier of the HHGovernanceAspiration to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HHGovernanceAspiration by their unique identifier.
     *
     * @param id The unique identifier of the HHGovernanceAspiration to retrieve
     * @return The HHGovernanceAspiration data transfer object containing the requested information
     */
    HHGovernanceAspirationDTO getById(Long id);



}
