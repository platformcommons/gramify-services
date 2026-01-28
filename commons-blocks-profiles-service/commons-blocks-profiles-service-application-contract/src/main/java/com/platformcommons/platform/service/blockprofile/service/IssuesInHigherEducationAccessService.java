package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing IssuesInHigherEducationAccess-related operations.
 * Provides CRUD operations and pagination support for IssuesInHigherEducationAccess entities.
 *
 * @since 1.0.0
 */

public interface IssuesInHigherEducationAccessService {

    /**
     * Retrieves all IssuesInHigherEducationAccesss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of IssuesInHigherEducationAccessDTO in the same order as retrieved
     */
    Set<IssuesInHigherEducationAccessDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new IssuesInHigherEducationAccess entry in the system.
     *
     * @param IssuesInHigherEducationAccess The IssuesInHigherEducationAccess information to be saved
     * @return The saved IssuesInHigherEducationAccess data
     * @since 1.0.0
     */
    IssuesInHigherEducationAccessDTO save(IssuesInHigherEducationAccessDTO IssuesInHigherEducationAccess);

    /**
     * Updates an existing IssuesInHigherEducationAccess entry.
     *
     * @param IssuesInHigherEducationAccess The IssuesInHigherEducationAccess information to be updated
     * @return The updated IssuesInHigherEducationAccess data
     * @since 1.0.0
     */
    IssuesInHigherEducationAccessDTO update(IssuesInHigherEducationAccessDTO IssuesInHigherEducationAccess);

    /**
     * Retrieves a paginated list of IssuesInHigherEducationAccesss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of IssuesInHigherEducationAccesss
     * @since 1.0.0
     */
    PageDTO<IssuesInHigherEducationAccessDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a IssuesInHigherEducationAccess by their ID with a specified reason.
     *
     * @param id     The ID of the IssuesInHigherEducationAccess to delete
     * @param reason The reason for deletion
     * @return IssuesInHigherEducationAccessDTO
     */
    IssuesInHigherEducationAccessDTO deleteById(Long id, String reason);

    /**
     * Retrieves a IssuesInHigherEducationAccess by their ID.
     *
     * @param id The ID of the IssuesInHigherEducationAccess to retrieve
     * @return The IssuesInHigherEducationAccess with the specified ID
     * @since 1.0.0
     */
    IssuesInHigherEducationAccessDTO getById(Long id);




}
