package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageForestProduceType-related operations in the system.
 * This interface provides methods for CRUD operations on VillageForestProduceType data,
 * including creation, retrieval, update, and deletion of VillageForestProduceType records.
 * It serves as the primary entry point for VillageForestProduceType management functionality.
 */
public interface VillageForestProduceTypeFacade {

    /**
     * Retrieves all VillageForestProduceTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageForestProduceTypeDTO
     */
    Set<VillageForestProduceTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageForestProduceType record in the system.
     *
     * @param VillageForestProduceTypeDTO The VillageForestProduceType data transfer object containing the information to be saved
     * @return The saved VillageForestProduceType data with generated identifiers and any system-modified fields
     */
    VillageForestProduceTypeDTO save(VillageForestProduceTypeDTO VillageForestProduceTypeDTO);

    /**
     * Updates an existing VillageForestProduceType record in the system.
     *
     * @param VillageForestProduceTypeDTO The VillageForestProduceType data transfer object containing the updated information
     * @return The updated VillageForestProduceType data as confirmed by the system
     */
    VillageForestProduceTypeDTO update(VillageForestProduceTypeDTO VillageForestProduceTypeDTO);

    /**
     * Retrieves a paginated list of all VillageForestProduceTypes in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageForestProduceType records
     */
    PageDTO<VillageForestProduceTypeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageForestProduceType record from the system.
     *
     * @param id     The unique identifier of the VillageForestProduceType to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageForestProduceType by their unique identifier.
     *
     * @param id The unique identifier of the VillageForestProduceType to retrieve
     * @return The VillageForestProduceType data transfer object containing the requested information
     */
    VillageForestProduceTypeDTO getById(Long id);



}
