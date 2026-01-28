package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageIrrigationInfrastructure-related operations in the system.
 * This interface provides methods for CRUD operations on VillageIrrigationInfrastructure data,
 * including creation, retrieval, update, and deletion of VillageIrrigationInfrastructure records.
 * It serves as the primary entry point for VillageIrrigationInfrastructure management functionality.
 */
public interface VillageIrrigationInfrastructureFacade {

    /**
     * Retrieves all VillageIrrigationInfrastructures by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageIrrigationInfrastructureDTO
     */
    Set<VillageIrrigationInfrastructureDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageIrrigationInfrastructure record in the system.
     *
     * @param VillageIrrigationInfrastructureDTO The VillageIrrigationInfrastructure data transfer object containing the information to be saved
     * @return The saved VillageIrrigationInfrastructure data with generated identifiers and any system-modified fields
     */
    VillageIrrigationInfrastructureDTO save(VillageIrrigationInfrastructureDTO VillageIrrigationInfrastructureDTO);

    /**
     * Updates an existing VillageIrrigationInfrastructure record in the system.
     *
     * @param VillageIrrigationInfrastructureDTO The VillageIrrigationInfrastructure data transfer object containing the updated information
     * @return The updated VillageIrrigationInfrastructure data as confirmed by the system
     */
    VillageIrrigationInfrastructureDTO update(VillageIrrigationInfrastructureDTO VillageIrrigationInfrastructureDTO);

    /**
     * Retrieves a paginated list of all VillageIrrigationInfrastructures in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageIrrigationInfrastructure records
     */
    PageDTO<VillageIrrigationInfrastructureDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageIrrigationInfrastructure record from the system.
     *
     * @param id     The unique identifier of the VillageIrrigationInfrastructure to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageIrrigationInfrastructure by their unique identifier.
     *
     * @param id The unique identifier of the VillageIrrigationInfrastructure to retrieve
     * @return The VillageIrrigationInfrastructure data transfer object containing the requested information
     */
    VillageIrrigationInfrastructureDTO getById(Long id);



}
