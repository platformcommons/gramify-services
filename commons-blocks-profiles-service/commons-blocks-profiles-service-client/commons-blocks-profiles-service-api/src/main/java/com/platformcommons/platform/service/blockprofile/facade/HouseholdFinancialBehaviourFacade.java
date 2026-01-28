package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HouseholdFinancialBehaviour-related operations in the system.
 * This interface provides methods for CRUD operations on HouseholdFinancialBehaviour data,
 * including creation, retrieval, update, and deletion of HouseholdFinancialBehaviour records.
 * It serves as the primary entry point for HouseholdFinancialBehaviour management functionality.
 */
public interface HouseholdFinancialBehaviourFacade {

    /**
     * Retrieves all HouseholdFinancialBehaviours by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdFinancialBehaviourDTO
     */
    Set<HouseholdFinancialBehaviourDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HouseholdFinancialBehaviour record in the system.
     *
     * @param HouseholdFinancialBehaviourDTO The HouseholdFinancialBehaviour data transfer object containing the information to be saved
     * @return The saved HouseholdFinancialBehaviour data with generated identifiers and any system-modified fields
     */
    HouseholdFinancialBehaviourDTO save(HouseholdFinancialBehaviourDTO HouseholdFinancialBehaviourDTO);

    /**
     * Updates an existing HouseholdFinancialBehaviour record in the system.
     *
     * @param HouseholdFinancialBehaviourDTO The HouseholdFinancialBehaviour data transfer object containing the updated information
     * @return The updated HouseholdFinancialBehaviour data as confirmed by the system
     */
    HouseholdFinancialBehaviourDTO update(HouseholdFinancialBehaviourDTO HouseholdFinancialBehaviourDTO);

    /**
     * Retrieves a paginated list of all HouseholdFinancialBehaviours in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HouseholdFinancialBehaviour records
     */
    PageDTO<HouseholdFinancialBehaviourDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HouseholdFinancialBehaviour record from the system.
     *
     * @param id     The unique identifier of the HouseholdFinancialBehaviour to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HouseholdFinancialBehaviour by their unique identifier.
     *
     * @param id The unique identifier of the HouseholdFinancialBehaviour to retrieve
     * @return The HouseholdFinancialBehaviour data transfer object containing the requested information
     */
    HouseholdFinancialBehaviourDTO getById(Long id);


    /**
     * Adds a list of hHSavingsModeList to a HouseholdFinancialBehaviour identified by their ID.
     *
     * @param id               The ID of the HouseholdFinancialBehaviour to add hobbies to
     * @param hHSavingsModeList  to be added
     * @since 1.0.0
     */
    void addHHSavingsModeToHouseholdFinancialBehaviour(Long id, List<HHSavingsModeDTO> hHSavingsModeList);


}
