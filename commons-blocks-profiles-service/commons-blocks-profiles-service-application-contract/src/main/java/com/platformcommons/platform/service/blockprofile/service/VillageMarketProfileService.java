package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageMarketProfile-related operations.
 * Provides CRUD operations and pagination support for VillageMarketProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageMarketProfileService {

    /**
     * Retrieves all VillageMarketProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageMarketProfileDTO in the same order as retrieved
     */
    Set<VillageMarketProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageMarketProfile entry in the system.
     *
     * @param VillageMarketProfile The VillageMarketProfile information to be saved
     * @return The saved VillageMarketProfile data
     * @since 1.0.0
     */
    VillageMarketProfileDTO save(VillageMarketProfileDTO VillageMarketProfile);

    /**
     * Updates an existing VillageMarketProfile entry.
     *
     * @param VillageMarketProfile The VillageMarketProfile information to be updated
     * @return The updated VillageMarketProfile data
     * @since 1.0.0
     */
    VillageMarketProfileDTO update(VillageMarketProfileDTO VillageMarketProfile);

    /**
     * Retrieves a paginated list of VillageMarketProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageMarketProfiles
     * @since 1.0.0
     */
    PageDTO<VillageMarketProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageMarketProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageMarketProfile to delete
     * @param reason The reason for deletion
     * @return VillageMarketProfileDTO
     */
    VillageMarketProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageMarketProfile by their ID.
     *
     * @param id The ID of the VillageMarketProfile to retrieve
     * @return The VillageMarketProfile with the specified ID
     * @since 1.0.0
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
