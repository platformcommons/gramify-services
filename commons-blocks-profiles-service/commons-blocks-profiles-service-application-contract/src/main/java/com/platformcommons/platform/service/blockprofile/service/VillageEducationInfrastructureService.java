package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageEducationInfrastructure-related operations.
 * Provides CRUD operations and pagination support for VillageEducationInfrastructure entities.
 *
 * @since 1.0.0
 */

public interface VillageEducationInfrastructureService {

    /**
     * Retrieves all VillageEducationInfrastructures by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageEducationInfrastructureDTO in the same order as retrieved
     */
    Set<VillageEducationInfrastructureDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageEducationInfrastructure entry in the system.
     *
     * @param VillageEducationInfrastructure The VillageEducationInfrastructure information to be saved
     * @return The saved VillageEducationInfrastructure data
     * @since 1.0.0
     */
    VillageEducationInfrastructureDTO save(VillageEducationInfrastructureDTO VillageEducationInfrastructure);

    /**
     * Updates an existing VillageEducationInfrastructure entry.
     *
     * @param VillageEducationInfrastructure The VillageEducationInfrastructure information to be updated
     * @return The updated VillageEducationInfrastructure data
     * @since 1.0.0
     */
    VillageEducationInfrastructureDTO update(VillageEducationInfrastructureDTO VillageEducationInfrastructure);

    /**
     * Retrieves a paginated list of VillageEducationInfrastructures.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageEducationInfrastructures
     * @since 1.0.0
     */
    PageDTO<VillageEducationInfrastructureDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageEducationInfrastructure by their ID with a specified reason.
     *
     * @param id     The ID of the VillageEducationInfrastructure to delete
     * @param reason The reason for deletion
     * @return VillageEducationInfrastructureDTO
     */
    VillageEducationInfrastructureDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageEducationInfrastructure by their ID.
     *
     * @param id The ID of the VillageEducationInfrastructure to retrieve
     * @return The VillageEducationInfrastructure with the specified ID
     * @since 1.0.0
     */
    VillageEducationInfrastructureDTO getById(Long id);


    /**
     * Adds a list of issuesInHigherEducationAccessList to a VillageEducationInfrastructure identified by their ID.
     *
     * @param id               The ID of the VillageEducationInfrastructure to add hobbies to
     * @param issuesInHigherEducationAccessList  to be added
     * @since 1.0.0
     */
    void addIssuesInHigherEducationAccessToVillageEducationInfrastructure(Long id, List<IssuesInHigherEducationAccessDTO> issuesInHigherEducationAccessList);



}
