package com.platformcommons.platform.service.blockprofile.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.blockprofile.dto.*;

import java.util.*;


/**
 * Facade interface for managing VillageAgriInputDemandProfile-related operations in the system.
 * This interface provides methods for CRUD operations on VillageAgriInputDemandProfile data,
 * including creation, retrieval, update, and deletion of VillageAgriInputDemandProfile records.
 * It serves as the primary entry point for VillageAgriInputDemandProfile management functionality.
 */
public interface VillageAgriInputDemandProfileFacade {

    /**
     * Retrieves all VillageAgriInputDemandProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageAgriInputDemandProfileDTO
     */
    Set<VillageAgriInputDemandProfileDTO> getAllByIds(Set<Long> ids);

    /**
     * Creates a new VillageAgriInputDemandProfile record in the system.
     *
     * @param VillageAgriInputDemandProfileDTO The VillageAgriInputDemandProfile data transfer object containing the information to be saved
     * @return The saved VillageAgriInputDemandProfile data with generated identifiers and any system-modified fields
     */
    VillageAgriInputDemandProfileDTO save(VillageAgriInputDemandProfileDTO VillageAgriInputDemandProfileDTO);

    /**
     * Updates an existing VillageAgriInputDemandProfile record in the system.
     *
     * @param VillageAgriInputDemandProfileDTO The VillageAgriInputDemandProfile data transfer object containing the updated information
     * @return The updated VillageAgriInputDemandProfile data as confirmed by the system
     */
    VillageAgriInputDemandProfileDTO update(VillageAgriInputDemandProfileDTO VillageAgriInputDemandProfileDTO);

    /**
     * Retrieves a paginated list of all VillageAgriInputDemandProfiles in the system.
     *
     * @param page The zero-based page number to retrieve
     * @param size The number of records per page
     * @return A page object containing the requested VillageAgriInputDemandProfile records
     */
    PageDTO<VillageAgriInputDemandProfileDTO> getAllPage(Integer page, Integer size);

    /**
     * Deletes a VillageAgriInputDemandProfile record from the system.
     *
     * @param id     The unique identifier of the VillageAgriInputDemandProfile to delete
     * @param reason The reason for deletion, used for audit purposes
     */
    void delete(Long id, String reason);

    /**
     * Retrieves a specific VillageAgriInputDemandProfile by their unique identifier.
     *
     * @param id The unique identifier of the VillageAgriInputDemandProfile to retrieve
     * @return The VillageAgriInputDemandProfile data transfer object containing the requested information
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
