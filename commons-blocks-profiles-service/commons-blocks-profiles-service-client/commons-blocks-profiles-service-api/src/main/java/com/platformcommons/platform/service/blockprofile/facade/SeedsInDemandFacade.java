package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing SeedsInDemand-related operations in the system.
 * This interface provides methods for CRUD operations on SeedsInDemand data,
 * including creation, retrieval, update, and deletion of SeedsInDemand records.
 * It serves as the primary entry point for SeedsInDemand management functionality.
 */
public interface SeedsInDemandFacade {

    /**
     * Retrieves all SeedsInDemands by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SeedsInDemandDTO
     */
    Set<SeedsInDemandDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new SeedsInDemand record in the system.
     *
     * @param SeedsInDemandDTO The SeedsInDemand data transfer object containing the information to be saved
     * @return The saved SeedsInDemand data with generated identifiers and any system-modified fields
     */
    SeedsInDemandDTO save(SeedsInDemandDTO SeedsInDemandDTO);

    /**
     * Updates an existing SeedsInDemand record in the system.
     *
     * @param SeedsInDemandDTO The SeedsInDemand data transfer object containing the updated information
     * @return The updated SeedsInDemand data as confirmed by the system
     */
    SeedsInDemandDTO update(SeedsInDemandDTO SeedsInDemandDTO);

    /**
     * Retrieves a paginated list of all SeedsInDemands in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested SeedsInDemand records
     */
    PageDTO<SeedsInDemandDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a SeedsInDemand record from the system.
     *
     * @param id     The unique identifier of the SeedsInDemand to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific SeedsInDemand by their unique identifier.
     *
     * @param id The unique identifier of the SeedsInDemand to retrieve
     * @return The SeedsInDemand data transfer object containing the requested information
     */
    SeedsInDemandDTO getById(Long id);



}
