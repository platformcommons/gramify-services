package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing SurplusProduceType-related operations in the system.
 * This interface provides methods for CRUD operations on SurplusProduceType data,
 * including creation, retrieval, update, and deletion of SurplusProduceType records.
 * It serves as the primary entry point for SurplusProduceType management functionality.
 */
public interface SurplusProduceTypeFacade {

    /**
     * Retrieves all SurplusProduceTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SurplusProduceTypeDTO
     */
    Set<SurplusProduceTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new SurplusProduceType record in the system.
     *
     * @param SurplusProduceTypeDTO The SurplusProduceType data transfer object containing the information to be saved
     * @return The saved SurplusProduceType data with generated identifiers and any system-modified fields
     */
    SurplusProduceTypeDTO save(SurplusProduceTypeDTO SurplusProduceTypeDTO);

    /**
     * Updates an existing SurplusProduceType record in the system.
     *
     * @param SurplusProduceTypeDTO The SurplusProduceType data transfer object containing the updated information
     * @return The updated SurplusProduceType data as confirmed by the system
     */
    SurplusProduceTypeDTO update(SurplusProduceTypeDTO SurplusProduceTypeDTO);

    /**
     * Retrieves a paginated list of all SurplusProduceTypes in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested SurplusProduceType records
     */
    PageDTO<SurplusProduceTypeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a SurplusProduceType record from the system.
     *
     * @param id     The unique identifier of the SurplusProduceType to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific SurplusProduceType by their unique identifier.
     *
     * @param id The unique identifier of the SurplusProduceType to retrieve
     * @return The SurplusProduceType data transfer object containing the requested information
     */
    SurplusProduceTypeDTO getById(Long id);



}
