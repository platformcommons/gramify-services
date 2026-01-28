package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing PesticidesInDemand-related operations.
 * Provides CRUD operations and pagination support for PesticidesInDemand entities.
 *
 * @since 1.0.0
 */

public interface PesticidesInDemandService {

    /**
     * Retrieves all PesticidesInDemands by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of PesticidesInDemandDTO in the same order as retrieved
     */
    Set<PesticidesInDemandDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new PesticidesInDemand entry in the system.
     *
     * @param PesticidesInDemand The PesticidesInDemand information to be saved
     * @return The saved PesticidesInDemand data
     * @since 1.0.0
     */
    PesticidesInDemandDTO save(PesticidesInDemandDTO PesticidesInDemand);

    /**
     * Updates an existing PesticidesInDemand entry.
     *
     * @param PesticidesInDemand The PesticidesInDemand information to be updated
     * @return The updated PesticidesInDemand data
     * @since 1.0.0
     */
    PesticidesInDemandDTO update(PesticidesInDemandDTO PesticidesInDemand);

    /**
     * Retrieves a paginated list of PesticidesInDemands.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of PesticidesInDemands
     * @since 1.0.0
     */
    PageDTO<PesticidesInDemandDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a PesticidesInDemand by their ID with a specified reason.
     *
     * @param id     The ID of the PesticidesInDemand to delete
     * @param reason The reason for deletion
     * @return PesticidesInDemandDTO
     */
    PesticidesInDemandDTO deleteById(Long id, String reason);

    /**
     * Retrieves a PesticidesInDemand by their ID.
     *
     * @param id The ID of the PesticidesInDemand to retrieve
     * @return The PesticidesInDemand with the specified ID
     * @since 1.0.0
     */
    PesticidesInDemandDTO getById(Long id);




}
