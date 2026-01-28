package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HHEconomicConstraints-related operations in the system.
 * This interface provides methods for CRUD operations on HHEconomicConstraints data,
 * including creation, retrieval, update, and deletion of HHEconomicConstraints records.
 * It serves as the primary entry point for HHEconomicConstraints management functionality.
 */
public interface HHEconomicConstraintsFacade {

    /**
     * Retrieves all HHEconomicConstraintss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHEconomicConstraintsDTO
     */
    Set<HHEconomicConstraintsDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHEconomicConstraints record in the system.
     *
     * @param HHEconomicConstraintsDTO The HHEconomicConstraints data transfer object containing the information to be saved
     * @return The saved HHEconomicConstraints data with generated identifiers and any system-modified fields
     */
    HHEconomicConstraintsDTO save(HHEconomicConstraintsDTO HHEconomicConstraintsDTO);

    /**
     * Updates an existing HHEconomicConstraints record in the system.
     *
     * @param HHEconomicConstraintsDTO The HHEconomicConstraints data transfer object containing the updated information
     * @return The updated HHEconomicConstraints data as confirmed by the system
     */
    HHEconomicConstraintsDTO update(HHEconomicConstraintsDTO HHEconomicConstraintsDTO);

    /**
     * Retrieves a paginated list of all HHEconomicConstraintss in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HHEconomicConstraints records
     */
    PageDTO<HHEconomicConstraintsDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HHEconomicConstraints record from the system.
     *
     * @param id     The unique identifier of the HHEconomicConstraints to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HHEconomicConstraints by their unique identifier.
     *
     * @param id The unique identifier of the HHEconomicConstraints to retrieve
     * @return The HHEconomicConstraints data transfer object containing the requested information
     */
    HHEconomicConstraintsDTO getById(Long id);



}
