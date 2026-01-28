package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageLandResourceProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageLandResourceProfile data,
 * including creation, retrieval, update, and deletion of VillageLandResourceProfile records.
 * It serves as the primary entry point for VillageLandResourceProfile management functionality.
 */
public interface VillageLandResourceProfileFacade {

    /**
     * Retrieves all VillageLandResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageLandResourceProfileDTO
     */
    Set<VillageLandResourceProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageLandResourceProfile record in the system.
     *
     * @param VillageLandResourceProfileDTO The VillageLandResourceProfile data transfer object containing the information to be saved
     * @return The saved VillageLandResourceProfile data with generated identifiers and any system-modified fields
     */
    VillageLandResourceProfileDTO save(VillageLandResourceProfileDTO VillageLandResourceProfileDTO);

    /**
     * Updates an existing VillageLandResourceProfile record in the system.
     *
     * @param VillageLandResourceProfileDTO The VillageLandResourceProfile data transfer object containing the updated information
     * @return The updated VillageLandResourceProfile data as confirmed by the system
     */
    VillageLandResourceProfileDTO update(VillageLandResourceProfileDTO VillageLandResourceProfileDTO);

    /**
     * Retrieves a paginated list of all VillageLandResourceProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageLandResourceProfile records
     */
    PageDTO<VillageLandResourceProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageLandResourceProfile record from the system.
     *
     * @param id     The unique identifier of the VillageLandResourceProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageLandResourceProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageLandResourceProfile to retrieve
     * @return The VillageLandResourceProfile data transfer object containing the requested information
     */
    VillageLandResourceProfileDTO getById(Long id);


    /**
     * Adds a list of villageSoilTypeList to a VillageLandResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageLandResourceProfile to add hobbies to
     * @param villageSoilTypeList  to be added
     * @since 1.0.0
     */
    void addVillageSoilTypeToVillageLandResourceProfile(Long id, List<VillageSoilTypeDTO> villageSoilTypeList);


}
