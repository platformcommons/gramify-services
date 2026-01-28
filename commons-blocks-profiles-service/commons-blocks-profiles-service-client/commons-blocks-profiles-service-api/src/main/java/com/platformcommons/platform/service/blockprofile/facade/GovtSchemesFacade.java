package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing GovtSchemes-related operations in the system.
 * This interface provides methods for CRUD operations on GovtSchemes data,
 * including creation, retrieval, update, and deletion of GovtSchemes records.
 * It serves as the primary entry point for GovtSchemes management functionality.
 */
public interface GovtSchemesFacade {

    /**
     * Retrieves all GovtSchemess by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of GovtSchemesDTO
     */
    Set<GovtSchemesDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new GovtSchemes record in the system.
     *
     * @param GovtSchemesDTO The GovtSchemes data transfer object containing the information to be saved
     * @return The saved GovtSchemes data with generated identifiers and any system-modified fields
     */
    GovtSchemesDTO save(GovtSchemesDTO GovtSchemesDTO);

    /**
     * Updates an existing GovtSchemes record in the system.
     *
     * @param GovtSchemesDTO The GovtSchemes data transfer object containing the updated information
     * @return The updated GovtSchemes data as confirmed by the system
     */
    GovtSchemesDTO update(GovtSchemesDTO GovtSchemesDTO);

    /**
     * Retrieves a paginated list of all GovtSchemess in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested GovtSchemes records
     */
    PageDTO<GovtSchemesDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a GovtSchemes record from the system.
     *
     * @param id     The unique identifier of the GovtSchemes to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific GovtSchemes by their unique identifier.
     *
     * @param id The unique identifier of the GovtSchemes to retrieve
     * @return The GovtSchemes data transfer object containing the requested information
     */
    GovtSchemesDTO getById(Long id);


    /**
     * Adds a list of schemeImplementationChallengeList to a GovtSchemes identified by their ID.
     *
     * @param id               The ID of the GovtSchemes to add hobbies to
     * @param schemeImplementationChallengeList  to be added
     * @since 1.0.0
     */
    void addSchemeImplementationChallengeToGovtSchemes(Long id, List<SchemeImplementationChallengeDTO> schemeImplementationChallengeList);

    /**
     * Adds a list of centralSchemeListList to a GovtSchemes identified by their ID.
     *
     * @param id               The ID of the GovtSchemes to add hobbies to
     * @param centralSchemeListList  to be added
     * @since 1.0.0
     */
    void addCentralSchemeListToGovtSchemes(Long id, List<CentralSchemeListDTO> centralSchemeListList);


}
