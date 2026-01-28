package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing OperatingDay-related operations in the system.
 * This interface provides methods for CRUD operations on OperatingDay data,
 * including creation, retrieval, update, and deletion of OperatingDay records.
 * It serves as the primary entry point for OperatingDay management functionality.
 */
public interface OperatingDayFacade {

    /**
     * Retrieves all OperatingDays by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of OperatingDayDTO
     */
    Set<OperatingDayDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new OperatingDay record in the system.
     *
     * @param OperatingDayDTO The OperatingDay data transfer object containing the information to be saved
     * @return The saved OperatingDay data with generated identifiers and any system-modified fields
     */
    OperatingDayDTO save(OperatingDayDTO OperatingDayDTO);

    /**
     * Updates an existing OperatingDay record in the system.
     *
     * @param OperatingDayDTO The OperatingDay data transfer object containing the updated information
     * @return The updated OperatingDay data as confirmed by the system
     */
    OperatingDayDTO update(OperatingDayDTO OperatingDayDTO);

    /**
     * Retrieves a paginated list of all OperatingDays in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested OperatingDay records
     */
    PageDTO<OperatingDayDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a OperatingDay record from the system.
     *
     * @param id     The unique identifier of the OperatingDay to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific OperatingDay by their unique identifier.
     *
     * @param id The unique identifier of the OperatingDay to retrieve
     * @return The OperatingDay data transfer object containing the requested information
     */
    OperatingDayDTO getById(Long id);



}
