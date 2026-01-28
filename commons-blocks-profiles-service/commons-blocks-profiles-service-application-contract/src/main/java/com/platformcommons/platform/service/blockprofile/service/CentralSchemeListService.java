package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing CentralSchemeList-related operations.
 * Provides CRUD operations and pagination support for CentralSchemeList entities.
 *
 * @since 1.0.0
 */

public interface CentralSchemeListService {

    /**
     * Retrieves all CentralSchemeLists by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CentralSchemeListDTO in the same order as retrieved
     */
    Set<CentralSchemeListDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new CentralSchemeList entry in the system.
     *
     * @param CentralSchemeList The CentralSchemeList information to be saved
     * @return The saved CentralSchemeList data
     * @since 1.0.0
     */
    CentralSchemeListDTO save(CentralSchemeListDTO CentralSchemeList);

    /**
     * Updates an existing CentralSchemeList entry.
     *
     * @param CentralSchemeList The CentralSchemeList information to be updated
     * @return The updated CentralSchemeList data
     * @since 1.0.0
     */
    CentralSchemeListDTO update(CentralSchemeListDTO CentralSchemeList);

    /**
     * Retrieves a paginated list of CentralSchemeLists.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of CentralSchemeLists
     * @since 1.0.0
     */
    PageDTO<CentralSchemeListDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a CentralSchemeList by their ID with a specified reason.
     *
     * @param id     The ID of the CentralSchemeList to delete
     * @param reason The reason for deletion
     * @return CentralSchemeListDTO
     */
    CentralSchemeListDTO deleteById(Long id, String reason);

    /**
     * Retrieves a CentralSchemeList by their ID.
     *
     * @param id The ID of the CentralSchemeList to retrieve
     * @return The CentralSchemeList with the specified ID
     * @since 1.0.0
     */
    CentralSchemeListDTO getById(Long id);




}
