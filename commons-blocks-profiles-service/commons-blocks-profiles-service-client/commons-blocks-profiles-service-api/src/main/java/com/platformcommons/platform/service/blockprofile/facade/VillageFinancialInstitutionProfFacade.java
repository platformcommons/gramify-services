package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageFinancialInstitutionProf-related operations in the system.
 * This interface provides methods for CRUD operations on VillageFinancialInstitutionProf data,
 * including creation, retrieval, update, and deletion of VillageFinancialInstitutionProf records.
 * It serves as the primary entry point for VillageFinancialInstitutionProf management functionality.
 */
public interface VillageFinancialInstitutionProfFacade {

    /**
     * Retrieves all VillageFinancialInstitutionProfs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageFinancialInstitutionProfDTO
     */
    Set<VillageFinancialInstitutionProfDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageFinancialInstitutionProf record in the system.
     *
     * @param VillageFinancialInstitutionProfDTO The VillageFinancialInstitutionProf data transfer object containing the information to be saved
     * @return The saved VillageFinancialInstitutionProf data with generated identifiers and any system-modified fields
     */
    VillageFinancialInstitutionProfDTO save(VillageFinancialInstitutionProfDTO VillageFinancialInstitutionProfDTO);

    /**
     * Updates an existing VillageFinancialInstitutionProf record in the system.
     *
     * @param VillageFinancialInstitutionProfDTO The VillageFinancialInstitutionProf data transfer object containing the updated information
     * @return The updated VillageFinancialInstitutionProf data as confirmed by the system
     */
    VillageFinancialInstitutionProfDTO update(VillageFinancialInstitutionProfDTO VillageFinancialInstitutionProfDTO);

    /**
     * Retrieves a paginated list of all VillageFinancialInstitutionProfs in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageFinancialInstitutionProf records
     */
    PageDTO<VillageFinancialInstitutionProfDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageFinancialInstitutionProf record from the system.
     *
     * @param id     The unique identifier of the VillageFinancialInstitutionProf to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageFinancialInstitutionProf by their unique identifier.
     *
     * @param id The unique identifier of the VillageFinancialInstitutionProf to retrieve
     * @return The VillageFinancialInstitutionProf data transfer object containing the requested information
     */
    VillageFinancialInstitutionProfDTO getById(Long id);



}
