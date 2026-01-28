package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageCommonFauna-related operations.
 * Provides CRUD operations and pagination support for VillageCommonFauna entities.
 *
 * @since 1.0.0
 */

public interface VillageCommonFaunaService {

    /**
     * Retrieves all VillageCommonFaunas by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCommonFaunaDTO in the same order as retrieved
     */
    Set<VillageCommonFaunaDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageCommonFauna entry in the system.
     *
     * @param VillageCommonFauna The VillageCommonFauna information to be saved
     * @return The saved VillageCommonFauna data
     * @since 1.0.0
     */
    VillageCommonFaunaDTO save(VillageCommonFaunaDTO VillageCommonFauna);

    /**
     * Updates an existing VillageCommonFauna entry.
     *
     * @param VillageCommonFauna The VillageCommonFauna information to be updated
     * @return The updated VillageCommonFauna data
     * @since 1.0.0
     */
    VillageCommonFaunaDTO update(VillageCommonFaunaDTO VillageCommonFauna);

    /**
     * Retrieves a paginated list of VillageCommonFaunas.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageCommonFaunas
     * @since 1.0.0
     */
    PageDTO<VillageCommonFaunaDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageCommonFauna by their ID with a specified reason.
     *
     * @param id     The ID of the VillageCommonFauna to delete
     * @param reason The reason for deletion
     * @return VillageCommonFaunaDTO
     */
    VillageCommonFaunaDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageCommonFauna by their ID.
     *
     * @param id The ID of the VillageCommonFauna to retrieve
     * @return The VillageCommonFauna with the specified ID
     * @since 1.0.0
     */
    VillageCommonFaunaDTO getById(Long id);




}
