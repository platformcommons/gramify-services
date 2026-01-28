package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing OtherCommunityGroup-related operations in the system.
 * This interface provides methods for CRUD operations on OtherCommunityGroup data,
 * including creation, retrieval, update, and deletion of OtherCommunityGroup records.
 * It serves as the primary entry point for OtherCommunityGroup management functionality.
 */
public interface OtherCommunityGroupFacade {

    /**
     * Retrieves all OtherCommunityGroups by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of OtherCommunityGroupDTO
     */
    Set<OtherCommunityGroupDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new OtherCommunityGroup record in the system.
     *
     * @param OtherCommunityGroupDTO The OtherCommunityGroup data transfer object containing the information to be saved
     * @return The saved OtherCommunityGroup data with generated identifiers and any system-modified fields
     */
    OtherCommunityGroupDTO save(OtherCommunityGroupDTO OtherCommunityGroupDTO);

    /**
     * Updates an existing OtherCommunityGroup record in the system.
     *
     * @param OtherCommunityGroupDTO The OtherCommunityGroup data transfer object containing the updated information
     * @return The updated OtherCommunityGroup data as confirmed by the system
     */
    OtherCommunityGroupDTO update(OtherCommunityGroupDTO OtherCommunityGroupDTO);

    /**
     * Retrieves a paginated list of all OtherCommunityGroups in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested OtherCommunityGroup records
     */
    PageDTO<OtherCommunityGroupDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a OtherCommunityGroup record from the system.
     *
     * @param id     The unique identifier of the OtherCommunityGroup to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific OtherCommunityGroup by their unique identifier.
     *
     * @param id The unique identifier of the OtherCommunityGroup to retrieve
     * @return The OtherCommunityGroup data transfer object containing the requested information
     */
    OtherCommunityGroupDTO getById(Long id);



}
