package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HyperLocalMarketProfile-related operations.
 * Provides CRUD operations and pagination support for HyperLocalMarketProfile entities.
 *
 * @since 1.0.0
 */

public interface HyperLocalMarketProfileService {

    /**
     * Retrieves all HyperLocalMarketProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HyperLocalMarketProfileDTO in the same order as retrieved
     */
    Set<HyperLocalMarketProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HyperLocalMarketProfile entry in the system.
     *
     * @param HyperLocalMarketProfile The HyperLocalMarketProfile information to be saved
     * @return The saved HyperLocalMarketProfile data
     * @since 1.0.0
     */
    HyperLocalMarketProfileDTO save(HyperLocalMarketProfileDTO HyperLocalMarketProfile);

    /**
     * Updates an existing HyperLocalMarketProfile entry.
     *
     * @param HyperLocalMarketProfile The HyperLocalMarketProfile information to be updated
     * @return The updated HyperLocalMarketProfile data
     * @since 1.0.0
     */
    HyperLocalMarketProfileDTO update(HyperLocalMarketProfileDTO HyperLocalMarketProfile);

    /**
     * Retrieves a paginated list of HyperLocalMarketProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HyperLocalMarketProfiles
     * @since 1.0.0
     */
    PageDTO<HyperLocalMarketProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HyperLocalMarketProfile by their ID with a specified reason.
     *
     * @param id     The ID of the HyperLocalMarketProfile to delete
     * @param reason The reason for deletion
     * @return HyperLocalMarketProfileDTO
     */
    HyperLocalMarketProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HyperLocalMarketProfile by their ID.
     *
     * @param id The ID of the HyperLocalMarketProfile to retrieve
     * @return The HyperLocalMarketProfile with the specified ID
     * @since 1.0.0
     */
    HyperLocalMarketProfileDTO getById(Long id);




}
