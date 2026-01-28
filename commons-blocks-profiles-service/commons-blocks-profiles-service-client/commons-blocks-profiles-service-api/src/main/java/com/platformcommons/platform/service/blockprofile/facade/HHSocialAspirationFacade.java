package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing HHSocialAspiration-related operations in the system.
 * This interface provides methods for CRUD operations on HHSocialAspiration data,
 * including creation, retrieval, update, and deletion of HHSocialAspiration records.
 * It serves as the primary entry point for HHSocialAspiration management functionality.
 */
public interface HHSocialAspirationFacade {

    /**
     * Retrieves all HHSocialAspirations by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHSocialAspirationDTO
     */
    Set<HHSocialAspirationDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new HHSocialAspiration record in the system.
     *
     * @param HHSocialAspirationDTO The HHSocialAspiration data transfer object containing the information to be saved
     * @return The saved HHSocialAspiration data with generated identifiers and any system-modified fields
     */
    HHSocialAspirationDTO save(HHSocialAspirationDTO HHSocialAspirationDTO);

    /**
     * Updates an existing HHSocialAspiration record in the system.
     *
     * @param HHSocialAspirationDTO The HHSocialAspiration data transfer object containing the updated information
     * @return The updated HHSocialAspiration data as confirmed by the system
     */
    HHSocialAspirationDTO update(HHSocialAspirationDTO HHSocialAspirationDTO);

    /**
     * Retrieves a paginated list of all HHSocialAspirations in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested HHSocialAspiration records
     */
    PageDTO<HHSocialAspirationDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a HHSocialAspiration record from the system.
     *
     * @param id     The unique identifier of the HHSocialAspiration to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific HHSocialAspiration by their unique identifier.
     *
     * @param id The unique identifier of the HHSocialAspiration to retrieve
     * @return The HHSocialAspiration data transfer object containing the requested information
     */
    HHSocialAspirationDTO getById(Long id);



}
