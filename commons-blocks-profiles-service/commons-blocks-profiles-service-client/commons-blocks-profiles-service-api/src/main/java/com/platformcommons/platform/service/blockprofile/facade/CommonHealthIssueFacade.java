package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing CommonHealthIssue-related operations in the system.
 * This interface provides methods for CRUD operations on CommonHealthIssue data,
 * including creation, retrieval, update, and deletion of CommonHealthIssue records.
 * It serves as the primary entry point for CommonHealthIssue management functionality.
 */
public interface CommonHealthIssueFacade {

    /**
     * Retrieves all CommonHealthIssues by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CommonHealthIssueDTO
     */
    Set<CommonHealthIssueDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new CommonHealthIssue record in the system.
     *
     * @param CommonHealthIssueDTO The CommonHealthIssue data transfer object containing the information to be saved
     * @return The saved CommonHealthIssue data with generated identifiers and any system-modified fields
     */
    CommonHealthIssueDTO save(CommonHealthIssueDTO CommonHealthIssueDTO);

    /**
     * Updates an existing CommonHealthIssue record in the system.
     *
     * @param CommonHealthIssueDTO The CommonHealthIssue data transfer object containing the updated information
     * @return The updated CommonHealthIssue data as confirmed by the system
     */
    CommonHealthIssueDTO update(CommonHealthIssueDTO CommonHealthIssueDTO);

    /**
     * Retrieves a paginated list of all CommonHealthIssues in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested CommonHealthIssue records
     */
    PageDTO<CommonHealthIssueDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a CommonHealthIssue record from the system.
     *
     * @param id     The unique identifier of the CommonHealthIssue to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific CommonHealthIssue by their unique identifier.
     *
     * @param id The unique identifier of the CommonHealthIssue to retrieve
     * @return The CommonHealthIssue data transfer object containing the requested information
     */
    CommonHealthIssueDTO getById(Long id);



}
