package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing ProductionSeasonality-related operations in the system.
 * This interface provides methods for CRUD operations on ProductionSeasonality data,
 * including creation, retrieval, update, and deletion of ProductionSeasonality records.
 * It serves as the primary entry point for ProductionSeasonality management functionality.
 */
public interface ProductionSeasonalityFacade {

    /**
     * Retrieves all ProductionSeasonalitys by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ProductionSeasonalityDTO
     */
    Set<ProductionSeasonalityDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new ProductionSeasonality record in the system.
     *
     * @param ProductionSeasonalityDTO The ProductionSeasonality data transfer object containing the information to be saved
     * @return The saved ProductionSeasonality data with generated identifiers and any system-modified fields
     */
    ProductionSeasonalityDTO save(ProductionSeasonalityDTO ProductionSeasonalityDTO);

    /**
     * Updates an existing ProductionSeasonality record in the system.
     *
     * @param ProductionSeasonalityDTO The ProductionSeasonality data transfer object containing the updated information
     * @return The updated ProductionSeasonality data as confirmed by the system
     */
    ProductionSeasonalityDTO update(ProductionSeasonalityDTO ProductionSeasonalityDTO);

    /**
     * Retrieves a paginated list of all ProductionSeasonalitys in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested ProductionSeasonality records
     */
    PageDTO<ProductionSeasonalityDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a ProductionSeasonality record from the system.
     *
     * @param id     The unique identifier of the ProductionSeasonality to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific ProductionSeasonality by their unique identifier.
     *
     * @param id The unique identifier of the ProductionSeasonality to retrieve
     * @return The ProductionSeasonality data transfer object containing the requested information
     */
    ProductionSeasonalityDTO getById(Long id);



}
