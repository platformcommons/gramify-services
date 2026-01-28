package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing RenewableInfraType-related operations.
 * Provides CRUD operations and pagination support for RenewableInfraType entities.
 *
 * @since 1.0.0
 */

public interface RenewableInfraTypeService {

    /**
     * Retrieves all RenewableInfraTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of RenewableInfraTypeDTO in the same order as retrieved
     */
    Set<RenewableInfraTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new RenewableInfraType entry in the system.
     *
     * @param RenewableInfraType The RenewableInfraType information to be saved
     * @return The saved RenewableInfraType data
     * @since 1.0.0
     */
    RenewableInfraTypeDTO save(RenewableInfraTypeDTO RenewableInfraType);

    /**
     * Updates an existing RenewableInfraType entry.
     *
     * @param RenewableInfraType The RenewableInfraType information to be updated
     * @return The updated RenewableInfraType data
     * @since 1.0.0
     */
    RenewableInfraTypeDTO update(RenewableInfraTypeDTO RenewableInfraType);

    /**
     * Retrieves a paginated list of RenewableInfraTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of RenewableInfraTypes
     * @since 1.0.0
     */
    PageDTO<RenewableInfraTypeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a RenewableInfraType by their ID with a specified reason.
     *
     * @param id     The ID of the RenewableInfraType to delete
     * @param reason The reason for deletion
     * @return RenewableInfraTypeDTO
     */
    RenewableInfraTypeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a RenewableInfraType by their ID.
     *
     * @param id The ID of the RenewableInfraType to retrieve
     * @return The RenewableInfraType with the specified ID
     * @since 1.0.0
     */
    RenewableInfraTypeDTO getById(Long id);




}
