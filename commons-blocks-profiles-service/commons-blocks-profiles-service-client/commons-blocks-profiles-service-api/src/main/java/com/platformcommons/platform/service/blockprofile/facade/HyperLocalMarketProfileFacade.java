package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HyperLocalMarketProfile-related operations in the system.
 * This interface provides methods for CRUD operations on HyperLocalMarketProfile data,
 * including creation, retrieval, update, and deletion of HyperLocalMarketProfile records.
 * It serves as the primary entry point for HyperLocalMarketProfile management functionality.
 */
public interface HyperLocalMarketProfileFacade {

    /**
     * Retrieves all HyperLocalMarketProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HyperLocalMarketProfileDTO
     */
    Set<HyperLocalMarketProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HyperLocalMarketProfile record in the system.
     *
     * @param HyperLocalMarketProfileDTO The HyperLocalMarketProfile data transfer object containing the information to be saved
     * @return The saved HyperLocalMarketProfile data with generated identifiers and any system-modified fields
     */
    HyperLocalMarketProfileDTO save(HyperLocalMarketProfileDTO HyperLocalMarketProfileDTO);

    /**
     * Updates an existing HyperLocalMarketProfile record in the system.
     *
     * @param HyperLocalMarketProfileDTO The HyperLocalMarketProfile data transfer object containing the updated information
     * @return The updated HyperLocalMarketProfile data as confirmed by the system
     */
    HyperLocalMarketProfileDTO update(HyperLocalMarketProfileDTO HyperLocalMarketProfileDTO);

    /**
     * Retrieves a paginated list of all HyperLocalMarketProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HyperLocalMarketProfile records
     */
    PageDTO<HyperLocalMarketProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HyperLocalMarketProfile record from the system.
     *
     * @param id     The unique identifier of the HyperLocalMarketProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HyperLocalMarketProfile by their unique identifier.
     *
     * @param id The unique identifier of the HyperLocalMarketProfile to retrieve
     * @return The HyperLocalMarketProfile data transfer object containing the requested information
     */
    HyperLocalMarketProfileDTO getById(Long id);



}
