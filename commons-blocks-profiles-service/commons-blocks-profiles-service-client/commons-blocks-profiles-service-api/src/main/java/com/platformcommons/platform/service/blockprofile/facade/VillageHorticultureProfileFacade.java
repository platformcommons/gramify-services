package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageHorticultureProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageHorticultureProfile data,
 * including creation, retrieval, update, and deletion of VillageHorticultureProfile records.
 * It serves as the primary entry point for VillageHorticultureProfile management functionality.
 */
public interface VillageHorticultureProfileFacade {

    /**
     * Retrieves all VillageHorticultureProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageHorticultureProfileDTO
     */
    Set<VillageHorticultureProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageHorticultureProfile record in the system.
     *
     * @param VillageHorticultureProfileDTO The VillageHorticultureProfile data transfer object containing the information to be saved
     * @return The saved VillageHorticultureProfile data with generated identifiers and any system-modified fields
     */
    VillageHorticultureProfileDTO save(VillageHorticultureProfileDTO VillageHorticultureProfileDTO);

    /**
     * Updates an existing VillageHorticultureProfile record in the system.
     *
     * @param VillageHorticultureProfileDTO The VillageHorticultureProfile data transfer object containing the updated information
     * @return The updated VillageHorticultureProfile data as confirmed by the system
     */
    VillageHorticultureProfileDTO update(VillageHorticultureProfileDTO VillageHorticultureProfileDTO);

    /**
     * Retrieves a paginated list of all VillageHorticultureProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageHorticultureProfile records
     */
    PageDTO<VillageHorticultureProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageHorticultureProfile record from the system.
     *
     * @param id     The unique identifier of the VillageHorticultureProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageHorticultureProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageHorticultureProfile to retrieve
     * @return The VillageHorticultureProfile data transfer object containing the requested information
     */
    VillageHorticultureProfileDTO getById(Long id);


    /**
     * Adds a list of horticultureProductList to a VillageHorticultureProfile identified by their ID.
     *
     * @param id               The ID of the VillageHorticultureProfile to add hobbies to
     * @param horticultureProductList  to be added
     * @since 1.0.0
     */
    void addHorticultureProductToVillageHorticultureProfile(Long id, List<HorticultureProductDTO> horticultureProductList);

    /**
     * Adds a list of productionSeasonList to a VillageHorticultureProfile identified by their ID.
     *
     * @param id               The ID of the VillageHorticultureProfile to add hobbies to
     * @param productionSeasonList  to be added
     * @since 1.0.0
     */
    void addProductionSeasonToVillageHorticultureProfile(Long id, List<ProductionSeasonDTO> productionSeasonList);


}
