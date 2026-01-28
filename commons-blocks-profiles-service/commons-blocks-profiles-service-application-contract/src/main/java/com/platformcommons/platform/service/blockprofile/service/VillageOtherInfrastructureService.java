package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageOtherInfrastructure-related operations.
 * Provides CRUD operations and pagination support for VillageOtherInfrastructure entities.
 *
 * @since 1.0.0
 */

public interface VillageOtherInfrastructureService {

    /**
     * Retrieves all VillageOtherInfrastructures by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageOtherInfrastructureDTO in the same order as retrieved
     */
    Set<VillageOtherInfrastructureDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageOtherInfrastructure entry in the system.
     *
     * @param VillageOtherInfrastructure The VillageOtherInfrastructure information to be saved
     * @return The saved VillageOtherInfrastructure data
     * @since 1.0.0
     */
    VillageOtherInfrastructureDTO save(VillageOtherInfrastructureDTO VillageOtherInfrastructure);

    /**
     * Updates an existing VillageOtherInfrastructure entry.
     *
     * @param VillageOtherInfrastructure The VillageOtherInfrastructure information to be updated
     * @return The updated VillageOtherInfrastructure data
     * @since 1.0.0
     */
    VillageOtherInfrastructureDTO update(VillageOtherInfrastructureDTO VillageOtherInfrastructure);

    /**
     * Retrieves a paginated list of VillageOtherInfrastructures.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageOtherInfrastructures
     * @since 1.0.0
     */
    PageDTO<VillageOtherInfrastructureDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageOtherInfrastructure by their ID with a specified reason.
     *
     * @param id     The ID of the VillageOtherInfrastructure to delete
     * @param reason The reason for deletion
     * @return VillageOtherInfrastructureDTO
     */
    VillageOtherInfrastructureDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageOtherInfrastructure by their ID.
     *
     * @param id The ID of the VillageOtherInfrastructure to retrieve
     * @return The VillageOtherInfrastructure with the specified ID
     * @since 1.0.0
     */
    VillageOtherInfrastructureDTO getById(Long id);




}
