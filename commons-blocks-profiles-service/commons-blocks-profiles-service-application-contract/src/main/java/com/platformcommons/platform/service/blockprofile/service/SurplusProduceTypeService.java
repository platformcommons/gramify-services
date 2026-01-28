package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing SurplusProduceType-related operations.
 * Provides CRUD operations and pagination support for SurplusProduceType entities.
 *
 * @since 1.0.0
 */

public interface SurplusProduceTypeService {

    /**
     * Retrieves all SurplusProduceTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SurplusProduceTypeDTO in the same order as retrieved
     */
    Set<SurplusProduceTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new SurplusProduceType entry in the system.
     *
     * @param SurplusProduceType The SurplusProduceType information to be saved
     * @return The saved SurplusProduceType data
     * @since 1.0.0
     */
    SurplusProduceTypeDTO save(SurplusProduceTypeDTO SurplusProduceType);

    /**
     * Updates an existing SurplusProduceType entry.
     *
     * @param SurplusProduceType The SurplusProduceType information to be updated
     * @return The updated SurplusProduceType data
     * @since 1.0.0
     */
    SurplusProduceTypeDTO update(SurplusProduceTypeDTO SurplusProduceType);

    /**
     * Retrieves a paginated list of SurplusProduceTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of SurplusProduceTypes
     * @since 1.0.0
     */
    PageDTO<SurplusProduceTypeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a SurplusProduceType by their ID with a specified reason.
     *
     * @param id     The ID of the SurplusProduceType to delete
     * @param reason The reason for deletion
     * @return SurplusProduceTypeDTO
     */
    SurplusProduceTypeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a SurplusProduceType by their ID.
     *
     * @param id The ID of the SurplusProduceType to retrieve
     * @return The SurplusProduceType with the specified ID
     * @since 1.0.0
     */
    SurplusProduceTypeDTO getById(Long id);




}
