package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageConsumptionPattern-related operations.
 * Provides CRUD operations and pagination support for VillageConsumptionPattern entities.
 *
 * @since 1.0.0
 */

public interface VillageConsumptionPatternService {

    /**
     * Retrieves all VillageConsumptionPatterns by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageConsumptionPatternDTO in the same order as retrieved
     */
    Set<VillageConsumptionPatternDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageConsumptionPattern entry in the system.
     *
     * @param VillageConsumptionPattern The VillageConsumptionPattern information to be saved
     * @return The saved VillageConsumptionPattern data
     * @since 1.0.0
     */
    VillageConsumptionPatternDTO save(VillageConsumptionPatternDTO VillageConsumptionPattern);

    /**
     * Updates an existing VillageConsumptionPattern entry.
     *
     * @param VillageConsumptionPattern The VillageConsumptionPattern information to be updated
     * @return The updated VillageConsumptionPattern data
     * @since 1.0.0
     */
    VillageConsumptionPatternDTO update(VillageConsumptionPatternDTO VillageConsumptionPattern);

    /**
     * Retrieves a paginated list of VillageConsumptionPatterns.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageConsumptionPatterns
     * @since 1.0.0
     */
    PageDTO<VillageConsumptionPatternDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageConsumptionPattern by their ID with a specified reason.
     *
     * @param id     The ID of the VillageConsumptionPattern to delete
     * @param reason The reason for deletion
     * @return VillageConsumptionPatternDTO
     */
    VillageConsumptionPatternDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageConsumptionPattern by their ID.
     *
     * @param id The ID of the VillageConsumptionPattern to retrieve
     * @return The VillageConsumptionPattern with the specified ID
     * @since 1.0.0
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
