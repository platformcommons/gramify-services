package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing NicheProductsAvailability-related operations.
 * Provides CRUD operations and pagination support for NicheProductsAvailability entities.
 *
 * @since 1.0.0
 */

public interface NicheProductsAvailabilityService {

    /**
     * Retrieves all NicheProductsAvailabilitys by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of NicheProductsAvailabilityDTO in the same order as retrieved
     */
    Set<NicheProductsAvailabilityDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new NicheProductsAvailability entry in the system.
     *
     * @param NicheProductsAvailability The NicheProductsAvailability information to be saved
     * @return The saved NicheProductsAvailability data
     * @since 1.0.0
     */
    NicheProductsAvailabilityDTO save(NicheProductsAvailabilityDTO NicheProductsAvailability);

    /**
     * Updates an existing NicheProductsAvailability entry.
     *
     * @param NicheProductsAvailability The NicheProductsAvailability information to be updated
     * @return The updated NicheProductsAvailability data
     * @since 1.0.0
     */
    NicheProductsAvailabilityDTO update(NicheProductsAvailabilityDTO NicheProductsAvailability);

    /**
     * Retrieves a paginated list of NicheProductsAvailabilitys.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of NicheProductsAvailabilitys
     * @since 1.0.0
     */
    PageDTO<NicheProductsAvailabilityDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a NicheProductsAvailability by their ID with a specified reason.
     *
     * @param id     The ID of the NicheProductsAvailability to delete
     * @param reason The reason for deletion
     * @return NicheProductsAvailabilityDTO
     */
    NicheProductsAvailabilityDTO deleteById(Long id, String reason);

    /**
     * Retrieves a NicheProductsAvailability by their ID.
     *
     * @param id The ID of the NicheProductsAvailability to retrieve
     * @return The NicheProductsAvailability with the specified ID
     * @since 1.0.0
     */
    NicheProductsAvailabilityDTO getById(Long id);




}
