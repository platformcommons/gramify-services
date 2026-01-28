package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageSocialStructureProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageSocialStructureProfile data,
 * including creation, retrieval, update, and deletion of VillageSocialStructureProfile records.
 * It serves as the primary entry point for VillageSocialStructureProfile management functionality.
 */
public interface VillageSocialStructureProfileFacade {

    /**
     * Retrieves all VillageSocialStructureProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageSocialStructureProfileDTO
     */
    Set<VillageSocialStructureProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageSocialStructureProfile record in the system.
     *
     * @param VillageSocialStructureProfileDTO The VillageSocialStructureProfile data transfer object containing the information to be saved
     * @return The saved VillageSocialStructureProfile data with generated identifiers and any system-modified fields
     */
    VillageSocialStructureProfileDTO save(VillageSocialStructureProfileDTO VillageSocialStructureProfileDTO);

    /**
     * Updates an existing VillageSocialStructureProfile record in the system.
     *
     * @param VillageSocialStructureProfileDTO The VillageSocialStructureProfile data transfer object containing the updated information
     * @return The updated VillageSocialStructureProfile data as confirmed by the system
     */
    VillageSocialStructureProfileDTO update(VillageSocialStructureProfileDTO VillageSocialStructureProfileDTO);

    /**
     * Retrieves a paginated list of all VillageSocialStructureProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageSocialStructureProfile records
     */
    PageDTO<VillageSocialStructureProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageSocialStructureProfile record from the system.
     *
     * @param id     The unique identifier of the VillageSocialStructureProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageSocialStructureProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageSocialStructureProfile to retrieve
     * @return The VillageSocialStructureProfile data transfer object containing the requested information
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
