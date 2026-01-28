package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing HHEconomicConstraints-related operations.
 * Provides CRUD operations and pagination support for HHEconomicConstraints entities.
 *
 * @since 1.0.0
 */

public interface HHEconomicConstraintsService {

    /**
     * Retrieves all HHEconomicConstraintss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHEconomicConstraintsDTO in the same order as retrieved
     */
    Set<HHEconomicConstraintsDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHEconomicConstraints entry in the system.
     *
     * @param HHEconomicConstraints The HHEconomicConstraints information to be saved
     * @return The saved HHEconomicConstraints data
     * @since 1.0.0
     */
    HHEconomicConstraintsDTO save(HHEconomicConstraintsDTO HHEconomicConstraints);

    /**
     * Updates an existing HHEconomicConstraints entry.
     *
     * @param HHEconomicConstraints The HHEconomicConstraints information to be updated
     * @return The updated HHEconomicConstraints data
     * @since 1.0.0
     */
    HHEconomicConstraintsDTO update(HHEconomicConstraintsDTO HHEconomicConstraints);

    /**
     * Retrieves a paginated list of HHEconomicConstraintss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHEconomicConstraintss
     * @since 1.0.0
     */
    PageDTO<HHEconomicConstraintsDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a HHEconomicConstraints by their ID with a specified reason.
     *
     * @param id     The ID of the HHEconomicConstraints to delete
     * @param reason The reason for deletion
     * @return HHEconomicConstraintsDTO
     */
    HHEconomicConstraintsDTO deleteById(Long id, String reason);

    /**
     * Retrieves a HHEconomicConstraints by their ID.
     *
     * @param id The ID of the HHEconomicConstraints to retrieve
     * @return The HHEconomicConstraints with the specified ID
     * @since 1.0.0
     */
    HHEconomicConstraintsDTO getById(Long id);




}
