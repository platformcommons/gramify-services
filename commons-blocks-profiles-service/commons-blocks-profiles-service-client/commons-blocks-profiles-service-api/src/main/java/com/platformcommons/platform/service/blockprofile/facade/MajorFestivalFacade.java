package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing MajorFestival-related operations in the system.
 * This interface provides methods for CRUD operations on MajorFestival data,
 * including creation, retrieval, update, and deletion of MajorFestival records.
 * It serves as the primary entry point for MajorFestival management functionality.
 */
public interface MajorFestivalFacade {

    /**
     * Retrieves all MajorFestivals by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MajorFestivalDTO
     */
    Set<MajorFestivalDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new MajorFestival record in the system.
     *
     * @param MajorFestivalDTO The MajorFestival data transfer object containing the information to be saved
     * @return The saved MajorFestival data with generated identifiers and any system-modified fields
     */
    MajorFestivalDTO save(MajorFestivalDTO MajorFestivalDTO);

    /**
     * Updates an existing MajorFestival record in the system.
     *
     * @param MajorFestivalDTO The MajorFestival data transfer object containing the updated information
     * @return The updated MajorFestival data as confirmed by the system
     */
    MajorFestivalDTO update(MajorFestivalDTO MajorFestivalDTO);

    /**
     * Retrieves a paginated list of all MajorFestivals in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested MajorFestival records
     */
    PageDTO<MajorFestivalDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a MajorFestival record from the system.
     *
     * @param id     The unique identifier of the MajorFestival to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific MajorFestival by their unique identifier.
     *
     * @param id The unique identifier of the MajorFestival to retrieve
     * @return The MajorFestival data transfer object containing the requested information
     */
    MajorFestivalDTO getById(Long id);



}
