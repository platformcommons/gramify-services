package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HorticultureProduct-related operations.
 * Provides CRUD operations and pagination support for HorticultureProduct entities.
 *
 * @since 1.0.0
 */

public interface HorticultureProductService {

    /**
     * Retrieves all HorticultureProducts by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HorticultureProductDTO in the same order as retrieved
     */
    Set<HorticultureProductDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HorticultureProduct entry in the system.
     *
     * @param HorticultureProduct The HorticultureProduct information to be saved
     * @return The saved HorticultureProduct data
     * @since 1.0.0
     */
    HorticultureProductDTO save(HorticultureProductDTO HorticultureProduct);

    /**
     * Updates an existing HorticultureProduct entry.
     *
     * @param HorticultureProduct The HorticultureProduct information to be updated
     * @return The updated HorticultureProduct data
     * @since 1.0.0
     */
    HorticultureProductDTO update(HorticultureProductDTO HorticultureProduct);

    /**
     * Retrieves a paginated list of HorticultureProducts.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HorticultureProducts
     * @since 1.0.0
     */
    PageDTO<HorticultureProductDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HorticultureProduct by their ID with a specified reason.
     *
     * @param id     The ID of the HorticultureProduct to delete
     * @param reason The reason for deletion
     * @return HorticultureProductDTO
     */
    HorticultureProductDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HorticultureProduct by their ID.
     *
     * @param id The ID of the HorticultureProduct to retrieve
     * @return The HorticultureProduct with the specified ID
     * @since 1.0.0
     */
    HorticultureProductDTO getById(Long id);




}
