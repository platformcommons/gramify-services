package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageHumanResourceProfile-related operations.
 * Provides CRUD operations and pagination support for VillageHumanResourceProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageHumanResourceProfileService {

    /**
     * Retrieves all VillageHumanResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageHumanResourceProfileDTO in the same order as retrieved
     */
    Set<VillageHumanResourceProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageHumanResourceProfile entry in the system.
     *
     * @param VillageHumanResourceProfile The VillageHumanResourceProfile information to be saved
     * @return The saved VillageHumanResourceProfile data
     * @since 1.0.0
     */
    VillageHumanResourceProfileDTO save(VillageHumanResourceProfileDTO VillageHumanResourceProfile);

    /**
     * Updates an existing VillageHumanResourceProfile entry.
     *
     * @param VillageHumanResourceProfile The VillageHumanResourceProfile information to be updated
     * @return The updated VillageHumanResourceProfile data
     * @since 1.0.0
     */
    VillageHumanResourceProfileDTO update(VillageHumanResourceProfileDTO VillageHumanResourceProfile);

    /**
     * Retrieves a paginated list of VillageHumanResourceProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageHumanResourceProfiles
     * @since 1.0.0
     */
    PageDTO<VillageHumanResourceProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageHumanResourceProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageHumanResourceProfile to delete
     * @param reason The reason for deletion
     * @return VillageHumanResourceProfileDTO
     */
    VillageHumanResourceProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageHumanResourceProfile by their ID.
     *
     * @param id The ID of the VillageHumanResourceProfile to retrieve
     * @return The VillageHumanResourceProfile with the specified ID
     * @since 1.0.0
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
