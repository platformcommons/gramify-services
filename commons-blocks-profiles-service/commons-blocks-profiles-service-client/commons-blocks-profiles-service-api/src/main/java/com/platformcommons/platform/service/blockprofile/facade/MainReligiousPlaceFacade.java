package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing MainReligiousPlace-related operations in the system.
 * This interface provides methods for CRUD operations on MainReligiousPlace data,
 * including creation, retrieval, update, and deletion of MainReligiousPlace records.
 * It serves as the primary entry point for MainReligiousPlace management functionality.
 */
public interface MainReligiousPlaceFacade {

    /**
     * Retrieves all MainReligiousPlaces by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MainReligiousPlaceDTO
     */
    Set<MainReligiousPlaceDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new MainReligiousPlace record in the system.
     *
     * @param MainReligiousPlaceDTO The MainReligiousPlace data transfer object containing the information to be saved
     * @return The saved MainReligiousPlace data with generated identifiers and any system-modified fields
     */
    MainReligiousPlaceDTO save(MainReligiousPlaceDTO MainReligiousPlaceDTO);

    /**
     * Updates an existing MainReligiousPlace record in the system.
     *
     * @param MainReligiousPlaceDTO The MainReligiousPlace data transfer object containing the updated information
     * @return The updated MainReligiousPlace data as confirmed by the system
     */
    MainReligiousPlaceDTO update(MainReligiousPlaceDTO MainReligiousPlaceDTO);

    /**
     * Retrieves a paginated list of all MainReligiousPlaces in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested MainReligiousPlace records
     */
    PageDTO<MainReligiousPlaceDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a MainReligiousPlace record from the system.
     *
     * @param id     The unique identifier of the MainReligiousPlace to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific MainReligiousPlace by their unique identifier.
     *
     * @param id The unique identifier of the MainReligiousPlace to retrieve
     * @return The MainReligiousPlace data transfer object containing the requested information
     */
    MainReligiousPlaceDTO getById(Long id);



}
