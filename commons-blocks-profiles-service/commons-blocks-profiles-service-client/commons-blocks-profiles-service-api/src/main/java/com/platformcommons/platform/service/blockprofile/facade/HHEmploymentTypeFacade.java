package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HHEmploymentType-related operations in the system.
 * This interface provides methods for CRUD operations on HHEmploymentType data,
 * including creation, retrieval, update, and deletion of HHEmploymentType records.
 * It serves as the primary entry point for HHEmploymentType management functionality.
 */
public interface HHEmploymentTypeFacade {

    /**
     * Retrieves all HHEmploymentTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHEmploymentTypeDTO
     */
    Set<HHEmploymentTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHEmploymentType record in the system.
     *
     * @param HHEmploymentTypeDTO The HHEmploymentType data transfer object containing the information to be saved
     * @return The saved HHEmploymentType data with generated identifiers and any system-modified fields
     */
    HHEmploymentTypeDTO save(HHEmploymentTypeDTO HHEmploymentTypeDTO);

    /**
     * Updates an existing HHEmploymentType record in the system.
     *
     * @param HHEmploymentTypeDTO The HHEmploymentType data transfer object containing the updated information
     * @return The updated HHEmploymentType data as confirmed by the system
     */
    HHEmploymentTypeDTO update(HHEmploymentTypeDTO HHEmploymentTypeDTO);

    /**
     * Retrieves a paginated list of all HHEmploymentTypes in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HHEmploymentType records
     */
    PageDTO<HHEmploymentTypeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HHEmploymentType record from the system.
     *
     * @param id     The unique identifier of the HHEmploymentType to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HHEmploymentType by their unique identifier.
     *
     * @param id The unique identifier of the HHEmploymentType to retrieve
     * @return The HHEmploymentType data transfer object containing the requested information
     */
    HHEmploymentTypeDTO getById(Long id);



}
