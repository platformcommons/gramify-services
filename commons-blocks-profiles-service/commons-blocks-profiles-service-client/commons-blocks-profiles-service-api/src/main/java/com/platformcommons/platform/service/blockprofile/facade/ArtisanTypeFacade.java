package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing ArtisanType-related operations in the system.
 * This interface provides methods for CRUD operations on ArtisanType data,
 * including creation, retrieval, update, and deletion of ArtisanType records.
 * It serves as the primary entry point for ArtisanType management functionality.
 */
public interface ArtisanTypeFacade {

    /**
     * Retrieves all ArtisanTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ArtisanTypeDTO
     */
    Set<ArtisanTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new ArtisanType record in the system.
     *
     * @param ArtisanTypeDTO The ArtisanType data transfer object containing the information to be saved
     * @return The saved ArtisanType data with generated identifiers and any system-modified fields
     */
    ArtisanTypeDTO save(ArtisanTypeDTO ArtisanTypeDTO);

    /**
     * Updates an existing ArtisanType record in the system.
     *
     * @param ArtisanTypeDTO The ArtisanType data transfer object containing the updated information
     * @return The updated ArtisanType data as confirmed by the system
     */
    ArtisanTypeDTO update(ArtisanTypeDTO ArtisanTypeDTO);

    /**
     * Retrieves a paginated list of all ArtisanTypes in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested ArtisanType records
     */
    PageDTO<ArtisanTypeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a ArtisanType record from the system.
     *
     * @param id     The unique identifier of the ArtisanType to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific ArtisanType by their unique identifier.
     *
     * @param id The unique identifier of the ArtisanType to retrieve
     * @return The ArtisanType data transfer object containing the requested information
     */
    ArtisanTypeDTO getById(Long id);



}
