package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HHArtisanType-related operations in the system.
 * This interface provides methods for CRUD operations on HHArtisanType data,
 * including creation, retrieval, update, and deletion of HHArtisanType records.
 * It serves as the primary entry point for HHArtisanType management functionality.
 */
public interface HHArtisanTypeFacade {

    /**
     * Retrieves all HHArtisanTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHArtisanTypeDTO
     */
    Set<HHArtisanTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHArtisanType record in the system.
     *
     * @param HHArtisanTypeDTO The HHArtisanType data transfer object containing the information to be saved
     * @return The saved HHArtisanType data with generated identifiers and any system-modified fields
     */
    HHArtisanTypeDTO save(HHArtisanTypeDTO HHArtisanTypeDTO);

    /**
     * Updates an existing HHArtisanType record in the system.
     *
     * @param HHArtisanTypeDTO The HHArtisanType data transfer object containing the updated information
     * @return The updated HHArtisanType data as confirmed by the system
     */
    HHArtisanTypeDTO update(HHArtisanTypeDTO HHArtisanTypeDTO);

    /**
     * Retrieves a paginated list of all HHArtisanTypes in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HHArtisanType records
     */
    PageDTO<HHArtisanTypeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HHArtisanType record from the system.
     *
     * @param id     The unique identifier of the HHArtisanType to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HHArtisanType by their unique identifier.
     *
     * @param id The unique identifier of the HHArtisanType to retrieve
     * @return The HHArtisanType data transfer object containing the requested information
     */
    HHArtisanTypeDTO getById(Long id);



}
