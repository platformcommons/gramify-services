package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageCasteComposition-related operations.
 * Provides CRUD operations and pagination support for VillageCasteComposition entities.
 *
 * @since 1.0.0
 */

public interface VillageCasteCompositionService {

    /**
     * Retrieves all VillageCasteCompositions by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCasteCompositionDTO in the same order as retrieved
     */
    Set<VillageCasteCompositionDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageCasteComposition entry in the system.
     *
     * @param VillageCasteComposition The VillageCasteComposition information to be saved
     * @return The saved VillageCasteComposition data
     * @since 1.0.0
     */
    VillageCasteCompositionDTO save(VillageCasteCompositionDTO VillageCasteComposition);

    /**
     * Updates an existing VillageCasteComposition entry.
     *
     * @param VillageCasteComposition The VillageCasteComposition information to be updated
     * @return The updated VillageCasteComposition data
     * @since 1.0.0
     */
    VillageCasteCompositionDTO update(VillageCasteCompositionDTO VillageCasteComposition);

    /**
     * Retrieves a paginated list of VillageCasteCompositions.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageCasteCompositions
     * @since 1.0.0
     */
    PageDTO<VillageCasteCompositionDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageCasteComposition by their ID with a specified reason.
     *
     * @param id     The ID of the VillageCasteComposition to delete
     * @param reason The reason for deletion
     * @return VillageCasteCompositionDTO
     */
    VillageCasteCompositionDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageCasteComposition by their ID.
     *
     * @param id The ID of the VillageCasteComposition to retrieve
     * @return The VillageCasteComposition with the specified ID
     * @since 1.0.0
     */
    VillageCasteCompositionDTO getById(Long id);




}
