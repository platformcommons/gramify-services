package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing NicheProductBuyerType-related operations.
 * Provides CRUD operations and pagination support for NicheProductBuyerType entities.
 *
 * @since 1.0.0
 */

public interface NicheProductBuyerTypeService {

    /**
     * Retrieves all NicheProductBuyerTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of NicheProductBuyerTypeDTO in the same order as retrieved
     */
    Set<NicheProductBuyerTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new NicheProductBuyerType entry in the system.
     *
     * @param NicheProductBuyerType The NicheProductBuyerType information to be saved
     * @return The saved NicheProductBuyerType data
     * @since 1.0.0
     */
    NicheProductBuyerTypeDTO save(NicheProductBuyerTypeDTO NicheProductBuyerType);

    /**
     * Updates an existing NicheProductBuyerType entry.
     *
     * @param NicheProductBuyerType The NicheProductBuyerType information to be updated
     * @return The updated NicheProductBuyerType data
     * @since 1.0.0
     */
    NicheProductBuyerTypeDTO update(NicheProductBuyerTypeDTO NicheProductBuyerType);

    /**
     * Retrieves a paginated list of NicheProductBuyerTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of NicheProductBuyerTypes
     * @since 1.0.0
     */
    PageDTO<NicheProductBuyerTypeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a NicheProductBuyerType by their ID with a specified reason.
     *
     * @param id     The ID of the NicheProductBuyerType to delete
     * @param reason The reason for deletion
     * @return NicheProductBuyerTypeDTO
     */
    NicheProductBuyerTypeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a NicheProductBuyerType by their ID.
     *
     * @param id The ID of the NicheProductBuyerType to retrieve
     * @return The NicheProductBuyerType with the specified ID
     * @since 1.0.0
     */
    NicheProductBuyerTypeDTO getById(Long id);




}
