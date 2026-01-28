package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageCulturalResourceProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageCulturalResourceProfile data,
 * including creation, retrieval, update, and deletion of VillageCulturalResourceProfile records.
 * It serves as the primary entry point for VillageCulturalResourceProfile management functionality.
 */
public interface VillageCulturalResourceProfileFacade {

    /**
     * Retrieves all VillageCulturalResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCulturalResourceProfileDTO
     */
    Set<VillageCulturalResourceProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageCulturalResourceProfile record in the system.
     *
     * @param VillageCulturalResourceProfileDTO The VillageCulturalResourceProfile data transfer object containing the information to be saved
     * @return The saved VillageCulturalResourceProfile data with generated identifiers and any system-modified fields
     */
    VillageCulturalResourceProfileDTO save(VillageCulturalResourceProfileDTO VillageCulturalResourceProfileDTO);

    /**
     * Updates an existing VillageCulturalResourceProfile record in the system.
     *
     * @param VillageCulturalResourceProfileDTO The VillageCulturalResourceProfile data transfer object containing the updated information
     * @return The updated VillageCulturalResourceProfile data as confirmed by the system
     */
    VillageCulturalResourceProfileDTO update(VillageCulturalResourceProfileDTO VillageCulturalResourceProfileDTO);

    /**
     * Retrieves a paginated list of all VillageCulturalResourceProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageCulturalResourceProfile records
     */
    PageDTO<VillageCulturalResourceProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageCulturalResourceProfile record from the system.
     *
     * @param id     The unique identifier of the VillageCulturalResourceProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageCulturalResourceProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageCulturalResourceProfile to retrieve
     * @return The VillageCulturalResourceProfile data transfer object containing the requested information
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
