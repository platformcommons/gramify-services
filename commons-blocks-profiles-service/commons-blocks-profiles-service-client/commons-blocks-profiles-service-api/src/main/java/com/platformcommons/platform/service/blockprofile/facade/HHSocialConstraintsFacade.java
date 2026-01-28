package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HHSocialConstraints-related operations in the system.
 * This interface provides methods for CRUD operations on HHSocialConstraints data,
 * including creation, retrieval, update, and deletion of HHSocialConstraints records.
 * It serves as the primary entry point for HHSocialConstraints management functionality.
 */
public interface HHSocialConstraintsFacade {

    /**
     * Retrieves all HHSocialConstraintss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHSocialConstraintsDTO
     */
    Set<HHSocialConstraintsDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHSocialConstraints record in the system.
     *
     * @param HHSocialConstraintsDTO The HHSocialConstraints data transfer object containing the information to be saved
     * @return The saved HHSocialConstraints data with generated identifiers and any system-modified fields
     */
    HHSocialConstraintsDTO save(HHSocialConstraintsDTO HHSocialConstraintsDTO);

    /**
     * Updates an existing HHSocialConstraints record in the system.
     *
     * @param HHSocialConstraintsDTO The HHSocialConstraints data transfer object containing the updated information
     * @return The updated HHSocialConstraints data as confirmed by the system
     */
    HHSocialConstraintsDTO update(HHSocialConstraintsDTO HHSocialConstraintsDTO);

    /**
     * Retrieves a paginated list of all HHSocialConstraintss in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HHSocialConstraints records
     */
    PageDTO<HHSocialConstraintsDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HHSocialConstraints record from the system.
     *
     * @param id     The unique identifier of the HHSocialConstraints to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HHSocialConstraints by their unique identifier.
     *
     * @param id The unique identifier of the HHSocialConstraints to retrieve
     * @return The HHSocialConstraints data transfer object containing the requested information
     */
    HHSocialConstraintsDTO getById(Long id);



}
