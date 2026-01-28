package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageSoilType-related operations in the system.
 * This interface provides methods for CRUD operations on VillageSoilType data,
 * including creation, retrieval, update, and deletion of VillageSoilType records.
 * It serves as the primary entry point for VillageSoilType management functionality.
 */
public interface VillageSoilTypeFacade {

    /**
     * Retrieves all VillageSoilTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageSoilTypeDTO
     */
    Set<VillageSoilTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageSoilType record in the system.
     *
     * @param VillageSoilTypeDTO The VillageSoilType data transfer object containing the information to be saved
     * @return The saved VillageSoilType data with generated identifiers and any system-modified fields
     */
    VillageSoilTypeDTO save(VillageSoilTypeDTO VillageSoilTypeDTO);

    /**
     * Updates an existing VillageSoilType record in the system.
     *
     * @param VillageSoilTypeDTO The VillageSoilType data transfer object containing the updated information
     * @return The updated VillageSoilType data as confirmed by the system
     */
    VillageSoilTypeDTO update(VillageSoilTypeDTO VillageSoilTypeDTO);

    /**
     * Retrieves a paginated list of all VillageSoilTypes in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageSoilType records
     */
    PageDTO<VillageSoilTypeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageSoilType record from the system.
     *
     * @param id     The unique identifier of the VillageSoilType to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageSoilType by their unique identifier.
     *
     * @param id The unique identifier of the VillageSoilType to retrieve
     * @return The VillageSoilType data transfer object containing the requested information
     */
    VillageSoilTypeDTO getById(Long id);



}
