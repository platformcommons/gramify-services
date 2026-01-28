package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageWaterSanitationProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageWaterSanitationProfile data,
 * including creation, retrieval, update, and deletion of VillageWaterSanitationProfile records.
 * It serves as the primary entry point for VillageWaterSanitationProfile management functionality.
 */
public interface VillageWaterSanitationProfileFacade {

    /**
     * Retrieves all VillageWaterSanitationProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageWaterSanitationProfileDTO
     */
    Set<VillageWaterSanitationProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageWaterSanitationProfile record in the system.
     *
     * @param VillageWaterSanitationProfileDTO The VillageWaterSanitationProfile data transfer object containing the information to be saved
     * @return The saved VillageWaterSanitationProfile data with generated identifiers and any system-modified fields
     */
    VillageWaterSanitationProfileDTO save(VillageWaterSanitationProfileDTO VillageWaterSanitationProfileDTO);

    /**
     * Updates an existing VillageWaterSanitationProfile record in the system.
     *
     * @param VillageWaterSanitationProfileDTO The VillageWaterSanitationProfile data transfer object containing the updated information
     * @return The updated VillageWaterSanitationProfile data as confirmed by the system
     */
    VillageWaterSanitationProfileDTO update(VillageWaterSanitationProfileDTO VillageWaterSanitationProfileDTO);

    /**
     * Retrieves a paginated list of all VillageWaterSanitationProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageWaterSanitationProfile records
     */
    PageDTO<VillageWaterSanitationProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageWaterSanitationProfile record from the system.
     *
     * @param id     The unique identifier of the VillageWaterSanitationProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageWaterSanitationProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageWaterSanitationProfile to retrieve
     * @return The VillageWaterSanitationProfile data transfer object containing the requested information
     */
    VillageWaterSanitationProfileDTO getById(Long id);



}
