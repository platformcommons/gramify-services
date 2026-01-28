package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageFisheriesProfileService;


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

public class VillageFisheriesProfileServiceImpl implements VillageFisheriesProfileService {

    private final VillageFisheriesProfileDTOAssembler assembler;
    private final VillageFisheriesProfileRepository repository;


    private final ProductionSeasonDTOAssembler assemblerProductionSeason;
    private final ProductionSeasonRepository repositoryProductionSeason;


    public VillageFisheriesProfileServiceImpl(
        ProductionSeasonDTOAssembler assemblerProductionSeason,  ProductionSeasonRepository repositoryProductionSeason,
        VillageFisheriesProfileRepository repository, VillageFisheriesProfileDTOAssembler assembler) {
        this.assemblerProductionSeason = assemblerProductionSeason;
        this.repositoryProductionSeason = repositoryProductionSeason;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageFisheriesProfile.
     *
     * @param VillageFisheriesProfile the VillageFisheriesProfile DTO to save
     * @return the saved VillageFisheriesProfile DTO
     */
    @Transactional
    @Override
    public VillageFisheriesProfileDTO save(VillageFisheriesProfileDTO VillageFisheriesProfile) {
        log.debug("Entry - save(VillageFisheriesProfile={})", VillageFisheriesProfile);
        VillageFisheriesProfile = preHookSave(VillageFisheriesProfile);
        VillageFisheriesProfile entity = assembler.fromDTO(VillageFisheriesProfile);
        VillageFisheriesProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageFisheriesProfile.
     *
     * @param VillageFisheriesProfile the VillageFisheriesProfile DTO to update
     * @return the updated VillageFisheriesProfile DTO
     * @throws NotFoundException if the VillageFisheriesProfile is not found
     */
    @Transactional
    @Override
    public VillageFisheriesProfileDTO update(VillageFisheriesProfileDTO VillageFisheriesProfile) {
        log.debug("Entry - update(VillageFisheriesProfile={})", VillageFisheriesProfile);
        VillageFisheriesProfile = preHookUpdate(VillageFisheriesProfile);
        VillageFisheriesProfile saved = repository.findById(VillageFisheriesProfile.getId()).orElseThrow(() -> new NotFoundException("VillageFisheriesProfile not found"));
        saved.update(assembler.fromDTO(VillageFisheriesProfile));
        saved = repository.save(saved);
        VillageFisheriesProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageFisheriesProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageFisheriesProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageFisheriesProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageFisheriesProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageFisheriesProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageFisheriesProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageFisheriesProfile by ID.
     *
     * @param id     the ID of the VillageFisheriesProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageFisheriesProfile DTO
     * @throws NotFoundException if the VillageFisheriesProfile is not found
     */
    @Transactional
    @Override
    public VillageFisheriesProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageFisheriesProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageFisheriesProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageFisheriesProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageFisheriesProfile by ID.
     *
     * @param id the ID of the VillageFisheriesProfile to retrieve
     * @return the VillageFisheriesProfile DTO
     * @throws NotFoundException if the VillageFisheriesProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageFisheriesProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageFisheriesProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageFisheriesProfile not found"));
        VillageFisheriesProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageFisheriesProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageFisheriesProfile> savedData = repository.findAllById(ids);
        Set<VillageFisheriesProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of productionSeasonList to a VillageFisheriesProfile identified by their ID.
      *
      * @param id               The ID of the VillageFisheriesProfile to add hobbies to
      * @param productionSeasonList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addProductionSeasonToVillageFisheriesProfile(Long id, List<ProductionSeasonDTO> productionSeasonList){
         VillageFisheriesProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageFisheriesProfile not found"));
         if(productionSeasonList != null && !productionSeasonList.isEmpty()) {

             Set<ProductionSeason> toBeSavedList = productionSeasonList.stream().map(it-> assemblerProductionSeason.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageFisheriesProfile(saved));
             repositoryProductionSeason.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageFisheriesProfileDTO postHookSave(VillageFisheriesProfileDTO dto) {
        return dto;
    }

    protected VillageFisheriesProfileDTO preHookSave(VillageFisheriesProfileDTO dto) {
        return dto;
    }

    protected VillageFisheriesProfileDTO postHookUpdate(VillageFisheriesProfileDTO dto) {
        return dto;
    }

    protected VillageFisheriesProfileDTO preHookUpdate(VillageFisheriesProfileDTO VillageFisheriesProfileDTO) {
        return VillageFisheriesProfileDTO;
    }

    protected VillageFisheriesProfileDTO postHookDelete(VillageFisheriesProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageFisheriesProfileDTO postHookGetById(VillageFisheriesProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageFisheriesProfileDTO> postHookGetAll(PageDTO<VillageFisheriesProfileDTO> result) {
        return result;
    }




}
