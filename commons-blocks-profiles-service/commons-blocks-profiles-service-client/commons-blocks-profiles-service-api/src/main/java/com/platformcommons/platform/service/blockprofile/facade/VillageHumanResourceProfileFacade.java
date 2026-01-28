package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageHumanResourceProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageHumanResourceProfile data,
 * including creation, retrieval, update, and deletion of VillageHumanResourceProfile records.
 * It serves as the primary entry point for VillageHumanResourceProfile management functionality.
 */
public interface VillageHumanResourceProfileFacade {

    /**
     * Retrieves all VillageHumanResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageHumanResourceProfileDTO
     */
    Set<VillageHumanResourceProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageHumanResourceProfile record in the system.
     *
     * @param VillageHumanResourceProfileDTO The VillageHumanResourceProfile data transfer object containing the information to be saved
     * @return The saved VillageHumanResourceProfile data with generated identifiers and any system-modified fields
     */
    VillageHumanResourceProfileDTO save(VillageHumanResourceProfileDTO VillageHumanResourceProfileDTO);

    /**
     * Updates an existing VillageHumanResourceProfile record in the system.
     *
     * @param VillageHumanResourceProfileDTO The VillageHumanResourceProfile data transfer object containing the updated information
     * @return The updated VillageHumanResourceProfile data as confirmed by the system
     */
    VillageHumanResourceProfileDTO update(VillageHumanResourceProfileDTO VillageHumanResourceProfileDTO);

    /**
     * Retrieves a paginated list of all VillageHumanResourceProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageHumanResourceProfile records
     */
    PageDTO<VillageHumanResourceProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageHumanResourceProfile record from the system.
     *
     * @param id     The unique identifier of the VillageHumanResourceProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageHumanResourceProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageHumanResourceProfile to retrieve
     * @return The VillageHumanResourceProfile data transfer object containing the requested information
     */
    VillageHumanResourceProfileDTO getById(Long id);


    /**
     * Adds a list of hHSkilledWorkerTypeList to a VillageHumanResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageHumanResourceProfile to add hobbies to
     * @param hHSkilledWorkerTypeList  to be added
     * @since 1.0.0
     */
    void addHHSkilledWorkerTypeToVillageHumanResourceProfile(Long id, List<HHSkilledWorkerTypeDTO> hHSkilledWorkerTypeList);

    /**
     * Adds a list of emergingEnterpriseTypeList to a VillageHumanResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageHumanResourceProfile to add hobbies to
     * @param emergingEnterpriseTypeList  to be added
     * @since 1.0.0
     */
    void addEmergingEnterpriseTypeToVillageHumanResourceProfile(Long id, List<EmergingEnterpriseTypeDTO> emergingEnterpriseTypeList);

    /**
     * Adds a list of mainSkilledTradesPresentList to a VillageHumanResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageHumanResourceProfile to add hobbies to
     * @param mainSkilledTradesPresentList  to be added
     * @since 1.0.0
     */
    void addMainSkilledTradesPresentToVillageHumanResourceProfile(Long id, List<MainSkilledTradesPresentDTO> mainSkilledTradesPresentList);

    /**
     * Adds a list of artisanTypeList to a VillageHumanResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageHumanResourceProfile to add hobbies to
     * @param artisanTypeList  to be added
     * @since 1.0.0
     */
    void addArtisanTypeToVillageHumanResourceProfile(Long id, List<ArtisanTypeDTO> artisanTypeList);


}
