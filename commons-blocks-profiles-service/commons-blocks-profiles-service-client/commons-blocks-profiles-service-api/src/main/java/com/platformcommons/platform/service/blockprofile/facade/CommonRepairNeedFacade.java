package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing CommonRepairNeed-related operations in the system.
 * This interface provides methods for CRUD operations on CommonRepairNeed data,
 * including creation, retrieval, update, and deletion of CommonRepairNeed records.
 * It serves as the primary entry point for CommonRepairNeed management functionality.
 */
public interface CommonRepairNeedFacade {

    /**
     * Retrieves all CommonRepairNeeds by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CommonRepairNeedDTO
     */
    Set<CommonRepairNeedDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new CommonRepairNeed record in the system.
     *
     * @param CommonRepairNeedDTO The CommonRepairNeed data transfer object containing the information to be saved
     * @return The saved CommonRepairNeed data with generated identifiers and any system-modified fields
     */
    CommonRepairNeedDTO save(CommonRepairNeedDTO CommonRepairNeedDTO);

    /**
     * Updates an existing CommonRepairNeed record in the system.
     *
     * @param CommonRepairNeedDTO The CommonRepairNeed data transfer object containing the updated information
     * @return The updated CommonRepairNeed data as confirmed by the system
     */
    CommonRepairNeedDTO update(CommonRepairNeedDTO CommonRepairNeedDTO);

    /**
     * Retrieves a paginated list of all CommonRepairNeeds in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested CommonRepairNeed records
     */
    PageDTO<CommonRepairNeedDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a CommonRepairNeed record from the system.
     *
     * @param id     The unique identifier of the CommonRepairNeed to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific CommonRepairNeed by their unique identifier.
     *
     * @param id The unique identifier of the CommonRepairNeed to retrieve
     * @return The CommonRepairNeed data transfer object containing the requested information
     */
    CommonRepairNeedDTO getById(Long id);



}
