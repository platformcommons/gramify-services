package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing IssuesInHigherEducationAccess-related operations in the system.
 * This interface provides methods for CRUD operations on IssuesInHigherEducationAccess data,
 * including creation, retrieval, update, and deletion of IssuesInHigherEducationAccess records.
 * It serves as the primary entry point for IssuesInHigherEducationAccess management functionality.
 */
public interface IssuesInHigherEducationAccessFacade {

    /**
     * Retrieves all IssuesInHigherEducationAccesss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of IssuesInHigherEducationAccessDTO
     */
    Set<IssuesInHigherEducationAccessDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new IssuesInHigherEducationAccess record in the system.
     *
     * @param IssuesInHigherEducationAccessDTO The IssuesInHigherEducationAccess data transfer object containing the information to be saved
     * @return The saved IssuesInHigherEducationAccess data with generated identifiers and any system-modified fields
     */
    IssuesInHigherEducationAccessDTO save(IssuesInHigherEducationAccessDTO IssuesInHigherEducationAccessDTO);

    /**
     * Updates an existing IssuesInHigherEducationAccess record in the system.
     *
     * @param IssuesInHigherEducationAccessDTO The IssuesInHigherEducationAccess data transfer object containing the updated information
     * @return The updated IssuesInHigherEducationAccess data as confirmed by the system
     */
    IssuesInHigherEducationAccessDTO update(IssuesInHigherEducationAccessDTO IssuesInHigherEducationAccessDTO);

    /**
     * Retrieves a paginated list of all IssuesInHigherEducationAccesss in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested IssuesInHigherEducationAccess records
     */
    PageDTO<IssuesInHigherEducationAccessDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a IssuesInHigherEducationAccess record from the system.
     *
     * @param id     The unique identifier of the IssuesInHigherEducationAccess to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific IssuesInHigherEducationAccess by their unique identifier.
     *
     * @param id The unique identifier of the IssuesInHigherEducationAccess to retrieve
     * @return The IssuesInHigherEducationAccess data transfer object containing the requested information
     */
    IssuesInHigherEducationAccessDTO getById(Long id);



}
