package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageMineralAndBiodiversityPrService;


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

public class VillageMineralAndBiodiversityPrServiceImpl implements VillageMineralAndBiodiversityPrService {

    private final VillageMineralAndBiodiversityPrDTOAssembler assembler;
    private final VillageMineralAndBiodiversityPrRepository repository;


    private final VillageCommonFloraDTOAssembler assemblerVillageCommonFlora;
    private final VillageCommonFloraRepository repositoryVillageCommonFlora;


    private final VillageCommonFaunaDTOAssembler assemblerVillageCommonFauna;
    private final VillageCommonFaunaRepository repositoryVillageCommonFauna;


    public VillageMineralAndBiodiversityPrServiceImpl(
        VillageCommonFloraDTOAssembler assemblerVillageCommonFlora,  VillageCommonFloraRepository repositoryVillageCommonFlora,
        VillageCommonFaunaDTOAssembler assemblerVillageCommonFauna,  VillageCommonFaunaRepository repositoryVillageCommonFauna,
        VillageMineralAndBiodiversityPrRepository repository, VillageMineralAndBiodiversityPrDTOAssembler assembler) {
        this.assemblerVillageCommonFlora = assemblerVillageCommonFlora;
        this.repositoryVillageCommonFlora = repositoryVillageCommonFlora;
        this.assemblerVillageCommonFauna = assemblerVillageCommonFauna;
        this.repositoryVillageCommonFauna = repositoryVillageCommonFauna;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageMineralAndBiodiversityPr.
     *
     * @param VillageMineralAndBiodiversityPr the VillageMineralAndBiodiversityPr DTO to save
     * @return the saved VillageMineralAndBiodiversityPr DTO
     */
    @Transactional
    @Override
    public VillageMineralAndBiodiversityPrDTO save(VillageMineralAndBiodiversityPrDTO VillageMineralAndBiodiversityPr) {
        log.debug("Entry - save(VillageMineralAndBiodiversityPr={})", VillageMineralAndBiodiversityPr);
        VillageMineralAndBiodiversityPr = preHookSave(VillageMineralAndBiodiversityPr);
        VillageMineralAndBiodiversityPr entity = assembler.fromDTO(VillageMineralAndBiodiversityPr);
        VillageMineralAndBiodiversityPrDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageMineralAndBiodiversityPr.
     *
     * @param VillageMineralAndBiodiversityPr the VillageMineralAndBiodiversityPr DTO to update
     * @return the updated VillageMineralAndBiodiversityPr DTO
     * @throws NotFoundException if the VillageMineralAndBiodiversityPr is not found
     */
    @Transactional
    @Override
    public VillageMineralAndBiodiversityPrDTO update(VillageMineralAndBiodiversityPrDTO VillageMineralAndBiodiversityPr) {
        log.debug("Entry - update(VillageMineralAndBiodiversityPr={})", VillageMineralAndBiodiversityPr);
        VillageMineralAndBiodiversityPr = preHookUpdate(VillageMineralAndBiodiversityPr);
        VillageMineralAndBiodiversityPr saved = repository.findById(VillageMineralAndBiodiversityPr.getId()).orElseThrow(() -> new NotFoundException("VillageMineralAndBiodiversityPr not found"));
        saved.update(assembler.fromDTO(VillageMineralAndBiodiversityPr));
        saved = repository.save(saved);
        VillageMineralAndBiodiversityPrDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageMineralAndBiodiversityPrs.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageMineralAndBiodiversityPr DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageMineralAndBiodiversityPrDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageMineralAndBiodiversityPr> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageMineralAndBiodiversityPrDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageMineralAndBiodiversityPrDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageMineralAndBiodiversityPr by ID.
     *
     * @param id     the ID of the VillageMineralAndBiodiversityPr to delete
     * @param reason the reason for deletion
     * @return the deleted VillageMineralAndBiodiversityPr DTO
     * @throws NotFoundException if the VillageMineralAndBiodiversityPr is not found
     */
    @Transactional
    @Override
    public VillageMineralAndBiodiversityPrDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageMineralAndBiodiversityPr saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageMineralAndBiodiversityPr not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageMineralAndBiodiversityPrDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageMineralAndBiodiversityPr by ID.
     *
     * @param id the ID of the VillageMineralAndBiodiversityPr to retrieve
     * @return the VillageMineralAndBiodiversityPr DTO
     * @throws NotFoundException if the VillageMineralAndBiodiversityPr is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageMineralAndBiodiversityPrDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageMineralAndBiodiversityPr saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageMineralAndBiodiversityPr not found"));
        VillageMineralAndBiodiversityPrDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageMineralAndBiodiversityPrDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageMineralAndBiodiversityPr> savedData = repository.findAllById(ids);
        Set<VillageMineralAndBiodiversityPrDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of villageCommonFloraList to a VillageMineralAndBiodiversityPr identified by their ID.
      *
      * @param id               The ID of the VillageMineralAndBiodiversityPr to add hobbies to
      * @param villageCommonFloraList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addVillageCommonFloraToVillageMineralAndBiodiversityPr(Long id, List<VillageCommonFloraDTO> villageCommonFloraList){
         VillageMineralAndBiodiversityPr saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageMineralAndBiodiversityPr not found"));
         if(villageCommonFloraList != null && !villageCommonFloraList.isEmpty()) {

             Set<VillageCommonFlora> toBeSavedList = villageCommonFloraList.stream().map(it-> assemblerVillageCommonFlora.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageMineralAndBiodiversityPr(saved));
             repositoryVillageCommonFlora.saveAll(toBeSavedList);

         }
     }
 /**
      * Adds a list of villageCommonFaunaList to a VillageMineralAndBiodiversityPr identified by their ID.
      *
      * @param id               The ID of the VillageMineralAndBiodiversityPr to add hobbies to
      * @param villageCommonFaunaList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addVillageCommonFaunaToVillageMineralAndBiodiversityPr(Long id, List<VillageCommonFaunaDTO> villageCommonFaunaList){
         VillageMineralAndBiodiversityPr saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageMineralAndBiodiversityPr not found"));
         if(villageCommonFaunaList != null && !villageCommonFaunaList.isEmpty()) {

             Set<VillageCommonFauna> toBeSavedList = villageCommonFaunaList.stream().map(it-> assemblerVillageCommonFauna.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageMineralAndBiodiversityPr(saved));
             repositoryVillageCommonFauna.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageMineralAndBiodiversityPrDTO postHookSave(VillageMineralAndBiodiversityPrDTO dto) {
        return dto;
    }

    protected VillageMineralAndBiodiversityPrDTO preHookSave(VillageMineralAndBiodiversityPrDTO dto) {
        return dto;
    }

    protected VillageMineralAndBiodiversityPrDTO postHookUpdate(VillageMineralAndBiodiversityPrDTO dto) {
        return dto;
    }

    protected VillageMineralAndBiodiversityPrDTO preHookUpdate(VillageMineralAndBiodiversityPrDTO VillageMineralAndBiodiversityPrDTO) {
        return VillageMineralAndBiodiversityPrDTO;
    }

    protected VillageMineralAndBiodiversityPrDTO postHookDelete(VillageMineralAndBiodiversityPrDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageMineralAndBiodiversityPrDTO postHookGetById(VillageMineralAndBiodiversityPrDTO dto) {
        return dto;
    }

    protected PageDTO<VillageMineralAndBiodiversityPrDTO> postHookGetAll(PageDTO<VillageMineralAndBiodiversityPrDTO> result) {
        return result;
    }




}
