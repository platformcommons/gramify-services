package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageInfrastructureConstraint-related operations.
 * Provides CRUD operations and pagination support for VillageInfrastructureConstraint entities.
 *
 * @since 1.0.0
 */

public interface VillageInfrastructureConstraintService {

    /**
     * Retrieves all VillageInfrastructureConstraints by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageInfrastructureConstraintDTO in the same order as retrieved
     */
    Set<VillageInfrastructureConstraintDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageInfrastructureConstraint entry in the system.
     *
     * @param VillageInfrastructureConstraint The VillageInfrastructureConstraint information to be saved
     * @return The saved VillageInfrastructureConstraint data
     * @since 1.0.0
     */
    VillageInfrastructureConstraintDTO save(VillageInfrastructureConstraintDTO VillageInfrastructureConstraint);

    /**
     * Updates an existing VillageInfrastructureConstraint entry.
     *
     * @param VillageInfrastructureConstraint The VillageInfrastructureConstraint information to be updated
     * @return The updated VillageInfrastructureConstraint data
     * @since 1.0.0
     */
    VillageInfrastructureConstraintDTO update(VillageInfrastructureConstraintDTO VillageInfrastructureConstraint);

    /**
     * Retrieves a paginated list of VillageInfrastructureConstraints.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageInfrastructureConstraints
     * @since 1.0.0
     */
    PageDTO<VillageInfrastructureConstraintDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageInfrastructureConstraint by their ID with a specified reason.
     *
     * @param id     The ID of the VillageInfrastructureConstraint to delete
     * @param reason The reason for deletion
     * @return VillageInfrastructureConstraintDTO
     */
    VillageInfrastructureConstraintDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageInfrastructureConstraint by their ID.
     *
     * @param id The ID of the VillageInfrastructureConstraint to retrieve
     * @return The VillageInfrastructureConstraint with the specified ID
     * @since 1.0.0
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
