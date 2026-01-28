package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageConsumptionPattern-related operations in the system.
 * This interface provides methods for CRUD operations on VillageConsumptionPattern data,
 * including creation, retrieval, update, and deletion of VillageConsumptionPattern records.
 * It serves as the primary entry point for VillageConsumptionPattern management functionality.
 */
public interface VillageConsumptionPatternFacade {

    /**
     * Retrieves all VillageConsumptionPatterns by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageConsumptionPatternDTO
     */
    Set<VillageConsumptionPatternDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageConsumptionPattern record in the system.
     *
     * @param VillageConsumptionPatternDTO The VillageConsumptionPattern data transfer object containing the information to be saved
     * @return The saved VillageConsumptionPattern data with generated identifiers and any system-modified fields
     */
    VillageConsumptionPatternDTO save(VillageConsumptionPatternDTO VillageConsumptionPatternDTO);

    /**
     * Updates an existing VillageConsumptionPattern record in the system.
     *
     * @param VillageConsumptionPatternDTO The VillageConsumptionPattern data transfer object containing the updated information
     * @return The updated VillageConsumptionPattern data as confirmed by the system
     */
    VillageConsumptionPatternDTO update(VillageConsumptionPatternDTO VillageConsumptionPatternDTO);

    /**
     * Retrieves a paginated list of all VillageConsumptionPatterns in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageConsumptionPattern records
     */
    PageDTO<VillageConsumptionPatternDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageConsumptionPattern record from the system.
     *
     * @param id     The unique identifier of the VillageConsumptionPattern to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageConsumptionPattern by their unique identifier.
     *
     * @param id The unique identifier of the VillageConsumptionPattern to retrieve
     * @return The VillageConsumptionPattern data transfer object containing the requested information
     */
    VillageConsumptionPatternDTO getById(Long id);


    /**
     * Adds a list of commonConsumerGoodsPurchasedList to a VillageConsumptionPattern identified by their ID.
     *
     * @param id               The ID of the VillageConsumptionPattern to add hobbies to
     * @param commonConsumerGoodsPurchasedList  to be added
     * @since 1.0.0
     */
    void addCommonConsumerGoodsPurchasedToVillageConsumptionPattern(Long id, List<CommonConsumerGoodsPurchasedDTO> commonConsumerGoodsPurchasedList);

    /**
     * Adds a list of itemsUsuallyBoughtFromOutsideList to a VillageConsumptionPattern identified by their ID.
     *
     * @param id               The ID of the VillageConsumptionPattern to add hobbies to
     * @param itemsUsuallyBoughtFromOutsideList  to be added
     * @since 1.0.0
     */
    void addItemsUsuallyBoughtFromOutsideToVillageConsumptionPattern(Long id, List<ItemsUsuallyBoughtFromOutsideDTO> itemsUsuallyBoughtFromOutsideList);

    /**
     * Adds a list of stapleFoodsConsumedList to a VillageConsumptionPattern identified by their ID.
     *
     * @param id               The ID of the VillageConsumptionPattern to add hobbies to
     * @param stapleFoodsConsumedList  to be added
     * @since 1.0.0
     */
    void addStapleFoodsConsumedToVillageConsumptionPattern(Long id, List<StapleFoodsConsumedDTO> stapleFoodsConsumedList);

    /**
     * Adds a list of itemsUsuallyBoughtLocallyList to a VillageConsumptionPattern identified by their ID.
     *
     * @param id               The ID of the VillageConsumptionPattern to add hobbies to
     * @param itemsUsuallyBoughtLocallyList  to be added
     * @since 1.0.0
     */
    void addItemsUsuallyBoughtLocallyToVillageConsumptionPattern(Long id, List<ItemsUsuallyBoughtLocallyDTO> itemsUsuallyBoughtLocallyList);


}
