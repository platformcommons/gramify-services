package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageTransportConnectivityIss-related operations.
 * Provides CRUD operations and pagination support for VillageTransportConnectivityIss entities.
 *
 * @since 1.0.0
 */

public interface VillageTransportConnectivityIssService {

    /**
     * Retrieves all VillageTransportConnectivityIsss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageTransportConnectivityIssDTO in the same order as retrieved
     */
    Set<VillageTransportConnectivityIssDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageTransportConnectivityIss entry in the system.
     *
     * @param VillageTransportConnectivityIss The VillageTransportConnectivityIss information to be saved
     * @return The saved VillageTransportConnectivityIss data
     * @since 1.0.0
     */
    VillageTransportConnectivityIssDTO save(VillageTransportConnectivityIssDTO VillageTransportConnectivityIss);

    /**
     * Updates an existing VillageTransportConnectivityIss entry.
     *
     * @param VillageTransportConnectivityIss The VillageTransportConnectivityIss information to be updated
     * @return The updated VillageTransportConnectivityIss data
     * @since 1.0.0
     */
    VillageTransportConnectivityIssDTO update(VillageTransportConnectivityIssDTO VillageTransportConnectivityIss);

    /**
     * Retrieves a paginated list of VillageTransportConnectivityIsss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageTransportConnectivityIsss
     * @since 1.0.0
     */
    PageDTO<VillageTransportConnectivityIssDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageTransportConnectivityIss by their ID with a specified reason.
     *
     * @param id     The ID of the VillageTransportConnectivityIss to delete
     * @param reason The reason for deletion
     * @return VillageTransportConnectivityIssDTO
     */
    VillageTransportConnectivityIssDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageTransportConnectivityIss by their ID.
     *
     * @param id The ID of the VillageTransportConnectivityIss to retrieve
     * @return The VillageTransportConnectivityIss with the specified ID
     * @since 1.0.0
     */
    VillageTransportConnectivityIssDTO getById(Long id);




}
