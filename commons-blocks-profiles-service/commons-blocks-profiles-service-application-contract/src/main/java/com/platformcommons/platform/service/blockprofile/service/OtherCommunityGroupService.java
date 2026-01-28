package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing OtherCommunityGroup-related operations.
 * Provides CRUD operations and pagination support for OtherCommunityGroup entities.
 *
 * @since 1.0.0
 */

public interface OtherCommunityGroupService {

    /**
     * Retrieves all OtherCommunityGroups by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of OtherCommunityGroupDTO in the same order as retrieved
     */
    Set<OtherCommunityGroupDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new OtherCommunityGroup entry in the system.
     *
     * @param OtherCommunityGroup The OtherCommunityGroup information to be saved
     * @return The saved OtherCommunityGroup data
     * @since 1.0.0
     */
    OtherCommunityGroupDTO save(OtherCommunityGroupDTO OtherCommunityGroup);

    /**
     * Updates an existing OtherCommunityGroup entry.
     *
     * @param OtherCommunityGroup The OtherCommunityGroup information to be updated
     * @return The updated OtherCommunityGroup data
     * @since 1.0.0
     */
    OtherCommunityGroupDTO update(OtherCommunityGroupDTO OtherCommunityGroup);

    /**
     * Retrieves a paginated list of OtherCommunityGroups.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of OtherCommunityGroups
     * @since 1.0.0
     */
    PageDTO<OtherCommunityGroupDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a OtherCommunityGroup by their ID with a specified reason.
     *
     * @param id     The ID of the OtherCommunityGroup to delete
     * @param reason The reason for deletion
     * @return OtherCommunityGroupDTO
     */
    OtherCommunityGroupDTO deleteById(Long id, String reason);

    /**
     * Retrieves a OtherCommunityGroup by their ID.
     *
     * @param id The ID of the OtherCommunityGroup to retrieve
     * @return The OtherCommunityGroup with the specified ID
     * @since 1.0.0
     */
    OtherCommunityGroupDTO getById(Long id);




}
