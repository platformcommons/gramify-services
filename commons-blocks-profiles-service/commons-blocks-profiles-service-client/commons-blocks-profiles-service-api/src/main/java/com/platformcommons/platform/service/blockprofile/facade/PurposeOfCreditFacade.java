package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing PurposeOfCredit-related operations in the system.
 * This interface provides methods for CRUD operations on PurposeOfCredit data,
 * including creation, retrieval, update, and deletion of PurposeOfCredit records.
 * It serves as the primary entry point for PurposeOfCredit management functionality.
 */
public interface PurposeOfCreditFacade {

    /**
     * Retrieves all PurposeOfCredits by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of PurposeOfCreditDTO
     */
    Set<PurposeOfCreditDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new PurposeOfCredit record in the system.
     *
     * @param PurposeOfCreditDTO The PurposeOfCredit data transfer object containing the information to be saved
     * @return The saved PurposeOfCredit data with generated identifiers and any system-modified fields
     */
    PurposeOfCreditDTO save(PurposeOfCreditDTO PurposeOfCreditDTO);

    /**
     * Updates an existing PurposeOfCredit record in the system.
     *
     * @param PurposeOfCreditDTO The PurposeOfCredit data transfer object containing the updated information
     * @return The updated PurposeOfCredit data as confirmed by the system
     */
    PurposeOfCreditDTO update(PurposeOfCreditDTO PurposeOfCreditDTO);

    /**
     * Retrieves a paginated list of all PurposeOfCredits in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested PurposeOfCredit records
     */
    PageDTO<PurposeOfCreditDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a PurposeOfCredit record from the system.
     *
     * @param id     The unique identifier of the PurposeOfCredit to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific PurposeOfCredit by their unique identifier.
     *
     * @param id The unique identifier of the PurposeOfCredit to retrieve
     * @return The PurposeOfCredit data transfer object containing the requested information
     */
    PurposeOfCreditDTO getById(Long id);



}
