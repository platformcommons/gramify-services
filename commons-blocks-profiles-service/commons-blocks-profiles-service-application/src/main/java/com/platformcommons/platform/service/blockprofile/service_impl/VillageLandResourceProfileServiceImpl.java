package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageLandResourceProfileService;


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

public class VillageLandResourceProfileServiceImpl implements VillageLandResourceProfileService {

    private final VillageLandResourceProfileDTOAssembler assembler;
    private final VillageLandResourceProfileRepository repository;


    private final VillageSoilTypeDTOAssembler assemblerVillageSoilType;
    private final VillageSoilTypeRepository repositoryVillageSoilType;


    public VillageLandResourceProfileServiceImpl(
        VillageSoilTypeDTOAssembler assemblerVillageSoilType,  VillageSoilTypeRepository repositoryVillageSoilType,
        VillageLandResourceProfileRepository repository, VillageLandResourceProfileDTOAssembler assembler) {
        this.assemblerVillageSoilType = assemblerVillageSoilType;
        this.repositoryVillageSoilType = repositoryVillageSoilType;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageLandResourceProfile.
     *
     * @param VillageLandResourceProfile the VillageLandResourceProfile DTO to save
     * @return the saved VillageLandResourceProfile DTO
     */
    @Transactional
    @Override
    public VillageLandResourceProfileDTO save(VillageLandResourceProfileDTO VillageLandResourceProfile) {
        log.debug("Entry - save(VillageLandResourceProfile={})", VillageLandResourceProfile);
        VillageLandResourceProfile = preHookSave(VillageLandResourceProfile);
        VillageLandResourceProfile entity = assembler.fromDTO(VillageLandResourceProfile);
        VillageLandResourceProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageLandResourceProfile.
     *
     * @param VillageLandResourceProfile the VillageLandResourceProfile DTO to update
     * @return the updated VillageLandResourceProfile DTO
     * @throws NotFoundException if the VillageLandResourceProfile is not found
     */
    @Transactional
    @Override
    public VillageLandResourceProfileDTO update(VillageLandResourceProfileDTO VillageLandResourceProfile) {
        log.debug("Entry - update(VillageLandResourceProfile={})", VillageLandResourceProfile);
        VillageLandResourceProfile = preHookUpdate(VillageLandResourceProfile);
        VillageLandResourceProfile saved = repository.findById(VillageLandResourceProfile.getId()).orElseThrow(() -> new NotFoundException("VillageLandResourceProfile not found"));
        saved.update(assembler.fromDTO(VillageLandResourceProfile));
        saved = repository.save(saved);
        VillageLandResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageLandResourceProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageLandResourceProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageLandResourceProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageLandResourceProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageLandResourceProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageLandResourceProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageLandResourceProfile by ID.
     *
     * @param id     the ID of the VillageLandResourceProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageLandResourceProfile DTO
     * @throws NotFoundException if the VillageLandResourceProfile is not found
     */
    @Transactional
    @Override
    public VillageLandResourceProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageLandResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageLandResourceProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageLandResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageLandResourceProfile by ID.
     *
     * @param id the ID of the VillageLandResourceProfile to retrieve
     * @return the VillageLandResourceProfile DTO
     * @throws NotFoundException if the VillageLandResourceProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageLandResourceProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageLandResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageLandResourceProfile not found"));
        VillageLandResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageLandResourceProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageLandResourceProfile> savedData = repository.findAllById(ids);
        Set<VillageLandResourceProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of villageSoilTypeList to a VillageLandResourceProfile identified by their ID.
      *
      * @param id               The ID of the VillageLandResourceProfile to add hobbies to
      * @param villageSoilTypeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addVillageSoilTypeToVillageLandResourceProfile(Long id, List<VillageSoilTypeDTO> villageSoilTypeList){
         VillageLandResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageLandResourceProfile not found"));
         if(villageSoilTypeList != null && !villageSoilTypeList.isEmpty()) {

             Set<VillageSoilType> toBeSavedList = villageSoilTypeList.stream().map(it-> assemblerVillageSoilType.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageLandResourceProfile(saved));
             repositoryVillageSoilType.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageLandResourceProfileDTO postHookSave(VillageLandResourceProfileDTO dto) {
        return dto;
    }

    protected VillageLandResourceProfileDTO preHookSave(VillageLandResourceProfileDTO dto) {
        return dto;
    }

    protected VillageLandResourceProfileDTO postHookUpdate(VillageLandResourceProfileDTO dto) {
        return dto;
    }

    protected VillageLandResourceProfileDTO preHookUpdate(VillageLandResourceProfileDTO VillageLandResourceProfileDTO) {
        return VillageLandResourceProfileDTO;
    }

    protected VillageLandResourceProfileDTO postHookDelete(VillageLandResourceProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageLandResourceProfileDTO postHookGetById(VillageLandResourceProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageLandResourceProfileDTO> postHookGetAll(PageDTO<VillageLandResourceProfileDTO> result) {
        return result;
    }




}
