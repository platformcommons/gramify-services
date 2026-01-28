package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing ItemsUsuallyBoughtFromOutside-related operations.
 * Provides CRUD operations and pagination support for ItemsUsuallyBoughtFromOutside entities.
 *
 * @since 1.0.0
 */

public interface ItemsUsuallyBoughtFromOutsideService {

    /**
     * Retrieves all ItemsUsuallyBoughtFromOutsides by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ItemsUsuallyBoughtFromOutsideDTO in the same order as retrieved
     */
    Set<ItemsUsuallyBoughtFromOutsideDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new ItemsUsuallyBoughtFromOutside entry in the system.
     *
     * @param ItemsUsuallyBoughtFromOutside The ItemsUsuallyBoughtFromOutside information to be saved
     * @return The saved ItemsUsuallyBoughtFromOutside data
     * @since 1.0.0
     */
    ItemsUsuallyBoughtFromOutsideDTO save(ItemsUsuallyBoughtFromOutsideDTO ItemsUsuallyBoughtFromOutside);

    /**
     * Updates an existing ItemsUsuallyBoughtFromOutside entry.
     *
     * @param ItemsUsuallyBoughtFromOutside The ItemsUsuallyBoughtFromOutside information to be updated
     * @return The updated ItemsUsuallyBoughtFromOutside data
     * @since 1.0.0
     */
    ItemsUsuallyBoughtFromOutsideDTO update(ItemsUsuallyBoughtFromOutsideDTO ItemsUsuallyBoughtFromOutside);

    /**
     * Retrieves a paginated list of ItemsUsuallyBoughtFromOutsides.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of ItemsUsuallyBoughtFromOutsides
     * @since 1.0.0
     */
    PageDTO<ItemsUsuallyBoughtFromOutsideDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a ItemsUsuallyBoughtFromOutside by their ID with a specified reason.
     *
     * @param id     The ID of the ItemsUsuallyBoughtFromOutside to delete
     * @param reason The reason for deletion
     * @return ItemsUsuallyBoughtFromOutsideDTO
     */
    ItemsUsuallyBoughtFromOutsideDTO deleteById(Long id, String reason);

    /**
     * Retrieves a ItemsUsuallyBoughtFromOutside by their ID.
     *
     * @param id The ID of the ItemsUsuallyBoughtFromOutside to retrieve
     * @return The ItemsUsuallyBoughtFromOutside with the specified ID
     * @since 1.0.0
     */
    ItemsUsuallyBoughtFromOutsideDTO getById(Long id);




}
