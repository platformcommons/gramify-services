package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing SchemeImplementationChallenge-related operations.
 * Provides CRUD operations and pagination support for SchemeImplementationChallenge entities.
 *
 * @since 1.0.0
 */

public interface SchemeImplementationChallengeService {

    /**
     * Retrieves all SchemeImplementationChallenges by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SchemeImplementationChallengeDTO in the same order as retrieved
     */
    Set<SchemeImplementationChallengeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new SchemeImplementationChallenge entry in the system.
     *
     * @param SchemeImplementationChallenge The SchemeImplementationChallenge information to be saved
     * @return The saved SchemeImplementationChallenge data
     * @since 1.0.0
     */
    SchemeImplementationChallengeDTO save(SchemeImplementationChallengeDTO SchemeImplementationChallenge);

    /**
     * Updates an existing SchemeImplementationChallenge entry.
     *
     * @param SchemeImplementationChallenge The SchemeImplementationChallenge information to be updated
     * @return The updated SchemeImplementationChallenge data
     * @since 1.0.0
     */
    SchemeImplementationChallengeDTO update(SchemeImplementationChallengeDTO SchemeImplementationChallenge);

    /**
     * Retrieves a paginated list of SchemeImplementationChallenges.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of SchemeImplementationChallenges
     * @since 1.0.0
     */
    PageDTO<SchemeImplementationChallengeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a SchemeImplementationChallenge by their ID with a specified reason.
     *
     * @param id     The ID of the SchemeImplementationChallenge to delete
     * @param reason The reason for deletion
     * @return SchemeImplementationChallengeDTO
     */
    SchemeImplementationChallengeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a SchemeImplementationChallenge by their ID.
     *
     * @param id The ID of the SchemeImplementationChallenge to retrieve
     * @return The SchemeImplementationChallenge with the specified ID
     * @since 1.0.0
     */
    SchemeImplementationChallengeDTO getById(Long id);




}
