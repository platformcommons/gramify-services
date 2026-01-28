package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing BlockVillageDemographicsProfile-related operations.
 * Provides CRUD operations and pagination support for BlockVillageDemographicsProfile entities.
 *
 * @since 1.0.0
 */

public interface BlockVillageDemographicsProfileService {

    /**
     * Retrieves all BlockVillageDemographicsProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of BlockVillageDemographicsProfileDTO in the same order as retrieved
     */
    Set<BlockVillageDemographicsProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new BlockVillageDemographicsProfile entry in the system.
     *
     * @param BlockVillageDemographicsProfile The BlockVillageDemographicsProfile information to be saved
     * @return The saved BlockVillageDemographicsProfile data
     * @since 1.0.0
     */
    BlockVillageDemographicsProfileDTO save(BlockVillageDemographicsProfileDTO BlockVillageDemographicsProfile);

    /**
     * Updates an existing BlockVillageDemographicsProfile entry.
     *
     * @param BlockVillageDemographicsProfile The BlockVillageDemographicsProfile information to be updated
     * @return The updated BlockVillageDemographicsProfile data
     * @since 1.0.0
     */
    BlockVillageDemographicsProfileDTO update(BlockVillageDemographicsProfileDTO BlockVillageDemographicsProfile);

    /**
     * Retrieves a paginated list of BlockVillageDemographicsProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of BlockVillageDemographicsProfiles
     * @since 1.0.0
     */
    PageDTO<BlockVillageDemographicsProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a BlockVillageDemographicsProfile by their ID with a specified reason.
     *
     * @param id     The ID of the BlockVillageDemographicsProfile to delete
     * @param reason The reason for deletion
     * @return BlockVillageDemographicsProfileDTO
     */
    BlockVillageDemographicsProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a BlockVillageDemographicsProfile by their ID.
     *
     * @param id The ID of the BlockVillageDemographicsProfile to retrieve
     * @return The BlockVillageDemographicsProfile with the specified ID
     * @since 1.0.0
     */
    BlockVillageDemographicsProfileDTO getById(Long id);




}
