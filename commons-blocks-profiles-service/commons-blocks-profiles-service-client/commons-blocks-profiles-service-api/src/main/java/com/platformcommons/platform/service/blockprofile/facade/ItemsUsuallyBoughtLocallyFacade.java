package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing ItemsUsuallyBoughtLocally-related operations in the system.
 * This interface provides methods for CRUD operations on ItemsUsuallyBoughtLocally data,
 * including creation, retrieval, update, and deletion of ItemsUsuallyBoughtLocally records.
 * It serves as the primary entry point for ItemsUsuallyBoughtLocally management functionality.
 */
public interface ItemsUsuallyBoughtLocallyFacade {

    /**
     * Retrieves all ItemsUsuallyBoughtLocallys by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ItemsUsuallyBoughtLocallyDTO
     */
    Set<ItemsUsuallyBoughtLocallyDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new ItemsUsuallyBoughtLocally record in the system.
     *
     * @param ItemsUsuallyBoughtLocallyDTO The ItemsUsuallyBoughtLocally data transfer object containing the information to be saved
     * @return The saved ItemsUsuallyBoughtLocally data with generated identifiers and any system-modified fields
     */
    ItemsUsuallyBoughtLocallyDTO save(ItemsUsuallyBoughtLocallyDTO ItemsUsuallyBoughtLocallyDTO);

    /**
     * Updates an existing ItemsUsuallyBoughtLocally record in the system.
     *
     * @param ItemsUsuallyBoughtLocallyDTO The ItemsUsuallyBoughtLocally data transfer object containing the updated information
     * @return The updated ItemsUsuallyBoughtLocally data as confirmed by the system
     */
    ItemsUsuallyBoughtLocallyDTO update(ItemsUsuallyBoughtLocallyDTO ItemsUsuallyBoughtLocallyDTO);

    /**
     * Retrieves a paginated list of all ItemsUsuallyBoughtLocallys in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested ItemsUsuallyBoughtLocally records
     */
    PageDTO<ItemsUsuallyBoughtLocallyDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a ItemsUsuallyBoughtLocally record from the system.
     *
     * @param id     The unique identifier of the ItemsUsuallyBoughtLocally to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific ItemsUsuallyBoughtLocally by their unique identifier.
     *
     * @param id The unique identifier of the ItemsUsuallyBoughtLocally to retrieve
     * @return The ItemsUsuallyBoughtLocally data transfer object containing the requested information
     */
    ItemsUsuallyBoughtLocallyDTO getById(Long id);



}
