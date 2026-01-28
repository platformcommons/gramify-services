package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing OtherIndustryType-related operations in the system.
 * This interface provides methods for CRUD operations on OtherIndustryType data,
 * including creation, retrieval, update, and deletion of OtherIndustryType records.
 * It serves as the primary entry point for OtherIndustryType management functionality.
 */
public interface OtherIndustryTypeFacade {

    /**
     * Retrieves all OtherIndustryTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of OtherIndustryTypeDTO
     */
    Set<OtherIndustryTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new OtherIndustryType record in the system.
     *
     * @param OtherIndustryTypeDTO The OtherIndustryType data transfer object containing the information to be saved
     * @return The saved OtherIndustryType data with generated identifiers and any system-modified fields
     */
    OtherIndustryTypeDTO save(OtherIndustryTypeDTO OtherIndustryTypeDTO);

    /**
     * Updates an existing OtherIndustryType record in the system.
     *
     * @param OtherIndustryTypeDTO The OtherIndustryType data transfer object containing the updated information
     * @return The updated OtherIndustryType data as confirmed by the system
     */
    OtherIndustryTypeDTO update(OtherIndustryTypeDTO OtherIndustryTypeDTO);

    /**
     * Retrieves a paginated list of all OtherIndustryTypes in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested OtherIndustryType records
     */
    PageDTO<OtherIndustryTypeDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a OtherIndustryType record from the system.
     *
     * @param id     The unique identifier of the OtherIndustryType to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific OtherIndustryType by their unique identifier.
     *
     * @param id The unique identifier of the OtherIndustryType to retrieve
     * @return The OtherIndustryType data transfer object containing the requested information
     */
    OtherIndustryTypeDTO getById(Long id);



}
