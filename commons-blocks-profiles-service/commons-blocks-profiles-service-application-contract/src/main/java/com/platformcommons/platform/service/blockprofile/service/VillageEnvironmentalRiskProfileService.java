package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageEnvironmentalRiskProfile-related operations.
 * Provides CRUD operations and pagination support for VillageEnvironmentalRiskProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageEnvironmentalRiskProfileService {

    /**
     * Retrieves all VillageEnvironmentalRiskProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageEnvironmentalRiskProfileDTO in the same order as retrieved
     */
    Set<VillageEnvironmentalRiskProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageEnvironmentalRiskProfile entry in the system.
     *
     * @param VillageEnvironmentalRiskProfile The VillageEnvironmentalRiskProfile information to be saved
     * @return The saved VillageEnvironmentalRiskProfile data
     * @since 1.0.0
     */
    VillageEnvironmentalRiskProfileDTO save(VillageEnvironmentalRiskProfileDTO VillageEnvironmentalRiskProfile);

    /**
     * Updates an existing VillageEnvironmentalRiskProfile entry.
     *
     * @param VillageEnvironmentalRiskProfile The VillageEnvironmentalRiskProfile information to be updated
     * @return The updated VillageEnvironmentalRiskProfile data
     * @since 1.0.0
     */
    VillageEnvironmentalRiskProfileDTO update(VillageEnvironmentalRiskProfileDTO VillageEnvironmentalRiskProfile);

    /**
     * Retrieves a paginated list of VillageEnvironmentalRiskProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageEnvironmentalRiskProfiles
     * @since 1.0.0
     */
    PageDTO<VillageEnvironmentalRiskProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageEnvironmentalRiskProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageEnvironmentalRiskProfile to delete
     * @param reason The reason for deletion
     * @return VillageEnvironmentalRiskProfileDTO
     */
    VillageEnvironmentalRiskProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageEnvironmentalRiskProfile by their ID.
     *
     * @param id The ID of the VillageEnvironmentalRiskProfile to retrieve
     * @return The VillageEnvironmentalRiskProfile with the specified ID
     * @since 1.0.0
     */
    VillageEnvironmentalRiskProfileDTO getById(Long id);




}
