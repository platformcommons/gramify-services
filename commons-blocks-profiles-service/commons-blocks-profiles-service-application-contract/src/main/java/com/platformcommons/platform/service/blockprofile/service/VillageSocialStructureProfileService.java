package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageSocialStructureProfile-related operations.
 * Provides CRUD operations and pagination support for VillageSocialStructureProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageSocialStructureProfileService {

    /**
     * Retrieves all VillageSocialStructureProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageSocialStructureProfileDTO in the same order as retrieved
     */
    Set<VillageSocialStructureProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageSocialStructureProfile entry in the system.
     *
     * @param VillageSocialStructureProfile The VillageSocialStructureProfile information to be saved
     * @return The saved VillageSocialStructureProfile data
     * @since 1.0.0
     */
    VillageSocialStructureProfileDTO save(VillageSocialStructureProfileDTO VillageSocialStructureProfile);

    /**
     * Updates an existing VillageSocialStructureProfile entry.
     *
     * @param VillageSocialStructureProfile The VillageSocialStructureProfile information to be updated
     * @return The updated VillageSocialStructureProfile data
     * @since 1.0.0
     */
    VillageSocialStructureProfileDTO update(VillageSocialStructureProfileDTO VillageSocialStructureProfile);

    /**
     * Retrieves a paginated list of VillageSocialStructureProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageSocialStructureProfiles
     * @since 1.0.0
     */
    PageDTO<VillageSocialStructureProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageSocialStructureProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageSocialStructureProfile to delete
     * @param reason The reason for deletion
     * @return VillageSocialStructureProfileDTO
     */
    VillageSocialStructureProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageSocialStructureProfile by their ID.
     *
     * @param id The ID of the VillageSocialStructureProfile to retrieve
     * @return The VillageSocialStructureProfile with the specified ID
     * @since 1.0.0
     */
    VillageSocialStructureProfileDTO getById(Long id);


    /**
     * Adds a list of otherCommunityGroupList to a VillageSocialStructureProfile identified by their ID.
     *
     * @param id               The ID of the VillageSocialStructureProfile to add hobbies to
     * @param otherCommunityGroupList  to be added
     * @since 1.0.0
     */
    void addOtherCommunityGroupToVillageSocialStructureProfile(Long id, List<OtherCommunityGroupDTO> otherCommunityGroupList);



}
