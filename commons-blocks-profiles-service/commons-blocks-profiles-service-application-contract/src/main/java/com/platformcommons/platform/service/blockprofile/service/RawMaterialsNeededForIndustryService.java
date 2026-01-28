package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing RawMaterialsNeededForIndustry-related operations.
 * Provides CRUD operations and pagination support for RawMaterialsNeededForIndustry entities.
 *
 * @since 1.0.0
 */

public interface RawMaterialsNeededForIndustryService {

    /**
     * Retrieves all RawMaterialsNeededForIndustrys by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of RawMaterialsNeededForIndustryDTO in the same order as retrieved
     */
    Set<RawMaterialsNeededForIndustryDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new RawMaterialsNeededForIndustry entry in the system.
     *
     * @param RawMaterialsNeededForIndustry The RawMaterialsNeededForIndustry information to be saved
     * @return The saved RawMaterialsNeededForIndustry data
     * @since 1.0.0
     */
    RawMaterialsNeededForIndustryDTO save(RawMaterialsNeededForIndustryDTO RawMaterialsNeededForIndustry);

    /**
     * Updates an existing RawMaterialsNeededForIndustry entry.
     *
     * @param RawMaterialsNeededForIndustry The RawMaterialsNeededForIndustry information to be updated
     * @return The updated RawMaterialsNeededForIndustry data
     * @since 1.0.0
     */
    RawMaterialsNeededForIndustryDTO update(RawMaterialsNeededForIndustryDTO RawMaterialsNeededForIndustry);

    /**
     * Retrieves a paginated list of RawMaterialsNeededForIndustrys.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of RawMaterialsNeededForIndustrys
     * @since 1.0.0
     */
    PageDTO<RawMaterialsNeededForIndustryDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a RawMaterialsNeededForIndustry by their ID with a specified reason.
     *
     * @param id     The ID of the RawMaterialsNeededForIndustry to delete
     * @param reason The reason for deletion
     * @return RawMaterialsNeededForIndustryDTO
     */
    RawMaterialsNeededForIndustryDTO deleteById(Long id, String reason);

    /**
     * Retrieves a RawMaterialsNeededForIndustry by their ID.
     *
     * @param id The ID of the RawMaterialsNeededForIndustry to retrieve
     * @return The RawMaterialsNeededForIndustry with the specified ID
     * @since 1.0.0
     */
    RawMaterialsNeededForIndustryDTO getById(Long id);




}
