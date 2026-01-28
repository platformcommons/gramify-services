package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing NicheProductsAvailability-related operations in the system.
 * This interface provides methods for CRUD operations on NicheProductsAvailability data,
 * including creation, retrieval, update, and deletion of NicheProductsAvailability records.
 * It serves as the primary entry point for NicheProductsAvailability management functionality.
 */
public interface NicheProductsAvailabilityFacade {

    /**
     * Retrieves all NicheProductsAvailabilitys by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of NicheProductsAvailabilityDTO
     */
    Set<NicheProductsAvailabilityDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new NicheProductsAvailability record in the system.
     *
     * @param NicheProductsAvailabilityDTO The NicheProductsAvailability data transfer object containing the information to be saved
     * @return The saved NicheProductsAvailability data with generated identifiers and any system-modified fields
     */
    NicheProductsAvailabilityDTO save(NicheProductsAvailabilityDTO NicheProductsAvailabilityDTO);

    /**
     * Updates an existing NicheProductsAvailability record in the system.
     *
     * @param NicheProductsAvailabilityDTO The NicheProductsAvailability data transfer object containing the updated information
     * @return The updated NicheProductsAvailability data as confirmed by the system
     */
    NicheProductsAvailabilityDTO update(NicheProductsAvailabilityDTO NicheProductsAvailabilityDTO);

    /**
     * Retrieves a paginated list of all NicheProductsAvailabilitys in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested NicheProductsAvailability records
     */
    PageDTO<NicheProductsAvailabilityDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a NicheProductsAvailability record from the system.
     *
     * @param id     The unique identifier of the NicheProductsAvailability to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific NicheProductsAvailability by their unique identifier.
     *
     * @param id The unique identifier of the NicheProductsAvailability to retrieve
     * @return The NicheProductsAvailability data transfer object containing the requested information
     */
    NicheProductsAvailabilityDTO getById(Long id);



}
