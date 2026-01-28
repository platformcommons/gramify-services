package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageLandResourceProfile-related operations.
 * Provides CRUD operations and pagination support for VillageLandResourceProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageLandResourceProfileService {

    /**
     * Retrieves all VillageLandResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageLandResourceProfileDTO in the same order as retrieved
     */
    Set<VillageLandResourceProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageLandResourceProfile entry in the system.
     *
     * @param VillageLandResourceProfile The VillageLandResourceProfile information to be saved
     * @return The saved VillageLandResourceProfile data
     * @since 1.0.0
     */
    VillageLandResourceProfileDTO save(VillageLandResourceProfileDTO VillageLandResourceProfile);

    /**
     * Updates an existing VillageLandResourceProfile entry.
     *
     * @param VillageLandResourceProfile The VillageLandResourceProfile information to be updated
     * @return The updated VillageLandResourceProfile data
     * @since 1.0.0
     */
    VillageLandResourceProfileDTO update(VillageLandResourceProfileDTO VillageLandResourceProfile);

    /**
     * Retrieves a paginated list of VillageLandResourceProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageLandResourceProfiles
     * @since 1.0.0
     */
    PageDTO<VillageLandResourceProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageLandResourceProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageLandResourceProfile to delete
     * @param reason The reason for deletion
     * @return VillageLandResourceProfileDTO
     */
    VillageLandResourceProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageLandResourceProfile by their ID.
     *
     * @param id The ID of the VillageLandResourceProfile to retrieve
     * @return The VillageLandResourceProfile with the specified ID
     * @since 1.0.0
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
