package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing SchemeImplementationChallenge-related operations in the system.
 * This interface provides methods for CRUD operations on SchemeImplementationChallenge data,
 * including creation, retrieval, update, and deletion of SchemeImplementationChallenge records.
 * It serves as the primary entry point for SchemeImplementationChallenge management functionality.
 */
public interface SchemeImplementationChallengeFacade {

    /**
     * Retrieves all SchemeImplementationChallenges by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SchemeImplementationChallengeDTO
     */
    Set<SchemeImplementationChallengeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new SchemeImplementationChallenge record in the system.
     *
     * @param SchemeImplementationChallengeDTO The SchemeImplementationChallenge data transfer object containing the information to be saved
     * @return The saved SchemeImplementationChallenge data with generated identifiers and any system-modified fields
     */
    SchemeImplementationChallengeDTO save(SchemeImplementationChallengeDTO SchemeImplementationChallengeDTO);

    /**
     * Updates an existing SchemeImplementationChallenge record in the system.
     *
     * @param SchemeImplementationChallengeDTO The SchemeImplementationChallenge data transfer object containing the updated information
     * @return The updated SchemeImplementationChallenge data as confirmed by the system
     */
    SchemeImplementationChallengeDTO update(SchemeImplementationChallengeDTO SchemeImplementationChallengeDTO);

    /**
     * Retrieves a paginated list of all SchemeImplementationChallenges in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested SchemeImplementationChallenge records
     */
    PageDTO<SchemeImplementationChallengeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a SchemeImplementationChallenge record from the system.
     *
     * @param id     The unique identifier of the SchemeImplementationChallenge to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific SchemeImplementationChallenge by their unique identifier.
     *
     * @param id The unique identifier of the SchemeImplementationChallenge to retrieve
     * @return The SchemeImplementationChallenge data transfer object containing the requested information
     */
    SchemeImplementationChallengeDTO getById(Long id);



}
