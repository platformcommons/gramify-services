package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageMarketProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageMarketProfile data,
 * including creation, retrieval, update, and deletion of VillageMarketProfile records.
 * It serves as the primary entry point for VillageMarketProfile management functionality.
 */
public interface VillageMarketProfileFacade {

    /**
     * Retrieves all VillageMarketProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageMarketProfileDTO
     */
    Set<VillageMarketProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageMarketProfile record in the system.
     *
     * @param VillageMarketProfileDTO The VillageMarketProfile data transfer object containing the information to be saved
     * @return The saved VillageMarketProfile data with generated identifiers and any system-modified fields
     */
    VillageMarketProfileDTO save(VillageMarketProfileDTO VillageMarketProfileDTO);

    /**
     * Updates an existing VillageMarketProfile record in the system.
     *
     * @param VillageMarketProfileDTO The VillageMarketProfile data transfer object containing the updated information
     * @return The updated VillageMarketProfile data as confirmed by the system
     */
    VillageMarketProfileDTO update(VillageMarketProfileDTO VillageMarketProfileDTO);

    /**
     * Retrieves a paginated list of all VillageMarketProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageMarketProfile records
     */
    PageDTO<VillageMarketProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageMarketProfile record from the system.
     *
     * @param id     The unique identifier of the VillageMarketProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageMarketProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageMarketProfile to retrieve
     * @return The VillageMarketProfile data transfer object containing the requested information
     */
    VillageMarketProfileDTO getById(Long id);


    /**
     * Adds a list of operatingDayList to a VillageMarketProfile identified by their ID.
     *
     * @param id               The ID of the VillageMarketProfile to add hobbies to
     * @param operatingDayList  to be added
     * @since 1.0.0
     */
    void addOperatingDayToVillageMarketProfile(Long id, List<OperatingDayDTO> operatingDayList);


}
