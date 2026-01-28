package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageCommonWildlife-related operations.
 * Provides CRUD operations and pagination support for VillageCommonWildlife entities.
 *
 * @since 1.0.0
 */

public interface VillageCommonWildlifeService {

    /**
     * Retrieves all VillageCommonWildlifes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCommonWildlifeDTO in the same order as retrieved
     */
    Set<VillageCommonWildlifeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageCommonWildlife entry in the system.
     *
     * @param VillageCommonWildlife The VillageCommonWildlife information to be saved
     * @return The saved VillageCommonWildlife data
     * @since 1.0.0
     */
    VillageCommonWildlifeDTO save(VillageCommonWildlifeDTO VillageCommonWildlife);

    /**
     * Updates an existing VillageCommonWildlife entry.
     *
     * @param VillageCommonWildlife The VillageCommonWildlife information to be updated
     * @return The updated VillageCommonWildlife data
     * @since 1.0.0
     */
    VillageCommonWildlifeDTO update(VillageCommonWildlifeDTO VillageCommonWildlife);

    /**
     * Retrieves a paginated list of VillageCommonWildlifes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageCommonWildlifes
     * @since 1.0.0
     */
    PageDTO<VillageCommonWildlifeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageCommonWildlife by their ID with a specified reason.
     *
     * @param id     The ID of the VillageCommonWildlife to delete
     * @param reason The reason for deletion
     * @return VillageCommonWildlifeDTO
     */
    VillageCommonWildlifeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageCommonWildlife by their ID.
     *
     * @param id The ID of the VillageCommonWildlife to retrieve
     * @return The VillageCommonWildlife with the specified ID
     * @since 1.0.0
     */
    VillageCommonWildlifeDTO getById(Long id);




}
