package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing SupportNeededForNicheGrowth-related operations in the system.
 * This interface provides methods for CRUD operations on SupportNeededForNicheGrowth data,
 * including creation, retrieval, update, and deletion of SupportNeededForNicheGrowth records.
 * It serves as the primary entry point for SupportNeededForNicheGrowth management functionality.
 */
public interface SupportNeededForNicheGrowthFacade {

    /**
     * Retrieves all SupportNeededForNicheGrowths by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SupportNeededForNicheGrowthDTO
     */
    Set<SupportNeededForNicheGrowthDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new SupportNeededForNicheGrowth record in the system.
     *
     * @param SupportNeededForNicheGrowthDTO The SupportNeededForNicheGrowth data transfer object containing the information to be saved
     * @return The saved SupportNeededForNicheGrowth data with generated identifiers and any system-modified fields
     */
    SupportNeededForNicheGrowthDTO save(SupportNeededForNicheGrowthDTO SupportNeededForNicheGrowthDTO);

    /**
     * Updates an existing SupportNeededForNicheGrowth record in the system.
     *
     * @param SupportNeededForNicheGrowthDTO The SupportNeededForNicheGrowth data transfer object containing the updated information
     * @return The updated SupportNeededForNicheGrowth data as confirmed by the system
     */
    SupportNeededForNicheGrowthDTO update(SupportNeededForNicheGrowthDTO SupportNeededForNicheGrowthDTO);

    /**
     * Retrieves a paginated list of all SupportNeededForNicheGrowths in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested SupportNeededForNicheGrowth records
     */
    PageDTO<SupportNeededForNicheGrowthDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a SupportNeededForNicheGrowth record from the system.
     *
     * @param id     The unique identifier of the SupportNeededForNicheGrowth to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific SupportNeededForNicheGrowth by their unique identifier.
     *
     * @param id The unique identifier of the SupportNeededForNicheGrowth to retrieve
     * @return The SupportNeededForNicheGrowth data transfer object containing the requested information
     */
    SupportNeededForNicheGrowthDTO getById(Long id);



}
