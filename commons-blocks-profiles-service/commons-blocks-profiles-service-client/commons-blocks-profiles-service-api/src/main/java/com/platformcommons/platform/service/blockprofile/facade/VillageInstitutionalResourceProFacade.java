package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageInstitutionalResourcePro-related operations in the system.
 * This interface provides methods for CRUD operations on VillageInstitutionalResourcePro data,
 * including creation, retrieval, update, and deletion of VillageInstitutionalResourcePro records.
 * It serves as the primary entry point for VillageInstitutionalResourcePro management functionality.
 */
public interface VillageInstitutionalResourceProFacade {

    /**
     * Retrieves all VillageInstitutionalResourcePros by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageInstitutionalResourceProDTO
     */
    Set<VillageInstitutionalResourceProDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageInstitutionalResourcePro record in the system.
     *
     * @param VillageInstitutionalResourceProDTO The VillageInstitutionalResourcePro data transfer object containing the information to be saved
     * @return The saved VillageInstitutionalResourcePro data with generated identifiers and any system-modified fields
     */
    VillageInstitutionalResourceProDTO save(VillageInstitutionalResourceProDTO VillageInstitutionalResourceProDTO);

    /**
     * Updates an existing VillageInstitutionalResourcePro record in the system.
     *
     * @param VillageInstitutionalResourceProDTO The VillageInstitutionalResourcePro data transfer object containing the updated information
     * @return The updated VillageInstitutionalResourcePro data as confirmed by the system
     */
    VillageInstitutionalResourceProDTO update(VillageInstitutionalResourceProDTO VillageInstitutionalResourceProDTO);

    /**
     * Retrieves a paginated list of all VillageInstitutionalResourcePros in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageInstitutionalResourcePro records
     */
    PageDTO<VillageInstitutionalResourceProDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageInstitutionalResourcePro record from the system.
     *
     * @param id     The unique identifier of the VillageInstitutionalResourcePro to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageInstitutionalResourcePro by their unique identifier.
     *
     * @param id The unique identifier of the VillageInstitutionalResourcePro to retrieve
     * @return The VillageInstitutionalResourcePro data transfer object containing the requested information
     */
    VillageInstitutionalResourceProDTO getById(Long id);


    /**
     * Adds a list of nGOThematicAreaList to a VillageInstitutionalResourcePro identified by their ID.
     *
     * @param id               The ID of the VillageInstitutionalResourcePro to add hobbies to
     * @param nGOThematicAreaList  to be added
     * @since 1.0.0
     */
    void addNGOThematicAreaToVillageInstitutionalResourcePro(Long id, List<NGOThematicAreaDTO> nGOThematicAreaList);


}
