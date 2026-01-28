package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageWaterSupplyGap-related operations in the system.
 * This interface provides methods for CRUD operations on VillageWaterSupplyGap data,
 * including creation, retrieval, update, and deletion of VillageWaterSupplyGap records.
 * It serves as the primary entry point for VillageWaterSupplyGap management functionality.
 */
public interface VillageWaterSupplyGapFacade {

    /**
     * Retrieves all VillageWaterSupplyGaps by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageWaterSupplyGapDTO
     */
    Set<VillageWaterSupplyGapDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageWaterSupplyGap record in the system.
     *
     * @param VillageWaterSupplyGapDTO The VillageWaterSupplyGap data transfer object containing the information to be saved
     * @return The saved VillageWaterSupplyGap data with generated identifiers and any system-modified fields
     */
    VillageWaterSupplyGapDTO save(VillageWaterSupplyGapDTO VillageWaterSupplyGapDTO);

    /**
     * Updates an existing VillageWaterSupplyGap record in the system.
     *
     * @param VillageWaterSupplyGapDTO The VillageWaterSupplyGap data transfer object containing the updated information
     * @return The updated VillageWaterSupplyGap data as confirmed by the system
     */
    VillageWaterSupplyGapDTO update(VillageWaterSupplyGapDTO VillageWaterSupplyGapDTO);

    /**
     * Retrieves a paginated list of all VillageWaterSupplyGaps in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageWaterSupplyGap records
     */
    PageDTO<VillageWaterSupplyGapDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageWaterSupplyGap record from the system.
     *
     * @param id     The unique identifier of the VillageWaterSupplyGap to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageWaterSupplyGap by their unique identifier.
     *
     * @param id The unique identifier of the VillageWaterSupplyGap to retrieve
     * @return The VillageWaterSupplyGap data transfer object containing the requested information
     */
    VillageWaterSupplyGapDTO getById(Long id);



}
