package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing ExistingStorageIssue-related operations.
 * Provides CRUD operations and pagination support for ExistingStorageIssue entities.
 *
 * @since 1.0.0
 */

public interface ExistingStorageIssueService {

    /**
     * Retrieves all ExistingStorageIssues by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ExistingStorageIssueDTO in the same order as retrieved
     */
    Set<ExistingStorageIssueDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new ExistingStorageIssue entry in the system.
     *
     * @param ExistingStorageIssue The ExistingStorageIssue information to be saved
     * @return The saved ExistingStorageIssue data
     * @since 1.0.0
     */
    ExistingStorageIssueDTO save(ExistingStorageIssueDTO ExistingStorageIssue);

    /**
     * Updates an existing ExistingStorageIssue entry.
     *
     * @param ExistingStorageIssue The ExistingStorageIssue information to be updated
     * @return The updated ExistingStorageIssue data
     * @since 1.0.0
     */
    ExistingStorageIssueDTO update(ExistingStorageIssueDTO ExistingStorageIssue);

    /**
     * Retrieves a paginated list of ExistingStorageIssues.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of ExistingStorageIssues
     * @since 1.0.0
     */
    PageDTO<ExistingStorageIssueDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a ExistingStorageIssue by their ID with a specified reason.
     *
     * @param id     The ID of the ExistingStorageIssue to delete
     * @param reason The reason for deletion
     * @return ExistingStorageIssueDTO
     */
    ExistingStorageIssueDTO deleteById(Long id, String reason);

    /**
     * Retrieves a ExistingStorageIssue by their ID.
     *
     * @param id The ID of the ExistingStorageIssue to retrieve
     * @return The ExistingStorageIssue with the specified ID
     * @since 1.0.0
     */
    ExistingStorageIssueDTO getById(Long id);




}
