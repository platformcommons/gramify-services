package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageEducationInfrastructure-related operations in the system.
 * This interface provides methods for CRUD operations on VillageEducationInfrastructure data,
 * including creation, retrieval, update, and deletion of VillageEducationInfrastructure records.
 * It serves as the primary entry point for VillageEducationInfrastructure management functionality.
 */
public interface VillageEducationInfrastructureFacade {

    /**
     * Retrieves all VillageEducationInfrastructures by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageEducationInfrastructureDTO
     */
    Set<VillageEducationInfrastructureDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageEducationInfrastructure record in the system.
     *
     * @param VillageEducationInfrastructureDTO The VillageEducationInfrastructure data transfer object containing the information to be saved
     * @return The saved VillageEducationInfrastructure data with generated identifiers and any system-modified fields
     */
    VillageEducationInfrastructureDTO save(VillageEducationInfrastructureDTO VillageEducationInfrastructureDTO);

    /**
     * Updates an existing VillageEducationInfrastructure record in the system.
     *
     * @param VillageEducationInfrastructureDTO The VillageEducationInfrastructure data transfer object containing the updated information
     * @return The updated VillageEducationInfrastructure data as confirmed by the system
     */
    VillageEducationInfrastructureDTO update(VillageEducationInfrastructureDTO VillageEducationInfrastructureDTO);

    /**
     * Retrieves a paginated list of all VillageEducationInfrastructures in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageEducationInfrastructure records
     */
    PageDTO<VillageEducationInfrastructureDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageEducationInfrastructure record from the system.
     *
     * @param id     The unique identifier of the VillageEducationInfrastructure to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageEducationInfrastructure by their unique identifier.
     *
     * @param id The unique identifier of the VillageEducationInfrastructure to retrieve
     * @return The VillageEducationInfrastructure data transfer object containing the requested information
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
