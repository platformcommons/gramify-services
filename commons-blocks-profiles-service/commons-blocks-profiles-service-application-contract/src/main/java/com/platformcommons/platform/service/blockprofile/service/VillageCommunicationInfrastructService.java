package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageCommunicationInfrastruct-related operations.
 * Provides CRUD operations and pagination support for VillageCommunicationInfrastruct entities.
 *
 * @since 1.0.0
 */

public interface VillageCommunicationInfrastructService {

    /**
     * Retrieves all VillageCommunicationInfrastructs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCommunicationInfrastructDTO in the same order as retrieved
     */
    Set<VillageCommunicationInfrastructDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageCommunicationInfrastruct entry in the system.
     *
     * @param VillageCommunicationInfrastruct The VillageCommunicationInfrastruct information to be saved
     * @return The saved VillageCommunicationInfrastruct data
     * @since 1.0.0
     */
    VillageCommunicationInfrastructDTO save(VillageCommunicationInfrastructDTO VillageCommunicationInfrastruct);

    /**
     * Updates an existing VillageCommunicationInfrastruct entry.
     *
     * @param VillageCommunicationInfrastruct The VillageCommunicationInfrastruct information to be updated
     * @return The updated VillageCommunicationInfrastruct data
     * @since 1.0.0
     */
    VillageCommunicationInfrastructDTO update(VillageCommunicationInfrastructDTO VillageCommunicationInfrastruct);

    /**
     * Retrieves a paginated list of VillageCommunicationInfrastructs.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageCommunicationInfrastructs
     * @since 1.0.0
     */
    PageDTO<VillageCommunicationInfrastructDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageCommunicationInfrastruct by their ID with a specified reason.
     *
     * @param id     The ID of the VillageCommunicationInfrastruct to delete
     * @param reason The reason for deletion
     * @return VillageCommunicationInfrastructDTO
     */
    VillageCommunicationInfrastructDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageCommunicationInfrastruct by their ID.
     *
     * @param id The ID of the VillageCommunicationInfrastruct to retrieve
     * @return The VillageCommunicationInfrastruct with the specified ID
     * @since 1.0.0
     */
    VillageCommunicationInfrastructDTO getById(Long id);




}
