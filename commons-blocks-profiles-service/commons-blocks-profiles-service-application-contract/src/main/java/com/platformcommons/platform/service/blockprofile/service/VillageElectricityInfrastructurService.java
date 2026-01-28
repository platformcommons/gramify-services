package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageElectricityInfrastructur-related operations.
 * Provides CRUD operations and pagination support for VillageElectricityInfrastructur entities.
 *
 * @since 1.0.0
 */

public interface VillageElectricityInfrastructurService {

    /**
     * Retrieves all VillageElectricityInfrastructurs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageElectricityInfrastructurDTO in the same order as retrieved
     */
    Set<VillageElectricityInfrastructurDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageElectricityInfrastructur entry in the system.
     *
     * @param VillageElectricityInfrastructur The VillageElectricityInfrastructur information to be saved
     * @return The saved VillageElectricityInfrastructur data
     * @since 1.0.0
     */
    VillageElectricityInfrastructurDTO save(VillageElectricityInfrastructurDTO VillageElectricityInfrastructur);

    /**
     * Updates an existing VillageElectricityInfrastructur entry.
     *
     * @param VillageElectricityInfrastructur The VillageElectricityInfrastructur information to be updated
     * @return The updated VillageElectricityInfrastructur data
     * @since 1.0.0
     */
    VillageElectricityInfrastructurDTO update(VillageElectricityInfrastructurDTO VillageElectricityInfrastructur);

    /**
     * Retrieves a paginated list of VillageElectricityInfrastructurs.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageElectricityInfrastructurs
     * @since 1.0.0
     */
    PageDTO<VillageElectricityInfrastructurDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageElectricityInfrastructur by their ID with a specified reason.
     *
     * @param id     The ID of the VillageElectricityInfrastructur to delete
     * @param reason The reason for deletion
     * @return VillageElectricityInfrastructurDTO
     */
    VillageElectricityInfrastructurDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageElectricityInfrastructur by their ID.
     *
     * @param id The ID of the VillageElectricityInfrastructur to retrieve
     * @return The VillageElectricityInfrastructur with the specified ID
     * @since 1.0.0
     */
    VillageElectricityInfrastructurDTO getById(Long id);


    /**
     * Adds a list of powerCutSeasonList to a VillageElectricityInfrastructur identified by their ID.
     *
     * @param id               The ID of the VillageElectricityInfrastructur to add hobbies to
     * @param powerCutSeasonList  to be added
     * @since 1.0.0
     */
    void addPowerCutSeasonToVillageElectricityInfrastructur(Long id, List<PowerCutSeasonDTO> powerCutSeasonList);

    /**
     * Adds a list of renewableInfraTypeList to a VillageElectricityInfrastructur identified by their ID.
     *
     * @param id               The ID of the VillageElectricityInfrastructur to add hobbies to
     * @param renewableInfraTypeList  to be added
     * @since 1.0.0
     */
    void addRenewableInfraTypeToVillageElectricityInfrastructur(Long id, List<RenewableInfraTypeDTO> renewableInfraTypeList);



}
