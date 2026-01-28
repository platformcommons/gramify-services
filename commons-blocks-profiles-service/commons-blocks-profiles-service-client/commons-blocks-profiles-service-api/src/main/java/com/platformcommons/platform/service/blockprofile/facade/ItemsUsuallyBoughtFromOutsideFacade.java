package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing ItemsUsuallyBoughtFromOutside-related operations in the system.
 * This interface provides methods for CRUD operations on ItemsUsuallyBoughtFromOutside data,
 * including creation, retrieval, update, and deletion of ItemsUsuallyBoughtFromOutside records.
 * It serves as the primary entry point for ItemsUsuallyBoughtFromOutside management functionality.
 */
public interface ItemsUsuallyBoughtFromOutsideFacade {

    /**
     * Retrieves all ItemsUsuallyBoughtFromOutsides by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ItemsUsuallyBoughtFromOutsideDTO
     */
    Set<ItemsUsuallyBoughtFromOutsideDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new ItemsUsuallyBoughtFromOutside record in the system.
     *
     * @param ItemsUsuallyBoughtFromOutsideDTO The ItemsUsuallyBoughtFromOutside data transfer object containing the information to be saved
     * @return The saved ItemsUsuallyBoughtFromOutside data with generated identifiers and any system-modified fields
     */
    ItemsUsuallyBoughtFromOutsideDTO save(ItemsUsuallyBoughtFromOutsideDTO ItemsUsuallyBoughtFromOutsideDTO);

    /**
     * Updates an existing ItemsUsuallyBoughtFromOutside record in the system.
     *
     * @param ItemsUsuallyBoughtFromOutsideDTO The ItemsUsuallyBoughtFromOutside data transfer object containing the updated information
     * @return The updated ItemsUsuallyBoughtFromOutside data as confirmed by the system
     */
    ItemsUsuallyBoughtFromOutsideDTO update(ItemsUsuallyBoughtFromOutsideDTO ItemsUsuallyBoughtFromOutsideDTO);

    /**
     * Retrieves a paginated list of all ItemsUsuallyBoughtFromOutsides in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested ItemsUsuallyBoughtFromOutside records
     */
    PageDTO<ItemsUsuallyBoughtFromOutsideDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a ItemsUsuallyBoughtFromOutside record from the system.
     *
     * @param id     The unique identifier of the ItemsUsuallyBoughtFromOutside to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific ItemsUsuallyBoughtFromOutside by their unique identifier.
     *
     * @param id The unique identifier of the ItemsUsuallyBoughtFromOutside to retrieve
     * @return The ItemsUsuallyBoughtFromOutside data transfer object containing the requested information
     */
    ItemsUsuallyBoughtFromOutsideDTO getById(Long id);



}
