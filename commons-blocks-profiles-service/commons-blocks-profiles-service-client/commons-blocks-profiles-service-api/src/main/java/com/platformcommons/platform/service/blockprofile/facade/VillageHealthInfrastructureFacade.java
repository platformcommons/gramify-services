package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageHealthInfrastructure-related operations in the system.
 * This interface provides methods for CRUD operations on VillageHealthInfrastructure data,
 * including creation, retrieval, update, and deletion of VillageHealthInfrastructure records.
 * It serves as the primary entry point for VillageHealthInfrastructure management functionality.
 */
public interface VillageHealthInfrastructureFacade {

    /**
     * Retrieves all VillageHealthInfrastructures by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageHealthInfrastructureDTO
     */
    Set<VillageHealthInfrastructureDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageHealthInfrastructure record in the system.
     *
     * @param VillageHealthInfrastructureDTO The VillageHealthInfrastructure data transfer object containing the information to be saved
     * @return The saved VillageHealthInfrastructure data with generated identifiers and any system-modified fields
     */
    VillageHealthInfrastructureDTO save(VillageHealthInfrastructureDTO VillageHealthInfrastructureDTO);

    /**
     * Updates an existing VillageHealthInfrastructure record in the system.
     *
     * @param VillageHealthInfrastructureDTO The VillageHealthInfrastructure data transfer object containing the updated information
     * @return The updated VillageHealthInfrastructure data as confirmed by the system
     */
    VillageHealthInfrastructureDTO update(VillageHealthInfrastructureDTO VillageHealthInfrastructureDTO);

    /**
     * Retrieves a paginated list of all VillageHealthInfrastructures in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageHealthInfrastructure records
     */
    PageDTO<VillageHealthInfrastructureDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageHealthInfrastructure record from the system.
     *
     * @param id     The unique identifier of the VillageHealthInfrastructure to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageHealthInfrastructure by their unique identifier.
     *
     * @param id The unique identifier of the VillageHealthInfrastructure to retrieve
     * @return The VillageHealthInfrastructure data transfer object containing the requested information
     */
    VillageHealthInfrastructureDTO getById(Long id);


    /**
     * Adds a list of commonHealthIssueList to a VillageHealthInfrastructure identified by their ID.
     *
     * @param id               The ID of the VillageHealthInfrastructure to add hobbies to
     * @param commonHealthIssueList  to be added
     * @since 1.0.0
     */
    void addCommonHealthIssueToVillageHealthInfrastructure(Long id, List<CommonHealthIssueDTO> commonHealthIssueList);


}
