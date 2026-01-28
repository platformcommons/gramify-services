package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageEnvironmentalRiskProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageEnvironmentalRiskProfile data,
 * including creation, retrieval, update, and deletion of VillageEnvironmentalRiskProfile records.
 * It serves as the primary entry point for VillageEnvironmentalRiskProfile management functionality.
 */
public interface VillageEnvironmentalRiskProfileFacade {

    /**
     * Retrieves all VillageEnvironmentalRiskProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageEnvironmentalRiskProfileDTO
     */
    Set<VillageEnvironmentalRiskProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageEnvironmentalRiskProfile record in the system.
     *
     * @param VillageEnvironmentalRiskProfileDTO The VillageEnvironmentalRiskProfile data transfer object containing the information to be saved
     * @return The saved VillageEnvironmentalRiskProfile data with generated identifiers and any system-modified fields
     */
    VillageEnvironmentalRiskProfileDTO save(VillageEnvironmentalRiskProfileDTO VillageEnvironmentalRiskProfileDTO);

    /**
     * Updates an existing VillageEnvironmentalRiskProfile record in the system.
     *
     * @param VillageEnvironmentalRiskProfileDTO The VillageEnvironmentalRiskProfile data transfer object containing the updated information
     * @return The updated VillageEnvironmentalRiskProfile data as confirmed by the system
     */
    VillageEnvironmentalRiskProfileDTO update(VillageEnvironmentalRiskProfileDTO VillageEnvironmentalRiskProfileDTO);

    /**
     * Retrieves a paginated list of all VillageEnvironmentalRiskProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageEnvironmentalRiskProfile records
     */
    PageDTO<VillageEnvironmentalRiskProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageEnvironmentalRiskProfile record from the system.
     *
     * @param id     The unique identifier of the VillageEnvironmentalRiskProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageEnvironmentalRiskProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageEnvironmentalRiskProfile to retrieve
     * @return The VillageEnvironmentalRiskProfile data transfer object containing the requested information
     */
    VillageEnvironmentalRiskProfileDTO getById(Long id);



}
