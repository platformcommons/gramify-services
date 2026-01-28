package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing BlockVillageDemographicsProfile-related operations in the system.
 * This interface provides methods for CRUD operations on BlockVillageDemographicsProfile data,
 * including creation, retrieval, update, and deletion of BlockVillageDemographicsProfile records.
 * It serves as the primary entry point for BlockVillageDemographicsProfile management functionality.
 */
public interface BlockVillageDemographicsProfileFacade {

    /**
     * Retrieves all BlockVillageDemographicsProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of BlockVillageDemographicsProfileDTO
     */
    Set<BlockVillageDemographicsProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new BlockVillageDemographicsProfile record in the system.
     *
     * @param BlockVillageDemographicsProfileDTO The BlockVillageDemographicsProfile data transfer object containing the information to be saved
     * @return The saved BlockVillageDemographicsProfile data with generated identifiers and any system-modified fields
     */
    BlockVillageDemographicsProfileDTO save(BlockVillageDemographicsProfileDTO BlockVillageDemographicsProfileDTO);

    /**
     * Updates an existing BlockVillageDemographicsProfile record in the system.
     *
     * @param BlockVillageDemographicsProfileDTO The BlockVillageDemographicsProfile data transfer object containing the updated information
     * @return The updated BlockVillageDemographicsProfile data as confirmed by the system
     */
    BlockVillageDemographicsProfileDTO update(BlockVillageDemographicsProfileDTO BlockVillageDemographicsProfileDTO);

    /**
     * Retrieves a paginated list of all BlockVillageDemographicsProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested BlockVillageDemographicsProfile records
     */
    PageDTO<BlockVillageDemographicsProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a BlockVillageDemographicsProfile record from the system.
     *
     * @param id     The unique identifier of the BlockVillageDemographicsProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific BlockVillageDemographicsProfile by their unique identifier.
     *
     * @param id The unique identifier of the BlockVillageDemographicsProfile to retrieve
     * @return The BlockVillageDemographicsProfile data transfer object containing the requested information
     */
    BlockVillageDemographicsProfileDTO getById(Long id);



}
