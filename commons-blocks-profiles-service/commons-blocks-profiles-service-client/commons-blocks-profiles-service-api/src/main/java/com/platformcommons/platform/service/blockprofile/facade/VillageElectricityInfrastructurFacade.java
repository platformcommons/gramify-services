package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageElectricityInfrastructur-related operations in the system.
 * This interface provides methods for CRUD operations on VillageElectricityInfrastructur data,
 * including creation, retrieval, update, and deletion of VillageElectricityInfrastructur records.
 * It serves as the primary entry point for VillageElectricityInfrastructur management functionality.
 */
public interface VillageElectricityInfrastructurFacade {

    /**
     * Retrieves all VillageElectricityInfrastructurs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageElectricityInfrastructurDTO
     */
    Set<VillageElectricityInfrastructurDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageElectricityInfrastructur record in the system.
     *
     * @param VillageElectricityInfrastructurDTO The VillageElectricityInfrastructur data transfer object containing the information to be saved
     * @return The saved VillageElectricityInfrastructur data with generated identifiers and any system-modified fields
     */
    VillageElectricityInfrastructurDTO save(VillageElectricityInfrastructurDTO VillageElectricityInfrastructurDTO);

    /**
     * Updates an existing VillageElectricityInfrastructur record in the system.
     *
     * @param VillageElectricityInfrastructurDTO The VillageElectricityInfrastructur data transfer object containing the updated information
     * @return The updated VillageElectricityInfrastructur data as confirmed by the system
     */
    VillageElectricityInfrastructurDTO update(VillageElectricityInfrastructurDTO VillageElectricityInfrastructurDTO);

    /**
     * Retrieves a paginated list of all VillageElectricityInfrastructurs in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageElectricityInfrastructur records
     */
    PageDTO<VillageElectricityInfrastructurDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageElectricityInfrastructur record from the system.
     *
     * @param id     The unique identifier of the VillageElectricityInfrastructur to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageElectricityInfrastructur by their unique identifier.
     *
     * @param id The unique identifier of the VillageElectricityInfrastructur to retrieve
     * @return The VillageElectricityInfrastructur data transfer object containing the requested information
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
