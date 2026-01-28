package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing WhereFarmersBuyInput-related operations.
 * Provides CRUD operations and pagination support for WhereFarmersBuyInput entities.
 *
 * @since 1.0.0
 */

public interface WhereFarmersBuyInputService {

    /**
     * Retrieves all WhereFarmersBuyInputs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of WhereFarmersBuyInputDTO in the same order as retrieved
     */
    Set<WhereFarmersBuyInputDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new WhereFarmersBuyInput entry in the system.
     *
     * @param WhereFarmersBuyInput The WhereFarmersBuyInput information to be saved
     * @return The saved WhereFarmersBuyInput data
     * @since 1.0.0
     */
    WhereFarmersBuyInputDTO save(WhereFarmersBuyInputDTO WhereFarmersBuyInput);

    /**
     * Updates an existing WhereFarmersBuyInput entry.
     *
     * @param WhereFarmersBuyInput The WhereFarmersBuyInput information to be updated
     * @return The updated WhereFarmersBuyInput data
     * @since 1.0.0
     */
    WhereFarmersBuyInputDTO update(WhereFarmersBuyInputDTO WhereFarmersBuyInput);

    /**
     * Retrieves a paginated list of WhereFarmersBuyInputs.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of WhereFarmersBuyInputs
     * @since 1.0.0
     */
    PageDTO<WhereFarmersBuyInputDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a WhereFarmersBuyInput by their ID with a specified reason.
     *
     * @param id     The ID of the WhereFarmersBuyInput to delete
     * @param reason The reason for deletion
     * @return WhereFarmersBuyInputDTO
     */
    WhereFarmersBuyInputDTO deleteById(Long id, String reason);

    /**
     * Retrieves a WhereFarmersBuyInput by their ID.
     *
     * @param id The ID of the WhereFarmersBuyInput to retrieve
     * @return The WhereFarmersBuyInput with the specified ID
     * @since 1.0.0
     */
    WhereFarmersBuyInputDTO getById(Long id);




}
