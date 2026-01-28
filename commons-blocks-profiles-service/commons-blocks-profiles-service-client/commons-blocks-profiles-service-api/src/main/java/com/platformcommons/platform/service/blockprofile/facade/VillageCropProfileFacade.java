package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageCropProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageCropProfile data,
 * including creation, retrieval, update, and deletion of VillageCropProfile records.
 * It serves as the primary entry point for VillageCropProfile management functionality.
 */
public interface VillageCropProfileFacade {

    /**
     * Retrieves all VillageCropProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCropProfileDTO
     */
    Set<VillageCropProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageCropProfile record in the system.
     *
     * @param VillageCropProfileDTO The VillageCropProfile data transfer object containing the information to be saved
     * @return The saved VillageCropProfile data with generated identifiers and any system-modified fields
     */
    VillageCropProfileDTO save(VillageCropProfileDTO VillageCropProfileDTO);

    /**
     * Updates an existing VillageCropProfile record in the system.
     *
     * @param VillageCropProfileDTO The VillageCropProfile data transfer object containing the updated information
     * @return The updated VillageCropProfile data as confirmed by the system
     */
    VillageCropProfileDTO update(VillageCropProfileDTO VillageCropProfileDTO);

    /**
     * Retrieves a paginated list of all VillageCropProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageCropProfile records
     */
    PageDTO<VillageCropProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageCropProfile record from the system.
     *
     * @param id     The unique identifier of the VillageCropProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageCropProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageCropProfile to retrieve
     * @return The VillageCropProfile data transfer object containing the requested information
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
