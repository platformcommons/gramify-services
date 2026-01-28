package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageFisheriesProfile-related operations.
 * Provides CRUD operations and pagination support for VillageFisheriesProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageFisheriesProfileService {

    /**
     * Retrieves all VillageFisheriesProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageFisheriesProfileDTO in the same order as retrieved
     */
    Set<VillageFisheriesProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageFisheriesProfile entry in the system.
     *
     * @param VillageFisheriesProfile The VillageFisheriesProfile information to be saved
     * @return The saved VillageFisheriesProfile data
     * @since 1.0.0
     */
    VillageFisheriesProfileDTO save(VillageFisheriesProfileDTO VillageFisheriesProfile);

    /**
     * Updates an existing VillageFisheriesProfile entry.
     *
     * @param VillageFisheriesProfile The VillageFisheriesProfile information to be updated
     * @return The updated VillageFisheriesProfile data
     * @since 1.0.0
     */
    VillageFisheriesProfileDTO update(VillageFisheriesProfileDTO VillageFisheriesProfile);

    /**
     * Retrieves a paginated list of VillageFisheriesProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageFisheriesProfiles
     * @since 1.0.0
     */
    PageDTO<VillageFisheriesProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageFisheriesProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageFisheriesProfile to delete
     * @param reason The reason for deletion
     * @return VillageFisheriesProfileDTO
     */
    VillageFisheriesProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageFisheriesProfile by their ID.
     *
     * @param id The ID of the VillageFisheriesProfile to retrieve
     * @return The VillageFisheriesProfile with the specified ID
     * @since 1.0.0
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
