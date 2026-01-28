package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HouseholdIncomeAndPovertyProfil-related operations in the system.
 * This interface provides methods for CRUD operations on HouseholdIncomeAndPovertyProfil data,
 * including creation, retrieval, update, and deletion of HouseholdIncomeAndPovertyProfil records.
 * It serves as the primary entry point for HouseholdIncomeAndPovertyProfil management functionality.
 */
public interface HouseholdIncomeAndPovertyProfilFacade {

    /**
     * Retrieves all HouseholdIncomeAndPovertyProfils by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdIncomeAndPovertyProfilDTO
     */
    Set<HouseholdIncomeAndPovertyProfilDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HouseholdIncomeAndPovertyProfil record in the system.
     *
     * @param HouseholdIncomeAndPovertyProfilDTO The HouseholdIncomeAndPovertyProfil data transfer object containing the information to be saved
     * @return The saved HouseholdIncomeAndPovertyProfil data with generated identifiers and any system-modified fields
     */
    HouseholdIncomeAndPovertyProfilDTO save(HouseholdIncomeAndPovertyProfilDTO HouseholdIncomeAndPovertyProfilDTO);

    /**
     * Updates an existing HouseholdIncomeAndPovertyProfil record in the system.
     *
     * @param HouseholdIncomeAndPovertyProfilDTO The HouseholdIncomeAndPovertyProfil data transfer object containing the updated information
     * @return The updated HouseholdIncomeAndPovertyProfil data as confirmed by the system
     */
    HouseholdIncomeAndPovertyProfilDTO update(HouseholdIncomeAndPovertyProfilDTO HouseholdIncomeAndPovertyProfilDTO);

    /**
     * Retrieves a paginated list of all HouseholdIncomeAndPovertyProfils in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HouseholdIncomeAndPovertyProfil records
     */
    PageDTO<HouseholdIncomeAndPovertyProfilDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HouseholdIncomeAndPovertyProfil record from the system.
     *
     * @param id     The unique identifier of the HouseholdIncomeAndPovertyProfil to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HouseholdIncomeAndPovertyProfil by their unique identifier.
     *
     * @param id The unique identifier of the HouseholdIncomeAndPovertyProfil to retrieve
     * @return The HouseholdIncomeAndPovertyProfil data transfer object containing the requested information
     */
    HouseholdIncomeAndPovertyProfilDTO getById(Long id);



}
