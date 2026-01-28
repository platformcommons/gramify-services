package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageExportPotentialProfileService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.*;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j

public class VillageExportPotentialProfileServiceImpl implements VillageExportPotentialProfileService {

    private final VillageExportPotentialProfileDTOAssembler assembler;
    private final VillageExportPotentialProfileRepository repository;


    private final NicheProductBuyerTypeDTOAssembler assemblerNicheProductBuyerType;
    private final NicheProductBuyerTypeRepository repositoryNicheProductBuyerType;


    private final SurplusProduceTypeDTOAssembler assemblerSurplusProduceType;
    private final SurplusProduceTypeRepository repositorySurplusProduceType;


    private final MainSurplusMarketsOutsideBlockDTOAssembler assemblerMainSurplusMarketsOutsideBlock;
    private final MainSurplusMarketsOutsideBlockRepository repositoryMainSurplusMarketsOutsideBlock;


    private final NicheProductsAvailabilityDTOAssembler assemblerNicheProductsAvailability;
    private final NicheProductsAvailabilityRepository repositoryNicheProductsAvailability;


    public VillageExportPotentialProfileServiceImpl(
        NicheProductBuyerTypeDTOAssembler assemblerNicheProductBuyerType,  NicheProductBuyerTypeRepository repositoryNicheProductBuyerType,
        SurplusProduceTypeDTOAssembler assemblerSurplusProduceType,  SurplusProduceTypeRepository repositorySurplusProduceType,
        MainSurplusMarketsOutsideBlockDTOAssembler assemblerMainSurplusMarketsOutsideBlock,  MainSurplusMarketsOutsideBlockRepository repositoryMainSurplusMarketsOutsideBlock,
        NicheProductsAvailabilityDTOAssembler assemblerNicheProductsAvailability,  NicheProductsAvailabilityRepository repositoryNicheProductsAvailability,
        VillageExportPotentialProfileRepository repository, VillageExportPotentialProfileDTOAssembler assembler) {
        this.assemblerNicheProductBuyerType = assemblerNicheProductBuyerType;
        this.repositoryNicheProductBuyerType = repositoryNicheProductBuyerType;
        this.assemblerSurplusProduceType = assemblerSurplusProduceType;
        this.repositorySurplusProduceType = repositorySurplusProduceType;
        this.assemblerMainSurplusMarketsOutsideBlock = assemblerMainSurplusMarketsOutsideBlock;
        this.repositoryMainSurplusMarketsOutsideBlock = repositoryMainSurplusMarketsOutsideBlock;
        this.assemblerNicheProductsAvailability = assemblerNicheProductsAvailability;
        this.repositoryNicheProductsAvailability = repositoryNicheProductsAvailability;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageExportPotentialProfile.
     *
     * @param VillageExportPotentialProfile the VillageExportPotentialProfile DTO to save
     * @return the saved VillageExportPotentialProfile DTO
     */
    @Transactional
    @Override
    public VillageExportPotentialProfileDTO save(VillageExportPotentialProfileDTO VillageExportPotentialProfile) {
        log.debug("Entry - save(VillageExportPotentialProfile={})", VillageExportPotentialProfile);
        VillageExportPotentialProfile = preHookSave(VillageExportPotentialProfile);
        VillageExportPotentialProfile entity = assembler.fromDTO(VillageExportPotentialProfile);
        VillageExportPotentialProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageExportPotentialProfile.
     *
     * @param VillageExportPotentialProfile the VillageExportPotentialProfile DTO to update
     * @return the updated VillageExportPotentialProfile DTO
     * @throws NotFoundException if the VillageExportPotentialProfile is not found
     */
    @Transactional
    @Override
    public VillageExportPotentialProfileDTO update(VillageExportPotentialProfileDTO VillageExportPotentialProfile) {
        log.debug("Entry - update(VillageExportPotentialProfile={})", VillageExportPotentialProfile);
        VillageExportPotentialProfile = preHookUpdate(VillageExportPotentialProfile);
        VillageExportPotentialProfile saved = repository.findById(VillageExportPotentialProfile.getId()).orElseThrow(() -> new NotFoundException("VillageExportPotentialProfile not found"));
        saved.update(assembler.fromDTO(VillageExportPotentialProfile));
        saved = repository.save(saved);
        VillageExportPotentialProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageExportPotentialProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageExportPotentialProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageExportPotentialProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageExportPotentialProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageExportPotentialProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageExportPotentialProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageExportPotentialProfile by ID.
     *
     * @param id     the ID of the VillageExportPotentialProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageExportPotentialProfile DTO
     * @throws NotFoundException if the VillageExportPotentialProfile is not found
     */
    @Transactional
    @Override
    public VillageExportPotentialProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageExportPotentialProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageExportPotentialProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageExportPotentialProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageExportPotentialProfile by ID.
     *
     * @param id the ID of the VillageExportPotentialProfile to retrieve
     * @return the VillageExportPotentialProfile DTO
     * @throws NotFoundException if the VillageExportPotentialProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageExportPotentialProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageExportPotentialProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageExportPotentialProfile not found"));
        VillageExportPotentialProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageExportPotentialProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageExportPotentialProfile> savedData = repository.findAllById(ids);
        Set<VillageExportPotentialProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of nicheProductBuyerTypeList to a VillageExportPotentialProfile identified by their ID.
      *
      * @param id               The ID of the VillageExportPotentialProfile to add hobbies to
      * @param nicheProductBuyerTypeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addNicheProductBuyerTypeToVillageExportPotentialProfile(Long id, List<NicheProductBuyerTypeDTO> nicheProductBuyerTypeList){
         VillageExportPotentialProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageExportPotentialProfile not found"));
         if(nicheProductBuyerTypeList != null && !nicheProductBuyerTypeList.isEmpty()) {

             Set<NicheProductBuyerType> toBeSavedList = nicheProductBuyerTypeList.stream().map(it-> assemblerNicheProductBuyerType.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageExportPotentialProfile(saved));
             repositoryNicheProductBuyerType.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of surplusProduceTypeList to a VillageExportPotentialProfile identified by their ID.
      *
      * @param id               The ID of the VillageExportPotentialProfile to add hobbies to
      * @param surplusProduceTypeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addSurplusProduceTypeToVillageExportPotentialProfile(Long id, List<SurplusProduceTypeDTO> surplusProduceTypeList){
         VillageExportPotentialProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageExportPotentialProfile not found"));
         if(surplusProduceTypeList != null && !surplusProduceTypeList.isEmpty()) {

             Set<SurplusProduceType> toBeSavedList = surplusProduceTypeList.stream().map(it-> assemblerSurplusProduceType.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageExportPotentialProfile(saved));
             repositorySurplusProduceType.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of mainSurplusMarketsOutsideBlockList to a VillageExportPotentialProfile identified by their ID.
      *
      * @param id               The ID of the VillageExportPotentialProfile to add hobbies to
      * @param mainSurplusMarketsOutsideBlockList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addMainSurplusMarketsOutsideBlockToVillageExportPotentialProfile(Long id, List<MainSurplusMarketsOutsideBlockDTO> mainSurplusMarketsOutsideBlockList){
         VillageExportPotentialProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageExportPotentialProfile not found"));
         if(mainSurplusMarketsOutsideBlockList != null && !mainSurplusMarketsOutsideBlockList.isEmpty()) {

             Set<MainSurplusMarketsOutsideBlock> toBeSavedList = mainSurplusMarketsOutsideBlockList.stream().map(it-> assemblerMainSurplusMarketsOutsideBlock.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageExportPotentialProfile(saved));
             repositoryMainSurplusMarketsOutsideBlock.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of nicheProductsAvailabilityList to a VillageExportPotentialProfile identified by their ID.
      *
      * @param id               The ID of the VillageExportPotentialProfile to add hobbies to
      * @param nicheProductsAvailabilityList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addNicheProductsAvailabilityToVillageExportPotentialProfile(Long id, List<NicheProductsAvailabilityDTO> nicheProductsAvailabilityList){
         VillageExportPotentialProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageExportPotentialProfile not found"));
         if(nicheProductsAvailabilityList != null && !nicheProductsAvailabilityList.isEmpty()) {

             Set<NicheProductsAvailability> toBeSavedList = nicheProductsAvailabilityList.stream().map(it-> assemblerNicheProductsAvailability.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageExportPotentialProfile(saved));
             repositoryNicheProductsAvailability.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageExportPotentialProfileDTO postHookSave(VillageExportPotentialProfileDTO dto) {
        return dto;
    }

    protected VillageExportPotentialProfileDTO preHookSave(VillageExportPotentialProfileDTO dto) {
        return dto;
    }

    protected VillageExportPotentialProfileDTO postHookUpdate(VillageExportPotentialProfileDTO dto) {
        return dto;
    }

    protected VillageExportPotentialProfileDTO preHookUpdate(VillageExportPotentialProfileDTO VillageExportPotentialProfileDTO) {
        return VillageExportPotentialProfileDTO;
    }

    protected VillageExportPotentialProfileDTO postHookDelete(VillageExportPotentialProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageExportPotentialProfileDTO postHookGetById(VillageExportPotentialProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageExportPotentialProfileDTO> postHookGetAll(PageDTO<VillageExportPotentialProfileDTO> result) {
        return result;
    }




}
