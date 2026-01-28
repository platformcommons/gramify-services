package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageHorticultureProfileService;


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

public class VillageHorticultureProfileServiceImpl implements VillageHorticultureProfileService {

    private final VillageHorticultureProfileDTOAssembler assembler;
    private final VillageHorticultureProfileRepository repository;


    private final HorticultureProductDTOAssembler assemblerHorticultureProduct;
    private final HorticultureProductRepository repositoryHorticultureProduct;


    private final ProductionSeasonDTOAssembler assemblerProductionSeason;
    private final ProductionSeasonRepository repositoryProductionSeason;


    public VillageHorticultureProfileServiceImpl(
        HorticultureProductDTOAssembler assemblerHorticultureProduct,  HorticultureProductRepository repositoryHorticultureProduct,
        ProductionSeasonDTOAssembler assemblerProductionSeason,  ProductionSeasonRepository repositoryProductionSeason,
        VillageHorticultureProfileRepository repository, VillageHorticultureProfileDTOAssembler assembler) {
        this.assemblerHorticultureProduct = assemblerHorticultureProduct;
        this.repositoryHorticultureProduct = repositoryHorticultureProduct;
        this.assemblerProductionSeason = assemblerProductionSeason;
        this.repositoryProductionSeason = repositoryProductionSeason;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageHorticultureProfile.
     *
     * @param VillageHorticultureProfile the VillageHorticultureProfile DTO to save
     * @return the saved VillageHorticultureProfile DTO
     */
    @Transactional
    @Override
    public VillageHorticultureProfileDTO save(VillageHorticultureProfileDTO VillageHorticultureProfile) {
        log.debug("Entry - save(VillageHorticultureProfile={})", VillageHorticultureProfile);
        VillageHorticultureProfile = preHookSave(VillageHorticultureProfile);
        VillageHorticultureProfile entity = assembler.fromDTO(VillageHorticultureProfile);
        VillageHorticultureProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageHorticultureProfile.
     *
     * @param VillageHorticultureProfile the VillageHorticultureProfile DTO to update
     * @return the updated VillageHorticultureProfile DTO
     * @throws NotFoundException if the VillageHorticultureProfile is not found
     */
    @Transactional
    @Override
    public VillageHorticultureProfileDTO update(VillageHorticultureProfileDTO VillageHorticultureProfile) {
        log.debug("Entry - update(VillageHorticultureProfile={})", VillageHorticultureProfile);
        VillageHorticultureProfile = preHookUpdate(VillageHorticultureProfile);
        VillageHorticultureProfile saved = repository.findById(VillageHorticultureProfile.getId()).orElseThrow(() -> new NotFoundException("VillageHorticultureProfile not found"));
        saved.update(assembler.fromDTO(VillageHorticultureProfile));
        saved = repository.save(saved);
        VillageHorticultureProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageHorticultureProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageHorticultureProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageHorticultureProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageHorticultureProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageHorticultureProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageHorticultureProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageHorticultureProfile by ID.
     *
     * @param id     the ID of the VillageHorticultureProfile to delete
     * @param reason the reason for deletion
     * @return the deleted VillageHorticultureProfile DTO
     * @throws NotFoundException if the VillageHorticultureProfile is not found
     */
    @Transactional
    @Override
    public VillageHorticultureProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageHorticultureProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageHorticultureProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageHorticultureProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageHorticultureProfile by ID.
     *
     * @param id the ID of the VillageHorticultureProfile to retrieve
     * @return the VillageHorticultureProfile DTO
     * @throws NotFoundException if the VillageHorticultureProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageHorticultureProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageHorticultureProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageHorticultureProfile not found"));
        VillageHorticultureProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageHorticultureProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageHorticultureProfile> savedData = repository.findAllById(ids);
        Set<VillageHorticultureProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of horticultureProductList to a VillageHorticultureProfile identified by their ID.
      *
      * @param id               The ID of the VillageHorticultureProfile to add hobbies to
      * @param horticultureProductList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addHorticultureProductToVillageHorticultureProfile(Long id, List<HorticultureProductDTO> horticultureProductList){
         VillageHorticultureProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageHorticultureProfile not found"));
         if(horticultureProductList != null && !horticultureProductList.isEmpty()) {

             Set<HorticultureProduct> toBeSavedList = horticultureProductList.stream().map(it-> assemblerHorticultureProduct.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageHorticultureProfile(saved));
             repositoryHorticultureProduct.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of productionSeasonList to a VillageHorticultureProfile identified by their ID.
      *
      * @param id               The ID of the VillageHorticultureProfile to add hobbies to
      * @param productionSeasonList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addProductionSeasonToVillageHorticultureProfile(Long id, List<ProductionSeasonDTO> productionSeasonList){
         VillageHorticultureProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageHorticultureProfile not found"));
         if(productionSeasonList != null && !productionSeasonList.isEmpty()) {

             Set<ProductionSeason> toBeSavedList = productionSeasonList.stream().map(it-> assemblerProductionSeason.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageHorticultureProfile(saved));
             repositoryProductionSeason.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageHorticultureProfileDTO postHookSave(VillageHorticultureProfileDTO dto) {
        return dto;
    }

    protected VillageHorticultureProfileDTO preHookSave(VillageHorticultureProfileDTO dto) {
        return dto;
    }

    protected VillageHorticultureProfileDTO postHookUpdate(VillageHorticultureProfileDTO dto) {
        return dto;
    }

    protected VillageHorticultureProfileDTO preHookUpdate(VillageHorticultureProfileDTO VillageHorticultureProfileDTO) {
        return VillageHorticultureProfileDTO;
    }

    protected VillageHorticultureProfileDTO postHookDelete(VillageHorticultureProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageHorticultureProfileDTO postHookGetById(VillageHorticultureProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageHorticultureProfileDTO> postHookGetAll(PageDTO<VillageHorticultureProfileDTO> result) {
        return result;
    }




}
