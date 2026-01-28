package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HHSocialConstraints-related operations.
 * Provides CRUD operations and pagination support for HHSocialConstraints entities.
 *
 * @since 1.0.0
 */

public interface HHSocialConstraintsService {

    /**
     * Retrieves all HHSocialConstraintss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHSocialConstraintsDTO in the same order as retrieved
     */
    Set<HHSocialConstraintsDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHSocialConstraints entry in the system.
     *
     * @param HHSocialConstraints The HHSocialConstraints information to be saved
     * @return The saved HHSocialConstraints data
     * @since 1.0.0
     */
    HHSocialConstraintsDTO save(HHSocialConstraintsDTO HHSocialConstraints);

    /**
     * Updates an existing HHSocialConstraints entry.
     *
     * @param HHSocialConstraints The HHSocialConstraints information to be updated
     * @return The updated HHSocialConstraints data
     * @since 1.0.0
     */
    HHSocialConstraintsDTO update(HHSocialConstraintsDTO HHSocialConstraints);

    /**
     * Retrieves a paginated list of HHSocialConstraintss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHSocialConstraintss
     * @since 1.0.0
     */
    PageDTO<HHSocialConstraintsDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HHSocialConstraints by their ID with a specified reason.
     *
     * @param id     The ID of the HHSocialConstraints to delete
     * @param reason The reason for deletion
     * @return HHSocialConstraintsDTO
     */
    HHSocialConstraintsDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HHSocialConstraints by their ID.
     *
     * @param id The ID of the HHSocialConstraints to retrieve
     * @return The HHSocialConstraints with the specified ID
     * @since 1.0.0
     */
    HHSocialConstraintsDTO getById(Long id);




}
