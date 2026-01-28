package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing NonAgriculturalMarketActivies-related operations in the system.
 * This interface provides methods for CRUD operations on NonAgriculturalMarketActivies data,
 * including creation, retrieval, update, and deletion of NonAgriculturalMarketActivies records.
 * It serves as the primary entry point for NonAgriculturalMarketActivies management functionality.
 */
public interface NonAgriculturalMarketActiviesFacade {

    /**
     * Retrieves all NonAgriculturalMarketActiviess by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of NonAgriculturalMarketActiviesDTO
     */
    Set<NonAgriculturalMarketActiviesDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new NonAgriculturalMarketActivies record in the system.
     *
     * @param NonAgriculturalMarketActiviesDTO The NonAgriculturalMarketActivies data transfer object containing the information to be saved
     * @return The saved NonAgriculturalMarketActivies data with generated identifiers and any system-modified fields
     */
    NonAgriculturalMarketActiviesDTO save(NonAgriculturalMarketActiviesDTO NonAgriculturalMarketActiviesDTO);

    /**
     * Updates an existing NonAgriculturalMarketActivies record in the system.
     *
     * @param NonAgriculturalMarketActiviesDTO The NonAgriculturalMarketActivies data transfer object containing the updated information
     * @return The updated NonAgriculturalMarketActivies data as confirmed by the system
     */
    NonAgriculturalMarketActiviesDTO update(NonAgriculturalMarketActiviesDTO NonAgriculturalMarketActiviesDTO);

    /**
     * Retrieves a paginated list of all NonAgriculturalMarketActiviess in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested NonAgriculturalMarketActivies records
     */
    PageDTO<NonAgriculturalMarketActiviesDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a NonAgriculturalMarketActivies record from the system.
     *
     * @param id     The unique identifier of the NonAgriculturalMarketActivies to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific NonAgriculturalMarketActivies by their unique identifier.
     *
     * @param id The unique identifier of the NonAgriculturalMarketActivies to retrieve
     * @return The NonAgriculturalMarketActivies data transfer object containing the requested information
     */
    NonAgriculturalMarketActiviesDTO getById(Long id);


    /**
     * Adds a list of otherIndustryTypeList to a NonAgriculturalMarketActivies identified by their ID.
     *
     * @param id               The ID of the NonAgriculturalMarketActivies to add hobbies to
     * @param otherIndustryTypeList  to be added
     * @since 1.0.0
     */
    void addOtherIndustryTypeToNonAgriculturalMarketActivies(Long id, List<OtherIndustryTypeDTO> otherIndustryTypeList);


}
