package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing MainReligiousPlace-related operations.
 * Provides CRUD operations and pagination support for MainReligiousPlace entities.
 *
 * @since 1.0.0
 */

public interface MainReligiousPlaceService {

    /**
     * Retrieves all MainReligiousPlaces by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MainReligiousPlaceDTO in the same order as retrieved
     */
    Set<MainReligiousPlaceDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new MainReligiousPlace entry in the system.
     *
     * @param MainReligiousPlace The MainReligiousPlace information to be saved
     * @return The saved MainReligiousPlace data
     * @since 1.0.0
     */
    MainReligiousPlaceDTO save(MainReligiousPlaceDTO MainReligiousPlace);

    /**
     * Updates an existing MainReligiousPlace entry.
     *
     * @param MainReligiousPlace The MainReligiousPlace information to be updated
     * @return The updated MainReligiousPlace data
     * @since 1.0.0
     */
    MainReligiousPlaceDTO update(MainReligiousPlaceDTO MainReligiousPlace);

    /**
     * Retrieves a paginated list of MainReligiousPlaces.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of MainReligiousPlaces
     * @since 1.0.0
     */
    PageDTO<MainReligiousPlaceDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a MainReligiousPlace by their ID with a specified reason.
     *
     * @param id     The ID of the MainReligiousPlace to delete
     * @param reason The reason for deletion
     * @return MainReligiousPlaceDTO
     */
    MainReligiousPlaceDTO deleteById(Long id, String reason);

    /**
     * Retrieves a MainReligiousPlace by their ID.
     *
     * @param id The ID of the MainReligiousPlace to retrieve
     * @return The MainReligiousPlace with the specified ID
     * @since 1.0.0
     */
    MainReligiousPlaceDTO getById(Long id);




}
