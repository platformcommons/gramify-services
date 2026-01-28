package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing LocalArtFormType-related operations.
 * Provides CRUD operations and pagination support for LocalArtFormType entities.
 *
 * @since 1.0.0
 */

public interface LocalArtFormTypeService {

    /**
     * Retrieves all LocalArtFormTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of LocalArtFormTypeDTO in the same order as retrieved
     */
    Set<LocalArtFormTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new LocalArtFormType entry in the system.
     *
     * @param LocalArtFormType The LocalArtFormType information to be saved
     * @return The saved LocalArtFormType data
     * @since 1.0.0
     */
    LocalArtFormTypeDTO save(LocalArtFormTypeDTO LocalArtFormType);

    /**
     * Updates an existing LocalArtFormType entry.
     *
     * @param LocalArtFormType The LocalArtFormType information to be updated
     * @return The updated LocalArtFormType data
     * @since 1.0.0
     */
    LocalArtFormTypeDTO update(LocalArtFormTypeDTO LocalArtFormType);

    /**
     * Retrieves a paginated list of LocalArtFormTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of LocalArtFormTypes
     * @since 1.0.0
     */
    PageDTO<LocalArtFormTypeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a LocalArtFormType by their ID with a specified reason.
     *
     * @param id     The ID of the LocalArtFormType to delete
     * @param reason The reason for deletion
     * @return LocalArtFormTypeDTO
     */
    LocalArtFormTypeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a LocalArtFormType by their ID.
     *
     * @param id The ID of the LocalArtFormType to retrieve
     * @return The LocalArtFormType with the specified ID
     * @since 1.0.0
     */
    LocalArtFormTypeDTO getById(Long id);




}
