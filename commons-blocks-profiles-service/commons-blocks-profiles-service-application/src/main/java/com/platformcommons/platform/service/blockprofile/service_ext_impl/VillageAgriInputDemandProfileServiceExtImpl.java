package com.platformcommons.platform.service.blockprofile.service_ext_impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service_ext.*;
import com.platformcommons.platform.service.blockprofile.service_impl.VillageAgriInputDemandProfileServiceImpl;

import java.util.*;


@Service
@Slf4j
public class VillageAgriInputDemandProfileServiceExtImpl extends VillageAgriInputDemandProfileServiceImpl implements VillageAgriInputDemandProfileServiceExt {

    public VillageAgriInputDemandProfileServiceExtImpl(
                    MachinesInDemandDTOAssembler assemblerMachinesInDemand,  MachinesInDemandRepository repositoryMachinesInDemand,
                    PurposeOfCreditDTOAssembler assemblerPurposeOfCredit,  PurposeOfCreditRepository repositoryPurposeOfCredit,
                    FertilizersInDemandDTOAssembler assemblerFertilizersInDemand,  FertilizersInDemandRepository repositoryFertilizersInDemand,
                    SourceOfRawMaterialDTOAssembler assemblerSourceOfRawMaterial,  SourceOfRawMaterialRepository repositorySourceOfRawMaterial,
                    SeedsInDemandDTOAssembler assemblerSeedsInDemand,  SeedsInDemandRepository repositorySeedsInDemand,
                    MainCreditSourceDTOAssembler assemblerMainCreditSource,  MainCreditSourceRepository repositoryMainCreditSource,
                    ExistingStorageIssueDTOAssembler assemblerExistingStorageIssue,  ExistingStorageIssueRepository repositoryExistingStorageIssue,
                    StorageNeededForCropDTOAssembler assemblerStorageNeededForCrop,  StorageNeededForCropRepository repositoryStorageNeededForCrop,
                    WhereFarmersBuyInputDTOAssembler assemblerWhereFarmersBuyInput,  WhereFarmersBuyInputRepository repositoryWhereFarmersBuyInput,
                    RawMaterialsNeededForIndustryDTOAssembler assemblerRawMaterialsNeededForIndustry,  RawMaterialsNeededForIndustryRepository repositoryRawMaterialsNeededForIndustry,
                    PesticidesInDemandDTOAssembler assemblerPesticidesInDemand,  PesticidesInDemandRepository repositoryPesticidesInDemand,
                    CurrentStorageMethodDTOAssembler assemblerCurrentStorageMethod,  CurrentStorageMethodRepository repositoryCurrentStorageMethod,
                    VillageAgriInputDemandProfileRepository repository, VillageAgriInputDemandProfileDTOAssembler assembler) {
        super(                 assemblerMachinesInDemand, repositoryMachinesInDemand,
                 assemblerPurposeOfCredit, repositoryPurposeOfCredit,
                 assemblerFertilizersInDemand, repositoryFertilizersInDemand,
                 assemblerSourceOfRawMaterial, repositorySourceOfRawMaterial,
                 assemblerSeedsInDemand, repositorySeedsInDemand,
                 assemblerMainCreditSource, repositoryMainCreditSource,
                 assemblerExistingStorageIssue, repositoryExistingStorageIssue,
                 assemblerStorageNeededForCrop, repositoryStorageNeededForCrop,
                 assemblerWhereFarmersBuyInput, repositoryWhereFarmersBuyInput,
                 assemblerRawMaterialsNeededForIndustry, repositoryRawMaterialsNeededForIndustry,
                 assemblerPesticidesInDemand, repositoryPesticidesInDemand,
                 assemblerCurrentStorageMethod, repositoryCurrentStorageMethod,

                repository, assembler);
    }

