package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageLivestockProfile-related operations.
 * Provides CRUD operations and pagination support for VillageLivestockProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageLivestockProfileService {

    /**
     * Retrieves all VillageLivestockProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageLivestockProfileDTO in the same order as retrieved
     */
    Set<VillageLivestockProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageLivestockProfile entry in the system.
     *
     * @param VillageLivestockProfile The VillageLivestockProfile information to be saved
     * @return The saved VillageLivestockProfile data
     * @since 1.0.0
     */
    VillageLivestockProfileDTO save(VillageLivestockProfileDTO VillageLivestockProfile);

    /**
     * Updates an existing VillageLivestockProfile entry.
     *
     * @param VillageLivestockProfile The VillageLivestockProfile information to be updated
     * @return The updated VillageLivestockProfile data
     * @since 1.0.0
     */
    VillageLivestockProfileDTO update(VillageLivestockProfileDTO VillageLivestockProfile);

    /**
     * Retrieves a paginated list of VillageLivestockProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageLivestockProfiles
     * @since 1.0.0
     */
    PageDTO<VillageLivestockProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageLivestockProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageLivestockProfile to delete
     * @param reason The reason for deletion
     * @return VillageLivestockProfileDTO
     */
    VillageLivestockProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageLivestockProfile by their ID.
     *
     * @param id The ID of the VillageLivestockProfile to retrieve
     * @return The VillageLivestockProfile with the specified ID
     * @since 1.0.0
     */
    VillageLivestockProfileDTO getById(Long id);


    /**
     * Adds a list of productionSeasonalityList to a VillageLivestockProfile identified by their ID.
     *
     * @param id               The ID of the VillageLivestockProfile to add hobbies to
     * @param productionSeasonalityList  to be added
     * @since 1.0.0
     */
    void addProductionSeasonalityToVillageLivestockProfile(Long id, List<ProductionSeasonalityDTO> productionSeasonalityList);



}
