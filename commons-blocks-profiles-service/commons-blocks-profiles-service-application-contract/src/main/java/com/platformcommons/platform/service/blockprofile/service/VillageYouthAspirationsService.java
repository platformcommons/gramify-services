package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageYouthAspirations-related operations.
 * Provides CRUD operations and pagination support for VillageYouthAspirations entities.
 *
 * @since 1.0.0
 */

public interface VillageYouthAspirationsService {

    /**
     * Retrieves all VillageYouthAspirationss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageYouthAspirationsDTO in the same order as retrieved
     */
    Set<VillageYouthAspirationsDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageYouthAspirations entry in the system.
     *
     * @param VillageYouthAspirations The VillageYouthAspirations information to be saved
     * @return The saved VillageYouthAspirations data
     * @since 1.0.0
     */
    VillageYouthAspirationsDTO save(VillageYouthAspirationsDTO VillageYouthAspirations);

    /**
     * Updates an existing VillageYouthAspirations entry.
     *
     * @param VillageYouthAspirations The VillageYouthAspirations information to be updated
     * @return The updated VillageYouthAspirations data
     * @since 1.0.0
     */
    VillageYouthAspirationsDTO update(VillageYouthAspirationsDTO VillageYouthAspirations);

    /**
     * Retrieves a paginated list of VillageYouthAspirationss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageYouthAspirationss
     * @since 1.0.0
     */
    PageDTO<VillageYouthAspirationsDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageYouthAspirations by their ID with a specified reason.
     *
     * @param id     The ID of the VillageYouthAspirations to delete
     * @param reason The reason for deletion
     * @return VillageYouthAspirationsDTO
     */
    VillageYouthAspirationsDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageYouthAspirations by their ID.
     *
     * @param id The ID of the VillageYouthAspirations to retrieve
     * @return The VillageYouthAspirations with the specified ID
     * @since 1.0.0
     */
    VillageYouthAspirationsDTO getById(Long id);




}
