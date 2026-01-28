package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HouseholdIncomeAndPovertyProfil-related operations.
 * Provides CRUD operations and pagination support for HouseholdIncomeAndPovertyProfil entities.
 *
 * @since 1.0.0
 */

public interface HouseholdIncomeAndPovertyProfilService {

    /**
     * Retrieves all HouseholdIncomeAndPovertyProfils by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdIncomeAndPovertyProfilDTO in the same order as retrieved
     */
    Set<HouseholdIncomeAndPovertyProfilDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HouseholdIncomeAndPovertyProfil entry in the system.
     *
     * @param HouseholdIncomeAndPovertyProfil The HouseholdIncomeAndPovertyProfil information to be saved
     * @return The saved HouseholdIncomeAndPovertyProfil data
     * @since 1.0.0
     */
    HouseholdIncomeAndPovertyProfilDTO save(HouseholdIncomeAndPovertyProfilDTO HouseholdIncomeAndPovertyProfil);

    /**
     * Updates an existing HouseholdIncomeAndPovertyProfil entry.
     *
     * @param HouseholdIncomeAndPovertyProfil The HouseholdIncomeAndPovertyProfil information to be updated
     * @return The updated HouseholdIncomeAndPovertyProfil data
     * @since 1.0.0
     */
    HouseholdIncomeAndPovertyProfilDTO update(HouseholdIncomeAndPovertyProfilDTO HouseholdIncomeAndPovertyProfil);

    /**
     * Retrieves a paginated list of HouseholdIncomeAndPovertyProfils.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HouseholdIncomeAndPovertyProfils
     * @since 1.0.0
     */
    PageDTO<HouseholdIncomeAndPovertyProfilDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HouseholdIncomeAndPovertyProfil by their ID with a specified reason.
     *
     * @param id     The ID of the HouseholdIncomeAndPovertyProfil to delete
     * @param reason The reason for deletion
     * @return HouseholdIncomeAndPovertyProfilDTO
     */
    HouseholdIncomeAndPovertyProfilDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HouseholdIncomeAndPovertyProfil by their ID.
     *
     * @param id The ID of the HouseholdIncomeAndPovertyProfil to retrieve
     * @return The HouseholdIncomeAndPovertyProfil with the specified ID
     * @since 1.0.0
     */
    HouseholdIncomeAndPovertyProfilDTO getById(Long id);




}
