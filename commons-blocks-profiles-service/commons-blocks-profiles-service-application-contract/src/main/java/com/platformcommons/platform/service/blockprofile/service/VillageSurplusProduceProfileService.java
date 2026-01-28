package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageSurplusProduceProfile-related operations.
 * Provides CRUD operations and pagination support for VillageSurplusProduceProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageSurplusProduceProfileService {

    /**
     * Retrieves all VillageSurplusProduceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageSurplusProduceProfileDTO in the same order as retrieved
     */
    Set<VillageSurplusProduceProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageSurplusProduceProfile entry in the system.
     *
     * @param VillageSurplusProduceProfile The VillageSurplusProduceProfile information to be saved
     * @return The saved VillageSurplusProduceProfile data
     * @since 1.0.0
     */
    VillageSurplusProduceProfileDTO save(VillageSurplusProduceProfileDTO VillageSurplusProduceProfile);

    /**
     * Updates an existing VillageSurplusProduceProfile entry.
     *
     * @param VillageSurplusProduceProfile The VillageSurplusProduceProfile information to be updated
     * @return The updated VillageSurplusProduceProfile data
     * @since 1.0.0
     */
    VillageSurplusProduceProfileDTO update(VillageSurplusProduceProfileDTO VillageSurplusProduceProfile);

    /**
     * Retrieves a paginated list of VillageSurplusProduceProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageSurplusProduceProfiles
     * @since 1.0.0
     */
    PageDTO<VillageSurplusProduceProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageSurplusProduceProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageSurplusProduceProfile to delete
     * @param reason The reason for deletion
     * @return VillageSurplusProduceProfileDTO
     */
    VillageSurplusProduceProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageSurplusProduceProfile by their ID.
     *
     * @param id The ID of the VillageSurplusProduceProfile to retrieve
     * @return The VillageSurplusProduceProfile with the specified ID
     * @since 1.0.0
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
