package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageTopInfrastructureNeed-related operations.
 * Provides CRUD operations and pagination support for VillageTopInfrastructureNeed entities.
 *
 * @since 1.0.0
 */

public interface VillageTopInfrastructureNeedService {

    /**
     * Retrieves all VillageTopInfrastructureNeeds by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageTopInfrastructureNeedDTO in the same order as retrieved
     */
    Set<VillageTopInfrastructureNeedDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageTopInfrastructureNeed entry in the system.
     *
     * @param VillageTopInfrastructureNeed The VillageTopInfrastructureNeed information to be saved
     * @return The saved VillageTopInfrastructureNeed data
     * @since 1.0.0
     */
    VillageTopInfrastructureNeedDTO save(VillageTopInfrastructureNeedDTO VillageTopInfrastructureNeed);

    /**
     * Updates an existing VillageTopInfrastructureNeed entry.
     *
     * @param VillageTopInfrastructureNeed The VillageTopInfrastructureNeed information to be updated
     * @return The updated VillageTopInfrastructureNeed data
     * @since 1.0.0
     */
    VillageTopInfrastructureNeedDTO update(VillageTopInfrastructureNeedDTO VillageTopInfrastructureNeed);

    /**
     * Retrieves a paginated list of VillageTopInfrastructureNeeds.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageTopInfrastructureNeeds
     * @since 1.0.0
     */
    PageDTO<VillageTopInfrastructureNeedDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageTopInfrastructureNeed by their ID with a specified reason.
     *
     * @param id     The ID of the VillageTopInfrastructureNeed to delete
     * @param reason The reason for deletion
     * @return VillageTopInfrastructureNeedDTO
     */
    VillageTopInfrastructureNeedDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageTopInfrastructureNeed by their ID.
     *
     * @param id The ID of the VillageTopInfrastructureNeed to retrieve
     * @return The VillageTopInfrastructureNeed with the specified ID
     * @since 1.0.0
     */
    VillageTopInfrastructureNeedDTO getById(Long id);




}
