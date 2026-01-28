package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing WhereFarmersBuyInput-related operations in the system.
 * This interface provides methods for CRUD operations on WhereFarmersBuyInput data,
 * including creation, retrieval, update, and deletion of WhereFarmersBuyInput records.
 * It serves as the primary entry point for WhereFarmersBuyInput management functionality.
 */
public interface WhereFarmersBuyInputFacade {

    /**
     * Retrieves all WhereFarmersBuyInputs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of WhereFarmersBuyInputDTO
     */
    Set<WhereFarmersBuyInputDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new WhereFarmersBuyInput record in the system.
     *
     * @param WhereFarmersBuyInputDTO The WhereFarmersBuyInput data transfer object containing the information to be saved
     * @return The saved WhereFarmersBuyInput data with generated identifiers and any system-modified fields
     */
    WhereFarmersBuyInputDTO save(WhereFarmersBuyInputDTO WhereFarmersBuyInputDTO);

    /**
     * Updates an existing WhereFarmersBuyInput record in the system.
     *
     * @param WhereFarmersBuyInputDTO The WhereFarmersBuyInput data transfer object containing the updated information
     * @return The updated WhereFarmersBuyInput data as confirmed by the system
     */
    WhereFarmersBuyInputDTO update(WhereFarmersBuyInputDTO WhereFarmersBuyInputDTO);

    /**
     * Retrieves a paginated list of all WhereFarmersBuyInputs in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested WhereFarmersBuyInput records
     */
    PageDTO<WhereFarmersBuyInputDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a WhereFarmersBuyInput record from the system.
     *
     * @param id     The unique identifier of the WhereFarmersBuyInput to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific WhereFarmersBuyInput by their unique identifier.
     *
     * @param id The unique identifier of the WhereFarmersBuyInput to retrieve
     * @return The WhereFarmersBuyInput data transfer object containing the requested information
     */
    WhereFarmersBuyInputDTO getById(Long id);



}
