package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageSkillShortageType-related operations.
 * Provides CRUD operations and pagination support for VillageSkillShortageType entities.
 *
 * @since 1.0.0
 */

public interface VillageSkillShortageTypeService {

    /**
     * Retrieves all VillageSkillShortageTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageSkillShortageTypeDTO in the same order as retrieved
     */
    Set<VillageSkillShortageTypeDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageSkillShortageType entry in the system.
     *
     * @param VillageSkillShortageType The VillageSkillShortageType information to be saved
     * @return The saved VillageSkillShortageType data
     * @since 1.0.0
     */
    VillageSkillShortageTypeDTO save(VillageSkillShortageTypeDTO VillageSkillShortageType);

    /**
     * Updates an existing VillageSkillShortageType entry.
     *
     * @param VillageSkillShortageType The VillageSkillShortageType information to be updated
     * @return The updated VillageSkillShortageType data
     * @since 1.0.0
     */
    VillageSkillShortageTypeDTO update(VillageSkillShortageTypeDTO VillageSkillShortageType);

    /**
     * Retrieves a paginated list of VillageSkillShortageTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageSkillShortageTypes
     * @since 1.0.0
     */
    PageDTO<VillageSkillShortageTypeDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageSkillShortageType by their ID with a specified reason.
     *
     * @param id     The ID of the VillageSkillShortageType to delete
     * @param reason The reason for deletion
     * @return VillageSkillShortageTypeDTO
     */
    VillageSkillShortageTypeDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageSkillShortageType by their ID.
     *
     * @param id The ID of the VillageSkillShortageType to retrieve
     * @return The VillageSkillShortageType with the specified ID
     * @since 1.0.0
     */
    VillageSkillShortageTypeDTO getById(Long id);




}
