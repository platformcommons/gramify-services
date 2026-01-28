package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageNicheProductProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageNicheProductProfile data,
 * including creation, retrieval, update, and deletion of VillageNicheProductProfile records.
 * It serves as the primary entry point for VillageNicheProductProfile management functionality.
 */
public interface VillageNicheProductProfileFacade {

    /**
     * Retrieves all VillageNicheProductProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageNicheProductProfileDTO
     */
    Set<VillageNicheProductProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageNicheProductProfile record in the system.
     *
     * @param VillageNicheProductProfileDTO The VillageNicheProductProfile data transfer object containing the information to be saved
     * @return The saved VillageNicheProductProfile data with generated identifiers and any system-modified fields
     */
    VillageNicheProductProfileDTO save(VillageNicheProductProfileDTO VillageNicheProductProfileDTO);

    /**
     * Updates an existing VillageNicheProductProfile record in the system.
     *
     * @param VillageNicheProductProfileDTO The VillageNicheProductProfile data transfer object containing the updated information
     * @return The updated VillageNicheProductProfile data as confirmed by the system
     */
    VillageNicheProductProfileDTO update(VillageNicheProductProfileDTO VillageNicheProductProfileDTO);

    /**
     * Retrieves a paginated list of all VillageNicheProductProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageNicheProductProfile records
     */
    PageDTO<VillageNicheProductProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageNicheProductProfile record from the system.
     *
     * @param id     The unique identifier of the VillageNicheProductProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageNicheProductProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageNicheProductProfile to retrieve
     * @return The VillageNicheProductProfile data transfer object containing the requested information
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
