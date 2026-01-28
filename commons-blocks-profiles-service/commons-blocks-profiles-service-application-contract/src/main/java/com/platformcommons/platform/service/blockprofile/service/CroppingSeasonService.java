package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing CroppingSeason-related operations.
 * Provides CRUD operations and pagination support for CroppingSeason entities.
 *
 * @since 1.0.0
 */

public interface CroppingSeasonService {

    /**
     * Retrieves all CroppingSeasons by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CroppingSeasonDTO in the same order as retrieved
     */
    Set<CroppingSeasonDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new CroppingSeason entry in the system.
     *
     * @param CroppingSeason The CroppingSeason information to be saved
     * @return The saved CroppingSeason data
     * @since 1.0.0
     */
    CroppingSeasonDTO save(CroppingSeasonDTO CroppingSeason);

    /**
     * Updates an existing CroppingSeason entry.
     *
     * @param CroppingSeason The CroppingSeason information to be updated
     * @return The updated CroppingSeason data
     * @since 1.0.0
     */
    CroppingSeasonDTO update(CroppingSeasonDTO CroppingSeason);

    /**
     * Retrieves a paginated list of CroppingSeasons.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of CroppingSeasons
     * @since 1.0.0
     */
    PageDTO<CroppingSeasonDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a CroppingSeason by their ID with a specified reason.
     *
     * @param id     The ID of the CroppingSeason to delete
     * @param reason The reason for deletion
     * @return CroppingSeasonDTO
     */
    CroppingSeasonDTO deleteById(Long id, String reason);

    /**
     * Retrieves a CroppingSeason by their ID.
     *
     * @param id The ID of the CroppingSeason to retrieve
     * @return The CroppingSeason with the specified ID
     * @since 1.0.0
     */
    CroppingSeasonDTO getById(Long id);




}
