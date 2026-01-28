package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HHSocialAspiration-related operations.
 * Provides CRUD operations and pagination support for HHSocialAspiration entities.
 *
 * @since 1.0.0
 */

public interface HHSocialAspirationService {

    /**
     * Retrieves all HHSocialAspirations by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHSocialAspirationDTO in the same order as retrieved
     */
    Set<HHSocialAspirationDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHSocialAspiration entry in the system.
     *
     * @param HHSocialAspiration The HHSocialAspiration information to be saved
     * @return The saved HHSocialAspiration data
     * @since 1.0.0
     */
    HHSocialAspirationDTO save(HHSocialAspirationDTO HHSocialAspiration);

    /**
     * Updates an existing HHSocialAspiration entry.
     *
     * @param HHSocialAspiration The HHSocialAspiration information to be updated
     * @return The updated HHSocialAspiration data
     * @since 1.0.0
     */
    HHSocialAspirationDTO update(HHSocialAspirationDTO HHSocialAspiration);

    /**
     * Retrieves a paginated list of HHSocialAspirations.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHSocialAspirations
     * @since 1.0.0
     */
    PageDTO<HHSocialAspirationDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HHSocialAspiration by their ID with a specified reason.
     *
     * @param id     The ID of the HHSocialAspiration to delete
     * @param reason The reason for deletion
     * @return HHSocialAspirationDTO
     */
    HHSocialAspirationDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HHSocialAspiration by their ID.
     *
     * @param id The ID of the HHSocialAspiration to retrieve
     * @return The HHSocialAspiration with the specified ID
     * @since 1.0.0
     */
    HHSocialAspirationDTO getById(Long id);




}
