package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing LocalFestival-related operations in the system.
 * This interface provides methods for CRUD operations on LocalFestival data,
 * including creation, retrieval, update, and deletion of LocalFestival records.
 * It serves as the primary entry point for LocalFestival management functionality.
 */
public interface LocalFestivalFacade {

    /**
     * Retrieves all LocalFestivals by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of LocalFestivalDTO
     */
    Set<LocalFestivalDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new LocalFestival record in the system.
     *
     * @param LocalFestivalDTO The LocalFestival data transfer object containing the information to be saved
     * @return The saved LocalFestival data with generated identifiers and any system-modified fields
     */
    LocalFestivalDTO save(LocalFestivalDTO LocalFestivalDTO);

    /**
     * Updates an existing LocalFestival record in the system.
     *
     * @param LocalFestivalDTO The LocalFestival data transfer object containing the updated information
     * @return The updated LocalFestival data as confirmed by the system
     */
    LocalFestivalDTO update(LocalFestivalDTO LocalFestivalDTO);

    /**
     * Retrieves a paginated list of all LocalFestivals in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested LocalFestival records
     */
    PageDTO<LocalFestivalDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a LocalFestival record from the system.
     *
     * @param id     The unique identifier of the LocalFestival to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific LocalFestival by their unique identifier.
     *
     * @param id The unique identifier of the LocalFestival to retrieve
     * @return The LocalFestival data transfer object containing the requested information
     */
    LocalFestivalDTO getById(Long id);



}
