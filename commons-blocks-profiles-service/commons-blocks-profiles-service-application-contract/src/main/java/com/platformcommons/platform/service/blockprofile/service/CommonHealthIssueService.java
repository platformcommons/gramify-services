package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing CommonHealthIssue-related operations.
 * Provides CRUD operations and pagination support for CommonHealthIssue entities.
 *
 * @since 1.0.0
 */

public interface CommonHealthIssueService {

    /**
     * Retrieves all CommonHealthIssues by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CommonHealthIssueDTO in the same order as retrieved
     */
    Set<CommonHealthIssueDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new CommonHealthIssue entry in the system.
     *
     * @param CommonHealthIssue The CommonHealthIssue information to be saved
     * @return The saved CommonHealthIssue data
     * @since 1.0.0
     */
    CommonHealthIssueDTO save(CommonHealthIssueDTO CommonHealthIssue);

    /**
     * Updates an existing CommonHealthIssue entry.
     *
     * @param CommonHealthIssue The CommonHealthIssue information to be updated
     * @return The updated CommonHealthIssue data
     * @since 1.0.0
     */
    CommonHealthIssueDTO update(CommonHealthIssueDTO CommonHealthIssue);

    /**
     * Retrieves a paginated list of CommonHealthIssues.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of CommonHealthIssues
     * @since 1.0.0
     */
    PageDTO<CommonHealthIssueDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a CommonHealthIssue by their ID with a specified reason.
     *
     * @param id     The ID of the CommonHealthIssue to delete
     * @param reason The reason for deletion
     * @return CommonHealthIssueDTO
     */
    CommonHealthIssueDTO deleteById(Long id, String reason);

    /**
     * Retrieves a CommonHealthIssue by their ID.
     *
     * @param id The ID of the CommonHealthIssue to retrieve
     * @return The CommonHealthIssue with the specified ID
     * @since 1.0.0
     */
    CommonHealthIssueDTO getById(Long id);




}
