package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageWaterResourceProfileService;


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

public class VillageWaterResourceProfileServiceImpl implements VillageWaterResourceProfileService {

    private final VillageWaterResourceProfileDTOAssembler assembler;
    private final VillageWaterResourceProfileRepository repository;


    private final VillageIrrigationSystemTypeDTOAssembler assemblerVillageIrrigationSystemType;
    private final VillageIrrigationSystemTypeRepository repositoryVillageIrrigationSystemType;


    public VillageWaterResourceProfileServiceImpl(
        VillageIrrigationSystemTypeDTOAssembler assemblerVillageIrrigationSystemType,  VillageIrrigationSystemTypeRepository repositoryVillageIrrigationSystemType,
        VillageWaterResourceProfileRepository repository, VillageWaterResourceProfileDTOAssembler assembler) {
        this.assemblerVillageIrrigationSystemType = assemblerVillageIrrigationSystemType;
        this.repositoryVillageIrrigationSystemType = repositoryVillageIrrigationSystemType;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageWaterResourceProfile.
     *
     * @param VillageWaterResourceProfile the VillageWaterResourceProfile DTO to save
     * @return the saved VillageWaterResourceProfile DTO
     */
    @Transactional
    @Override
    public VillageWaterResourceProfileDTO save(VillageWaterResourceProfileDTO VillageWaterResourceProfile) {
        log.debug("Entry - save(VillageWaterResourceProfile={})", VillageWaterResourceProfile);
        VillageWaterResourceProfile = preHookSave(VillageWaterResourceProfile);
        VillageWaterResourceProfile entity = assembler.fromDTO(VillageWaterResourceProfile);
        VillageWaterResourceProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageWaterResourceProfile.
     *
     * @param VillageWaterResourceProfile the VillageWaterResourceProfile DTO to update
     * @return the updated VillageWaterResourceProfile DTO
     * @throws NotFoundException if the VillageWaterResourceProfile is not found
     */
    @Transactional
    @Override
    public VillageWaterResourceProfileDTO update(VillageWaterResourceProfileDTO VillageWaterResourceProfile) {
        log.debug("Entry - update(VillageWaterResourceProfile={})", VillageWaterResourceProfile);
        VillageWaterResourceProfile = preHookUpdate(VillageWaterResourceProfile);
        VillageWaterResourceProfile saved = repository.findById(VillageWaterResourceProfile.getId()).orElseThrow(() -> new NotFoundException("VillageWaterResourceProfile not found"));
        saved.update(assembler.fromDTO(VillageWaterResourceProfile));
        saved = repository.save(saved);
        VillageWaterResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageWaterResourceProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageWaterResourceProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageWaterResourceProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageWaterResourceProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageWaterResourceProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageWaterResourceProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageWaterResourceProfile by ID.
     *
     * @param id     the ID of the VillageWaterResourceProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageWaterResourceProfile DTO
     * @throws NotFoundException if the VillageWaterResourceProfile is not found
     */
    @Transactional
    @Override
    public VillageWaterResourceProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageWaterResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageWaterResourceProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageWaterResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageWaterResourceProfile by ID.
     *
     * @param id the ID of the VillageWaterResourceProfile to retrieve
     * @return the VillageWaterResourceProfile DTO
     * @throws NotFoundException if the VillageWaterResourceProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageWaterResourceProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageWaterResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageWaterResourceProfile not found"));
        VillageWaterResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageWaterResourceProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageWaterResourceProfile> savedData = repository.findAllById(ids);
        Set<VillageWaterResourceProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of villageIrrigationSystemTypeList to a VillageWaterResourceProfile identified by their ID.
      *
      * @param id               The ID of the VillageWaterResourceProfile to add hobbies to
      * @param villageIrrigationSystemTypeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addVillageIrrigationSystemTypeToVillageWaterResourceProfile(Long id, List<VillageIrrigationSystemTypeDTO> villageIrrigationSystemTypeList){
         VillageWaterResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageWaterResourceProfile not found"));
         if(villageIrrigationSystemTypeList != null && !villageIrrigationSystemTypeList.isEmpty()) {

             Set<VillageIrrigationSystemType> toBeSavedList = villageIrrigationSystemTypeList.stream().map(it-> assemblerVillageIrrigationSystemType.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageWaterResourceProfile(saved));
             repositoryVillageIrrigationSystemType.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageWaterResourceProfileDTO postHookSave(VillageWaterResourceProfileDTO dto) {
        return dto;
    }

    protected VillageWaterResourceProfileDTO preHookSave(VillageWaterResourceProfileDTO dto) {
        return dto;
    }

    protected VillageWaterResourceProfileDTO postHookUpdate(VillageWaterResourceProfileDTO dto) {
        return dto;
    }

    protected VillageWaterResourceProfileDTO preHookUpdate(VillageWaterResourceProfileDTO VillageWaterResourceProfileDTO) {
        return VillageWaterResourceProfileDTO;
    }

    protected VillageWaterResourceProfileDTO postHookDelete(VillageWaterResourceProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageWaterResourceProfileDTO postHookGetById(VillageWaterResourceProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageWaterResourceProfileDTO> postHookGetAll(PageDTO<VillageWaterResourceProfileDTO> result) {
        return result;
    }




}
