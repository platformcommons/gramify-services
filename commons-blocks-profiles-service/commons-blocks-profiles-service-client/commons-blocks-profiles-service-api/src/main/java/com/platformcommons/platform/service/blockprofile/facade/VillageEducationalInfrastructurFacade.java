package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageEducationalInfrastructur-related operations in the system.
 * This interface provides methods for CRUD operations on VillageEducationalInfrastructur data,
 * including creation, retrieval, update, and deletion of VillageEducationalInfrastructur records.
 * It serves as the primary entry point for VillageEducationalInfrastructur management functionality.
 */
public interface VillageEducationalInfrastructurFacade {

    /**
     * Retrieves all VillageEducationalInfrastructurs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageEducationalInfrastructurDTO
     */
    Set<VillageEducationalInfrastructurDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageEducationalInfrastructur record in the system.
     *
     * @param VillageEducationalInfrastructurDTO The VillageEducationalInfrastructur data transfer object containing the information to be saved
     * @return The saved VillageEducationalInfrastructur data with generated identifiers and any system-modified fields
     */
    VillageEducationalInfrastructurDTO save(VillageEducationalInfrastructurDTO VillageEducationalInfrastructurDTO);

    /**
     * Updates an existing VillageEducationalInfrastructur record in the system.
     *
     * @param VillageEducationalInfrastructurDTO The VillageEducationalInfrastructur data transfer object containing the updated information
     * @return The updated VillageEducationalInfrastructur data as confirmed by the system
     */
    VillageEducationalInfrastructurDTO update(VillageEducationalInfrastructurDTO VillageEducationalInfrastructurDTO);

    /**
     * Retrieves a paginated list of all VillageEducationalInfrastructurs in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageEducationalInfrastructur records
     */
    PageDTO<VillageEducationalInfrastructurDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageEducationalInfrastructur record from the system.
     *
     * @param id     The unique identifier of the VillageEducationalInfrastructur to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageEducationalInfrastructur by their unique identifier.
     *
     * @param id The unique identifier of the VillageEducationalInfrastructur to retrieve
     * @return The VillageEducationalInfrastructur data transfer object containing the requested information
     */
    VillageEducationalInfrastructurDTO getById(Long id);



}
