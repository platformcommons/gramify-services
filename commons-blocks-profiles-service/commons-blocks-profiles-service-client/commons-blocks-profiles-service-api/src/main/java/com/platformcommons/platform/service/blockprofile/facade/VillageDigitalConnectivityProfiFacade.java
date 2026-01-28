package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageDigitalConnectivityProfi-related operations in the system.
 * This interface provides methods for CRUD operations on VillageDigitalConnectivityProfi data,
 * including creation, retrieval, update, and deletion of VillageDigitalConnectivityProfi records.
 * It serves as the primary entry point for VillageDigitalConnectivityProfi management functionality.
 */
public interface VillageDigitalConnectivityProfiFacade {

    /**
     * Retrieves all VillageDigitalConnectivityProfis by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageDigitalConnectivityProfiDTO
     */
    Set<VillageDigitalConnectivityProfiDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageDigitalConnectivityProfi record in the system.
     *
     * @param VillageDigitalConnectivityProfiDTO The VillageDigitalConnectivityProfi data transfer object containing the information to be saved
     * @return The saved VillageDigitalConnectivityProfi data with generated identifiers and any system-modified fields
     */
    VillageDigitalConnectivityProfiDTO save(VillageDigitalConnectivityProfiDTO VillageDigitalConnectivityProfiDTO);

    /**
     * Updates an existing VillageDigitalConnectivityProfi record in the system.
     *
     * @param VillageDigitalConnectivityProfiDTO The VillageDigitalConnectivityProfi data transfer object containing the updated information
     * @return The updated VillageDigitalConnectivityProfi data as confirmed by the system
     */
    VillageDigitalConnectivityProfiDTO update(VillageDigitalConnectivityProfiDTO VillageDigitalConnectivityProfiDTO);

    /**
     * Retrieves a paginated list of all VillageDigitalConnectivityProfis in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageDigitalConnectivityProfi records
     */
    PageDTO<VillageDigitalConnectivityProfiDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageDigitalConnectivityProfi record from the system.
     *
     * @param id     The unique identifier of the VillageDigitalConnectivityProfi to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageDigitalConnectivityProfi by their unique identifier.
     *
     * @param id The unique identifier of the VillageDigitalConnectivityProfi to retrieve
     * @return The VillageDigitalConnectivityProfi data transfer object containing the requested information
     */
    VillageDigitalConnectivityProfiDTO getById(Long id);



}
