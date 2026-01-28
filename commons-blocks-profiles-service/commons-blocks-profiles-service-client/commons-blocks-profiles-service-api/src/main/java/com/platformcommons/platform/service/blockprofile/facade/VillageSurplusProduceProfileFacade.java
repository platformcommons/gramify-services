package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageSurplusProduceProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageSurplusProduceProfile data,
 * including creation, retrieval, update, and deletion of VillageSurplusProduceProfile records.
 * It serves as the primary entry point for VillageSurplusProduceProfile management functionality.
 */
public interface VillageSurplusProduceProfileFacade {

    /**
     * Retrieves all VillageSurplusProduceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageSurplusProduceProfileDTO
     */
    Set<VillageSurplusProduceProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageSurplusProduceProfile record in the system.
     *
     * @param VillageSurplusProduceProfileDTO The VillageSurplusProduceProfile data transfer object containing the information to be saved
     * @return The saved VillageSurplusProduceProfile data with generated identifiers and any system-modified fields
     */
    VillageSurplusProduceProfileDTO save(VillageSurplusProduceProfileDTO VillageSurplusProduceProfileDTO);

    /**
     * Updates an existing VillageSurplusProduceProfile record in the system.
     *
     * @param VillageSurplusProduceProfileDTO The VillageSurplusProduceProfile data transfer object containing the updated information
     * @return The updated VillageSurplusProduceProfile data as confirmed by the system
     */
    VillageSurplusProduceProfileDTO update(VillageSurplusProduceProfileDTO VillageSurplusProduceProfileDTO);

    /**
     * Retrieves a paginated list of all VillageSurplusProduceProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageSurplusProduceProfile records
     */
    PageDTO<VillageSurplusProduceProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageSurplusProduceProfile record from the system.
     *
     * @param id     The unique identifier of the VillageSurplusProduceProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageSurplusProduceProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageSurplusProduceProfile to retrieve
     * @return The VillageSurplusProduceProfile data transfer object containing the requested information
     */
    VillageSurplusProduceProfileDTO getById(Long id);


    /**
     * Adds a list of surplusMovedThroughList to a VillageSurplusProduceProfile identified by their ID.
     *
     * @param id               The ID of the VillageSurplusProduceProfile to add hobbies to
     * @param surplusMovedThroughList  to be added
     * @since 1.0.0
     */
    void addSurplusMovedThroughToVillageSurplusProduceProfile(Long id, List<SurplusMovedThroughDTO> surplusMovedThroughList);

    /**
     * Adds a list of seasonalityOfSurplusList to a VillageSurplusProduceProfile identified by their ID.
     *
     * @param id               The ID of the VillageSurplusProduceProfile to add hobbies to
     * @param seasonalityOfSurplusList  to be added
     * @since 1.0.0
     */
    void addSeasonalityOfSurplusToVillageSurplusProduceProfile(Long id, List<SeasonalityOfSurplusDTO> seasonalityOfSurplusList);

    /**
     * Adds a list of keyConstraintsForSurplusExportList to a VillageSurplusProduceProfile identified by their ID.
     *
     * @param id               The ID of the VillageSurplusProduceProfile to add hobbies to
     * @param keyConstraintsForSurplusExportList  to be added
     * @since 1.0.0
     */
    void addKeyConstraintsForSurplusExportToVillageSurplusProduceProfile(Long id, List<KeyConstraintsForSurplusExportDTO> keyConstraintsForSurplusExportList);

    /**
     * Adds a list of mainSurplusDestinationList to a VillageSurplusProduceProfile identified by their ID.
     *
     * @param id               The ID of the VillageSurplusProduceProfile to add hobbies to
     * @param mainSurplusDestinationList  to be added
     * @since 1.0.0
     */
    void addMainSurplusDestinationToVillageSurplusProduceProfile(Long id, List<MainSurplusDestinationDTO> mainSurplusDestinationList);

    /**
     * Adds a list of surplusProduceTypeList to a VillageSurplusProduceProfile identified by their ID.
     *
     * @param id               The ID of the VillageSurplusProduceProfile to add hobbies to
     * @param surplusProduceTypeList  to be added
     * @since 1.0.0
     */
    void addSurplusProduceTypeToVillageSurplusProduceProfile(Long id, List<SurplusProduceTypeDTO> surplusProduceTypeList);


}
