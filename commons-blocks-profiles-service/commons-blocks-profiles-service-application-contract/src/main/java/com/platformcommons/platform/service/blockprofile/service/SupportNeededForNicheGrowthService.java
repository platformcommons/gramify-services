package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing SupportNeededForNicheGrowth-related operations.
 * Provides CRUD operations and pagination support for SupportNeededForNicheGrowth entities.
 *
 * @since 1.0.0
 */

public interface SupportNeededForNicheGrowthService {

    /**
     * Retrieves all SupportNeededForNicheGrowths by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SupportNeededForNicheGrowthDTO in the same order as retrieved
     */
    Set<SupportNeededForNicheGrowthDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new SupportNeededForNicheGrowth entry in the system.
     *
     * @param SupportNeededForNicheGrowth The SupportNeededForNicheGrowth information to be saved
     * @return The saved SupportNeededForNicheGrowth data
     * @since 1.0.0
     */
    SupportNeededForNicheGrowthDTO save(SupportNeededForNicheGrowthDTO SupportNeededForNicheGrowth);

    /**
     * Updates an existing SupportNeededForNicheGrowth entry.
     *
     * @param SupportNeededForNicheGrowth The SupportNeededForNicheGrowth information to be updated
     * @return The updated SupportNeededForNicheGrowth data
     * @since 1.0.0
     */
    SupportNeededForNicheGrowthDTO update(SupportNeededForNicheGrowthDTO SupportNeededForNicheGrowth);

    /**
     * Retrieves a paginated list of SupportNeededForNicheGrowths.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of SupportNeededForNicheGrowths
     * @since 1.0.0
     */
    PageDTO<SupportNeededForNicheGrowthDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a SupportNeededForNicheGrowth by their ID with a specified reason.
     *
     * @param id     The ID of the SupportNeededForNicheGrowth to delete
     * @param reason The reason for deletion
     * @return SupportNeededForNicheGrowthDTO
     */
    SupportNeededForNicheGrowthDTO deleteById(Long id, String reason);

    /**
     * Retrieves a SupportNeededForNicheGrowth by their ID.
     *
     * @param id The ID of the SupportNeededForNicheGrowth to retrieve
     * @return The SupportNeededForNicheGrowth with the specified ID
     * @since 1.0.0
     */
    SupportNeededForNicheGrowthDTO getById(Long id);




}
