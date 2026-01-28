package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageForestResourceProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageForestResourceProfile data,
 * including creation, retrieval, update, and deletion of VillageForestResourceProfile records.
 * It serves as the primary entry point for VillageForestResourceProfile management functionality.
 */
public interface VillageForestResourceProfileFacade {

    /**
     * Retrieves all VillageForestResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageForestResourceProfileDTO
     */
    Set<VillageForestResourceProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageForestResourceProfile record in the system.
     *
     * @param VillageForestResourceProfileDTO The VillageForestResourceProfile data transfer object containing the information to be saved
     * @return The saved VillageForestResourceProfile data with generated identifiers and any system-modified fields
     */
    VillageForestResourceProfileDTO save(VillageForestResourceProfileDTO VillageForestResourceProfileDTO);

    /**
     * Updates an existing VillageForestResourceProfile record in the system.
     *
     * @param VillageForestResourceProfileDTO The VillageForestResourceProfile data transfer object containing the updated information
     * @return The updated VillageForestResourceProfile data as confirmed by the system
     */
    VillageForestResourceProfileDTO update(VillageForestResourceProfileDTO VillageForestResourceProfileDTO);

    /**
     * Retrieves a paginated list of all VillageForestResourceProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageForestResourceProfile records
     */
    PageDTO<VillageForestResourceProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageForestResourceProfile record from the system.
     *
     * @param id     The unique identifier of the VillageForestResourceProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageForestResourceProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageForestResourceProfile to retrieve
     * @return The VillageForestResourceProfile data transfer object containing the requested information
     */
    VillageForestResourceProfileDTO getById(Long id);


    /**
     * Adds a list of villageForestProduceTypeList to a VillageForestResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageForestResourceProfile to add hobbies to
     * @param villageForestProduceTypeList  to be added
     * @since 1.0.0
     */
    void addVillageForestProduceTypeToVillageForestResourceProfile(Long id, List<VillageForestProduceTypeDTO> villageForestProduceTypeList);

    /**
     * Adds a list of villageCommonWildlifeList to a VillageForestResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageForestResourceProfile to add hobbies to
     * @param villageCommonWildlifeList  to be added
     * @since 1.0.0
     */
    void addVillageCommonWildlifeToVillageForestResourceProfile(Long id, List<VillageCommonWildlifeDTO> villageCommonWildlifeList);


}
