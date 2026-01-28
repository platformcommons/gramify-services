package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing RenewableInfraType-related operations in the system.
 * This interface provides methods for CRUD operations on RenewableInfraType data,
 * including creation, retrieval, update, and deletion of RenewableInfraType records.
 * It serves as the primary entry point for RenewableInfraType management functionality.
 */
public interface RenewableInfraTypeFacade {

    /**
     * Retrieves all RenewableInfraTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of RenewableInfraTypeDTO
     */
    Set<RenewableInfraTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new RenewableInfraType record in the system.
     *
     * @param RenewableInfraTypeDTO The RenewableInfraType data transfer object containing the information to be saved
     * @return The saved RenewableInfraType data with generated identifiers and any system-modified fields
     */
    RenewableInfraTypeDTO save(RenewableInfraTypeDTO RenewableInfraTypeDTO);

    /**
     * Updates an existing RenewableInfraType record in the system.
     *
     * @param RenewableInfraTypeDTO The RenewableInfraType data transfer object containing the updated information
     * @return The updated RenewableInfraType data as confirmed by the system
     */
    RenewableInfraTypeDTO update(RenewableInfraTypeDTO RenewableInfraTypeDTO);

    /**
     * Retrieves a paginated list of all RenewableInfraTypes in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested RenewableInfraType records
     */
    PageDTO<RenewableInfraTypeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a RenewableInfraType record from the system.
     *
     * @param id     The unique identifier of the RenewableInfraType to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific RenewableInfraType by their unique identifier.
     *
     * @param id The unique identifier of the RenewableInfraType to retrieve
     * @return The RenewableInfraType data transfer object containing the requested information
     */
    RenewableInfraTypeDTO getById(Long id);



}
