package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageFinancialInstitutionProf-related operations.
 * Provides CRUD operations and pagination support for VillageFinancialInstitutionProf entities.
 *
 * @since 1.0.0
 */

public interface VillageFinancialInstitutionProfService {

    /**
     * Retrieves all VillageFinancialInstitutionProfs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageFinancialInstitutionProfDTO in the same order as retrieved
     */
    Set<VillageFinancialInstitutionProfDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageFinancialInstitutionProf entry in the system.
     *
     * @param VillageFinancialInstitutionProf The VillageFinancialInstitutionProf information to be saved
     * @return The saved VillageFinancialInstitutionProf data
     * @since 1.0.0
     */
    VillageFinancialInstitutionProfDTO save(VillageFinancialInstitutionProfDTO VillageFinancialInstitutionProf);

    /**
     * Updates an existing VillageFinancialInstitutionProf entry.
     *
     * @param VillageFinancialInstitutionProf The VillageFinancialInstitutionProf information to be updated
     * @return The updated VillageFinancialInstitutionProf data
     * @since 1.0.0
     */
    VillageFinancialInstitutionProfDTO update(VillageFinancialInstitutionProfDTO VillageFinancialInstitutionProf);

    /**
     * Retrieves a paginated list of VillageFinancialInstitutionProfs.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageFinancialInstitutionProfs
     * @since 1.0.0
     */
    PageDTO<VillageFinancialInstitutionProfDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageFinancialInstitutionProf by their ID with a specified reason.
     *
     * @param id     The ID of the VillageFinancialInstitutionProf to delete
     * @param reason The reason for deletion
     * @return VillageFinancialInstitutionProfDTO
     */
    VillageFinancialInstitutionProfDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageFinancialInstitutionProf by their ID.
     *
     * @param id The ID of the VillageFinancialInstitutionProf to retrieve
     * @return The VillageFinancialInstitutionProf with the specified ID
     * @since 1.0.0
     */
    VillageFinancialInstitutionProfDTO getById(Long id);




}
