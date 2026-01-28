package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageTransportConnectivityIss-related operations in the system.
 * This interface provides methods for CRUD operations on VillageTransportConnectivityIss data,
 * including creation, retrieval, update, and deletion of VillageTransportConnectivityIss records.
 * It serves as the primary entry point for VillageTransportConnectivityIss management functionality.
 */
public interface VillageTransportConnectivityIssFacade {

    /**
     * Retrieves all VillageTransportConnectivityIsss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageTransportConnectivityIssDTO
     */
    Set<VillageTransportConnectivityIssDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageTransportConnectivityIss record in the system.
     *
     * @param VillageTransportConnectivityIssDTO The VillageTransportConnectivityIss data transfer object containing the information to be saved
     * @return The saved VillageTransportConnectivityIss data with generated identifiers and any system-modified fields
     */
    VillageTransportConnectivityIssDTO save(VillageTransportConnectivityIssDTO VillageTransportConnectivityIssDTO);

    /**
     * Updates an existing VillageTransportConnectivityIss record in the system.
     *
     * @param VillageTransportConnectivityIssDTO The VillageTransportConnectivityIss data transfer object containing the updated information
     * @return The updated VillageTransportConnectivityIss data as confirmed by the system
     */
    VillageTransportConnectivityIssDTO update(VillageTransportConnectivityIssDTO VillageTransportConnectivityIssDTO);

    /**
     * Retrieves a paginated list of all VillageTransportConnectivityIsss in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageTransportConnectivityIss records
     */
    PageDTO<VillageTransportConnectivityIssDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageTransportConnectivityIss record from the system.
     *
     * @param id     The unique identifier of the VillageTransportConnectivityIss to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageTransportConnectivityIss by their unique identifier.
     *
     * @param id The unique identifier of the VillageTransportConnectivityIss to retrieve
     * @return The VillageTransportConnectivityIss data transfer object containing the requested information
     */
    VillageTransportConnectivityIssDTO getById(Long id);



}
