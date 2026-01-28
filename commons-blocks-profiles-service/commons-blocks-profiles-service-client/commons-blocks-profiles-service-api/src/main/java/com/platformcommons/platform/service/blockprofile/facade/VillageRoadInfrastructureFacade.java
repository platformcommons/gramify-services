package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageRoadInfrastructure-related operations in the system.
 * This interface provides methods for CRUD operations on VillageRoadInfrastructure data,
 * including creation, retrieval, update, and deletion of VillageRoadInfrastructure records.
 * It serves as the primary entry point for VillageRoadInfrastructure management functionality.
 */
public interface VillageRoadInfrastructureFacade {

    /**
     * Retrieves all VillageRoadInfrastructures by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageRoadInfrastructureDTO
     */
    Set<VillageRoadInfrastructureDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageRoadInfrastructure record in the system.
     *
     * @param VillageRoadInfrastructureDTO The VillageRoadInfrastructure data transfer object containing the information to be saved
     * @return The saved VillageRoadInfrastructure data with generated identifiers and any system-modified fields
     */
    VillageRoadInfrastructureDTO save(VillageRoadInfrastructureDTO VillageRoadInfrastructureDTO);

    /**
     * Updates an existing VillageRoadInfrastructure record in the system.
     *
     * @param VillageRoadInfrastructureDTO The VillageRoadInfrastructure data transfer object containing the updated information
     * @return The updated VillageRoadInfrastructure data as confirmed by the system
     */
    VillageRoadInfrastructureDTO update(VillageRoadInfrastructureDTO VillageRoadInfrastructureDTO);

    /**
     * Retrieves a paginated list of all VillageRoadInfrastructures in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageRoadInfrastructure records
     */
    PageDTO<VillageRoadInfrastructureDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageRoadInfrastructure record from the system.
     *
     * @param id     The unique identifier of the VillageRoadInfrastructure to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageRoadInfrastructure by their unique identifier.
     *
     * @param id The unique identifier of the VillageRoadInfrastructure to retrieve
     * @return The VillageRoadInfrastructure data transfer object containing the requested information
     */
    VillageRoadInfrastructureDTO getById(Long id);



}
