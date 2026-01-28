package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageDigitalConnectivityProfi-related operations.
 * Provides CRUD operations and pagination support for VillageDigitalConnectivityProfi entities.
 *
 * @since 1.0.0
 */

public interface VillageDigitalConnectivityProfiService {

    /**
     * Retrieves all VillageDigitalConnectivityProfis by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageDigitalConnectivityProfiDTO in the same order as retrieved
     */
    Set<VillageDigitalConnectivityProfiDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageDigitalConnectivityProfi entry in the system.
     *
     * @param VillageDigitalConnectivityProfi The VillageDigitalConnectivityProfi information to be saved
     * @return The saved VillageDigitalConnectivityProfi data
     * @since 1.0.0
     */
    VillageDigitalConnectivityProfiDTO save(VillageDigitalConnectivityProfiDTO VillageDigitalConnectivityProfi);

    /**
     * Updates an existing VillageDigitalConnectivityProfi entry.
     *
     * @param VillageDigitalConnectivityProfi The VillageDigitalConnectivityProfi information to be updated
     * @return The updated VillageDigitalConnectivityProfi data
     * @since 1.0.0
     */
    VillageDigitalConnectivityProfiDTO update(VillageDigitalConnectivityProfiDTO VillageDigitalConnectivityProfi);

    /**
     * Retrieves a paginated list of VillageDigitalConnectivityProfis.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageDigitalConnectivityProfis
     * @since 1.0.0
     */
    PageDTO<VillageDigitalConnectivityProfiDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageDigitalConnectivityProfi by their ID with a specified reason.
     *
     * @param id     The ID of the VillageDigitalConnectivityProfi to delete
     * @param reason The reason for deletion
     * @return VillageDigitalConnectivityProfiDTO
     */
    VillageDigitalConnectivityProfiDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageDigitalConnectivityProfi by their ID.
     *
     * @param id The ID of the VillageDigitalConnectivityProfi to retrieve
     * @return The VillageDigitalConnectivityProfi with the specified ID
     * @since 1.0.0
     */
    VillageDigitalConnectivityProfiDTO getById(Long id);




}