    /**
     * Pre-processing hook for save operation.
     *
     * @param dto the VillageAgriInputDemandProfile DTO before save
     * @return the processed VillageAgriInputDemandProfile DTO
     */
    @Override
    protected VillageAgriInputDemandProfileDTO preHookSave(VillageAgriInputDemandProfileDTO dto) {
        log.debug("Entry - preHookSave(dto={})", dto);
        VillageAgriInputDemandProfileDTO result = super.preHookSave(dto);
        log.debug("Exit - preHookSave() with result: {}", result);
        return super.preHookSave(result);
    }

    /**
     * Post-processing hook for save operation.
     *
     * @param dto the VillageAgriInputDemandProfile DTO after save
     * @return the processed VillageAgriInputDemandProfile DTO
     */
    @Override
    protected VillageAgriInputDemandProfileDTO postHookSave(VillageAgriInputDemandProfileDTO dto) {
        log.debug("Entry - postHookSave(dto={})", dto);
        log.debug("Exit - postHookSave() with result: {}", dto);
        return super.postHookSave(dto);
    }

    /**
     * Pre-processing hook for update operation.
     *
     * @param dto the VillageAgriInputDemandProfile DTO before update
     * @return the processed VillageAgriInputDemandProfile DTO
     */
    @Override
    protected VillageAgriInputDemandProfileDTO preHookUpdate(VillageAgriInputDemandProfileDTO dto) {
        log.debug("Entry - preHookUpdate(dto={})", dto);
        log.debug("Exit - preHookUpdate() with result: {}", dto);
        return super.preHookUpdate(dto);
    }

    /**
     * Post-processing hook for update operation.
     *
     * @param dto the VillageAgriInputDemandProfile DTO after update
     * @return the processed VillageAgriInputDemandProfile DTO
     */
    @Override
    protected VillageAgriInputDemandProfileDTO postHookUpdate(VillageAgriInputDemandProfileDTO dto) {
        log.debug("Entry - postHookUpdate(dto={})", dto);
        log.debug("Exit - postHookUpdate() with result: {}", dto);
        return super.postHookUpdate(dto);
    }

    /**
     * Pre-processing hook for delete operation.
     *
     * @param id     the VillageAgriInputDemandProfile ID
     * @param reason the reason for deletion
     */
    @Override
    protected void preHookDelete(Long id, String reason) {
        log.debug("Entry - preHookDelete(id={}, reason={})", id, reason);
        log.debug("Exit - preHookDelete()");
        super.preHookDelete(id, reason);
    }

    /**
     * Post-processing hook for delete operation.
     *
     * @param dto the VillageAgriInputDemandProfile DTO after delete
     * @return the processed VillageAgriInputDemandProfile DTO
     */
    @Override
    protected VillageAgriInputDemandProfileDTO postHookDelete(VillageAgriInputDemandProfileDTO dto) {
        log.debug("Entry - postHookDelete(dto={})", dto);
        log.debug("Exit - postHookDelete() with result: {}", dto);
        return super.postHookDelete(dto);
    }

    /**
     * Post-processing hook for getById operation.
     *
     * @param dto the VillageAgriInputDemandProfile DTO after retrieval
     * @return the processed VillageAgriInputDemandProfile DTO
     */
    @Override
    protected VillageAgriInputDemandProfileDTO postHookGetById(VillageAgriInputDemandProfileDTO dto) {
        log.debug("Entry - postHookGetById(dto={})", dto);
        log.debug("Exit - postHookGetById() with result: {}", dto);
        return super.postHookGetById(dto);
    }

    /**
     * Post-processing hook for getAll operation.
     *
     * @param result the page of VillageAgriInputDemandProfile DTOs after retrieval
     * @return the processed page of VillageAgriInputDemandProfile DTOs
     */
    @Override
    protected PageDTO<VillageAgriInputDemandProfileDTO> postHookGetAll(PageDTO<VillageAgriInputDemandProfileDTO> result) {
        log.debug("Entry - postHookGetAll(result={})", result);
        log.debug("Exit - postHookGetAll() with result: {}", result);
        return super.postHookGetAll(result);
    }
}
