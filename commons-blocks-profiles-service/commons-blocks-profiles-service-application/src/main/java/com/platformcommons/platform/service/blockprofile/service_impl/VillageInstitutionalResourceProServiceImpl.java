package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageInstitutionalResourceProService;


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

public class VillageInstitutionalResourceProServiceImpl implements VillageInstitutionalResourceProService {

    private final VillageInstitutionalResourceProDTOAssembler assembler;
    private final VillageInstitutionalResourceProRepository repository;


    private final NGOThematicAreaDTOAssembler assemblerNGOThematicArea;
    private final NGOThematicAreaRepository repositoryNGOThematicArea;


    public VillageInstitutionalResourceProServiceImpl(
        NGOThematicAreaDTOAssembler assemblerNGOThematicArea,  NGOThematicAreaRepository repositoryNGOThematicArea,
        VillageInstitutionalResourceProRepository repository, VillageInstitutionalResourceProDTOAssembler assembler) {
        this.assemblerNGOThematicArea = assemblerNGOThematicArea;
        this.repositoryNGOThematicArea = repositoryNGOThematicArea;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageInstitutionalResourcePro.
     *
     * @param VillageInstitutionalResourcePro the VillageInstitutionalResourcePro DTO to save
     * @return the saved VillageInstitutionalResourcePro DTO
     */
    @Transactional
    @Override
    public VillageInstitutionalResourceProDTO save(VillageInstitutionalResourceProDTO VillageInstitutionalResourcePro) {
        log.debug("Entry - save(VillageInstitutionalResourcePro={})", VillageInstitutionalResourcePro);
        VillageInstitutionalResourcePro = preHookSave(VillageInstitutionalResourcePro);
        VillageInstitutionalResourcePro entity = assembler.fromDTO(VillageInstitutionalResourcePro);
        VillageInstitutionalResourceProDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageInstitutionalResourcePro.
     *
     * @param VillageInstitutionalResourcePro the VillageInstitutionalResourcePro DTO to update
     * @return the updated VillageInstitutionalResourcePro DTO
     * @throws NotFoundException if the VillageInstitutionalResourcePro is not found
     */
    @Transactional
    @Override
    public VillageInstitutionalResourceProDTO update(VillageInstitutionalResourceProDTO VillageInstitutionalResourcePro) {
        log.debug("Entry - update(VillageInstitutionalResourcePro={})", VillageInstitutionalResourcePro);
        VillageInstitutionalResourcePro = preHookUpdate(VillageInstitutionalResourcePro);
        VillageInstitutionalResourcePro saved = repository.findById(VillageInstitutionalResourcePro.getId()).orElseThrow(() -> new NotFoundException("VillageInstitutionalResourcePro not found"));
        saved.update(assembler.fromDTO(VillageInstitutionalResourcePro));
        saved = repository.save(saved);
        VillageInstitutionalResourceProDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageInstitutionalResourcePros.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageInstitutionalResourcePro DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageInstitutionalResourceProDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageInstitutionalResourcePro> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageInstitutionalResourceProDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageInstitutionalResourceProDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageInstitutionalResourcePro by ID.
     *
     * @param id     the ID of the VillageInstitutionalResourcePro to delete
     * @param reason the reason for deletion
     * @return the deleted VillageInstitutionalResourcePro DTO
     * @throws NotFoundException if the VillageInstitutionalResourcePro is not found
     */
    @Transactional
    @Override
    public VillageInstitutionalResourceProDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageInstitutionalResourcePro saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageInstitutionalResourcePro not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageInstitutionalResourceProDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageInstitutionalResourcePro by ID.
     *
     * @param id the ID of the VillageInstitutionalResourcePro to retrieve
     * @return the VillageInstitutionalResourcePro DTO
     * @throws NotFoundException if the VillageInstitutionalResourcePro is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageInstitutionalResourceProDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageInstitutionalResourcePro saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageInstitutionalResourcePro not found"));
        VillageInstitutionalResourceProDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageInstitutionalResourceProDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageInstitutionalResourcePro> savedData = repository.findAllById(ids);
        Set<VillageInstitutionalResourceProDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of nGOThematicAreaList to a VillageInstitutionalResourcePro identified by their ID.
      *
      * @param id               The ID of the VillageInstitutionalResourcePro to add hobbies to
      * @param nGOThematicAreaList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addNGOThematicAreaToVillageInstitutionalResourcePro(Long id, List<NGOThematicAreaDTO> nGOThematicAreaList){
         VillageInstitutionalResourcePro saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageInstitutionalResourcePro not found"));
         if(nGOThematicAreaList != null && !nGOThematicAreaList.isEmpty()) {

             Set<NGOThematicArea> toBeSavedList = nGOThematicAreaList.stream().map(it-> assemblerNGOThematicArea.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setVillageInstitutionalResourcePro(saved));
             repositoryNGOThematicArea.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected VillageInstitutionalResourceProDTO postHookSave(VillageInstitutionalResourceProDTO dto) {
        return dto;
    }

    protected VillageInstitutionalResourceProDTO preHookSave(VillageInstitutionalResourceProDTO dto) {
        return dto;
    }

    protected VillageInstitutionalResourceProDTO postHookUpdate(VillageInstitutionalResourceProDTO dto) {
        return dto;
    }

    protected VillageInstitutionalResourceProDTO preHookUpdate(VillageInstitutionalResourceProDTO VillageInstitutionalResourceProDTO) {
        return VillageInstitutionalResourceProDTO;
    }

    protected VillageInstitutionalResourceProDTO postHookDelete(VillageInstitutionalResourceProDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageInstitutionalResourceProDTO postHookGetById(VillageInstitutionalResourceProDTO dto) {
        return dto;
    }

    protected PageDTO<VillageInstitutionalResourceProDTO> postHookGetAll(PageDTO<VillageInstitutionalResourceProDTO> result) {
        return result;
    }




}
