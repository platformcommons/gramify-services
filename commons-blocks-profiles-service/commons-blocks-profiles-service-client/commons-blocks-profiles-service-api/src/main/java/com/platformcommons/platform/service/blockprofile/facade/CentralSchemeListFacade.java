package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing CentralSchemeList-related operations in the system.
 * This interface provides methods for CRUD operations on CentralSchemeList data,
 * including creation, retrieval, update, and deletion of CentralSchemeList records.
 * It serves as the primary entry point for CentralSchemeList management functionality.
 */
public interface CentralSchemeListFacade {

    /**
     * Retrieves all CentralSchemeLists by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CentralSchemeListDTO
     */
    Set<CentralSchemeListDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new CentralSchemeList record in the system.
     *
     * @param CentralSchemeListDTO The CentralSchemeList data transfer object containing the information to be saved
     * @return The saved CentralSchemeList data with generated identifiers and any system-modified fields
     */
    CentralSchemeListDTO save(CentralSchemeListDTO CentralSchemeListDTO);

    /**
     * Updates an existing CentralSchemeList record in the system.
     *
     * @param CentralSchemeListDTO The CentralSchemeList data transfer object containing the updated information
     * @return The updated CentralSchemeList data as confirmed by the system
     */
    CentralSchemeListDTO update(CentralSchemeListDTO CentralSchemeListDTO);

    /**
     * Retrieves a paginated list of all CentralSchemeLists in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested CentralSchemeList records
     */
    PageDTO<CentralSchemeListDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a CentralSchemeList record from the system.
     *
     * @param id     The unique identifier of the CentralSchemeList to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific CentralSchemeList by their unique identifier.
     *
     * @param id The unique identifier of the CentralSchemeList to retrieve
     * @return The CentralSchemeList data transfer object containing the requested information
     */
    CentralSchemeListDTO getById(Long id);



}
