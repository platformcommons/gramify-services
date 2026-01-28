package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageIrrigationSystemType-related operations in the system.
 * This interface provides methods for CRUD operations on VillageIrrigationSystemType data,
 * including creation, retrieval, update, and deletion of VillageIrrigationSystemType records.
 * It serves as the primary entry point for VillageIrrigationSystemType management functionality.
 */
public interface VillageIrrigationSystemTypeFacade {

    /**
     * Retrieves all VillageIrrigationSystemTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageIrrigationSystemTypeDTO
     */
    Set<VillageIrrigationSystemTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageIrrigationSystemType record in the system.
     *
     * @param VillageIrrigationSystemTypeDTO The VillageIrrigationSystemType data transfer object containing the information to be saved
     * @return The saved VillageIrrigationSystemType data with generated identifiers and any system-modified fields
     */
    VillageIrrigationSystemTypeDTO save(VillageIrrigationSystemTypeDTO VillageIrrigationSystemTypeDTO);

    /**
     * Updates an existing VillageIrrigationSystemType record in the system.
     *
     * @param VillageIrrigationSystemTypeDTO The VillageIrrigationSystemType data transfer object containing the updated information
     * @return The updated VillageIrrigationSystemType data as confirmed by the system
     */
    VillageIrrigationSystemTypeDTO update(VillageIrrigationSystemTypeDTO VillageIrrigationSystemTypeDTO);

    /**
     * Retrieves a paginated list of all VillageIrrigationSystemTypes in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageIrrigationSystemType records
     */
    PageDTO<VillageIrrigationSystemTypeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageIrrigationSystemType record from the system.
     *
     * @param id     The unique identifier of the VillageIrrigationSystemType to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageIrrigationSystemType by their unique identifier.
     *
     * @param id The unique identifier of the VillageIrrigationSystemType to retrieve
     * @return The VillageIrrigationSystemType data transfer object containing the requested information
     */
    VillageIrrigationSystemTypeDTO getById(Long id);



}
