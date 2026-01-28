package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageExportPotentialProfile-related operations.
 * Provides CRUD operations and pagination support for VillageExportPotentialProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageExportPotentialProfileService {

    /**
     * Retrieves all VillageExportPotentialProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageExportPotentialProfileDTO in the same order as retrieved
     */
    Set<VillageExportPotentialProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageExportPotentialProfile entry in the system.
     *
     * @param VillageExportPotentialProfile The VillageExportPotentialProfile information to be saved
     * @return The saved VillageExportPotentialProfile data
     * @since 1.0.0
     */
    VillageExportPotentialProfileDTO save(VillageExportPotentialProfileDTO VillageExportPotentialProfile);

    /**
     * Updates an existing VillageExportPotentialProfile entry.
     *
     * @param VillageExportPotentialProfile The VillageExportPotentialProfile information to be updated
     * @return The updated VillageExportPotentialProfile data
     * @since 1.0.0
     */
    VillageExportPotentialProfileDTO update(VillageExportPotentialProfileDTO VillageExportPotentialProfile);

    /**
     * Retrieves a paginated list of VillageExportPotentialProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageExportPotentialProfiles
     * @since 1.0.0
     */
    PageDTO<VillageExportPotentialProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageExportPotentialProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageExportPotentialProfile to delete
     * @param reason The reason for deletion
     * @return VillageExportPotentialProfileDTO
     */
    VillageExportPotentialProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageExportPotentialProfile by their ID.
     *
     * @param id The ID of the VillageExportPotentialProfile to retrieve
     * @return The VillageExportPotentialProfile with the specified ID
     * @since 1.0.0
     */
    VillageExportPotentialProfileDTO getById(Long id);


    /**
     * Adds a list of nicheProductBuyerTypeList to a VillageExportPotentialProfile identified by their ID.
     *
     * @param id               The ID of the VillageExportPotentialProfile to add hobbies to
     * @param nicheProductBuyerTypeList  to be added
     * @since 1.0.0
     */
    void addNicheProductBuyerTypeToVillageExportPotentialProfile(Long id, List<NicheProductBuyerTypeDTO> nicheProductBuyerTypeList);

    /**
     * Adds a list of surplusProduceTypeList to a VillageExportPotentialProfile identified by their ID.
     *
     * @param id               The ID of the VillageExportPotentialProfile to add hobbies to
     * @param surplusProduceTypeList  to be added
     * @since 1.0.0
     */
    void addSurplusProduceTypeToVillageExportPotentialProfile(Long id, List<SurplusProduceTypeDTO> surplusProduceTypeList);

    /**
     * Adds a list of mainSurplusMarketsOutsideBlockList to a VillageExportPotentialProfile identified by their ID.
     *
     * @param id               The ID of the VillageExportPotentialProfile to add hobbies to
     * @param mainSurplusMarketsOutsideBlockList  to be added
     * @since 1.0.0
     */
    void addMainSurplusMarketsOutsideBlockToVillageExportPotentialProfile(Long id, List<MainSurplusMarketsOutsideBlockDTO> mainSurplusMarketsOutsideBlockList);

    /**
     * Adds a list of nicheProductsAvailabilityList to a VillageExportPotentialProfile identified by their ID.
     *
     * @param id               The ID of the VillageExportPotentialProfile to add hobbies to
     * @param nicheProductsAvailabilityList  to be added
     * @since 1.0.0
     */
    void addNicheProductsAvailabilityToVillageExportPotentialProfile(Long id, List<NicheProductsAvailabilityDTO> nicheProductsAvailabilityList);



}
