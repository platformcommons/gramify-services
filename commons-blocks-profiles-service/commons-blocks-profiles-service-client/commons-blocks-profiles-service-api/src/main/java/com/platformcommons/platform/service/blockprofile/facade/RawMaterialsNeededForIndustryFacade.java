package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing RawMaterialsNeededForIndustry-related operations in the system.
 * This interface provides methods for CRUD operations on RawMaterialsNeededForIndustry data,
 * including creation, retrieval, update, and deletion of RawMaterialsNeededForIndustry records.
 * It serves as the primary entry point for RawMaterialsNeededForIndustry management functionality.
 */
public interface RawMaterialsNeededForIndustryFacade {

    /**
     * Retrieves all RawMaterialsNeededForIndustrys by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of RawMaterialsNeededForIndustryDTO
     */
    Set<RawMaterialsNeededForIndustryDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new RawMaterialsNeededForIndustry record in the system.
     *
     * @param RawMaterialsNeededForIndustryDTO The RawMaterialsNeededForIndustry data transfer object containing the information to be saved
     * @return The saved RawMaterialsNeededForIndustry data with generated identifiers and any system-modified fields
     */
    RawMaterialsNeededForIndustryDTO save(RawMaterialsNeededForIndustryDTO RawMaterialsNeededForIndustryDTO);

    /**
     * Updates an existing RawMaterialsNeededForIndustry record in the system.
     *
     * @param RawMaterialsNeededForIndustryDTO The RawMaterialsNeededForIndustry data transfer object containing the updated information
     * @return The updated RawMaterialsNeededForIndustry data as confirmed by the system
     */
    RawMaterialsNeededForIndustryDTO update(RawMaterialsNeededForIndustryDTO RawMaterialsNeededForIndustryDTO);

    /**
     * Retrieves a paginated list of all RawMaterialsNeededForIndustrys in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested RawMaterialsNeededForIndustry records
     */
    PageDTO<RawMaterialsNeededForIndustryDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a RawMaterialsNeededForIndustry record from the system.
     *
     * @param id     The unique identifier of the RawMaterialsNeededForIndustry to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific RawMaterialsNeededForIndustry by their unique identifier.
     *
     * @param id The unique identifier of the RawMaterialsNeededForIndustry to retrieve
     * @return The RawMaterialsNeededForIndustry data transfer object containing the requested information
     */
    RawMaterialsNeededForIndustryDTO getById(Long id);



}
