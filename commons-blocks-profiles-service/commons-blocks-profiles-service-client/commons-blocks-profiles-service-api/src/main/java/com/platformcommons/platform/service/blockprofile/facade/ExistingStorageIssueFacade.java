package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing ExistingStorageIssue-related operations in the system.
 * This interface provides methods for CRUD operations on ExistingStorageIssue data,
 * including creation, retrieval, update, and deletion of ExistingStorageIssue records.
 * It serves as the primary entry point for ExistingStorageIssue management functionality.
 */
public interface ExistingStorageIssueFacade {

    /**
     * Retrieves all ExistingStorageIssues by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ExistingStorageIssueDTO
     */
    Set<ExistingStorageIssueDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new ExistingStorageIssue record in the system.
     *
     * @param ExistingStorageIssueDTO The ExistingStorageIssue data transfer object containing the information to be saved
     * @return The saved ExistingStorageIssue data with generated identifiers and any system-modified fields
     */
    ExistingStorageIssueDTO save(ExistingStorageIssueDTO ExistingStorageIssueDTO);

    /**
     * Updates an existing ExistingStorageIssue record in the system.
     *
     * @param ExistingStorageIssueDTO The ExistingStorageIssue data transfer object containing the updated information
     * @return The updated ExistingStorageIssue data as confirmed by the system
     */
    ExistingStorageIssueDTO update(ExistingStorageIssueDTO ExistingStorageIssueDTO);

    /**
     * Retrieves a paginated list of all ExistingStorageIssues in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested ExistingStorageIssue records
     */
    PageDTO<ExistingStorageIssueDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a ExistingStorageIssue record from the system.
     *
     * @param id     The unique identifier of the ExistingStorageIssue to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific ExistingStorageIssue by their unique identifier.
     *
     * @param id The unique identifier of the ExistingStorageIssue to retrieve
     * @return The ExistingStorageIssue data transfer object containing the requested information
     */
    ExistingStorageIssueDTO getById(Long id);



}
