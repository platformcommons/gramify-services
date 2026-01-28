package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageForestResourceProfileService;


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

public class VillageForestResourceProfileServiceImpl implements VillageForestResourceProfileService {

    private final VillageForestResourceProfileDTOAssembler assembler;
    private final VillageForestResourceProfileRepository repository;


    private final VillageForestProduceTypeDTOAssembler assemblerVillageForestProduceType;
    private final VillageForestProduceTypeRepository repositoryVillageForestProduceType;


    private final VillageCommonWildlifeDTOAssembler assemblerVillageCommonWildlife;
    private final VillageCommonWildlifeRepository repositoryVillageCommonWildlife;


    public VillageForestResourceProfileServiceImpl(
        VillageForestProduceTypeDTOAssembler assemblerVillageForestProduceType,  VillageForestProduceTypeRepository repositoryVillageForestProduceType,
        VillageCommonWildlifeDTOAssembler assemblerVillageCommonWildlife,  VillageCommonWildlifeRepository repositoryVillageCommonWildlife,
        VillageForestResourceProfileRepository repository, VillageForestResourceProfileDTOAssembler assembler) {
        this.assemblerVillageForestProduceType = assemblerVillageForestProduceType;
        this.repositoryVillageForestProduceType = repositoryVillageForestProduceType;
        this.assemblerVillageCommonWildlife = assemblerVillageCommonWildlife;
        this.repositoryVillageCommonWildlife = repositoryVillageCommonWildlife;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageForestResourceProfile.
     *
     * @param VillageForestResourceProfile the VillageForestResourceProfile DTO to save
     * @return the saved VillageForestResourceProfile DTO
     */
    @Transactional
    @Override
    public VillageForestResourceProfileDTO save(VillageForestResourceProfileDTO VillageForestResourceProfile) {
        log.debug("Entry - save(VillageForestResourceProfile={})", VillageForestResourceProfile);
        VillageForestResourceProfile = preHookSave(VillageForestResourceProfile);
        VillageForestResourceProfile entity = assembler.fromDTO(VillageForestResourceProfile);
        VillageForestResourceProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageForestResourceProfile.
     *
     * @param VillageForestResourceProfile the VillageForestResourceProfile DTO to update
     * @return the updated VillageForestResourceProfile DTO
     * @throws NotFoundException if the VillageForestResourceProfile is not found
     */
    @Transactional
    @Override
    public VillageForestResourceProfileDTO update(VillageForestResourceProfileDTO VillageForestResourceProfile) {
        log.debug("Entry - update(VillageForestResourceProfile={})", VillageForestResourceProfile);
        VillageForestResourceProfile = preHookUpdate(VillageForestResourceProfile);
        VillageForestResourceProfile saved = repository.findById(VillageForestResourceProfile.getId()).orElseThrow(() -> new NotFoundException("VillageForestResourceProfile not found"));
        saved.update(assembler.fromDTO(VillageForestResourceProfile));
        saved = repository.save(saved);
        VillageForestResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageForestResourceProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageForestResourceProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageForestResourceProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageForestResourceProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageForestResourceProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageForestResourceProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageForestResourceProfile by ID.
     *
     * @param id     the ID of the VillageForestResourceProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageForestResourceProfile DTO
     * @throws NotFoundException if the VillageForestResourceProfile is not found
     */
    @Transactional
    @Override
    public VillageForestResourceProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageForestResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageForestResourceProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageForestResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageForestResourceProfile by ID.
     *
     * @param id the ID of the VillageForestResourceProfile to retrieve
     * @return the VillageForestResourceProfile DTO
     * @throws NotFoundException if the VillageForestResourceProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageForestResourceProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageForestResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageForestResourceProfile not found"));
        VillageForestResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageForestResourceProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageForestResourceProfile> savedData = repository.findAllById(ids);
        Set<VillageForestResourceProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of villageForestProduceTypeList to a VillageForestResourceProfile identified by their ID.
      *
      * @param id               The ID of the VillageForestResourceProfile to add hobbies to
      * @param villageForestProduceTypeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addVillageForestProduceTypeToVillageForestResourceProfile(Long id, List<VillageForestProduceTypeDTO> villageForestProduceTypeList){
         VillageForestResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageForestResourceProfile not found"));
         if(villageForestProduceTypeList != null && !villageForestProduceTypeList.isEmpty()) {

             Set<VillageForestProduceType> toBeSavedList = villageForestProduceTypeList.stream().map(it-> assemblerVillageForestProduceType.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageForestResourceProfile(saved));
             repositoryVillageForestProduceType.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of villageCommonWildlifeList to a VillageForestResourceProfile identified by their ID.
      *
      * @param id               The ID of the VillageForestResourceProfile to add hobbies to
      * @param villageCommonWildlifeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addVillageCommonWildlifeToVillageForestResourceProfile(Long id, List<VillageCommonWildlifeDTO> villageCommonWildlifeList){
         VillageForestResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageForestResourceProfile not found"));
         if(villageCommonWildlifeList != null && !villageCommonWildlifeList.isEmpty()) {

             Set<VillageCommonWildlife> toBeSavedList = villageCommonWildlifeList.stream().map(it-> assemblerVillageCommonWildlife.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageForestResourceProfile(saved));
             repositoryVillageCommonWildlife.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageForestResourceProfileDTO postHookSave(VillageForestResourceProfileDTO dto) {
        return dto;
    }

    protected VillageForestResourceProfileDTO preHookSave(VillageForestResourceProfileDTO dto) {
        return dto;
    }

    protected VillageForestResourceProfileDTO postHookUpdate(VillageForestResourceProfileDTO dto) {
        return dto;
    }

    protected VillageForestResourceProfileDTO preHookUpdate(VillageForestResourceProfileDTO VillageForestResourceProfileDTO) {
        return VillageForestResourceProfileDTO;
    }

    protected VillageForestResourceProfileDTO postHookDelete(VillageForestResourceProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageForestResourceProfileDTO postHookGetById(VillageForestResourceProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageForestResourceProfileDTO> postHookGetAll(PageDTO<VillageForestResourceProfileDTO> result) {
        return result;
    }




}
