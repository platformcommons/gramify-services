package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageCommunicationInfrastruct-related operations in the system.
 * This interface provides methods for CRUD operations on VillageCommunicationInfrastruct data,
 * including creation, retrieval, update, and deletion of VillageCommunicationInfrastruct records.
 * It serves as the primary entry point for VillageCommunicationInfrastruct management functionality.
 */
public interface VillageCommunicationInfrastructFacade {

    /**
     * Retrieves all VillageCommunicationInfrastructs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCommunicationInfrastructDTO
     */
    Set<VillageCommunicationInfrastructDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageCommunicationInfrastruct record in the system.
     *
     * @param VillageCommunicationInfrastructDTO The VillageCommunicationInfrastruct data transfer object containing the information to be saved
     * @return The saved VillageCommunicationInfrastruct data with generated identifiers and any system-modified fields
     */
    VillageCommunicationInfrastructDTO save(VillageCommunicationInfrastructDTO VillageCommunicationInfrastructDTO);

    /**
     * Updates an existing VillageCommunicationInfrastruct record in the system.
     *
     * @param VillageCommunicationInfrastructDTO The VillageCommunicationInfrastruct data transfer object containing the updated information
     * @return The updated VillageCommunicationInfrastruct data as confirmed by the system
     */
    VillageCommunicationInfrastructDTO update(VillageCommunicationInfrastructDTO VillageCommunicationInfrastructDTO);

    /**
     * Retrieves a paginated list of all VillageCommunicationInfrastructs in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageCommunicationInfrastruct records
     */
    PageDTO<VillageCommunicationInfrastructDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageCommunicationInfrastruct record from the system.
     *
     * @param id     The unique identifier of the VillageCommunicationInfrastruct to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageCommunicationInfrastruct by their unique identifier.
     *
     * @param id The unique identifier of the VillageCommunicationInfrastruct to retrieve
     * @return The VillageCommunicationInfrastruct data transfer object containing the requested information
     */
    VillageCommunicationInfrastructDTO getById(Long id);



}
