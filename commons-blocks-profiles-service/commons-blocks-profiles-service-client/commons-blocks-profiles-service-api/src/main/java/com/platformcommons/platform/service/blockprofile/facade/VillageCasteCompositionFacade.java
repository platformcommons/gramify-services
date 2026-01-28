package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageCasteComposition-related operations in the system.
 * This interface provides methods for CRUD operations on VillageCasteComposition data,
 * including creation, retrieval, update, and deletion of VillageCasteComposition records.
 * It serves as the primary entry point for VillageCasteComposition management functionality.
 */
public interface VillageCasteCompositionFacade {

    /**
     * Retrieves all VillageCasteCompositions by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCasteCompositionDTO
     */
    Set<VillageCasteCompositionDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageCasteComposition record in the system.
     *
     * @param VillageCasteCompositionDTO The VillageCasteComposition data transfer object containing the information to be saved
     * @return The saved VillageCasteComposition data with generated identifiers and any system-modified fields
     */
    VillageCasteCompositionDTO save(VillageCasteCompositionDTO VillageCasteCompositionDTO);

    /**
     * Updates an existing VillageCasteComposition record in the system.
     *
     * @param VillageCasteCompositionDTO The VillageCasteComposition data transfer object containing the updated information
     * @return The updated VillageCasteComposition data as confirmed by the system
     */
    VillageCasteCompositionDTO update(VillageCasteCompositionDTO VillageCasteCompositionDTO);

    /**
     * Retrieves a paginated list of all VillageCasteCompositions in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageCasteComposition records
     */
    PageDTO<VillageCasteCompositionDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageCasteComposition record from the system.
     *
     * @param id     The unique identifier of the VillageCasteComposition to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageCasteComposition by their unique identifier.
     *
     * @param id The unique identifier of the VillageCasteComposition to retrieve
     * @return The VillageCasteComposition data transfer object containing the requested information
     */
    VillageCasteCompositionDTO getById(Long id);



}
