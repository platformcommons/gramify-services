package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageCropProfile-related operations.
 * Provides CRUD operations and pagination support for VillageCropProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageCropProfileService {

    /**
     * Retrieves all VillageCropProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCropProfileDTO in the same order as retrieved
     */
    Set<VillageCropProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageCropProfile entry in the system.
     *
     * @param VillageCropProfile The VillageCropProfile information to be saved
     * @return The saved VillageCropProfile data
     * @since 1.0.0
     */
    VillageCropProfileDTO save(VillageCropProfileDTO VillageCropProfile);

    /**
     * Updates an existing VillageCropProfile entry.
     *
     * @param VillageCropProfile The VillageCropProfile information to be updated
     * @return The updated VillageCropProfile data
     * @since 1.0.0
     */
    VillageCropProfileDTO update(VillageCropProfileDTO VillageCropProfile);

    /**
     * Retrieves a paginated list of VillageCropProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageCropProfiles
     * @since 1.0.0
     */
    PageDTO<VillageCropProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageCropProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageCropProfile to delete
     * @param reason The reason for deletion
     * @return VillageCropProfileDTO
     */
    VillageCropProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageCropProfile by their ID.
     *
     * @param id The ID of the VillageCropProfile to retrieve
     * @return The VillageCropProfile with the specified ID
     * @since 1.0.0
     */
    VillageCropProfileDTO getById(Long id);


    /**
     * Adds a list of cropList to a VillageCropProfile identified by their ID.
     *
     * @param id               The ID of the VillageCropProfile to add hobbies to
     * @param cropList  to be added
     * @since 1.0.0
     */
    void addCropToVillageCropProfile(Long id, List<CropDTO> cropList);

    /**
     * Adds a list of croppingSeasonList to a VillageCropProfile identified by their ID.
     *
     * @param id               The ID of the VillageCropProfile to add hobbies to
     * @param croppingSeasonList  to be added
     * @since 1.0.0
     */
    void addCroppingSeasonToVillageCropProfile(Long id, List<CroppingSeasonDTO> croppingSeasonList);



}
