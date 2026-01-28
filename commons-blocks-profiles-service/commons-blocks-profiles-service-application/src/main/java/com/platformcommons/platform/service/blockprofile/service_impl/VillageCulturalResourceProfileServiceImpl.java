package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageCulturalResourceProfileService;


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

public class VillageCulturalResourceProfileServiceImpl implements VillageCulturalResourceProfileService {

    private final VillageCulturalResourceProfileDTOAssembler assembler;
    private final VillageCulturalResourceProfileRepository repository;


    private final MajorFestivalDTOAssembler assemblerMajorFestival;
    private final MajorFestivalRepository repositoryMajorFestival;


    private final MainReligiousPlaceDTOAssembler assemblerMainReligiousPlace;
    private final MainReligiousPlaceRepository repositoryMainReligiousPlace;


    private final LocalFestivalDTOAssembler assemblerLocalFestival;
    private final LocalFestivalRepository repositoryLocalFestival;


    private final LocalArtFormTypeDTOAssembler assemblerLocalArtFormType;
    private final LocalArtFormTypeRepository repositoryLocalArtFormType;


    public VillageCulturalResourceProfileServiceImpl(
        MajorFestivalDTOAssembler assemblerMajorFestival,  MajorFestivalRepository repositoryMajorFestival,
        MainReligiousPlaceDTOAssembler assemblerMainReligiousPlace,  MainReligiousPlaceRepository repositoryMainReligiousPlace,
        LocalFestivalDTOAssembler assemblerLocalFestival,  LocalFestivalRepository repositoryLocalFestival,
        LocalArtFormTypeDTOAssembler assemblerLocalArtFormType,  LocalArtFormTypeRepository repositoryLocalArtFormType,
        VillageCulturalResourceProfileRepository repository, VillageCulturalResourceProfileDTOAssembler assembler) {
        this.assemblerMajorFestival = assemblerMajorFestival;
        this.repositoryMajorFestival = repositoryMajorFestival;
        this.assemblerMainReligiousPlace = assemblerMainReligiousPlace;
        this.repositoryMainReligiousPlace = repositoryMainReligiousPlace;
        this.assemblerLocalFestival = assemblerLocalFestival;
        this.repositoryLocalFestival = repositoryLocalFestival;
        this.assemblerLocalArtFormType = assemblerLocalArtFormType;
        this.repositoryLocalArtFormType = repositoryLocalArtFormType;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageCulturalResourceProfile.
     *
     * @param VillageCulturalResourceProfile the VillageCulturalResourceProfile DTO to save
     * @return the saved VillageCulturalResourceProfile DTO
     */
    @Transactional
    @Override
    public VillageCulturalResourceProfileDTO save(VillageCulturalResourceProfileDTO VillageCulturalResourceProfile) {
        log.debug("Entry - save(VillageCulturalResourceProfile={})", VillageCulturalResourceProfile);
        VillageCulturalResourceProfile = preHookSave(VillageCulturalResourceProfile);
        VillageCulturalResourceProfile entity = assembler.fromDTO(VillageCulturalResourceProfile);
        VillageCulturalResourceProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageCulturalResourceProfile.
     *
     * @param VillageCulturalResourceProfile the VillageCulturalResourceProfile DTO to update
     * @return the updated VillageCulturalResourceProfile DTO
     * @throws NotFoundException if the VillageCulturalResourceProfile is not found
     */
    @Transactional
    @Override
    public VillageCulturalResourceProfileDTO update(VillageCulturalResourceProfileDTO VillageCulturalResourceProfile) {
        log.debug("Entry - update(VillageCulturalResourceProfile={})", VillageCulturalResourceProfile);
        VillageCulturalResourceProfile = preHookUpdate(VillageCulturalResourceProfile);
        VillageCulturalResourceProfile saved = repository.findById(VillageCulturalResourceProfile.getId()).orElseThrow(() -> new NotFoundException("VillageCulturalResourceProfile not found"));
        saved.update(assembler.fromDTO(VillageCulturalResourceProfile));
        saved = repository.save(saved);
        VillageCulturalResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageCulturalResourceProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageCulturalResourceProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageCulturalResourceProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageCulturalResourceProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageCulturalResourceProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageCulturalResourceProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageCulturalResourceProfile by ID.
     *
     * @param id     the ID of the VillageCulturalResourceProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageCulturalResourceProfile DTO
     * @throws NotFoundException if the VillageCulturalResourceProfile is not found
     */
    @Transactional
    @Override
    public VillageCulturalResourceProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageCulturalResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCulturalResourceProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageCulturalResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageCulturalResourceProfile by ID.
     *
     * @param id the ID of the VillageCulturalResourceProfile to retrieve
     * @return the VillageCulturalResourceProfile DTO
     * @throws NotFoundException if the VillageCulturalResourceProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageCulturalResourceProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageCulturalResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCulturalResourceProfile not found"));
        VillageCulturalResourceProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageCulturalResourceProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageCulturalResourceProfile> savedData = repository.findAllById(ids);
        Set<VillageCulturalResourceProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of majorFestivalList to a VillageCulturalResourceProfile identified by their ID.
      *
      * @param id               The ID of the VillageCulturalResourceProfile to add hobbies to
      * @param majorFestivalList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addMajorFestivalToVillageCulturalResourceProfile(Long id, List<MajorFestivalDTO> majorFestivalList){
         VillageCulturalResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCulturalResourceProfile not found"));
         if(majorFestivalList != null && !majorFestivalList.isEmpty()) {

             Set<MajorFestival> toBeSavedList = majorFestivalList.stream().map(it-> assemblerMajorFestival.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageCulturalResourceProfile(saved));
             repositoryMajorFestival.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of mainReligiousPlaceList to a VillageCulturalResourceProfile identified by their ID.
      *
      * @param id               The ID of the VillageCulturalResourceProfile to add hobbies to
      * @param mainReligiousPlaceList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addMainReligiousPlaceToVillageCulturalResourceProfile(Long id, List<MainReligiousPlaceDTO> mainReligiousPlaceList){
         VillageCulturalResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCulturalResourceProfile not found"));
         if(mainReligiousPlaceList != null && !mainReligiousPlaceList.isEmpty()) {

             Set<MainReligiousPlace> toBeSavedList = mainReligiousPlaceList.stream().map(it-> assemblerMainReligiousPlace.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageCulturalResourceProfile(saved));
             repositoryMainReligiousPlace.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of localFestivalList to a VillageCulturalResourceProfile identified by their ID.
      *
      * @param id               The ID of the VillageCulturalResourceProfile to add hobbies to
      * @param localFestivalList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addLocalFestivalToVillageCulturalResourceProfile(Long id, List<LocalFestivalDTO> localFestivalList){
         VillageCulturalResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCulturalResourceProfile not found"));
         if(localFestivalList != null && !localFestivalList.isEmpty()) {

             Set<LocalFestival> toBeSavedList = localFestivalList.stream().map(it-> assemblerLocalFestival.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageCulturalResourceProfile(saved));
             repositoryLocalFestival.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of localArtFormTypeList to a VillageCulturalResourceProfile identified by their ID.
      *
      * @param id               The ID of the VillageCulturalResourceProfile to add hobbies to
      * @param localArtFormTypeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addLocalArtFormTypeToVillageCulturalResourceProfile(Long id, List<LocalArtFormTypeDTO> localArtFormTypeList){
         VillageCulturalResourceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageCulturalResourceProfile not found"));
         if(localArtFormTypeList != null && !localArtFormTypeList.isEmpty()) {

             Set<LocalArtFormType> toBeSavedList = localArtFormTypeList.stream().map(it-> assemblerLocalArtFormType.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageCulturalResourceProfile(saved));
             repositoryLocalArtFormType.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageCulturalResourceProfileDTO postHookSave(VillageCulturalResourceProfileDTO dto) {
        return dto;
    }

    protected VillageCulturalResourceProfileDTO preHookSave(VillageCulturalResourceProfileDTO dto) {
        return dto;
    }

    protected VillageCulturalResourceProfileDTO postHookUpdate(VillageCulturalResourceProfileDTO dto) {
        return dto;
    }

    protected VillageCulturalResourceProfileDTO preHookUpdate(VillageCulturalResourceProfileDTO VillageCulturalResourceProfileDTO) {
        return VillageCulturalResourceProfileDTO;
    }

    protected VillageCulturalResourceProfileDTO postHookDelete(VillageCulturalResourceProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageCulturalResourceProfileDTO postHookGetById(VillageCulturalResourceProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageCulturalResourceProfileDTO> postHookGetAll(PageDTO<VillageCulturalResourceProfileDTO> result) {
        return result;
    }




}
