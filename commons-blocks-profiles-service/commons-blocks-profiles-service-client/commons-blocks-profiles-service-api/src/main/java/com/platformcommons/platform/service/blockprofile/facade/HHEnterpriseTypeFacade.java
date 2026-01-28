package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HHEnterpriseType-related operations in the system.
 * This interface provides methods for CRUD operations on HHEnterpriseType data,
 * including creation, retrieval, update, and deletion of HHEnterpriseType records.
 * It serves as the primary entry point for HHEnterpriseType management functionality.
 */
public interface HHEnterpriseTypeFacade {

    /**
     * Retrieves all HHEnterpriseTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHEnterpriseTypeDTO
     */
    Set<HHEnterpriseTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHEnterpriseType record in the system.
     *
     * @param HHEnterpriseTypeDTO The HHEnterpriseType data transfer object containing the information to be saved
     * @return The saved HHEnterpriseType data with generated identifiers and any system-modified fields
     */
    HHEnterpriseTypeDTO save(HHEnterpriseTypeDTO HHEnterpriseTypeDTO);

    /**
     * Updates an existing HHEnterpriseType record in the system.
     *
     * @param HHEnterpriseTypeDTO The HHEnterpriseType data transfer object containing the updated information
     * @return The updated HHEnterpriseType data as confirmed by the system
     */
    HHEnterpriseTypeDTO update(HHEnterpriseTypeDTO HHEnterpriseTypeDTO);

    /**
     * Retrieves a paginated list of all HHEnterpriseTypes in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HHEnterpriseType records
     */
    PageDTO<HHEnterpriseTypeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HHEnterpriseType record from the system.
     *
     * @param id     The unique identifier of the HHEnterpriseType to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HHEnterpriseType by their unique identifier.
     *
     * @param id The unique identifier of the HHEnterpriseType to retrieve
     * @return The HHEnterpriseType data transfer object containing the requested information
     */
    HHEnterpriseTypeDTO getById(Long id);



}
