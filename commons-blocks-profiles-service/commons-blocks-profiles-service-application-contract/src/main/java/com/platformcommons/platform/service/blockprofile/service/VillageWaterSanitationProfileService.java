package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageWaterSanitationProfile-related operations.
 * Provides CRUD operations and pagination support for VillageWaterSanitationProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageWaterSanitationProfileService {

    /**
     * Retrieves all VillageWaterSanitationProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageWaterSanitationProfileDTO in the same order as retrieved
     */
    Set<VillageWaterSanitationProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageWaterSanitationProfile entry in the system.
     *
     * @param VillageWaterSanitationProfile The VillageWaterSanitationProfile information to be saved
     * @return The saved VillageWaterSanitationProfile data
     * @since 1.0.0
     */
    VillageWaterSanitationProfileDTO save(VillageWaterSanitationProfileDTO VillageWaterSanitationProfile);

    /**
     * Updates an existing VillageWaterSanitationProfile entry.
     *
     * @param VillageWaterSanitationProfile The VillageWaterSanitationProfile information to be updated
     * @return The updated VillageWaterSanitationProfile data
     * @since 1.0.0
     */
    VillageWaterSanitationProfileDTO update(VillageWaterSanitationProfileDTO VillageWaterSanitationProfile);

    /**
     * Retrieves a paginated list of VillageWaterSanitationProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageWaterSanitationProfiles
     * @since 1.0.0
     */
    PageDTO<VillageWaterSanitationProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageWaterSanitationProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageWaterSanitationProfile to delete
     * @param reason The reason for deletion
     * @return VillageWaterSanitationProfileDTO
     */
    VillageWaterSanitationProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageWaterSanitationProfile by their ID.
     *
     * @param id The ID of the VillageWaterSanitationProfile to retrieve
     * @return The VillageWaterSanitationProfile with the specified ID
     * @since 1.0.0
     */
    VillageWaterSanitationProfileDTO getById(Long id);




}
