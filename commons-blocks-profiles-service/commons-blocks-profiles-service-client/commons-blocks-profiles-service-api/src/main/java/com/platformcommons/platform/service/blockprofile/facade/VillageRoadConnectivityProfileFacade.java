package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageRoadConnectivityProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageRoadConnectivityProfile data,
 * including creation, retrieval, update, and deletion of VillageRoadConnectivityProfile records.
 * It serves as the primary entry point for VillageRoadConnectivityProfile management functionality.
 */
public interface VillageRoadConnectivityProfileFacade {

    /**
     * Retrieves all VillageRoadConnectivityProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageRoadConnectivityProfileDTO
     */
    Set<VillageRoadConnectivityProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageRoadConnectivityProfile record in the system.
     *
     * @param VillageRoadConnectivityProfileDTO The VillageRoadConnectivityProfile data transfer object containing the information to be saved
     * @return The saved VillageRoadConnectivityProfile data with generated identifiers and any system-modified fields
     */
    VillageRoadConnectivityProfileDTO save(VillageRoadConnectivityProfileDTO VillageRoadConnectivityProfileDTO);

    /**
     * Updates an existing VillageRoadConnectivityProfile record in the system.
     *
     * @param VillageRoadConnectivityProfileDTO The VillageRoadConnectivityProfile data transfer object containing the updated information
     * @return The updated VillageRoadConnectivityProfile data as confirmed by the system
     */
    VillageRoadConnectivityProfileDTO update(VillageRoadConnectivityProfileDTO VillageRoadConnectivityProfileDTO);

    /**
     * Retrieves a paginated list of all VillageRoadConnectivityProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageRoadConnectivityProfile records
     */
    PageDTO<VillageRoadConnectivityProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageRoadConnectivityProfile record from the system.
     *
     * @param id     The unique identifier of the VillageRoadConnectivityProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageRoadConnectivityProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageRoadConnectivityProfile to retrieve
     * @return The VillageRoadConnectivityProfile data transfer object containing the requested information
     */
    VillageRoadConnectivityProfileDTO getById(Long id);



}
