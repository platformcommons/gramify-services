package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageLivestockProfileService;


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

public class VillageLivestockProfileServiceImpl implements VillageLivestockProfileService {

    private final VillageLivestockProfileDTOAssembler assembler;
    private final VillageLivestockProfileRepository repository;


    private final ProductionSeasonalityDTOAssembler assemblerProductionSeasonality;
    private final ProductionSeasonalityRepository repositoryProductionSeasonality;


    public VillageLivestockProfileServiceImpl(
        ProductionSeasonalityDTOAssembler assemblerProductionSeasonality,  ProductionSeasonalityRepository repositoryProductionSeasonality,
        VillageLivestockProfileRepository repository, VillageLivestockProfileDTOAssembler assembler) {
        this.assemblerProductionSeasonality = assemblerProductionSeasonality;
        this.repositoryProductionSeasonality = repositoryProductionSeasonality;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageLivestockProfile.
     *
     * @param VillageLivestockProfile the VillageLivestockProfile DTO to save
     * @return the saved VillageLivestockProfile DTO
     */
    @Transactional
    @Override
    public VillageLivestockProfileDTO save(VillageLivestockProfileDTO VillageLivestockProfile) {
        log.debug("Entry - save(VillageLivestockProfile={})", VillageLivestockProfile);
        VillageLivestockProfile = preHookSave(VillageLivestockProfile);
        VillageLivestockProfile entity = assembler.fromDTO(VillageLivestockProfile);
        VillageLivestockProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageLivestockProfile.
     *
     * @param VillageLivestockProfile the VillageLivestockProfile DTO to update
     * @return the updated VillageLivestockProfile DTO
     * @throws NotFoundException if the VillageLivestockProfile is not found
     */
    @Transactional
    @Override
    public VillageLivestockProfileDTO update(VillageLivestockProfileDTO VillageLivestockProfile) {
        log.debug("Entry - update(VillageLivestockProfile={})", VillageLivestockProfile);
        VillageLivestockProfile = preHookUpdate(VillageLivestockProfile);
        VillageLivestockProfile saved = repository.findById(VillageLivestockProfile.getId()).orElseThrow(() -> new NotFoundException("VillageLivestockProfile not found"));
        saved.update(assembler.fromDTO(VillageLivestockProfile));
        saved = repository.save(saved);
        VillageLivestockProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageLivestockProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageLivestockProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageLivestockProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageLivestockProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageLivestockProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageLivestockProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageLivestockProfile by ID.
     *
     * @param id     the ID of the VillageLivestockProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageLivestockProfile DTO
     * @throws NotFoundException if the VillageLivestockProfile is not found
     */
    @Transactional
    @Override
    public VillageLivestockProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageLivestockProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageLivestockProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageLivestockProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageLivestockProfile by ID.
     *
     * @param id the ID of the VillageLivestockProfile to retrieve
     * @return the VillageLivestockProfile DTO
     * @throws NotFoundException if the VillageLivestockProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageLivestockProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageLivestockProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageLivestockProfile not found"));
        VillageLivestockProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageLivestockProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageLivestockProfile> savedData = repository.findAllById(ids);
        Set<VillageLivestockProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of productionSeasonalityList to a VillageLivestockProfile identified by their ID.
      *
      * @param id               The ID of the VillageLivestockProfile to add hobbies to
      * @param productionSeasonalityList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addProductionSeasonalityToVillageLivestockProfile(Long id, List<ProductionSeasonalityDTO> productionSeasonalityList){
         VillageLivestockProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageLivestockProfile not found"));
         if(productionSeasonalityList != null && !productionSeasonalityList.isEmpty()) {

             Set<ProductionSeasonality> toBeSavedList = productionSeasonalityList.stream().map(it-> assemblerProductionSeasonality.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageLivestockProfile(saved));
             repositoryProductionSeasonality.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageLivestockProfileDTO postHookSave(VillageLivestockProfileDTO dto) {
        return dto;
    }

    protected VillageLivestockProfileDTO preHookSave(VillageLivestockProfileDTO dto) {
        return dto;
    }

    protected VillageLivestockProfileDTO postHookUpdate(VillageLivestockProfileDTO dto) {
        return dto;
    }

    protected VillageLivestockProfileDTO preHookUpdate(VillageLivestockProfileDTO VillageLivestockProfileDTO) {
        return VillageLivestockProfileDTO;
    }

    protected VillageLivestockProfileDTO postHookDelete(VillageLivestockProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageLivestockProfileDTO postHookGetById(VillageLivestockProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageLivestockProfileDTO> postHookGetAll(PageDTO<VillageLivestockProfileDTO> result) {
        return result;
    }




}
