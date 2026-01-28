package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageMineralAndBiodiversityPr-related operations.
 * Provides CRUD operations and pagination support for VillageMineralAndBiodiversityPr entities.
 *
 * @since 1.0.0
 */

public interface VillageMineralAndBiodiversityPrService {

    /**
     * Retrieves all VillageMineralAndBiodiversityPrs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageMineralAndBiodiversityPrDTO in the same order as retrieved
     */
    Set<VillageMineralAndBiodiversityPrDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageMineralAndBiodiversityPr entry in the system.
     *
     * @param VillageMineralAndBiodiversityPr The VillageMineralAndBiodiversityPr information to be saved
     * @return The saved VillageMineralAndBiodiversityPr data
     * @since 1.0.0
     */
    VillageMineralAndBiodiversityPrDTO save(VillageMineralAndBiodiversityPrDTO VillageMineralAndBiodiversityPr);

    /**
     * Updates an existing VillageMineralAndBiodiversityPr entry.
     *
     * @param VillageMineralAndBiodiversityPr The VillageMineralAndBiodiversityPr information to be updated
     * @return The updated VillageMineralAndBiodiversityPr data
     * @since 1.0.0
     */
    VillageMineralAndBiodiversityPrDTO update(VillageMineralAndBiodiversityPrDTO VillageMineralAndBiodiversityPr);

    /**
     * Retrieves a paginated list of VillageMineralAndBiodiversityPrs.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageMineralAndBiodiversityPrs
     * @since 1.0.0
     */
    PageDTO<VillageMineralAndBiodiversityPrDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageMineralAndBiodiversityPr by their ID with a specified reason.
     *
     * @param id     The ID of the VillageMineralAndBiodiversityPr to delete
     * @param reason The reason for deletion
     * @return VillageMineralAndBiodiversityPrDTO
     */
    VillageMineralAndBiodiversityPrDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageMineralAndBiodiversityPr by their ID.
     *
     * @param id The ID of the VillageMineralAndBiodiversityPr to retrieve
     * @return The VillageMineralAndBiodiversityPr with the specified ID
     * @since 1.0.0
     */
    VillageMineralAndBiodiversityPrDTO getById(Long id);


    /**
     * Adds a list of villageCommonFloraList to a VillageMineralAndBiodiversityPr identified by their ID.
     *
     * @param id               The ID of the VillageMineralAndBiodiversityPr to add hobbies to
     * @param villageCommonFloraList  to be added
     * @since 1.0.0
     */
    void addVillageCommonFloraToVillageMineralAndBiodiversityPr(Long id, List<VillageCommonFloraDTO> villageCommonFloraList);

    /**
     * Adds a list of villageCommonFaunaList to a VillageMineralAndBiodiversityPr identified by their ID.
     *
     * @param id               The ID of the VillageMineralAndBiodiversityPr to add hobbies to
     * @param villageCommonFaunaList  to be added
     * @since 1.0.0
     */
    void addVillageCommonFaunaToVillageMineralAndBiodiversityPr(Long id, List<VillageCommonFaunaDTO> villageCommonFaunaList);



}
