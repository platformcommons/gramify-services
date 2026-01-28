package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing NicheProductBuyerType-related operations in the system.
 * This interface provides methods for CRUD operations on NicheProductBuyerType data,
 * including creation, retrieval, update, and deletion of NicheProductBuyerType records.
 * It serves as the primary entry point for NicheProductBuyerType management functionality.
 */
public interface NicheProductBuyerTypeFacade {

    /**
     * Retrieves all NicheProductBuyerTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of NicheProductBuyerTypeDTO
     */
    Set<NicheProductBuyerTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new NicheProductBuyerType record in the system.
     *
     * @param NicheProductBuyerTypeDTO The NicheProductBuyerType data transfer object containing the information to be saved
     * @return The saved NicheProductBuyerType data with generated identifiers and any system-modified fields
     */
    NicheProductBuyerTypeDTO save(NicheProductBuyerTypeDTO NicheProductBuyerTypeDTO);

    /**
     * Updates an existing NicheProductBuyerType record in the system.
     *
     * @param NicheProductBuyerTypeDTO The NicheProductBuyerType data transfer object containing the updated information
     * @return The updated NicheProductBuyerType data as confirmed by the system
     */
    NicheProductBuyerTypeDTO update(NicheProductBuyerTypeDTO NicheProductBuyerTypeDTO);

    /**
     * Retrieves a paginated list of all NicheProductBuyerTypes in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested NicheProductBuyerType records
     */
    PageDTO<NicheProductBuyerTypeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a NicheProductBuyerType record from the system.
     *
     * @param id     The unique identifier of the NicheProductBuyerType to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific NicheProductBuyerType by their unique identifier.
     *
     * @param id The unique identifier of the NicheProductBuyerType to retrieve
     * @return The NicheProductBuyerType data transfer object containing the requested information
     */
    NicheProductBuyerTypeDTO getById(Long id);



}
