package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageCommonWildlife-related operations in the system.
 * This interface provides methods for CRUD operations on VillageCommonWildlife data,
 * including creation, retrieval, update, and deletion of VillageCommonWildlife records.
 * It serves as the primary entry point for VillageCommonWildlife management functionality.
 */
public interface VillageCommonWildlifeFacade {

    /**
     * Retrieves all VillageCommonWildlifes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCommonWildlifeDTO
     */
    Set<VillageCommonWildlifeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageCommonWildlife record in the system.
     *
     * @param VillageCommonWildlifeDTO The VillageCommonWildlife data transfer object containing the information to be saved
     * @return The saved VillageCommonWildlife data with generated identifiers and any system-modified fields
     */
    VillageCommonWildlifeDTO save(VillageCommonWildlifeDTO VillageCommonWildlifeDTO);

    /**
     * Updates an existing VillageCommonWildlife record in the system.
     *
     * @param VillageCommonWildlifeDTO The VillageCommonWildlife data transfer object containing the updated information
     * @return The updated VillageCommonWildlife data as confirmed by the system
     */
    VillageCommonWildlifeDTO update(VillageCommonWildlifeDTO VillageCommonWildlifeDTO);

    /**
     * Retrieves a paginated list of all VillageCommonWildlifes in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageCommonWildlife records
     */
    PageDTO<VillageCommonWildlifeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageCommonWildlife record from the system.
     *
     * @param id     The unique identifier of the VillageCommonWildlife to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageCommonWildlife by their unique identifier.
     *
     * @param id The unique identifier of the VillageCommonWildlife to retrieve
     * @return The VillageCommonWildlife data transfer object containing the requested information
     */
    VillageCommonWildlifeDTO getById(Long id);



}
