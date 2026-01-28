package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageCulturalResourceProfile-related operations.
 * Provides CRUD operations and pagination support for VillageCulturalResourceProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageCulturalResourceProfileService {

    /**
     * Retrieves all VillageCulturalResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCulturalResourceProfileDTO in the same order as retrieved
     */
    Set<VillageCulturalResourceProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageCulturalResourceProfile entry in the system.
     *
     * @param VillageCulturalResourceProfile The VillageCulturalResourceProfile information to be saved
     * @return The saved VillageCulturalResourceProfile data
     * @since 1.0.0
     */
    VillageCulturalResourceProfileDTO save(VillageCulturalResourceProfileDTO VillageCulturalResourceProfile);

    /**
     * Updates an existing VillageCulturalResourceProfile entry.
     *
     * @param VillageCulturalResourceProfile The VillageCulturalResourceProfile information to be updated
     * @return The updated VillageCulturalResourceProfile data
     * @since 1.0.0
     */
    VillageCulturalResourceProfileDTO update(VillageCulturalResourceProfileDTO VillageCulturalResourceProfile);

    /**
     * Retrieves a paginated list of VillageCulturalResourceProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageCulturalResourceProfiles
     * @since 1.0.0
     */
    PageDTO<VillageCulturalResourceProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageCulturalResourceProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageCulturalResourceProfile to delete
     * @param reason The reason for deletion
     * @return VillageCulturalResourceProfileDTO
     */
    VillageCulturalResourceProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageCulturalResourceProfile by their ID.
     *
     * @param id The ID of the VillageCulturalResourceProfile to retrieve
     * @return The VillageCulturalResourceProfile with the specified ID
     * @since 1.0.0
     */
    VillageCulturalResourceProfileDTO getById(Long id);


    /**
     * Adds a list of majorFestivalList to a VillageCulturalResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageCulturalResourceProfile to add hobbies to
     * @param majorFestivalList  to be added
     * @since 1.0.0
     */
    void addMajorFestivalToVillageCulturalResourceProfile(Long id, List<MajorFestivalDTO> majorFestivalList);

    /**
     * Adds a list of mainReligiousPlaceList to a VillageCulturalResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageCulturalResourceProfile to add hobbies to
     * @param mainReligiousPlaceList  to be added
     * @since 1.0.0
     */
    void addMainReligiousPlaceToVillageCulturalResourceProfile(Long id, List<MainReligiousPlaceDTO> mainReligiousPlaceList);

    /**
     * Adds a list of localFestivalList to a VillageCulturalResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageCulturalResourceProfile to add hobbies to
     * @param localFestivalList  to be added
     * @since 1.0.0
     */
    void addLocalFestivalToVillageCulturalResourceProfile(Long id, List<LocalFestivalDTO> localFestivalList);

    /**
     * Adds a list of localArtFormTypeList to a VillageCulturalResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageCulturalResourceProfile to add hobbies to
     * @param localArtFormTypeList  to be added
     * @since 1.0.0
     */
    void addLocalArtFormTypeToVillageCulturalResourceProfile(Long id, List<LocalArtFormTypeDTO> localArtFormTypeList);



}
