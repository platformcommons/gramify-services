package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing LocalArtFormType-related operations in the system.
 * This interface provides methods for CRUD operations on LocalArtFormType data,
 * including creation, retrieval, update, and deletion of LocalArtFormType records.
 * It serves as the primary entry point for LocalArtFormType management functionality.
 */
public interface LocalArtFormTypeFacade {

    /**
     * Retrieves all LocalArtFormTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of LocalArtFormTypeDTO
     */
    Set<LocalArtFormTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new LocalArtFormType record in the system.
     *
     * @param LocalArtFormTypeDTO The LocalArtFormType data transfer object containing the information to be saved
     * @return The saved LocalArtFormType data with generated identifiers and any system-modified fields
     */
    LocalArtFormTypeDTO save(LocalArtFormTypeDTO LocalArtFormTypeDTO);

    /**
     * Updates an existing LocalArtFormType record in the system.
     *
     * @param LocalArtFormTypeDTO The LocalArtFormType data transfer object containing the updated information
     * @return The updated LocalArtFormType data as confirmed by the system
     */
    LocalArtFormTypeDTO update(LocalArtFormTypeDTO LocalArtFormTypeDTO);

    /**
     * Retrieves a paginated list of all LocalArtFormTypes in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested LocalArtFormType records
     */
    PageDTO<LocalArtFormTypeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a LocalArtFormType record from the system.
     *
     * @param id     The unique identifier of the LocalArtFormType to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific LocalArtFormType by their unique identifier.
     *
     * @param id The unique identifier of the LocalArtFormType to retrieve
     * @return The LocalArtFormType data transfer object containing the requested information
     */
    LocalArtFormTypeDTO getById(Long id);



}
