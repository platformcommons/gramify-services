package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HHLoanSource-related operations.
 * Provides CRUD operations and pagination support for HHLoanSource entities.
 *
 * @since 1.0.0
 */

public interface HHLoanSourceService {

    /**
     * Retrieves all HHLoanSources by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHLoanSourceDTO in the same order as retrieved
     */
    Set<HHLoanSourceDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHLoanSource entry in the system.
     *
     * @param HHLoanSource The HHLoanSource information to be saved
     * @return The saved HHLoanSource data
     * @since 1.0.0
     */
    HHLoanSourceDTO save(HHLoanSourceDTO HHLoanSource);

    /**
     * Updates an existing HHLoanSource entry.
     *
     * @param HHLoanSource The HHLoanSource information to be updated
     * @return The updated HHLoanSource data
     * @since 1.0.0
     */
    HHLoanSourceDTO update(HHLoanSourceDTO HHLoanSource);

    /**
     * Retrieves a paginated list of HHLoanSources.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHLoanSources
     * @since 1.0.0
     */
    PageDTO<HHLoanSourceDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HHLoanSource by their ID with a specified reason.
     *
     * @param id     The ID of the HHLoanSource to delete
     * @param reason The reason for deletion
     * @return HHLoanSourceDTO
     */
    HHLoanSourceDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HHLoanSource by their ID.
     *
     * @param id The ID of the HHLoanSource to retrieve
     * @return The HHLoanSource with the specified ID
     * @since 1.0.0
     */
    HHLoanSourceDTO getById(Long id);




}
