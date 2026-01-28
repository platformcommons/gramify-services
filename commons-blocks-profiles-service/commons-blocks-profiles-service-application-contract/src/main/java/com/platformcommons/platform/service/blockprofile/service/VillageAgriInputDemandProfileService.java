package com.platformcommons.platform.service.blockprofile.service;


import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;

/**
 * GENERATED_CLASS - DO NOT MODIFY.
 * Service interface for managing VillageAgriInputDemandProfile-related operations.
 * Provides CRUD operations and pagination support for VillageAgriInputDemandProfile entities.
 *
 * @since 1.0.0
 */

public interface VillageAgriInputDemandProfileService {

    /**
     * Retrieves all VillageAgriInputDemandProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageAgriInputDemandProfileDTO in the same order as retrieved
     */
    Set<VillageAgriInputDemandProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageAgriInputDemandProfile entry in the system.
     *
     * @param VillageAgriInputDemandProfile The VillageAgriInputDemandProfile information to be saved
     * @return The saved VillageAgriInputDemandProfile data
     * @since 1.0.0
     */
    VillageAgriInputDemandProfileDTO save(VillageAgriInputDemandProfileDTO VillageAgriInputDemandProfile);

    /**
     * Updates an existing VillageAgriInputDemandProfile entry.
     *
     * @param VillageAgriInputDemandProfile The VillageAgriInputDemandProfile information to be updated
     * @return The updated VillageAgriInputDemandProfile data
     * @since 1.0.0
     */
    VillageAgriInputDemandProfileDTO update(VillageAgriInputDemandProfileDTO VillageAgriInputDemandProfile);

    /**
     * Retrieves a paginated list of VillageAgriInputDemandProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageAgriInputDemandProfiles
     * @since 1.0.0
     */
    PageDTO<VillageAgriInputDemandProfileDTO> getByPage(Integer page, Integer size);

    /**
     * Deletes a VillageAgriInputDemandProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageAgriInputDemandProfile to delete
     * @param reason The reason for deletion
     * @return VillageAgriInputDemandProfileDTO
     */
    VillageAgriInputDemandProfileDTO deleteById(Long id, String reason);

    /**
     * Retrieves a VillageAgriInputDemandProfile by their ID.
     *
     * @param id The ID of the VillageAgriInputDemandProfile to retrieve
     * @return The VillageAgriInputDemandProfile with the specified ID
     * @since 1.0.0
     */
    VillageAgriInputDemandProfileDTO getById(Long id);


    /**
     * Adds a list of machinesInDemandList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param machinesInDemandList  to be added
     * @since 1.0.0
     */
    void addMachinesInDemandToVillageAgriInputDemandProfile(Long id, List<MachinesInDemandDTO> machinesInDemandList);

    /**
     * Adds a list of purposeOfCreditList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param purposeOfCreditList  to be added
     * @since 1.0.0
     */
    void addPurposeOfCreditToVillageAgriInputDemandProfile(Long id, List<PurposeOfCreditDTO> purposeOfCreditList);

    /**
     * Adds a list of fertilizersInDemandList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param fertilizersInDemandList  to be added
     * @since 1.0.0
     */
    void addFertilizersInDemandToVillageAgriInputDemandProfile(Long id, List<FertilizersInDemandDTO> fertilizersInDemandList);

    /**
     * Adds a list of sourceOfRawMaterialList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param sourceOfRawMaterialList  to be added
     * @since 1.0.0
     */
    void addSourceOfRawMaterialToVillageAgriInputDemandProfile(Long id, List<SourceOfRawMaterialDTO> sourceOfRawMaterialList);

    /**
     * Adds a list of seedsInDemandList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param seedsInDemandList  to be added
     * @since 1.0.0
     */
    void addSeedsInDemandToVillageAgriInputDemandProfile(Long id, List<SeedsInDemandDTO> seedsInDemandList);

    /**
     * Adds a list of mainCreditSourceList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param mainCreditSourceList  to be added
     * @since 1.0.0
     */
    void addMainCreditSourceToVillageAgriInputDemandProfile(Long id, List<MainCreditSourceDTO> mainCreditSourceList);

    /**
     * Adds a list of existingStorageIssueList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param existingStorageIssueList  to be added
     * @since 1.0.0
     */
    void addExistingStorageIssueToVillageAgriInputDemandProfile(Long id, List<ExistingStorageIssueDTO> existingStorageIssueList);

    /**
     * Adds a list of storageNeededForCropList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param storageNeededForCropList  to be added
     * @since 1.0.0
     */
    void addStorageNeededForCropToVillageAgriInputDemandProfile(Long id, List<StorageNeededForCropDTO> storageNeededForCropList);

    /**
     * Adds a list of whereFarmersBuyInputList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param whereFarmersBuyInputList  to be added
     * @since 1.0.0
     */
    void addWhereFarmersBuyInputToVillageAgriInputDemandProfile(Long id, List<WhereFarmersBuyInputDTO> whereFarmersBuyInputList);

    /**
     * Adds a list of rawMaterialsNeededForIndustryList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param rawMaterialsNeededForIndustryList  to be added
     * @since 1.0.0
     */
    void addRawMaterialsNeededForIndustryToVillageAgriInputDemandProfile(Long id, List<RawMaterialsNeededForIndustryDTO> rawMaterialsNeededForIndustryList);

    /**
     * Adds a list of pesticidesInDemandList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param pesticidesInDemandList  to be added
     * @since 1.0.0
     */
    void addPesticidesInDemandToVillageAgriInputDemandProfile(Long id, List<PesticidesInDemandDTO> pesticidesInDemandList);

    /**
     * Adds a list of currentStorageMethodList to a VillageAgriInputDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageAgriInputDemandProfile to add hobbies to
     * @param currentStorageMethodList  to be added
     * @since 1.0.0
     */
    void addCurrentStorageMethodToVillageAgriInputDemandProfile(Long id, List<CurrentStorageMethodDTO> currentStorageMethodList);



}
