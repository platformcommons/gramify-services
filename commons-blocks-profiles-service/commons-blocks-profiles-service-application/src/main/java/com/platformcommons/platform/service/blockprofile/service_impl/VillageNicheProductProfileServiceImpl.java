package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageNicheProductProfileService;


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

public class VillageNicheProductProfileServiceImpl implements VillageNicheProductProfileService {

    private final VillageNicheProductProfileDTOAssembler assembler;
    private final VillageNicheProductProfileRepository repository;


    private final MainNicheMarketDTOAssembler assemblerMainNicheMarket;
    private final MainNicheMarketRepository repositoryMainNicheMarket;


    private final SupportNeededForNicheGrowthDTOAssembler assemblerSupportNeededForNicheGrowth;
    private final SupportNeededForNicheGrowthRepository repositorySupportNeededForNicheGrowth;


    private final NicheProductsAvailabilityDTOAssembler assemblerNicheProductsAvailability;
    private final NicheProductsAvailabilityRepository repositoryNicheProductsAvailability;


    private final NicheProductBuyerTypeDTOAssembler assemblerNicheProductBuyerType;
    private final NicheProductBuyerTypeRepository repositoryNicheProductBuyerType;


    public VillageNicheProductProfileServiceImpl(
        MainNicheMarketDTOAssembler assemblerMainNicheMarket,  MainNicheMarketRepository repositoryMainNicheMarket,
        SupportNeededForNicheGrowthDTOAssembler assemblerSupportNeededForNicheGrowth,  SupportNeededForNicheGrowthRepository repositorySupportNeededForNicheGrowth,
        NicheProductsAvailabilityDTOAssembler assemblerNicheProductsAvailability,  NicheProductsAvailabilityRepository repositoryNicheProductsAvailability,
        NicheProductBuyerTypeDTOAssembler assemblerNicheProductBuyerType,  NicheProductBuyerTypeRepository repositoryNicheProductBuyerType,
        VillageNicheProductProfileRepository repository, VillageNicheProductProfileDTOAssembler assembler) {
        this.assemblerMainNicheMarket = assemblerMainNicheMarket;
        this.repositoryMainNicheMarket = repositoryMainNicheMarket;
        this.assemblerSupportNeededForNicheGrowth = assemblerSupportNeededForNicheGrowth;
        this.repositorySupportNeededForNicheGrowth = repositorySupportNeededForNicheGrowth;
        this.assemblerNicheProductsAvailability = assemblerNicheProductsAvailability;
        this.repositoryNicheProductsAvailability = repositoryNicheProductsAvailability;
        this.assemblerNicheProductBuyerType = assemblerNicheProductBuyerType;
        this.repositoryNicheProductBuyerType = repositoryNicheProductBuyerType;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageNicheProductProfile.
     *
     * @param VillageNicheProductProfile the VillageNicheProductProfile DTO to save
     * @return the saved VillageNicheProductProfile DTO
     */
    @Transactional
    @Override
    public VillageNicheProductProfileDTO save(VillageNicheProductProfileDTO VillageNicheProductProfile) {
        log.debug("Entry - save(VillageNicheProductProfile={})", VillageNicheProductProfile);
        VillageNicheProductProfile = preHookSave(VillageNicheProductProfile);
        VillageNicheProductProfile entity = assembler.fromDTO(VillageNicheProductProfile);
        VillageNicheProductProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageNicheProductProfile.
     *
     * @param VillageNicheProductProfile the VillageNicheProductProfile DTO to update
     * @return the updated VillageNicheProductProfile DTO
     * @throws NotFoundException if the VillageNicheProductProfile is not found
     */
    @Transactional
    @Override
    public VillageNicheProductProfileDTO update(VillageNicheProductProfileDTO VillageNicheProductProfile) {
        log.debug("Entry - update(VillageNicheProductProfile={})", VillageNicheProductProfile);
        VillageNicheProductProfile = preHookUpdate(VillageNicheProductProfile);
        VillageNicheProductProfile saved = repository.findById(VillageNicheProductProfile.getId()).orElseThrow(() -> new NotFoundException("VillageNicheProductProfile not found"));
        saved.update(assembler.fromDTO(VillageNicheProductProfile));
        saved = repository.save(saved);
        VillageNicheProductProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageNicheProductProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageNicheProductProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageNicheProductProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageNicheProductProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageNicheProductProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageNicheProductProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageNicheProductProfile by ID.
     *
     * @param id     the ID of the VillageNicheProductProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageNicheProductProfile DTO
     * @throws NotFoundException if the VillageNicheProductProfile is not found
     */
    @Transactional
    @Override
    public VillageNicheProductProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageNicheProductProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageNicheProductProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageNicheProductProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageNicheProductProfile by ID.
     *
     * @param id the ID of the VillageNicheProductProfile to retrieve
     * @return the VillageNicheProductProfile DTO
     * @throws NotFoundException if the VillageNicheProductProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageNicheProductProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageNicheProductProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageNicheProductProfile not found"));
        VillageNicheProductProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageNicheProductProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageNicheProductProfile> savedData = repository.findAllById(ids);
        Set<VillageNicheProductProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of mainNicheMarketList to a VillageNicheProductProfile identified by their ID.
      *
      * @param id               The ID of the VillageNicheProductProfile to add hobbies to
      * @param mainNicheMarketList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addMainNicheMarketToVillageNicheProductProfile(Long id, List<MainNicheMarketDTO> mainNicheMarketList){
         VillageNicheProductProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageNicheProductProfile not found"));
         if(mainNicheMarketList != null && !mainNicheMarketList.isEmpty()) {

             Set<MainNicheMarket> toBeSavedList = mainNicheMarketList.stream().map(it-> assemblerMainNicheMarket.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageNicheProductProfile(saved));
             repositoryMainNicheMarket.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of supportNeededForNicheGrowthList to a VillageNicheProductProfile identified by their ID.
      *
      * @param id               The ID of the VillageNicheProductProfile to add hobbies to
      * @param supportNeededForNicheGrowthList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addSupportNeededForNicheGrowthToVillageNicheProductProfile(Long id, List<SupportNeededForNicheGrowthDTO> supportNeededForNicheGrowthList){
         VillageNicheProductProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageNicheProductProfile not found"));
         if(supportNeededForNicheGrowthList != null && !supportNeededForNicheGrowthList.isEmpty()) {

             Set<SupportNeededForNicheGrowth> toBeSavedList = supportNeededForNicheGrowthList.stream().map(it-> assemblerSupportNeededForNicheGrowth.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageNicheProductProfile(saved));
             repositorySupportNeededForNicheGrowth.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of nicheProductsAvailabilityList to a VillageNicheProductProfile identified by their ID.
      *
      * @param id               The ID of the VillageNicheProductProfile to add hobbies to
      * @param nicheProductsAvailabilityList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addNicheProductsAvailabilityToVillageNicheProductProfile(Long id, List<NicheProductsAvailabilityDTO> nicheProductsAvailabilityList){
         VillageNicheProductProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageNicheProductProfile not found"));
         if(nicheProductsAvailabilityList != null && !nicheProductsAvailabilityList.isEmpty()) {

             Set<NicheProductsAvailability> toBeSavedList = nicheProductsAvailabilityList.stream().map(it-> assemblerNicheProductsAvailability.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageNicheProductProfile(saved));
             repositoryNicheProductsAvailability.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of nicheProductBuyerTypeList to a VillageNicheProductProfile identified by their ID.
      *
      * @param id               The ID of the VillageNicheProductProfile to add hobbies to
      * @param nicheProductBuyerTypeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addNicheProductBuyerTypeToVillageNicheProductProfile(Long id, List<NicheProductBuyerTypeDTO> nicheProductBuyerTypeList){
         VillageNicheProductProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageNicheProductProfile not found"));
         if(nicheProductBuyerTypeList != null && !nicheProductBuyerTypeList.isEmpty()) {

             Set<NicheProductBuyerType> toBeSavedList = nicheProductBuyerTypeList.stream().map(it-> assemblerNicheProductBuyerType.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageNicheProductProfile(saved));
             repositoryNicheProductBuyerType.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageNicheProductProfileDTO postHookSave(VillageNicheProductProfileDTO dto) {
        return dto;
    }

    protected VillageNicheProductProfileDTO preHookSave(VillageNicheProductProfileDTO dto) {
        return dto;
    }

    protected VillageNicheProductProfileDTO postHookUpdate(VillageNicheProductProfileDTO dto) {
        return dto;
    }

    protected VillageNicheProductProfileDTO preHookUpdate(VillageNicheProductProfileDTO VillageNicheProductProfileDTO) {
        return VillageNicheProductProfileDTO;
    }

    protected VillageNicheProductProfileDTO postHookDelete(VillageNicheProductProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageNicheProductProfileDTO postHookGetById(VillageNicheProductProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageNicheProductProfileDTO> postHookGetAll(PageDTO<VillageNicheProductProfileDTO> result) {
        return result;
    }




}
