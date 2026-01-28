package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageOtherInfrastructure-related operations in the system.
 * This interface provides methods for CRUD operations on VillageOtherInfrastructure data,
 * including creation, retrieval, update, and deletion of VillageOtherInfrastructure records.
 * It serves as the primary entry point for VillageOtherInfrastructure management functionality.
 */
public interface VillageOtherInfrastructureFacade {

    /**
     * Retrieves all VillageOtherInfrastructures by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageOtherInfrastructureDTO
     */
    Set<VillageOtherInfrastructureDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageOtherInfrastructure record in the system.
     *
     * @param VillageOtherInfrastructureDTO The VillageOtherInfrastructure data transfer object containing the information to be saved
     * @return The saved VillageOtherInfrastructure data with generated identifiers and any system-modified fields
     */
    VillageOtherInfrastructureDTO save(VillageOtherInfrastructureDTO VillageOtherInfrastructureDTO);

    /**
     * Updates an existing VillageOtherInfrastructure record in the system.
     *
     * @param VillageOtherInfrastructureDTO The VillageOtherInfrastructure data transfer object containing the updated information
     * @return The updated VillageOtherInfrastructure data as confirmed by the system
     */
    VillageOtherInfrastructureDTO update(VillageOtherInfrastructureDTO VillageOtherInfrastructureDTO);

    /**
     * Retrieves a paginated list of all VillageOtherInfrastructures in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageOtherInfrastructure records
     */
    PageDTO<VillageOtherInfrastructureDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageOtherInfrastructure record from the system.
     *
     * @param id     The unique identifier of the VillageOtherInfrastructure to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageOtherInfrastructure by their unique identifier.
     *
     * @param id The unique identifier of the VillageOtherInfrastructure to retrieve
     * @return The VillageOtherInfrastructure data transfer object containing the requested information
     */
    VillageOtherInfrastructureDTO getById(Long id);



}
