package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageInstitutionalResourcePro-related operations.
 * Provides CRUD operations and pagination support for VillageInstitutionalResourcePro entities.
 *
 * @since 1.0.0
 */

public interface VillageInstitutionalResourceProService {

    /**
     * Retrieves all VillageInstitutionalResourcePros by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageInstitutionalResourceProDTO in the same order as retrieved
     */
    Set<VillageInstitutionalResourceProDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageInstitutionalResourcePro entry in the system.
     *
     * @param VillageInstitutionalResourcePro The VillageInstitutionalResourcePro information to be saved
     * @return The saved VillageInstitutionalResourcePro data
     * @since 1.0.0
     */
    VillageInstitutionalResourceProDTO save(VillageInstitutionalResourceProDTO VillageInstitutionalResourcePro);

    /**
     * Updates an existing VillageInstitutionalResourcePro entry.
     *
     * @param VillageInstitutionalResourcePro The VillageInstitutionalResourcePro information to be updated
     * @return The updated VillageInstitutionalResourcePro data
     * @since 1.0.0
     */
    VillageInstitutionalResourceProDTO update(VillageInstitutionalResourceProDTO VillageInstitutionalResourcePro);

    /**
     * Retrieves a paginated list of VillageInstitutionalResourcePros.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageInstitutionalResourcePros
     * @since 1.0.0
     */
    PageDTO<VillageInstitutionalResourceProDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageInstitutionalResourcePro by their ID with a specified reason.
     *
     * @param id     The ID of the VillageInstitutionalResourcePro to delete
     * @param reason The reason for deletion
     * @return VillageInstitutionalResourceProDTO
     */
    VillageInstitutionalResourceProDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageInstitutionalResourcePro by their ID.
     *
     * @param id The ID of the VillageInstitutionalResourcePro to retrieve
     * @return The VillageInstitutionalResourcePro with the specified ID
     * @since 1.0.0
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
