package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.HHInfrastructureAspirationService;


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

public class HHInfrastructureAspirationServiceImpl implements HHInfrastructureAspirationService {

    private final HHInfrastructureAspirationDTOAssembler assembler;
    private final HHInfrastructureAspirationRepository repository;


    private final HHInfrastructureAspirationDTOAssembler assemblerHHInfrastructureAspiration;
    private final HHInfrastructureAspirationRepository repositoryHHInfrastructureAspiration;


    public HHInfrastructureAspirationServiceImpl(
        HHInfrastructureAspirationDTOAssembler assemblerHHInfrastructureAspiration,  HHInfrastructureAspirationRepository repositoryHHInfrastructureAspiration,
        HHInfrastructureAspirationRepository repository, HHInfrastructureAspirationDTOAssembler assembler) {
        this.assemblerHHInfrastructureAspiration = assemblerHHInfrastructureAspiration;
        this.repositoryHHInfrastructureAspiration = repositoryHHInfrastructureAspiration;
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new HHInfrastructureAspiration.
     *
     * @param HHInfrastructureAspiration the HHInfrastructureAspiration DTO to save
     * @return the saved HHInfrastructureAspiration DTO
     */
    @Transactional
    @Override
    public HHInfrastructureAspirationDTO save(HHInfrastructureAspirationDTO HHInfrastructureAspiration) {
        log.debug("Entry - save(HHInfrastructureAspiration={})", HHInfrastructureAspiration);
        HHInfrastructureAspiration = preHookSave(HHInfrastructureAspiration);
        HHInfrastructureAspiration entity = assembler.fromDTO(HHInfrastructureAspiration);
        HHInfrastructureAspirationDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing HHInfrastructureAspiration.
     *
     * @param HHInfrastructureAspiration the HHInfrastructureAspiration DTO to update
     * @return the updated HHInfrastructureAspiration DTO
     * @throws NotFoundException if the HHInfrastructureAspiration is not found
     */
    @Transactional
    @Override
    public HHInfrastructureAspirationDTO update(HHInfrastructureAspirationDTO HHInfrastructureAspiration) {
        log.debug("Entry - update(HHInfrastructureAspiration={})", HHInfrastructureAspiration);
        HHInfrastructureAspiration = preHookUpdate(HHInfrastructureAspiration);
        HHInfrastructureAspiration saved = repository.findById(HHInfrastructureAspiration.getId()).orElseThrow(() -> new NotFoundException("HHInfrastructureAspiration not found"));
        saved.update(assembler.fromDTO(HHInfrastructureAspiration));
        saved = repository.save(saved);
        HHInfrastructureAspirationDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of HHInfrastructureAspirations.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing HHInfrastructureAspiration DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<HHInfrastructureAspirationDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<HHInfrastructureAspiration> result = repository.findAll(PageRequest.of(page, size));
        Set<HHInfrastructureAspirationDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<HHInfrastructureAspirationDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a HHInfrastructureAspiration by ID.
     *
     * @param id     the ID of the HHInfrastructureAspiration to delete
     * @param reason the reason for deletion
     * @return the deleted HHInfrastructureAspiration DTO
     * @throws NotFoundException if the HHInfrastructureAspiration is not found
     */
    @Transactional
    @Override
    public HHInfrastructureAspirationDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        HHInfrastructureAspiration saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHInfrastructureAspiration not found"));
        saved.deactivate(reason);
        repository.save(saved);
        HHInfrastructureAspirationDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a HHInfrastructureAspiration by ID.
     *
     * @param id the ID of the HHInfrastructureAspiration to retrieve
     * @return the HHInfrastructureAspiration DTO
     * @throws NotFoundException if the HHInfrastructureAspiration is not found
     */
    @Transactional(readOnly = true)
    @Override
    public HHInfrastructureAspirationDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        HHInfrastructureAspiration saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHInfrastructureAspiration not found"));
        HHInfrastructureAspirationDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<HHInfrastructureAspirationDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<HHInfrastructureAspiration> savedData = repository.findAllById(ids);
        Set<HHInfrastructureAspirationDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


 /**
      * Adds a list of hHInfrastructureAspirationList to a HHInfrastructureAspiration identified by their ID.
      *
      * @param id               The ID of the HHInfrastructureAspiration to add hobbies to
      * @param hHInfrastructureAspirationList  to be added
      * @since 1.0.0
      */
     @Transactional
     @Override
     public void addHHInfrastructureAspirationToHHInfrastructureAspiration(Long id, List<HHInfrastructureAspirationDTO> hHInfrastructureAspirationList){
         HHInfrastructureAspiration saved = repository.findById(id).orElseThrow(() -> new NotFoundException("HHInfrastructureAspiration not found"));
         if(hHInfrastructureAspirationList != null && !hHInfrastructureAspirationList.isEmpty()) {

             Set<HHInfrastructureAspiration> toBeSavedList = hHInfrastructureAspirationList.stream().map(it-> assemblerHHInfrastructureAspiration.fromDTO(it))
                     .collect(Collectors.toCollection(LinkedHashSet::new));
             toBeSavedList.forEach(it-> it.setHHInfrastructureAspiration(saved));
             repositoryHHInfrastructureAspiration.saveAll(toBeSavedList);

         }
     }

    /*Hooks to be overridden by subclasses*/
    protected HHInfrastructureAspirationDTO postHookSave(HHInfrastructureAspirationDTO dto) {
        return dto;
    }

    protected HHInfrastructureAspirationDTO preHookSave(HHInfrastructureAspirationDTO dto) {
        return dto;
    }

    protected HHInfrastructureAspirationDTO postHookUpdate(HHInfrastructureAspirationDTO dto) {
        return dto;
    }

    protected HHInfrastructureAspirationDTO preHookUpdate(HHInfrastructureAspirationDTO HHInfrastructureAspirationDTO) {
        return HHInfrastructureAspirationDTO;
    }

    protected HHInfrastructureAspirationDTO postHookDelete(HHInfrastructureAspirationDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHInfrastructureAspirationDTO postHookGetById(HHInfrastructureAspirationDTO dto) {
        return dto;
    }

    protected PageDTO<HHInfrastructureAspirationDTO> postHookGetAll(PageDTO<HHInfrastructureAspirationDTO> result) {
        return result;
    }




}
