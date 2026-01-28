package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageTopInfrastructureNeed-related operations in the system.
 * This interface provides methods for CRUD operations on VillageTopInfrastructureNeed data,
 * including creation, retrieval, update, and deletion of VillageTopInfrastructureNeed records.
 * It serves as the primary entry point for VillageTopInfrastructureNeed management functionality.
 */
public interface VillageTopInfrastructureNeedFacade {

    /**
     * Retrieves all VillageTopInfrastructureNeeds by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageTopInfrastructureNeedDTO
     */
    Set<VillageTopInfrastructureNeedDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageTopInfrastructureNeed record in the system.
     *
     * @param VillageTopInfrastructureNeedDTO The VillageTopInfrastructureNeed data transfer object containing the information to be saved
     * @return The saved VillageTopInfrastructureNeed data with generated identifiers and any system-modified fields
     */
    VillageTopInfrastructureNeedDTO save(VillageTopInfrastructureNeedDTO VillageTopInfrastructureNeedDTO);

    /**
     * Updates an existing VillageTopInfrastructureNeed record in the system.
     *
     * @param VillageTopInfrastructureNeedDTO The VillageTopInfrastructureNeed data transfer object containing the updated information
     * @return The updated VillageTopInfrastructureNeed data as confirmed by the system
     */
    VillageTopInfrastructureNeedDTO update(VillageTopInfrastructureNeedDTO VillageTopInfrastructureNeedDTO);

    /**
     * Retrieves a paginated list of all VillageTopInfrastructureNeeds in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageTopInfrastructureNeed records
     */
    PageDTO<VillageTopInfrastructureNeedDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageTopInfrastructureNeed record from the system.
     *
     * @param id     The unique identifier of the VillageTopInfrastructureNeed to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageTopInfrastructureNeed by their unique identifier.
     *
     * @param id The unique identifier of the VillageTopInfrastructureNeed to retrieve
     * @return The VillageTopInfrastructureNeed data transfer object containing the requested information
     */
    VillageTopInfrastructureNeedDTO getById(Long id);



}
