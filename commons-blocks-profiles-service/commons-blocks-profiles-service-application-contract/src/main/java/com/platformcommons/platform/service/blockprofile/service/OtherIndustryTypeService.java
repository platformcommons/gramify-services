package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing OtherIndustryType-related operations.
 * Provides CRUD operations and pagination support for OtherIndustryType entities.
 *
 * @since 1.0.0
 */

public interface OtherIndustryTypeService {

    /**
     * Retrieves all OtherIndustryTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of OtherIndustryTypeDTO in the same order as retrieved
     */
    Set<OtherIndustryTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new OtherIndustryType entry in the system.
     *
     * @param OtherIndustryType The OtherIndustryType information to be saved
     * @return The saved OtherIndustryType data
     * @since 1.0.0
     */
    OtherIndustryTypeDTO save(OtherIndustryTypeDTO OtherIndustryType);

    /**
     * Updates an existing OtherIndustryType entry.
     *
     * @param OtherIndustryType The OtherIndustryType information to be updated
     * @return The updated OtherIndustryType data
     * @since 1.0.0
     */
    OtherIndustryTypeDTO update(OtherIndustryTypeDTO OtherIndustryType);

    /**
     * Retrieves a paginated list of OtherIndustryTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of OtherIndustryTypes
     * @since 1.0.0
     */
    PageDTO<OtherIndustryTypeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a OtherIndustryType by their ID with a specified reason.
     *
     * @param id     The ID of the OtherIndustryType to delete
     * @param reason The reason for deletion
     * @return OtherIndustryTypeDTO
     */
    OtherIndustryTypeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a OtherIndustryType by their ID.
     *
     * @param id The ID of the OtherIndustryType to retrieve
     * @return The OtherIndustryType with the specified ID
     * @since 1.0.0
     */
    OtherIndustryTypeDTO getById(Long id);




}
