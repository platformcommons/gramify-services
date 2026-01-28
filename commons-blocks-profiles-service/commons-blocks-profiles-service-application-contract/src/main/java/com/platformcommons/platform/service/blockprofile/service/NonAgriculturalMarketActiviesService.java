package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing NonAgriculturalMarketActivies-related operations.
 * Provides CRUD operations and pagination support for NonAgriculturalMarketActivies entities.
 *
 * @since 1.0.0
 */

public interface NonAgriculturalMarketActiviesService {

    /**
     * Retrieves all NonAgriculturalMarketActiviess by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of NonAgriculturalMarketActiviesDTO in the same order as retrieved
     */
    Set<NonAgriculturalMarketActiviesDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new NonAgriculturalMarketActivies entry in the system.
     *
     * @param NonAgriculturalMarketActivies The NonAgriculturalMarketActivies information to be saved
     * @return The saved NonAgriculturalMarketActivies data
     * @since 1.0.0
     */
    NonAgriculturalMarketActiviesDTO save(NonAgriculturalMarketActiviesDTO NonAgriculturalMarketActivies);

    /**
     * Updates an existing NonAgriculturalMarketActivies entry.
     *
     * @param NonAgriculturalMarketActivies The NonAgriculturalMarketActivies information to be updated
     * @return The updated NonAgriculturalMarketActivies data
     * @since 1.0.0
     */
    NonAgriculturalMarketActiviesDTO update(NonAgriculturalMarketActiviesDTO NonAgriculturalMarketActivies);

    /**
     * Retrieves a paginated list of NonAgriculturalMarketActiviess.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of NonAgriculturalMarketActiviess
     * @since 1.0.0
     */
    PageDTO<NonAgriculturalMarketActiviesDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a NonAgriculturalMarketActivies by their ID with a specified reason.
     *
     * @param id     The ID of the NonAgriculturalMarketActivies to delete
     * @param reason The reason for deletion
     * @return NonAgriculturalMarketActiviesDTO
     */
    NonAgriculturalMarketActiviesDTO deleteById(Long id, String reason);

    /**
     * Retrieves a NonAgriculturalMarketActivies by their ID.
     *
     * @param id The ID of the NonAgriculturalMarketActivies to retrieve
     * @return The NonAgriculturalMarketActivies with the specified ID
     * @since 1.0.0
     */
    NonAgriculturalMarketActiviesDTO getById(Long id);


    /**
     * Adds a list of otherIndustryTypeList to a NonAgriculturalMarketActivies identified by their ID.
     *
     * @param id               The ID of the NonAgriculturalMarketActivies to add hobbies to
     * @param otherIndustryTypeList  to be added
     * @since 1.0.0
     */
    void addOtherIndustryTypeToNonAgriculturalMarketActivies(Long id, List<OtherIndustryTypeDTO> otherIndustryTypeList);



}
