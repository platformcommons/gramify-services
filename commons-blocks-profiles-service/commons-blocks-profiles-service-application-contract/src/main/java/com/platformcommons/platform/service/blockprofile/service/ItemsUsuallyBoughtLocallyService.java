package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing ItemsUsuallyBoughtLocally-related operations.
 * Provides CRUD operations and pagination support for ItemsUsuallyBoughtLocally entities.
 *
 * @since 1.0.0
 */

public interface ItemsUsuallyBoughtLocallyService {

    /**
     * Retrieves all ItemsUsuallyBoughtLocallys by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ItemsUsuallyBoughtLocallyDTO in the same order as retrieved
     */
    Set<ItemsUsuallyBoughtLocallyDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new ItemsUsuallyBoughtLocally entry in the system.
     *
     * @param ItemsUsuallyBoughtLocally The ItemsUsuallyBoughtLocally information to be saved
     * @return The saved ItemsUsuallyBoughtLocally data
     * @since 1.0.0
     */
    ItemsUsuallyBoughtLocallyDTO save(ItemsUsuallyBoughtLocallyDTO ItemsUsuallyBoughtLocally);

    /**
     * Updates an existing ItemsUsuallyBoughtLocally entry.
     *
     * @param ItemsUsuallyBoughtLocally The ItemsUsuallyBoughtLocally information to be updated
     * @return The updated ItemsUsuallyBoughtLocally data
     * @since 1.0.0
     */
    ItemsUsuallyBoughtLocallyDTO update(ItemsUsuallyBoughtLocallyDTO ItemsUsuallyBoughtLocally);

    /**
     * Retrieves a paginated list of ItemsUsuallyBoughtLocallys.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of ItemsUsuallyBoughtLocallys
     * @since 1.0.0
     */
    PageDTO<ItemsUsuallyBoughtLocallyDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a ItemsUsuallyBoughtLocally by their ID with a specified reason.
     *
     * @param id     The ID of the ItemsUsuallyBoughtLocally to delete
     * @param reason The reason for deletion
     * @return ItemsUsuallyBoughtLocallyDTO
     */
    ItemsUsuallyBoughtLocallyDTO deleteById(Long id, String reason);

    /**
     * Retrieves a ItemsUsuallyBoughtLocally by their ID.
     *
     * @param id The ID of the ItemsUsuallyBoughtLocally to retrieve
     * @return The ItemsUsuallyBoughtLocally with the specified ID
     * @since 1.0.0
     */
    ItemsUsuallyBoughtLocallyDTO getById(Long id);




}
