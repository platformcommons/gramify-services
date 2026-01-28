package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageHealthInfrastructure-related operations.
 * Provides CRUD operations and pagination support for VillageHealthInfrastructure entities.
 *
 * @since 1.0.0
 */

public interface VillageHealthInfrastructureService {

    /**
     * Retrieves all VillageHealthInfrastructures by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageHealthInfrastructureDTO in the same order as retrieved
     */
    Set<VillageHealthInfrastructureDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageHealthInfrastructure entry in the system.
     *
     * @param VillageHealthInfrastructure The VillageHealthInfrastructure information to be saved
     * @return The saved VillageHealthInfrastructure data
     * @since 1.0.0
     */
    VillageHealthInfrastructureDTO save(VillageHealthInfrastructureDTO VillageHealthInfrastructure);

    /**
     * Updates an existing VillageHealthInfrastructure entry.
     *
     * @param VillageHealthInfrastructure The VillageHealthInfrastructure information to be updated
     * @return The updated VillageHealthInfrastructure data
     * @since 1.0.0
     */
    VillageHealthInfrastructureDTO update(VillageHealthInfrastructureDTO VillageHealthInfrastructure);

    /**
     * Retrieves a paginated list of VillageHealthInfrastructures.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageHealthInfrastructures
     * @since 1.0.0
     */
    PageDTO<VillageHealthInfrastructureDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageHealthInfrastructure by their ID with a specified reason.
     *
     * @param id     The ID of the VillageHealthInfrastructure to delete
     * @param reason The reason for deletion
     * @return VillageHealthInfrastructureDTO
     */
    VillageHealthInfrastructureDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageHealthInfrastructure by their ID.
     *
     * @param id The ID of the VillageHealthInfrastructure to retrieve
     * @return The VillageHealthInfrastructure with the specified ID
     * @since 1.0.0
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
