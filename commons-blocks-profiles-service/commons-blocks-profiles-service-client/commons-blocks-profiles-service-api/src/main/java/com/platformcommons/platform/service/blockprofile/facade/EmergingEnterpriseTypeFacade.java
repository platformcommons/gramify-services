package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing EmergingEnterpriseType-related operations in the system.
 * This interface provides methods for CRUD operations on EmergingEnterpriseType data,
 * including creation, retrieval, update, and deletion of EmergingEnterpriseType records.
 * It serves as the primary entry point for EmergingEnterpriseType management functionality.
 */
public interface EmergingEnterpriseTypeFacade {

    /**
     * Retrieves all EmergingEnterpriseTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of EmergingEnterpriseTypeDTO
     */
    Set<EmergingEnterpriseTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new EmergingEnterpriseType record in the system.
     *
     * @param EmergingEnterpriseTypeDTO The EmergingEnterpriseType data transfer object containing the information to be saved
     * @return The saved EmergingEnterpriseType data with generated identifiers and any system-modified fields
     */
    EmergingEnterpriseTypeDTO save(EmergingEnterpriseTypeDTO EmergingEnterpriseTypeDTO);

    /**
     * Updates an existing EmergingEnterpriseType record in the system.
     *
     * @param EmergingEnterpriseTypeDTO The EmergingEnterpriseType data transfer object containing the updated information
     * @return The updated EmergingEnterpriseType data as confirmed by the system
     */
    EmergingEnterpriseTypeDTO update(EmergingEnterpriseTypeDTO EmergingEnterpriseTypeDTO);

    /**
     * Retrieves a paginated list of all EmergingEnterpriseTypes in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested EmergingEnterpriseType records
     */
    PageDTO<EmergingEnterpriseTypeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a EmergingEnterpriseType record from the system.
     *
     * @param id     The unique identifier of the EmergingEnterpriseType to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific EmergingEnterpriseType by their unique identifier.
     *
     * @param id The unique identifier of the EmergingEnterpriseType to retrieve
     * @return The EmergingEnterpriseType data transfer object containing the requested information
     */
    EmergingEnterpriseTypeDTO getById(Long id);



}
