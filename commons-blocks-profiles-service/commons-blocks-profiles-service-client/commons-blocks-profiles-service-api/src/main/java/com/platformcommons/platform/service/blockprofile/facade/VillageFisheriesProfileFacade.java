package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageFisheriesProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageFisheriesProfile data,
 * including creation, retrieval, update, and deletion of VillageFisheriesProfile records.
 * It serves as the primary entry point for VillageFisheriesProfile management functionality.
 */
public interface VillageFisheriesProfileFacade {

    /**
     * Retrieves all VillageFisheriesProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageFisheriesProfileDTO
     */
    Set<VillageFisheriesProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageFisheriesProfile record in the system.
     *
     * @param VillageFisheriesProfileDTO The VillageFisheriesProfile data transfer object containing the information to be saved
     * @return The saved VillageFisheriesProfile data with generated identifiers and any system-modified fields
     */
    VillageFisheriesProfileDTO save(VillageFisheriesProfileDTO VillageFisheriesProfileDTO);

    /**
     * Updates an existing VillageFisheriesProfile record in the system.
     *
     * @param VillageFisheriesProfileDTO The VillageFisheriesProfile data transfer object containing the updated information
     * @return The updated VillageFisheriesProfile data as confirmed by the system
     */
    VillageFisheriesProfileDTO update(VillageFisheriesProfileDTO VillageFisheriesProfileDTO);

    /**
     * Retrieves a paginated list of all VillageFisheriesProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageFisheriesProfile records
     */
    PageDTO<VillageFisheriesProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageFisheriesProfile record from the system.
     *
     * @param id     The unique identifier of the VillageFisheriesProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageFisheriesProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageFisheriesProfile to retrieve
     * @return The VillageFisheriesProfile data transfer object containing the requested information
     */
    VillageFisheriesProfileDTO getById(Long id);


    /**
     * Adds a list of productionSeasonList to a VillageFisheriesProfile identified by their ID.
     *
     * @param id               The ID of the VillageFisheriesProfile to add hobbies to
     * @param productionSeasonList  to be added
     * @since 1.0.0
     */
    void addProductionSeasonToVillageFisheriesProfile(Long id, List<ProductionSeasonDTO> productionSeasonList);


}
