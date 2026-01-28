package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing PesticidesInDemand-related operations in the system.
 * This interface provides methods for CRUD operations on PesticidesInDemand data,
 * including creation, retrieval, update, and deletion of PesticidesInDemand records.
 * It serves as the primary entry point for PesticidesInDemand management functionality.
 */
public interface PesticidesInDemandFacade {

    /**
     * Retrieves all PesticidesInDemands by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of PesticidesInDemandDTO
     */
    Set<PesticidesInDemandDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new PesticidesInDemand record in the system.
     *
     * @param PesticidesInDemandDTO The PesticidesInDemand data transfer object containing the information to be saved
     * @return The saved PesticidesInDemand data with generated identifiers and any system-modified fields
     */
    PesticidesInDemandDTO save(PesticidesInDemandDTO PesticidesInDemandDTO);

    /**
     * Updates an existing PesticidesInDemand record in the system.
     *
     * @param PesticidesInDemandDTO The PesticidesInDemand data transfer object containing the updated information
     * @return The updated PesticidesInDemand data as confirmed by the system
     */
    PesticidesInDemandDTO update(PesticidesInDemandDTO PesticidesInDemandDTO);

    /**
     * Retrieves a paginated list of all PesticidesInDemands in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested PesticidesInDemand records
     */
    PageDTO<PesticidesInDemandDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a PesticidesInDemand record from the system.
     *
     * @param id     The unique identifier of the PesticidesInDemand to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific PesticidesInDemand by their unique identifier.
     *
     * @param id The unique identifier of the PesticidesInDemand to retrieve
     * @return The PesticidesInDemand data transfer object containing the requested information
     */
    PesticidesInDemandDTO getById(Long id);



}
