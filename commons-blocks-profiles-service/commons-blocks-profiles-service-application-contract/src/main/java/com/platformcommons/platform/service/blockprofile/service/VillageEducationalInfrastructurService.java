package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageEducationalInfrastructur-related operations.
 * Provides CRUD operations and pagination support for VillageEducationalInfrastructur entities.
 *
 * @since 1.0.0
 */

public interface VillageEducationalInfrastructurService {

    /**
     * Retrieves all VillageEducationalInfrastructurs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageEducationalInfrastructurDTO in the same order as retrieved
     */
    Set<VillageEducationalInfrastructurDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageEducationalInfrastructur entry in the system.
     *
     * @param VillageEducationalInfrastructur The VillageEducationalInfrastructur information to be saved
     * @return The saved VillageEducationalInfrastructur data
     * @since 1.0.0
     */
    VillageEducationalInfrastructurDTO save(VillageEducationalInfrastructurDTO VillageEducationalInfrastructur);

    /**
     * Updates an existing VillageEducationalInfrastructur entry.
     *
     * @param VillageEducationalInfrastructur The VillageEducationalInfrastructur information to be updated
     * @return The updated VillageEducationalInfrastructur data
     * @since 1.0.0
     */
    VillageEducationalInfrastructurDTO update(VillageEducationalInfrastructurDTO VillageEducationalInfrastructur);

    /**
     * Retrieves a paginated list of VillageEducationalInfrastructurs.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageEducationalInfrastructurs
     * @since 1.0.0
     */
    PageDTO<VillageEducationalInfrastructurDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageEducationalInfrastructur by their ID with a specified reason.
     *
     * @param id     The ID of the VillageEducationalInfrastructur to delete
     * @param reason The reason for deletion
     * @return VillageEducationalInfrastructurDTO
     */
    VillageEducationalInfrastructurDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageEducationalInfrastructur by their ID.
     *
     * @param id The ID of the VillageEducationalInfrastructur to retrieve
     * @return The VillageEducationalInfrastructur with the specified ID
     * @since 1.0.0
     */
    VillageEducationalInfrastructurDTO getById(Long id);




}
