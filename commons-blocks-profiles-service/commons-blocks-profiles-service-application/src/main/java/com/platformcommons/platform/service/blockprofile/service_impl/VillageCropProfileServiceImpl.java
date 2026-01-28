package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageCropProfileService;


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

public class VillageCropProfileServiceImpl implements VillageCropProfileService {

    private final VillageCropProfileDTOAssembler assembler;
    private final VillageCropProfileRepository repository;


    private final CropDTOAssembler assemblerCrop;
    private final CropRepository repositoryCrop;


    private final CroppingSeasonDTOAssembler assemblerCroppingSeason;
    private final CroppingSeasonRepository repositoryCroppingSeason;


    public VillageCropProfileServiceImpl(
        CropDTOAssembler assemblerCrop,  CropRepository repositoryCrop,
        CroppingSeasonDTOAssembler assemblerCroppingSeason,  CroppingSeasonRepository repositoryCroppingSeason,
        VillageCropProfileRepository repository, VillageCropProfileDTOAssembler assembler) {
        this.assemblerCrop = assemblerCrop;
        this.repositoryCrop = repositoryCrop;
        this.assemblerCroppingSeason = assemblerCroppingSeason;
        this.repositoryCroppingSeason = repositoryCroppingSeason;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageCropProfile.
     *
     * @param VillageCropProfile the VillageCropProfile DTO to save
     * @return the saved VillageCropProfile DTO
     */
    @Transactional
    @Override
    public VillageCropProfileDTO save(VillageCropProfileDTO VillageCropProfile) {
        log.debug("Entry - save(VillageCropProfile={})", VillageCropProfile);
        VillageCropProfile = preHookSave(VillageCropProfile);
        VillageCropProfile entity = assembler.fromDTO(VillageCropProfile);
        VillageCropProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageCropProfile.
     *
     * @param VillageCropProfile the VillageCropProfile DTO to update
     * @return the updated VillageCropProfile DTO
     * @throws NotFoundException if the VillageCropProfile is not found
     */
    @Transactional
    @Override
    public VillageCropProfileDTO update(VillageCropProfileDTO VillageCropProfile) {
        log.debug("Entry - update(VillageCropProfile={})", VillageCropProfile);
        VillageCropProfile = preHookUpdate(VillageCropProfile);
        VillageCropProfile saved = repository.findById(VillageCropProfile.getId()).orElseThrow(() -> new NotFoundException("VillageCropProfile not found"));
        saved.update(assembler.fromDTO(VillageCropProfile));
        saved = repository.save(saved);
        VillageCropProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageCropProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageCropProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageCropProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageCropProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageCropProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageCropProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageCropProfile by ID.
     *
     * @param id     the ID of the VillageCropProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageCropProfile DTO
     * @throws NotFoundException if the VillageCropProfile is not found
     */
    @Transactional
    @Override
    public VillageCropProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageCropProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCropProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageCropProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageCropProfile by ID.
     *
     * @param id the ID of the VillageCropProfile to retrieve
     * @return the VillageCropProfile DTO
     * @throws NotFoundException if the VillageCropProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageCropProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageCropProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCropProfile not found"));
        VillageCropProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageCropProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageCropProfile> savedData = repository.findAllById(ids);
        Set<VillageCropProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of cropList to a VillageCropProfile identified by their ID.
      *
      * @param id               The ID of the VillageCropProfile to add hobbies to
      * @param cropList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addCropToVillageCropProfile(Long id, List<CropDTO> cropList){
         VillageCropProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCropProfile not found"));
         if(cropList != null && !cropList.isEmpty()) {

             Set<Crop> toBeSavedList = cropList.stream().map(it-> assemblerCrop.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageCropProfile(saved));
             repositoryCrop.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of croppingSeasonList to a VillageCropProfile identified by their ID.
      *
      * @param id               The ID of the VillageCropProfile to add hobbies to
      * @param croppingSeasonList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addCroppingSeasonToVillageCropProfile(Long id, List<CroppingSeasonDTO> croppingSeasonList){
         VillageCropProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCropProfile not found"));
         if(croppingSeasonList != null && !croppingSeasonList.isEmpty()) {

             Set<CroppingSeason> toBeSavedList = croppingSeasonList.stream().map(it-> assemblerCroppingSeason.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageCropProfile(saved));
             repositoryCroppingSeason.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageCropProfileDTO postHookSave(VillageCropProfileDTO dto) {
        return dto;
    }

    protected VillageCropProfileDTO preHookSave(VillageCropProfileDTO dto) {
        return dto;
    }

    protected VillageCropProfileDTO postHookUpdate(VillageCropProfileDTO dto) {
        return dto;
    }

    protected VillageCropProfileDTO preHookUpdate(VillageCropProfileDTO VillageCropProfileDTO) {
        return VillageCropProfileDTO;
    }

    protected VillageCropProfileDTO postHookDelete(VillageCropProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageCropProfileDTO postHookGetById(VillageCropProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageCropProfileDTO> postHookGetAll(PageDTO<VillageCropProfileDTO> result) {
        return result;
    }




}
