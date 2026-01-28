package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing Household-related operations.
 * Provides CRUD operations and pagination support for Household entities.
 *
 * @since 1.0.0
 */

public interface HouseholdService {

    /**
     * Retrieves all Households by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdDTO in the same order as retrieved
     */
    Set<HouseholdDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new Household entry in the system.
     *
     * @param Household The Household information to be saved
     * @return The saved Household data
     * @since 1.0.0
     */
    HouseholdDTO save(HouseholdDTO Household);

    /**
     * Updates an existing Household entry.
     *
     * @param Household The Household information to be updated
     * @return The updated Household data
     * @since 1.0.0
     */
    HouseholdDTO update(HouseholdDTO Household);

    /**
     * Retrieves a paginated list of Households.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of Households
     * @since 1.0.0
     */
    PageDTO<HouseholdDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a Household by their ID with a specified reason.
     *
     * @param id     The ID of the Household to delete
     * @param reason The reason for deletion
     * @return HouseholdDTO
     */
    HouseholdDTO deleteById(Long id, String reason);

    /**
     * Retrieves a Household by their ID.
     *
     * @param id The ID of the Household to retrieve
     * @return The Household with the specified ID
     * @since 1.0.0
     */
    HouseholdDTO getById(Long id);


    /**
     * Adds a list of hHLoanSourceList to a Household identified by their ID.
     *
     * @param id               The ID of the Household to add hobbies to
     * @param hHLoanSourceList  to be added
     * @since 1.0.0
     */
    void addHHLoanSourceToHousehold(Long id, List<HHLoanSourceDTO> hHLoanSourceList);



}
