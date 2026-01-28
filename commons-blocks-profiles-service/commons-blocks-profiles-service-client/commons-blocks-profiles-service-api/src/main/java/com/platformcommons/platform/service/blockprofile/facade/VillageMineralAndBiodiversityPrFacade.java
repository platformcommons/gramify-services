package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageMineralAndBiodiversityPr-related operations in the system.
 * This interface provides methods for CRUD operations on VillageMineralAndBiodiversityPr data,
 * including creation, retrieval, update, and deletion of VillageMineralAndBiodiversityPr records.
 * It serves as the primary entry point for VillageMineralAndBiodiversityPr management functionality.
 */
public interface VillageMineralAndBiodiversityPrFacade {

    /**
     * Retrieves all VillageMineralAndBiodiversityPrs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageMineralAndBiodiversityPrDTO
     */
    Set<VillageMineralAndBiodiversityPrDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageMineralAndBiodiversityPr record in the system.
     *
     * @param VillageMineralAndBiodiversityPrDTO The VillageMineralAndBiodiversityPr data transfer object containing the information to be saved
     * @return The saved VillageMineralAndBiodiversityPr data with generated identifiers and any system-modified fields
     */
    VillageMineralAndBiodiversityPrDTO save(VillageMineralAndBiodiversityPrDTO VillageMineralAndBiodiversityPrDTO);

    /**
     * Updates an existing VillageMineralAndBiodiversityPr record in the system.
     *
     * @param VillageMineralAndBiodiversityPrDTO The VillageMineralAndBiodiversityPr data transfer object containing the updated information
     * @return The updated VillageMineralAndBiodiversityPr data as confirmed by the system
     */
    VillageMineralAndBiodiversityPrDTO update(VillageMineralAndBiodiversityPrDTO VillageMineralAndBiodiversityPrDTO);

    /**
     * Retrieves a paginated list of all VillageMineralAndBiodiversityPrs in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageMineralAndBiodiversityPr records
     */
    PageDTO<VillageMineralAndBiodiversityPrDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageMineralAndBiodiversityPr record from the system.
     *
     * @param id     The unique identifier of the VillageMineralAndBiodiversityPr to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageMineralAndBiodiversityPr by their unique identifier.
     *
     * @param id The unique identifier of the VillageMineralAndBiodiversityPr to retrieve
     * @return The VillageMineralAndBiodiversityPr data transfer object containing the requested information
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
