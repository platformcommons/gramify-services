package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing GovtSchemes-related operations.
 * Provides CRUD operations and pagination support for GovtSchemes entities.
 *
 * @since 1.0.0
 */

public interface GovtSchemesService {

    /**
     * Retrieves all GovtSchemess by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of GovtSchemesDTO in the same order as retrieved
     */
    Set<GovtSchemesDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new GovtSchemes entry in the system.
     *
     * @param GovtSchemes The GovtSchemes information to be saved
     * @return The saved GovtSchemes data
     * @since 1.0.0
     */
    GovtSchemesDTO save(GovtSchemesDTO GovtSchemes);

    /**
     * Updates an existing GovtSchemes entry.
     *
     * @param GovtSchemes The GovtSchemes information to be updated
     * @return The updated GovtSchemes data
     * @since 1.0.0
     */
    GovtSchemesDTO update(GovtSchemesDTO GovtSchemes);

    /**
     * Retrieves a paginated list of GovtSchemess.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of GovtSchemess
     * @since 1.0.0
     */
    PageDTO<GovtSchemesDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a GovtSchemes by their ID with a specified reason.
     *
     * @param id     The ID of the GovtSchemes to delete
     * @param reason The reason for deletion
     * @return GovtSchemesDTO
     */
    GovtSchemesDTO deleteById(Long id, String reason);

    /**
     * Retrieves a GovtSchemes by their ID.
     *
     * @param id The ID of the GovtSchemes to retrieve
     * @return The GovtSchemes with the specified ID
     * @since 1.0.0
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
