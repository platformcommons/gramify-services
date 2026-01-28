package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing ProductionSeason-related operations in the system.
 * This interface provides methods for CRUD operations on ProductionSeason data,
 * including creation, retrieval, update, and deletion of ProductionSeason records.
 * It serves as the primary entry point for ProductionSeason management functionality.
 */
public interface ProductionSeasonFacade {

    /**
     * Retrieves all ProductionSeasons by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ProductionSeasonDTO
     */
    Set<ProductionSeasonDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new ProductionSeason record in the system.
     *
     * @param ProductionSeasonDTO The ProductionSeason data transfer object containing the information to be saved
     * @return The saved ProductionSeason data with generated identifiers and any system-modified fields
     */
    ProductionSeasonDTO save(ProductionSeasonDTO ProductionSeasonDTO);

    /**
     * Updates an existing ProductionSeason record in the system.
     *
     * @param ProductionSeasonDTO The ProductionSeason data transfer object containing the updated information
     * @return The updated ProductionSeason data as confirmed by the system
     */
    ProductionSeasonDTO update(ProductionSeasonDTO ProductionSeasonDTO);

    /**
     * Retrieves a paginated list of all ProductionSeasons in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested ProductionSeason records
     */
    PageDTO<ProductionSeasonDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a ProductionSeason record from the system.
     *
     * @param id     The unique identifier of the ProductionSeason to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific ProductionSeason by their unique identifier.
     *
     * @param id The unique identifier of the ProductionSeason to retrieve
     * @return The ProductionSeason data transfer object containing the requested information
     */
    ProductionSeasonDTO getById(Long id);



}
