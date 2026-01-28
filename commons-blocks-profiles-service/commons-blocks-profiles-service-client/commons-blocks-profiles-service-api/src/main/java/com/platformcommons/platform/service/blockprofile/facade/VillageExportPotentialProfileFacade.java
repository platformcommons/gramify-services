package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageExportPotentialProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageExportPotentialProfile data,
 * including creation, retrieval, update, and deletion of VillageExportPotentialProfile records.
 * It serves as the primary entry point for VillageExportPotentialProfile management functionality.
 */
public interface VillageExportPotentialProfileFacade {

    /**
     * Retrieves all VillageExportPotentialProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageExportPotentialProfileDTO
     */
    Set<VillageExportPotentialProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageExportPotentialProfile record in the system.
     *
     * @param VillageExportPotentialProfileDTO The VillageExportPotentialProfile data transfer object containing the information to be saved
     * @return The saved VillageExportPotentialProfile data with generated identifiers and any system-modified fields
     */
    VillageExportPotentialProfileDTO save(VillageExportPotentialProfileDTO VillageExportPotentialProfileDTO);

    /**
     * Updates an existing VillageExportPotentialProfile record in the system.
     *
     * @param VillageExportPotentialProfileDTO The VillageExportPotentialProfile data transfer object containing the updated information
     * @return The updated VillageExportPotentialProfile data as confirmed by the system
     */
    VillageExportPotentialProfileDTO update(VillageExportPotentialProfileDTO VillageExportPotentialProfileDTO);

    /**
     * Retrieves a paginated list of all VillageExportPotentialProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageExportPotentialProfile records
     */
    PageDTO<VillageExportPotentialProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageExportPotentialProfile record from the system.
     *
     * @param id     The unique identifier of the VillageExportPotentialProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageExportPotentialProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageExportPotentialProfile to retrieve
     * @return The VillageExportPotentialProfile data transfer object containing the requested information
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
