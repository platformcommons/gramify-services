package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageLivestockProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageLivestockProfile data,
 * including creation, retrieval, update, and deletion of VillageLivestockProfile records.
 * It serves as the primary entry point for VillageLivestockProfile management functionality.
 */
public interface VillageLivestockProfileFacade {

    /**
     * Retrieves all VillageLivestockProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageLivestockProfileDTO
     */
    Set<VillageLivestockProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageLivestockProfile record in the system.
     *
     * @param VillageLivestockProfileDTO The VillageLivestockProfile data transfer object containing the information to be saved
     * @return The saved VillageLivestockProfile data with generated identifiers and any system-modified fields
     */
    VillageLivestockProfileDTO save(VillageLivestockProfileDTO VillageLivestockProfileDTO);

    /**
     * Updates an existing VillageLivestockProfile record in the system.
     *
     * @param VillageLivestockProfileDTO The VillageLivestockProfile data transfer object containing the updated information
     * @return The updated VillageLivestockProfile data as confirmed by the system
     */
    VillageLivestockProfileDTO update(VillageLivestockProfileDTO VillageLivestockProfileDTO);

    /**
     * Retrieves a paginated list of all VillageLivestockProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageLivestockProfile records
     */
    PageDTO<VillageLivestockProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageLivestockProfile record from the system.
     *
     * @param id     The unique identifier of the VillageLivestockProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageLivestockProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageLivestockProfile to retrieve
     * @return The VillageLivestockProfile data transfer object containing the requested information
     */
    VillageLivestockProfileDTO getById(Long id);


    /**
     * Adds a list of productionSeasonalityList to a VillageLivestockProfile identified by their ID.
     *
     * @param id               The ID of the VillageLivestockProfile to add hobbies to
     * @param productionSeasonalityList  to be added
     * @since 1.0.0
     */
    void addProductionSeasonalityToVillageLivestockProfile(Long id, List<ProductionSeasonalityDTO> productionSeasonalityList);


}
