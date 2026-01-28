package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageInfrastructureConstraint-related operations in the system.
 * This interface provides methods for CRUD operations on VillageInfrastructureConstraint data,
 * including creation, retrieval, update, and deletion of VillageInfrastructureConstraint records.
 * It serves as the primary entry point for VillageInfrastructureConstraint management functionality.
 */
public interface VillageInfrastructureConstraintFacade {

    /**
     * Retrieves all VillageInfrastructureConstraints by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageInfrastructureConstraintDTO
     */
    Set<VillageInfrastructureConstraintDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageInfrastructureConstraint record in the system.
     *
     * @param VillageInfrastructureConstraintDTO The VillageInfrastructureConstraint data transfer object containing the information to be saved
     * @return The saved VillageInfrastructureConstraint data with generated identifiers and any system-modified fields
     */
    VillageInfrastructureConstraintDTO save(VillageInfrastructureConstraintDTO VillageInfrastructureConstraintDTO);

    /**
     * Updates an existing VillageInfrastructureConstraint record in the system.
     *
     * @param VillageInfrastructureConstraintDTO The VillageInfrastructureConstraint data transfer object containing the updated information
     * @return The updated VillageInfrastructureConstraint data as confirmed by the system
     */
    VillageInfrastructureConstraintDTO update(VillageInfrastructureConstraintDTO VillageInfrastructureConstraintDTO);

    /**
     * Retrieves a paginated list of all VillageInfrastructureConstraints in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageInfrastructureConstraint records
     */
    PageDTO<VillageInfrastructureConstraintDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageInfrastructureConstraint record from the system.
     *
     * @param id     The unique identifier of the VillageInfrastructureConstraint to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageInfrastructureConstraint by their unique identifier.
     *
     * @param id The unique identifier of the VillageInfrastructureConstraint to retrieve
     * @return The VillageInfrastructureConstraint data transfer object containing the requested information
     */
    VillageInfrastructureConstraintDTO getById(Long id);


    /**
     * Adds a list of villageTopInfrastructureNeedList to a VillageInfrastructureConstraint identified by their ID.
     *
     * @param id               The ID of the VillageInfrastructureConstraint to add hobbies to
     * @param villageTopInfrastructureNeedList  to be added
     * @since 1.0.0
     */
    void addVillageTopInfrastructureNeedToVillageInfrastructureConstraint(Long id, List<VillageTopInfrastructureNeedDTO> villageTopInfrastructureNeedList);

    /**
     * Adds a list of villageTransportConnectivityIssList to a VillageInfrastructureConstraint identified by their ID.
     *
     * @param id               The ID of the VillageInfrastructureConstraint to add hobbies to
     * @param villageTransportConnectivityIssList  to be added
     * @since 1.0.0
     */
    void addVillageTransportConnectivityIssToVillageInfrastructureConstraint(Long id, List<VillageTransportConnectivityIssDTO> villageTransportConnectivityIssList);

    /**
     * Adds a list of villageWaterSupplyGapList to a VillageInfrastructureConstraint identified by their ID.
     *
     * @param id               The ID of the VillageInfrastructureConstraint to add hobbies to
     * @param villageWaterSupplyGapList  to be added
     * @since 1.0.0
     */
    void addVillageWaterSupplyGapToVillageInfrastructureConstraint(Long id, List<VillageWaterSupplyGapDTO> villageWaterSupplyGapList);


}
