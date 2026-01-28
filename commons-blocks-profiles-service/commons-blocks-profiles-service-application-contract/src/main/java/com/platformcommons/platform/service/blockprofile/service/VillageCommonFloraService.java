package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageCommonFlora-related operations.
 * Provides CRUD operations and pagination support for VillageCommonFlora entities.
 *
 * @since 1.0.0
 */

public interface VillageCommonFloraService {

    /**
     * Retrieves all VillageCommonFloras by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCommonFloraDTO in the same order as retrieved
     */
    Set<VillageCommonFloraDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageCommonFlora entry in the system.
     *
     * @param VillageCommonFlora The VillageCommonFlora information to be saved
     * @return The saved VillageCommonFlora data
     * @since 1.0.0
     */
    VillageCommonFloraDTO save(VillageCommonFloraDTO VillageCommonFlora);

    /**
     * Updates an existing VillageCommonFlora entry.
     *
     * @param VillageCommonFlora The VillageCommonFlora information to be updated
     * @return The updated VillageCommonFlora data
     * @since 1.0.0
     */
    VillageCommonFloraDTO update(VillageCommonFloraDTO VillageCommonFlora);

    /**
     * Retrieves a paginated list of VillageCommonFloras.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageCommonFloras
     * @since 1.0.0
     */
    PageDTO<VillageCommonFloraDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageCommonFlora by their ID with a specified reason.
     *
     * @param id     The ID of the VillageCommonFlora to delete
     * @param reason The reason for deletion
     * @return VillageCommonFloraDTO
     */
    VillageCommonFloraDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageCommonFlora by their ID.
     *
     * @param id The ID of the VillageCommonFlora to retrieve
     * @return The VillageCommonFlora with the specified ID
     * @since 1.0.0
     */
    VillageCommonFloraDTO getById(Long id);




}
