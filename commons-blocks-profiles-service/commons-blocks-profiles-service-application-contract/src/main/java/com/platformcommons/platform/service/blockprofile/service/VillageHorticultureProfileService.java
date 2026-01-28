package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageHorticultureProfile-related operations.
 * Provides CRUD operations and pagination support for VillageHorticultureProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageHorticultureProfileService {

    /**
     * Retrieves all VillageHorticultureProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageHorticultureProfileDTO in the same order as retrieved
     */
    Set<VillageHorticultureProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageHorticultureProfile entry in the system.
     *
     * @param VillageHorticultureProfile The VillageHorticultureProfile information to be saved
     * @return The saved VillageHorticultureProfile data
     * @since 1.0.0
     */
    VillageHorticultureProfileDTO save(VillageHorticultureProfileDTO VillageHorticultureProfile);

    /**
     * Updates an existing VillageHorticultureProfile entry.
     *
     * @param VillageHorticultureProfile The VillageHorticultureProfile information to be updated
     * @return The updated VillageHorticultureProfile data
     * @since 1.0.0
     */
    VillageHorticultureProfileDTO update(VillageHorticultureProfileDTO VillageHorticultureProfile);

    /**
     * Retrieves a paginated list of VillageHorticultureProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageHorticultureProfiles
     * @since 1.0.0
     */
    PageDTO<VillageHorticultureProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageHorticultureProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageHorticultureProfile to delete
     * @param reason The reason for deletion
     * @return VillageHorticultureProfileDTO
     */
    VillageHorticultureProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageHorticultureProfile by their ID.
     *
     * @param id The ID of the VillageHorticultureProfile to retrieve
     * @return The VillageHorticultureProfile with the specified ID
     * @since 1.0.0
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
