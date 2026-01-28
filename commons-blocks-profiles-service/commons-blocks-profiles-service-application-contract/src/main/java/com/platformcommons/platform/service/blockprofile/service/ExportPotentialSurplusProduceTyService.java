package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing ExportPotentialSurplusProduceTy-related operations.
 * Provides CRUD operations and pagination support for ExportPotentialSurplusProduceTy entities.
 *
 * @since 1.0.0
 */

public interface ExportPotentialSurplusProduceTyService {

    /**
     * Retrieves all ExportPotentialSurplusProduceTys by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ExportPotentialSurplusProduceTyDTO in the same order as retrieved
     */
    Set<ExportPotentialSurplusProduceTyDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new ExportPotentialSurplusProduceTy entry in the system.
     *
     * @param ExportPotentialSurplusProduceTy The ExportPotentialSurplusProduceTy information to be saved
     * @return The saved ExportPotentialSurplusProduceTy data
     * @since 1.0.0
     */
    ExportPotentialSurplusProduceTyDTO save(ExportPotentialSurplusProduceTyDTO ExportPotentialSurplusProduceTy);

    /**
     * Updates an existing ExportPotentialSurplusProduceTy entry.
     *
     * @param ExportPotentialSurplusProduceTy The ExportPotentialSurplusProduceTy information to be updated
     * @return The updated ExportPotentialSurplusProduceTy data
     * @since 1.0.0
     */
    ExportPotentialSurplusProduceTyDTO update(ExportPotentialSurplusProduceTyDTO ExportPotentialSurplusProduceTy);

    /**
     * Retrieves a paginated list of ExportPotentialSurplusProduceTys.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of ExportPotentialSurplusProduceTys
     * @since 1.0.0
     */
    PageDTO<ExportPotentialSurplusProduceTyDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a ExportPotentialSurplusProduceTy by their ID with a specified reason.
     *
     * @param id     The ID of the ExportPotentialSurplusProduceTy to delete
     * @param reason The reason for deletion
     * @return ExportPotentialSurplusProduceTyDTO
     */
    ExportPotentialSurplusProduceTyDTO deleteById(Long id, String reason);

    /**
     * Retrieves a ExportPotentialSurplusProduceTy by their ID.
     *
     * @param id The ID of the ExportPotentialSurplusProduceTy to retrieve
     * @return The ExportPotentialSurplusProduceTy with the specified ID
     * @since 1.0.0
     */
    ExportPotentialSurplusProduceTyDTO getById(Long id);




}
