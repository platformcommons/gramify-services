package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HouseholdFinancialBehaviour-related operations.
 * Provides CRUD operations and pagination support for HouseholdFinancialBehaviour entities.
 *
 * @since 1.0.0
 */

public interface HouseholdFinancialBehaviourService {

    /**
     * Retrieves all HouseholdFinancialBehaviours by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdFinancialBehaviourDTO in the same order as retrieved
     */
    Set<HouseholdFinancialBehaviourDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HouseholdFinancialBehaviour entry in the system.
     *
     * @param HouseholdFinancialBehaviour The HouseholdFinancialBehaviour information to be saved
     * @return The saved HouseholdFinancialBehaviour data
     * @since 1.0.0
     */
    HouseholdFinancialBehaviourDTO save(HouseholdFinancialBehaviourDTO HouseholdFinancialBehaviour);

    /**
     * Updates an existing HouseholdFinancialBehaviour entry.
     *
     * @param HouseholdFinancialBehaviour The HouseholdFinancialBehaviour information to be updated
     * @return The updated HouseholdFinancialBehaviour data
     * @since 1.0.0
     */
    HouseholdFinancialBehaviourDTO update(HouseholdFinancialBehaviourDTO HouseholdFinancialBehaviour);

    /**
     * Retrieves a paginated list of HouseholdFinancialBehaviours.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HouseholdFinancialBehaviours
     * @since 1.0.0
     */
    PageDTO<HouseholdFinancialBehaviourDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HouseholdFinancialBehaviour by their ID with a specified reason.
     *
     * @param id     The ID of the HouseholdFinancialBehaviour to delete
     * @param reason The reason for deletion
     * @return HouseholdFinancialBehaviourDTO
     */
    HouseholdFinancialBehaviourDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HouseholdFinancialBehaviour by their ID.
     *
     * @param id The ID of the HouseholdFinancialBehaviour to retrieve
     * @return The HouseholdFinancialBehaviour with the specified ID
     * @since 1.0.0
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
