package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing Household-related operations in the system.
 * This interface provides methods for CRUD operations on Household data,
 * including creation, retrieval, update, and deletion of Household records.
 * It serves as the primary entry point for Household management functionality.
 */
public interface HouseholdFacade {

    /**
     * Retrieves all Households by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdDTO
     */
    Set<HouseholdDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new Household record in the system.
     *
     * @param HouseholdDTO The Household data transfer object containing the information to be saved
     * @return The saved Household data with generated identifiers and any system-modified fields
     */
    HouseholdDTO save(HouseholdDTO HouseholdDTO);

    /**
     * Updates an existing Household record in the system.
     *
     * @param HouseholdDTO The Household data transfer object containing the updated information
     * @return The updated Household data as confirmed by the system
     */
    HouseholdDTO update(HouseholdDTO HouseholdDTO);

    /**
     * Retrieves a paginated list of all Households in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested Household records
     */
    PageDTO<HouseholdDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a Household record from the system.
     *
     * @param id     The unique identifier of the Household to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific Household by their unique identifier.
     *
     * @param id The unique identifier of the Household to retrieve
     * @return The Household data transfer object containing the requested information
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
