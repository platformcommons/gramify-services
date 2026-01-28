package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing ExportPotentialSurplusProduceTy-related operations in the system.
 * This interface provides methods for CRUD operations on ExportPotentialSurplusProduceTy data,
 * including creation, retrieval, update, and deletion of ExportPotentialSurplusProduceTy records.
 * It serves as the primary entry point for ExportPotentialSurplusProduceTy management functionality.
 */
public interface ExportPotentialSurplusProduceTyFacade {

    /**
     * Retrieves all ExportPotentialSurplusProduceTys by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ExportPotentialSurplusProduceTyDTO
     */
    Set<ExportPotentialSurplusProduceTyDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new ExportPotentialSurplusProduceTy record in the system.
     *
     * @param ExportPotentialSurplusProduceTyDTO The ExportPotentialSurplusProduceTy data transfer object containing the information to be saved
     * @return The saved ExportPotentialSurplusProduceTy data with generated identifiers and any system-modified fields
     */
    ExportPotentialSurplusProduceTyDTO save(ExportPotentialSurplusProduceTyDTO ExportPotentialSurplusProduceTyDTO);

    /**
     * Updates an existing ExportPotentialSurplusProduceTy record in the system.
     *
     * @param ExportPotentialSurplusProduceTyDTO The ExportPotentialSurplusProduceTy data transfer object containing the updated information
     * @return The updated ExportPotentialSurplusProduceTy data as confirmed by the system
     */
    ExportPotentialSurplusProduceTyDTO update(ExportPotentialSurplusProduceTyDTO ExportPotentialSurplusProduceTyDTO);

    /**
     * Retrieves a paginated list of all ExportPotentialSurplusProduceTys in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested ExportPotentialSurplusProduceTy records
     */
    PageDTO<ExportPotentialSurplusProduceTyDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a ExportPotentialSurplusProduceTy record from the system.
     *
     * @param id     The unique identifier of the ExportPotentialSurplusProduceTy to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific ExportPotentialSurplusProduceTy by their unique identifier.
     *
     * @param id The unique identifier of the ExportPotentialSurplusProduceTy to retrieve
     * @return The ExportPotentialSurplusProduceTy data transfer object containing the requested information
     */
    ExportPotentialSurplusProduceTyDTO getById(Long id);



}
