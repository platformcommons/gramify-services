package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing PurposeOfCredit-related operations.
 * Provides CRUD operations and pagination support for PurposeOfCredit entities.
 *
 * @since 1.0.0
 */

public interface PurposeOfCreditService {

    /**
     * Retrieves all PurposeOfCredits by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of PurposeOfCreditDTO in the same order as retrieved
     */
    Set<PurposeOfCreditDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new PurposeOfCredit entry in the system.
     *
     * @param PurposeOfCredit The PurposeOfCredit information to be saved
     * @return The saved PurposeOfCredit data
     * @since 1.0.0
     */
    PurposeOfCreditDTO save(PurposeOfCreditDTO PurposeOfCredit);

    /**
     * Updates an existing PurposeOfCredit entry.
     *
     * @param PurposeOfCredit The PurposeOfCredit information to be updated
     * @return The updated PurposeOfCredit data
     * @since 1.0.0
     */
    PurposeOfCreditDTO update(PurposeOfCreditDTO PurposeOfCredit);

    /**
     * Retrieves a paginated list of PurposeOfCredits.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of PurposeOfCredits
     * @since 1.0.0
     */
    PageDTO<PurposeOfCreditDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a PurposeOfCredit by their ID with a specified reason.
     *
     * @param id     The ID of the PurposeOfCredit to delete
     * @param reason The reason for deletion
     * @return PurposeOfCreditDTO
     */
    PurposeOfCreditDTO deleteById(Long id, String reason);

    /**
     * Retrieves a PurposeOfCredit by their ID.
     *
     * @param id The ID of the PurposeOfCredit to retrieve
     * @return The PurposeOfCredit with the specified ID
     * @since 1.0.0
     */
    PurposeOfCreditDTO getById(Long id);




}
