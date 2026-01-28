package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageSurplusProduceProfileService;


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

public class VillageSurplusProduceProfileServiceImpl implements VillageSurplusProduceProfileService {

    private final VillageSurplusProduceProfileDTOAssembler assembler;
    private final VillageSurplusProduceProfileRepository repository;


    private final SurplusMovedThroughDTOAssembler assemblerSurplusMovedThrough;
    private final SurplusMovedThroughRepository repositorySurplusMovedThrough;


    private final SeasonalityOfSurplusDTOAssembler assemblerSeasonalityOfSurplus;
    private final SeasonalityOfSurplusRepository repositorySeasonalityOfSurplus;


    private final KeyConstraintsForSurplusExportDTOAssembler assemblerKeyConstraintsForSurplusExport;
    private final KeyConstraintsForSurplusExportRepository repositoryKeyConstraintsForSurplusExport;


    private final MainSurplusDestinationDTOAssembler assemblerMainSurplusDestination;
    private final MainSurplusDestinationRepository repositoryMainSurplusDestination;


    private final SurplusProduceTypeDTOAssembler assemblerSurplusProduceType;
    private final SurplusProduceTypeRepository repositorySurplusProduceType;


    public VillageSurplusProduceProfileServiceImpl(
        SurplusMovedThroughDTOAssembler assemblerSurplusMovedThrough,  SurplusMovedThroughRepository repositorySurplusMovedThrough,
        SeasonalityOfSurplusDTOAssembler assemblerSeasonalityOfSurplus,  SeasonalityOfSurplusRepository repositorySeasonalityOfSurplus,
        KeyConstraintsForSurplusExportDTOAssembler assemblerKeyConstraintsForSurplusExport,  KeyConstraintsForSurplusExportRepository repositoryKeyConstraintsForSurplusExport,
        MainSurplusDestinationDTOAssembler assemblerMainSurplusDestination,  MainSurplusDestinationRepository repositoryMainSurplusDestination,
        SurplusProduceTypeDTOAssembler assemblerSurplusProduceType,  SurplusProduceTypeRepository repositorySurplusProduceType,
        VillageSurplusProduceProfileRepository repository, VillageSurplusProduceProfileDTOAssembler assembler) {
        this.assemblerSurplusMovedThrough = assemblerSurplusMovedThrough;
        this.repositorySurplusMovedThrough = repositorySurplusMovedThrough;
        this.assemblerSeasonalityOfSurplus = assemblerSeasonalityOfSurplus;
        this.repositorySeasonalityOfSurplus = repositorySeasonalityOfSurplus;
        this.assemblerKeyConstraintsForSurplusExport = assemblerKeyConstraintsForSurplusExport;
        this.repositoryKeyConstraintsForSurplusExport = repositoryKeyConstraintsForSurplusExport;
        this.assemblerMainSurplusDestination = assemblerMainSurplusDestination;
        this.repositoryMainSurplusDestination = repositoryMainSurplusDestination;
        this.assemblerSurplusProduceType = assemblerSurplusProduceType;
        this.repositorySurplusProduceType = repositorySurplusProduceType;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageSurplusProduceProfile.
     *
     * @param VillageSurplusProduceProfile the VillageSurplusProduceProfile DTO to save
     * @return the saved VillageSurplusProduceProfile DTO
     */
    @Transactional
    @Override
    public VillageSurplusProduceProfileDTO save(VillageSurplusProduceProfileDTO VillageSurplusProduceProfile) {
        log.debug("Entry - save(VillageSurplusProduceProfile={})", VillageSurplusProduceProfile);
        VillageSurplusProduceProfile = preHookSave(VillageSurplusProduceProfile);
        VillageSurplusProduceProfile entity = assembler.fromDTO(VillageSurplusProduceProfile);
        VillageSurplusProduceProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageSurplusProduceProfile.
     *
     * @param VillageSurplusProduceProfile the VillageSurplusProduceProfile DTO to update
     * @return the updated VillageSurplusProduceProfile DTO
     * @throws NotFoundException if the VillageSurplusProduceProfile is not found
     */
    @Transactional
    @Override
    public VillageSurplusProduceProfileDTO update(VillageSurplusProduceProfileDTO VillageSurplusProduceProfile) {
        log.debug("Entry - update(VillageSurplusProduceProfile={})", VillageSurplusProduceProfile);
        VillageSurplusProduceProfile = preHookUpdate(VillageSurplusProduceProfile);
        VillageSurplusProduceProfile saved = repository.findById(VillageSurplusProduceProfile.getId()).orElseThrow(() -> new NotFoundException("VillageSurplusProduceProfile not found"));
        saved.update(assembler.fromDTO(VillageSurplusProduceProfile));
        saved = repository.save(saved);
        VillageSurplusProduceProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageSurplusProduceProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageSurplusProduceProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageSurplusProduceProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageSurplusProduceProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageSurplusProduceProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageSurplusProduceProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageSurplusProduceProfile by ID.
     *
     * @param id     the ID of the VillageSurplusProduceProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageSurplusProduceProfile DTO
     * @throws NotFoundException if the VillageSurplusProduceProfile is not found
     */
    @Transactional
    @Override
    public VillageSurplusProduceProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageSurplusProduceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageSurplusProduceProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageSurplusProduceProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageSurplusProduceProfile by ID.
     *
     * @param id the ID of the VillageSurplusProduceProfile to retrieve
     * @return the VillageSurplusProduceProfile DTO
     * @throws NotFoundException if the VillageSurplusProduceProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageSurplusProduceProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageSurplusProduceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageSurplusProduceProfile not found"));
        VillageSurplusProduceProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageSurplusProduceProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageSurplusProduceProfile> savedData = repository.findAllById(ids);
        Set<VillageSurplusProduceProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of surplusMovedThroughList to a VillageSurplusProduceProfile identified by their ID.
      *
      * @param id               The ID of the VillageSurplusProduceProfile to add hobbies to
      * @param surplusMovedThroughList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addSurplusMovedThroughToVillageSurplusProduceProfile(Long id, List<SurplusMovedThroughDTO> surplusMovedThroughList){
         VillageSurplusProduceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageSurplusProduceProfile not found"));
         if(surplusMovedThroughList != null && !surplusMovedThroughList.isEmpty()) {

             Set<SurplusMovedThrough> toBeSavedList = surplusMovedThroughList.stream().map(it-> assemblerSurplusMovedThrough.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageSurplusProduceProfile(saved));
             repositorySurplusMovedThrough.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of seasonalityOfSurplusList to a VillageSurplusProduceProfile identified by their ID.
      *
      * @param id               The ID of the VillageSurplusProduceProfile to add hobbies to
      * @param seasonalityOfSurplusList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addSeasonalityOfSurplusToVillageSurplusProduceProfile(Long id, List<SeasonalityOfSurplusDTO> seasonalityOfSurplusList){
         VillageSurplusProduceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageSurplusProduceProfile not found"));
         if(seasonalityOfSurplusList != null && !seasonalityOfSurplusList.isEmpty()) {

             Set<SeasonalityOfSurplus> toBeSavedList = seasonalityOfSurplusList.stream().map(it-> assemblerSeasonalityOfSurplus.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageSurplusProduceProfile(saved));
             repositorySeasonalityOfSurplus.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of keyConstraintsForSurplusExportList to a VillageSurplusProduceProfile identified by their ID.
      *
      * @param id               The ID of the VillageSurplusProduceProfile to add hobbies to
      * @param keyConstraintsForSurplusExportList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addKeyConstraintsForSurplusExportToVillageSurplusProduceProfile(Long id, List<KeyConstraintsForSurplusExportDTO> keyConstraintsForSurplusExportList){
         VillageSurplusProduceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageSurplusProduceProfile not found"));
         if(keyConstraintsForSurplusExportList != null && !keyConstraintsForSurplusExportList.isEmpty()) {

             Set<KeyConstraintsForSurplusExport> toBeSavedList = keyConstraintsForSurplusExportList.stream().map(it-> assemblerKeyConstraintsForSurplusExport.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageSurplusProduceProfile(saved));
             repositoryKeyConstraintsForSurplusExport.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of mainSurplusDestinationList to a VillageSurplusProduceProfile identified by their ID.
      *
      * @param id               The ID of the VillageSurplusProduceProfile to add hobbies to
      * @param mainSurplusDestinationList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addMainSurplusDestinationToVillageSurplusProduceProfile(Long id, List<MainSurplusDestinationDTO> mainSurplusDestinationList){
         VillageSurplusProduceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageSurplusProduceProfile not found"));
         if(mainSurplusDestinationList != null && !mainSurplusDestinationList.isEmpty()) {

             Set<MainSurplusDestination> toBeSavedList = mainSurplusDestinationList.stream().map(it-> assemblerMainSurplusDestination.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageSurplusProduceProfile(saved));
             repositoryMainSurplusDestination.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of surplusProduceTypeList to a VillageSurplusProduceProfile identified by their ID.
      *
      * @param id               The ID of the VillageSurplusProduceProfile to add hobbies to
      * @param surplusProduceTypeList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addSurplusProduceTypeToVillageSurplusProduceProfile(Long id, List<SurplusProduceTypeDTO> surplusProduceTypeList){
         VillageSurplusProduceProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageSurplusProduceProfile not found"));
         if(surplusProduceTypeList != null && !surplusProduceTypeList.isEmpty()) {

             Set<SurplusProduceType> toBeSavedList = surplusProduceTypeList.stream().map(it-> assemblerSurplusProduceType.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageSurplusProduceProfile(saved));
             repositorySurplusProduceType.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageSurplusProduceProfileDTO postHookSave(VillageSurplusProduceProfileDTO dto) {
        return dto;
    }

    protected VillageSurplusProduceProfileDTO preHookSave(VillageSurplusProduceProfileDTO dto) {
        return dto;
    }

    protected VillageSurplusProduceProfileDTO postHookUpdate(VillageSurplusProduceProfileDTO dto) {
        return dto;
    }

    protected VillageSurplusProduceProfileDTO preHookUpdate(VillageSurplusProduceProfileDTO VillageSurplusProduceProfileDTO) {
        return VillageSurplusProduceProfileDTO;
    }

    protected VillageSurplusProduceProfileDTO postHookDelete(VillageSurplusProduceProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageSurplusProduceProfileDTO postHookGetById(VillageSurplusProduceProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageSurplusProduceProfileDTO> postHookGetAll(PageDTO<VillageSurplusProduceProfileDTO> result) {
        return result;
    }




}
