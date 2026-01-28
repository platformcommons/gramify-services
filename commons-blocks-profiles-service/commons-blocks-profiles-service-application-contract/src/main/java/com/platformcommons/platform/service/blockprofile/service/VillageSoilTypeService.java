package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageSoilType-related operations.
 * Provides CRUD operations and pagination support for VillageSoilType entities.
 *
 * @since 1.0.0
 */

public interface VillageSoilTypeService {

    /**
     * Retrieves all VillageSoilTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageSoilTypeDTO in the same order as retrieved
     */
    Set<VillageSoilTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageSoilType entry in the system.
     *
     * @param VillageSoilType The VillageSoilType information to be saved
     * @return The saved VillageSoilType data
     * @since 1.0.0
     */
    VillageSoilTypeDTO save(VillageSoilTypeDTO VillageSoilType);

    /**
     * Updates an existing VillageSoilType entry.
     *
     * @param VillageSoilType The VillageSoilType information to be updated
     * @return The updated VillageSoilType data
     * @since 1.0.0
     */
    VillageSoilTypeDTO update(VillageSoilTypeDTO VillageSoilType);

    /**
     * Retrieves a paginated list of VillageSoilTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageSoilTypes
     * @since 1.0.0
     */
    PageDTO<VillageSoilTypeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageSoilType by their ID with a specified reason.
     *
     * @param id     The ID of the VillageSoilType to delete
     * @param reason The reason for deletion
     * @return VillageSoilTypeDTO
     */
    VillageSoilTypeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageSoilType by their ID.
     *
     * @param id The ID of the VillageSoilType to retrieve
     * @return The VillageSoilType with the specified ID
     * @since 1.0.0
     */
    VillageSoilTypeDTO getById(Long id);




}
