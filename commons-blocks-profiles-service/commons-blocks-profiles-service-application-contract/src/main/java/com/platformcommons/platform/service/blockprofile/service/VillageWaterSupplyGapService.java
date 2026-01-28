package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageWaterSupplyGap-related operations.
 * Provides CRUD operations and pagination support for VillageWaterSupplyGap entities.
 *
 * @since 1.0.0
 */

public interface VillageWaterSupplyGapService {

    /**
     * Retrieves all VillageWaterSupplyGaps by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageWaterSupplyGapDTO in the same order as retrieved
     */
    Set<VillageWaterSupplyGapDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageWaterSupplyGap entry in the system.
     *
     * @param VillageWaterSupplyGap The VillageWaterSupplyGap information to be saved
     * @return The saved VillageWaterSupplyGap data
     * @since 1.0.0
     */
    VillageWaterSupplyGapDTO save(VillageWaterSupplyGapDTO VillageWaterSupplyGap);

    /**
     * Updates an existing VillageWaterSupplyGap entry.
     *
     * @param VillageWaterSupplyGap The VillageWaterSupplyGap information to be updated
     * @return The updated VillageWaterSupplyGap data
     * @since 1.0.0
     */
    VillageWaterSupplyGapDTO update(VillageWaterSupplyGapDTO VillageWaterSupplyGap);

    /**
     * Retrieves a paginated list of VillageWaterSupplyGaps.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageWaterSupplyGaps
     * @since 1.0.0
     */
    PageDTO<VillageWaterSupplyGapDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageWaterSupplyGap by their ID with a specified reason.
     *
     * @param id     The ID of the VillageWaterSupplyGap to delete
     * @param reason The reason for deletion
     * @return VillageWaterSupplyGapDTO
     */
    VillageWaterSupplyGapDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageWaterSupplyGap by their ID.
     *
     * @param id The ID of the VillageWaterSupplyGap to retrieve
     * @return The VillageWaterSupplyGap with the specified ID
     * @since 1.0.0
     */
    VillageWaterSupplyGapDTO getById(Long id);




}
