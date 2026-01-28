package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing ExportPotentialNicheProductBuye-related operations in the system.
 * This interface provides methods for CRUD operations on ExportPotentialNicheProductBuye data,
 * including creation, retrieval, update, and deletion of ExportPotentialNicheProductBuye records.
 * It serves as the primary entry point for ExportPotentialNicheProductBuye management functionality.
 */
public interface ExportPotentialNicheProductBuyeFacade {

    /**
     * Retrieves all ExportPotentialNicheProductBuyes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ExportPotentialNicheProductBuyeDTO
     */
    Set<ExportPotentialNicheProductBuyeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new ExportPotentialNicheProductBuye record in the system.
     *
     * @param ExportPotentialNicheProductBuyeDTO The ExportPotentialNicheProductBuye data transfer object containing the information to be saved
     * @return The saved ExportPotentialNicheProductBuye data with generated identifiers and any system-modified fields
     */
    ExportPotentialNicheProductBuyeDTO save(ExportPotentialNicheProductBuyeDTO ExportPotentialNicheProductBuyeDTO);

    /**
     * Updates an existing ExportPotentialNicheProductBuye record in the system.
     *
     * @param ExportPotentialNicheProductBuyeDTO The ExportPotentialNicheProductBuye data transfer object containing the updated information
     * @return The updated ExportPotentialNicheProductBuye data as confirmed by the system
     */
    ExportPotentialNicheProductBuyeDTO update(ExportPotentialNicheProductBuyeDTO ExportPotentialNicheProductBuyeDTO);

    /**
     * Retrieves a paginated list of all ExportPotentialNicheProductBuyes in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested ExportPotentialNicheProductBuye records
     */
    PageDTO<ExportPotentialNicheProductBuyeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a ExportPotentialNicheProductBuye record from the system.
     *
     * @param id     The unique identifier of the ExportPotentialNicheProductBuye to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific ExportPotentialNicheProductBuye by their unique identifier.
     *
     * @param id The unique identifier of the ExportPotentialNicheProductBuye to retrieve
     * @return The ExportPotentialNicheProductBuye data transfer object containing the requested information
     */
    ExportPotentialNicheProductBuyeDTO getById(Long id);



}
