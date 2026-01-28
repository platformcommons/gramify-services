package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageNicheProductProfile-related operations.
 * Provides CRUD operations and pagination support for VillageNicheProductProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageNicheProductProfileService {

    /**
     * Retrieves all VillageNicheProductProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageNicheProductProfileDTO in the same order as retrieved
     */
    Set<VillageNicheProductProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageNicheProductProfile entry in the system.
     *
     * @param VillageNicheProductProfile The VillageNicheProductProfile information to be saved
     * @return The saved VillageNicheProductProfile data
     * @since 1.0.0
     */
    VillageNicheProductProfileDTO save(VillageNicheProductProfileDTO VillageNicheProductProfile);

    /**
     * Updates an existing VillageNicheProductProfile entry.
     *
     * @param VillageNicheProductProfile The VillageNicheProductProfile information to be updated
     * @return The updated VillageNicheProductProfile data
     * @since 1.0.0
     */
    VillageNicheProductProfileDTO update(VillageNicheProductProfileDTO VillageNicheProductProfile);

    /**
     * Retrieves a paginated list of VillageNicheProductProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageNicheProductProfiles
     * @since 1.0.0
     */
    PageDTO<VillageNicheProductProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageNicheProductProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageNicheProductProfile to delete
     * @param reason The reason for deletion
     * @return VillageNicheProductProfileDTO
     */
    VillageNicheProductProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageNicheProductProfile by their ID.
     *
     * @param id The ID of the VillageNicheProductProfile to retrieve
     * @return The VillageNicheProductProfile with the specified ID
     * @since 1.0.0
     */
    VillageNicheProductProfileDTO getById(Long id);


    /**
     * Adds a list of mainNicheMarketList to a VillageNicheProductProfile identified by their ID.
     *
     * @param id               The ID of the VillageNicheProductProfile to add hobbies to
     * @param mainNicheMarketList  to be added
     * @since 1.0.0
     */
    void addMainNicheMarketToVillageNicheProductProfile(Long id, List<MainNicheMarketDTO> mainNicheMarketList);

    /**
     * Adds a list of supportNeededForNicheGrowthList to a VillageNicheProductProfile identified by their ID.
     *
     * @param id               The ID of the VillageNicheProductProfile to add hobbies to
     * @param supportNeededForNicheGrowthList  to be added
     * @since 1.0.0
     */
    void addSupportNeededForNicheGrowthToVillageNicheProductProfile(Long id, List<SupportNeededForNicheGrowthDTO> supportNeededForNicheGrowthList);

    /**
     * Adds a list of nicheProductsAvailabilityList to a VillageNicheProductProfile identified by their ID.
     *
     * @param id               The ID of the VillageNicheProductProfile to add hobbies to
     * @param nicheProductsAvailabilityList  to be added
     * @since 1.0.0
     */
    void addNicheProductsAvailabilityToVillageNicheProductProfile(Long id, List<NicheProductsAvailabilityDTO> nicheProductsAvailabilityList);

    /**
     * Adds a list of nicheProductBuyerTypeList to a VillageNicheProductProfile identified by their ID.
     *
     * @param id               The ID of the VillageNicheProductProfile to add hobbies to
     * @param nicheProductBuyerTypeList  to be added
     * @since 1.0.0
     */
    void addNicheProductBuyerTypeToVillageNicheProductProfile(Long id, List<NicheProductBuyerTypeDTO> nicheProductBuyerTypeList);



}
